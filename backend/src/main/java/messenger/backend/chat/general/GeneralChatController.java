package messenger.backend.chat.general;


import lombok.RequiredArgsConstructor;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.chat.general.dto.LastSeenResponseDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat/general")
public class GeneralChatController {

    private final GeneralChatService generalChatService;

    @GetMapping("/all")
    public Response<List<GeneralChatResponseDto>> getAllChats() {
        return Response.success(generalChatService.getAll());
    }

    @GetMapping("/seen")
    public Response<List<LastSeenResponseDto>> getSeenAt() {
        return Response.success(generalChatService.getSeenAt());
    }

    @PostMapping("/read/{chatId}")
    public Response<Date> read(@PathVariable UUID chatId) {
        return Response.success(generalChatService.read(chatId));
    }
}
