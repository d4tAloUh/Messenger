package messenger.backend.chat.general;


import lombok.RequiredArgsConstructor;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.chat.group.GroupChatService;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat/general")
public class GeneralChatController {

    private final GeneralChatService generalChatService;

    @GetMapping("/all")
    public Response<List<GeneralChatResponseDto>> getAllChats() {
        return Response.success(generalChatService.getAll());
    }
}
