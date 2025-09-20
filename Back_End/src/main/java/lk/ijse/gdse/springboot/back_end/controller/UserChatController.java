package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.ChatDTO;
import lk.ijse.gdse.springboot.back_end.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserChatController {
    private final ChatService chatService;

    @GetMapping("/searchCompany")
    public APIResponse searchCompany(@RequestParam int jobId){
        return new APIResponse(
                200,
                "send Company",
                        chatService.findCompanyByJobId(jobId)
                );
    }

    @GetMapping("/searchChat")
    public APIResponse searchChat(@RequestParam int companyProfile, String userName){
        return new APIResponse(
                200,
                "get chatData",
                chatService.getChatData(companyProfile,userName)
        );
    }

    @PostMapping("/saveMessages")
    public APIResponse saveMessages(@RequestBody ChatDTO chatDTO){
        System.out.println(chatDTO.getMessage());
        System.out.println(chatDTO.getId());
        return new APIResponse(
                200,
                "send",
                chatService.saveMessage(chatDTO)
        );
    }


}
