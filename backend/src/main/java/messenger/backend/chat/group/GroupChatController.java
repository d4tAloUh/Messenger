package messenger.backend.chat.group;


import lombok.RequiredArgsConstructor;
import messenger.backend.chat.group.dto.*;
import messenger.backend.user.dto.UserSearchInfoDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/add-member")
    public void addMemberToChat(@Valid @RequestBody AddMemberToGroupChatRequestDto requestDto) {
        groupChatService.addMemberToChat(requestDto);
    }

    @PostMapping("/remove-member")
    public void removeMemberFromChat(@Valid @RequestBody RemoveMemberFromGroupChatRequestDto requestDto) {
        groupChatService.removeMemberFromChat(requestDto);
    }


    //just for test todo delete this (or no)
    @GetMapping("/users-list")
    public Response<List<UserSearchInfoDto>> getChatUsersList(@RequestParam(name = "chatId") UUID chatId) {
        return Response.success(groupChatService.getChatUsersList(chatId));
    }

}
