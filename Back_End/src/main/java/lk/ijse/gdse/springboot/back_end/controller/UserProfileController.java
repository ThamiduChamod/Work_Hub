package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.UserProfileAboutDTO;
import lk.ijse.gdse.springboot.back_end.dto.UserProfileDetailsDTO;
import lk.ijse.gdse.springboot.back_end.dto.UserProfileExperienceDTO;
import lk.ijse.gdse.springboot.back_end.service.UserPostService;
import lk.ijse.gdse.springboot.back_end.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final UserPostService userPostService;


    @PostMapping("/updateProfileDetails")
    public APIResponse updateUserProfileDetails(@RequestBody UserProfileDetailsDTO userProfileDetailsDTO){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.updateOrSveUserProfile(userProfileDetailsDTO)
        );

    }

    @PostMapping("/updateProfileAbout")
    public APIResponse updateUserProfileAbout(@RequestBody UserProfileAboutDTO userProfileAboutDTO){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.updateOrSveUserProfileAbout(userProfileAboutDTO)
        );

    }

    @PostMapping("/updateProfileExperience")
    public APIResponse updateUserProfileExperience(@RequestBody UserProfileExperienceDTO userProfileExperienceDTO){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.updateOrSveUserProfileExperience(userProfileExperienceDTO)
        );

    }

    @GetMapping("/getUserProfile")
    public APIResponse getUserProfile(Authentication authentication){
        return new APIResponse(
                200,
                "update Details",
                userProfileService.getProfile(authentication.getName())
        );

    }

    @GetMapping("/getFollowingList")
    public APIResponse getFollowingList(Authentication authentication){
        return new APIResponse(
                200,
                "getFollowingList",
                userPostService.getFollowedCompanies(authentication.getName())
        );
    }

}
