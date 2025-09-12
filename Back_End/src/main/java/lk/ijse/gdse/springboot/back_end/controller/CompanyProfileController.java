package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin
@RequiredArgsConstructor
//@PreAuthorize("hasRole('COMPANY')")
public class CompanyProfileController {

    private final CompanyService companyService;
    @PostMapping("/update")
    public APIResponse updateCompanyProfile() {

        System.out.println("wadaaaaaaa");
        return new APIResponse(
                200,
                "update profile",
                "qqw"
//                companyService.saveOrUpdate(companyProfileDTO)

        );

    }



}
