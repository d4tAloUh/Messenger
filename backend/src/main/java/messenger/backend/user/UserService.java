package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.AuthService;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.chat.group.GroupChatRepository;
import messenger.backend.chat.personal.PersonalChatRepository;
import messenger.backend.message.MessageService;
import messenger.backend.refreshToken.RefreshTokenRepository;
import messenger.backend.sockets.SocketSender;
import messenger.backend.sockets.SubscribedOn;
import messenger.backend.user.dto.ChangePasswordRequestDto;
import messenger.backend.user.dto.ChangeUsernameResponseDto;
import messenger.backend.user.dto.UpdateProfileRequestDto;
import messenger.backend.user.dto.UserSearchInfoDto;
import messenger.backend.user.exceptions.IncorrectPasswordException;
import messenger.backend.user.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PersonalChatRepository personalChatRepository;
    private final GroupChatRepository groupChatRepository;
    private final MessageService messageService;
    private final SocketSender socketSender;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService authService;

    public UserSearchInfoDto getUserSearchInfo(String username) {
        return userRepository.getByUsername(username)
                .map(UserSearchInfoDto::from)
                .orElseThrow(UserNotFoundException::new);
    }

    // TODO delete
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateProfile(UpdateProfileRequestDto requestDto) {
        UserEntity contextUser = JwtTokenService.getContextUser();
        contextUser.setFullName(requestDto.getFullName());
        contextUser.setBio(requestDto.getBio());
        userRepository.saveAndFlush(contextUser);

        personalChatRepository.findAllByUserId(contextUser.getId())
                .forEach(personalChat -> { // todo optimize?
                    UUID targetUserId = personalChat.getUserChats().stream()
                            .map(c -> c.getUser().getId())
                            .filter(userId -> !userId.equals(contextUser.getId()))
                            .findFirst()
                            .orElseThrow();
                    GeneralChatResponseDto response = GeneralChatResponseDto.fromPrivateEntity(
                            personalChat,
                            messageService.getLastMessageByChatId(personalChat.getId()),
                            targetUserId
                    );
                    socketSender.send(
                            SubscribedOn.UPDATE_CHAT,
                            targetUserId,
                            response
                    );
                });

        groupChatRepository.findAllByUserId(contextUser.getId())
                .forEach(groupChat -> {
                    groupChat.getUserChats().forEach(userChat -> {
                        socketSender.send(
                                SubscribedOn.UPDATE_MESSAGES_USERNAME,
                                userChat.getUser().getId(),
                                new ChangeUsernameResponseDto(contextUser.getId(), contextUser.getFullName()));
                    });
                });
    }

    public AuthResponseDto changeUserPassword(ChangePasswordRequestDto requestDto) {
        UserEntity contextUser = JwtTokenService.getContextUser();
        boolean isPasswordsMatches = passwordEncoder.matches(requestDto.getOldPassword(), contextUser.getPassword());
        if (!isPasswordsMatches) throw new IncorrectPasswordException();

        refreshTokenRepository.deleteAllByUserEntityId(contextUser.getId());
        contextUser.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.saveAndFlush(contextUser);
        return authService.buildAuthResponse(contextUser);
    }
}
