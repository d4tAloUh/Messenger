package messenger.backend.chat.personal;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.chat.exceptions.ChatNotFoundException;
import messenger.backend.chat.exceptions.UserNotMemberOfChatException;
import messenger.backend.chat.personal.dto.CreatePersonalChatRequestDto;
import messenger.backend.chat.personal.dto.CreatePersonalChatResponseDto;
import messenger.backend.chat.personal.dto.DeletePersonalChatRequestDto;
import messenger.backend.chat.personal.exceptions.PersonalChatAlreadyExistsException;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import messenger.backend.user.exceptions.UserNotFoundException;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor

@Service
public class PersonalChatService {

    private final UserRepository userRepository;
    private final PersonalChatRepository personalChatRepository;
    private final UserChatRepository userChatRepository;

    //todo decide what to do when you create a chat with yourself
    public CreatePersonalChatResponseDto createPrivateChat(CreatePersonalChatRequestDto requestDto) {
        UserEntity contextUser = JwtTokenService.getContextUser();
        UserEntity targetUser = userRepository.getByUsername(requestDto.getTargetUsername())
                .orElseThrow(UserNotFoundException::new);

        checkIfPersonalChatExists(contextUser, targetUser);

        PrivateChatEntity personalChat = new PrivateChatEntity();

        personalChat = personalChatRepository.saveAndFlush(personalChat);

        UserChat contextUserChat = UserChat.builder()
                .user(contextUser)
                .chat(personalChat)
                .permissionLevel(UserChat.PermissionLevel.MEMBER)
                .build();
        UserChat targetUserChat = UserChat.builder()
                .user(targetUser)
                .chat(personalChat)
                .permissionLevel(UserChat.PermissionLevel.MEMBER)
                .build();
        userChatRepository.saveAndFlush(contextUserChat);
        userChatRepository.saveAndFlush(targetUserChat);

        CreatePersonalChatResponseDto response = new CreatePersonalChatResponseDto();
        response.setChatId(personalChat.getId());
        return response;
    }

    private void checkIfPersonalChatExists(UserEntity contextUser, UserEntity targetUser) {
       personalChatRepository.findByMembers(contextUser, targetUser)
               .ifPresent(chat -> {throw new PersonalChatAlreadyExistsException();});
    }

    public void deletePersonalChat(DeletePersonalChatRequestDto requestDto) {

        PrivateChatEntity privateChatEntity = personalChatRepository
                .findByIdWithFetch(requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();
        boolean isUserMemberOfChat = privateChatEntity.getUserChats().stream()
                .anyMatch(chat -> chat.getUser().getId().equals(contextUser.getId()));
        if(!isUserMemberOfChat) throw new UserNotMemberOfChatException();

        personalChatRepository.delete(privateChatEntity);
    }

    public List<Map<String, String>> getAllChats() {
        return personalChatRepository.findAll().stream()
                .map(personalChat -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", personalChat.getId().toString());

                    return map;
                })
                .collect(Collectors.toList());
    }
}
