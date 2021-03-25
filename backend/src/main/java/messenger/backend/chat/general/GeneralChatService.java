package messenger.backend.chat.general;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.chat.group.GroupChatRepository;
import messenger.backend.user.UserRepository;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class GeneralChatService {

    private final GeneralChatRepository generalChatRepository;
    private final UserChatRepository userChatRepository;

    public List<GeneralChatResponseDto> getAll() {
        var currentUserId = JwtTokenService.getCurrentUserId();
        return generalChatRepository
                .findAllByMemberId(currentUserId)
                .stream()
                .map(chat -> GeneralChatResponseDto.fromEntity(chat, currentUserId))
                .collect(Collectors.toList());
    }
}
