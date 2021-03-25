package messenger.backend.message;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.exceptions.ChatNotFoundException;
import messenger.backend.chat.general.GeneralChatRepository;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.message.dto.MessageResponseDto;
import messenger.backend.message.dto.SendMessageRequestDto;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserChatRepository userChatRepository;

    public List<MessageResponseDto> getAllByChat(UUID chatId) {
        var currentUserId = JwtTokenService.getCurrentUserId();
        var chat = userChatRepository
                .findByUserIdAndChatId(currentUserId, chatId)
                .map(UserChat::getChat)
                .orElseThrow(ChatNotFoundException::new);

        return chat.getUserChats()
                .stream()
                .flatMap(c -> c.getMessageEntities().stream())
                .map(MessageResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public MessageResponseDto sendMessage(SendMessageRequestDto requestDto) {
        var currentUserId = JwtTokenService.getCurrentUserId();
        var userChat = userChatRepository
                .findByUserIdAndChatId(currentUserId, requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        var message = MessageEntity.builder()
                .messageBody(requestDto.getText())
                .userChat(userChat)
                .build();
        messageRepository.save(message);

        return MessageResponseDto.fromEntity(message);
    }
}
