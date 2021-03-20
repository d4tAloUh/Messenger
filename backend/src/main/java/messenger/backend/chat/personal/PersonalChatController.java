package messenger.backend.chat.personal;

import lombok.RequiredArgsConstructor;
import messenger.backend.chat.personal.dto.CreatePersonalChatRequestDto;
import messenger.backend.chat.personal.dto.CreatePersonalChatResponseDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor

@RestController
@RequestMapping("/api/chat/personal")
public class PersonalChatController {

    private final PersonalChatService personalChatService;

    @PostMapping("/create")
    public Response<CreatePersonalChatResponseDto> createPersonalChat(@Valid @RequestBody CreatePersonalChatRequestDto requestDto) {
        return Response.success(personalChatService.createPrivateChat(requestDto));
    }

}
