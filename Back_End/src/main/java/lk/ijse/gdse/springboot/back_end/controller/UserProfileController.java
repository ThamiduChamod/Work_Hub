package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.UserProfileDetailsDTO;
import lk.ijse.gdse.springboot.back_end.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/updateProfileDetails")
    public APIResponse updateUserProfileDetails(@RequestBody UserProfileDetailsDTO userProfileDetailsDTO){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.updateOrSveUserProfile(userProfileDetailsDTO)
        );

    }

}
