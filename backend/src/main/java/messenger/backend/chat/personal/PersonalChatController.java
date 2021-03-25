package messenger.backend.chat.personal;

import lombok.RequiredArgsConstructor;
import messenger.backend.chat.personal.dto.CreatePersonalChatRequestDto;
import messenger.backend.chat.personal.dto.CreatePersonalChatResponseDto;
import messenger.backend.chat.personal.dto.DeletePersonalChatRequestDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor

@RestController
@RequestMapping("/api/chat/personal")
public class PersonalChatController {

    private final PersonalChatService personalChatService;

    @PostMapping("/create")
    public Response<CreatePersonalChatResponseDto> createPersonalChat(@Valid @RequestBody CreatePersonalChatRequestDto requestDto) {
        return Response.success(personalChatService.createPrivateChat(requestDto));
    }

    @PostMapping("/delete")
    public void deletePersonalChat(@Valid @RequestBody DeletePersonalChatRequestDto requestDto) {
        personalChatService.deletePersonalChat(requestDto);
    }

    @GetMapping("/all") //just for test todo delete this
    public Response<List<Map<String, String>>> getAllChats() {
        return Response.success(personalChatService.getAllChats());
    }

}
