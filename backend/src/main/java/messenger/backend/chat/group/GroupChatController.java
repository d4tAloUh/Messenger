package messenger.backend.chat.group;


import lombok.RequiredArgsConstructor;
import messenger.backend.chat.group.dto.CreateGroupChatRequestDto;
import messenger.backend.chat.group.dto.CreateGroupChatResponseDto;
import messenger.backend.chat.group.dto.DeleteGroupChatRequestDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/chat/group")
public class GroupChatController {

    private final GroupChatService groupChatService;

    @PostMapping("/create")
    public Response<CreateGroupChatResponseDto> createGroupChat(@Valid @RequestBody CreateGroupChatRequestDto requestDto) {
        return Response.success(groupChatService.createGroupChat(requestDto));
    }

    @PostMapping("/delete")
    public void deleteGroupChat(@Valid @RequestBody DeleteGroupChatRequestDto requestDto) {
        groupChatService.deleteGroupChat(requestDto);
    }

}
