package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

//import static jdk.internal.jrtfs.JrtFileAttributeView.AttrID.extension;


@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyProfileRepository companyProfileRepository;
    private final UserRepository userRepository;

    public String saveOrUpdate(CompanyProfileDTO companyProfileDTO) {
        User user = userRepository.findIdByUsername(companyProfileDTO.getMali());

        if (user == null) {
            return "cannot find user";
        }
        String banner= saveImage(companyProfileDTO.getBannerImage());
        String profile = saveImage(companyProfileDTO.getProfileImage());
        if (banner == null) {
            return "cannot save image banner";
        }
        if (profile == null) {
            return "cannot save image banner";
        }


        CompanyProfile company = companyProfileRepository.findByuser(user);

        if (company != null) {
            company.setBannerImagePath(banner);
            company.setCompanyName(companyProfileDTO.getCompanyName());
            company.setIndustry(companyProfileDTO.getIndustry());
//            company.setLocations(String.valueOf(companyProfileDTO.getLocations()));
            company.setMission(companyProfileDTO.getMission());
            company.setOverview(companyProfileDTO.getOverview());
            company.setProfileImagePath(profile);
            company.setTagline(companyProfileDTO.getTagline());
            company.setVision(companyProfileDTO.getVision());

            try {
                companyProfileRepository.save(company);
            } catch (RuntimeException e) {
                return "can't update company profile";
            }
        }
        try {
//            companyProfileRepository.save(new CompanyProfile(
//                    banner,
//                    companyProfileDTO.getCompanyName(),
//                    companyProfileDTO.getIndustry(),
////                    String.valueOf(companyProfileDTO.getLocations()),
//                    companyProfileDTO.getMission(),
//                    companyProfileDTO.getOverview(),
//                    profile,
//                    companyProfileDTO.getTagline(),
//                    companyProfileDTO.getVision(),
//                    user.getId()
//
//            ));
        } catch (RuntimeException e) {
            return "can't save company profile";
        }


    return "can't find user Id";

    }


    public String saveImage (String jsonImageString){

        String base64Image = jsonImageString.split(",")[1]; // Remove "data:image/png;base64,"

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        String filename = "uploads/company_" + System.currentTimeMillis() ;

        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(imageBytes);
            System.out.println("Image saved to file system: " + filename);
            return filename;
        } catch (IOException e) {
           return null;
        }

    }
}
