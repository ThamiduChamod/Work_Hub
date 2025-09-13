package lk.ijse.gdse.springboot.back_end.controller;


import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin
@RequiredArgsConstructor
public class CompanyMainPageController {

    private final CompanyService companyService;

    @GetMapping("getProfile")
    public APIResponse getProfile(Authentication authentication) {
        CompanyProfile all = companyService.getAll(authentication.getName());
        System.out.println(all.getBannerImagePath());
        return new APIResponse(
                200,
                "get all details",
                all
        );
    }
}
