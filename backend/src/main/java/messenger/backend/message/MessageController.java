package messenger.backend.message;


import lombok.RequiredArgsConstructor;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.message.dto.MessageResponseDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/chat/{chatId}")
    public Response<List<MessageResponseDto>> getAllByChat(@PathVariable UUID chatId) {
        return Response.success(messageService.getAllByChat(chatId));
    }
}
