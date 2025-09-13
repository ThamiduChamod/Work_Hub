package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;

//import static jdk.internal.jrtfs.JrtFileAttributeView.AttrID.extension;


@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyProfileRepository companyProfileRepository;
    private final UserRepository userRepository;

    public String saveOrUpdate(CompanyProfileDTO companyProfileDTO) {
        System.out.println("companyProfileDTO: " + companyProfileDTO);
        System.out.println(companyProfileDTO);
        Optional <User> optionalUser = userRepository.findByUsername("thamiduchamod100@gmail.com");

        if (optionalUser.isEmpty()) {
            System.out.println("userwa hoyaganna bari una");
            return "cannot find user";
        }
        User user = optionalUser.get();

        String banner= saveImage(companyProfileDTO.getBannerImage());
        String profile = saveImage(companyProfileDTO.getProfileImage());
        if (banner == null) {
            System.out.println("image eka save une na");
            return "cannot save image banner";
        }
        if (profile == null) {
            return "cannot save image banner";
        }


        CompanyProfile company = companyProfileRepository.findByuser(user);
        System.out.println();
        if (company != null) {
            company.setBannerImagePath(banner);
            company.setCompanyName(companyProfileDTO.getCompanyName());
            company.setIndustry(companyProfileDTO.getIndustry());
            company.setLocations(companyProfileDTO.getLocations());
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

            CompanyProfile companyProfile = new CompanyProfile();
            companyProfile.setCompanyName(companyProfileDTO.getCompanyName());
            companyProfile.setTagline(companyProfileDTO.getTagline());
            companyProfile.setIndustry(companyProfileDTO.getIndustry());
            companyProfile.setOverview(companyProfileDTO.getOverview());
            companyProfile.setMission(companyProfileDTO.getMission());
            companyProfile.setVision(companyProfileDTO.getVision());
            companyProfile.setLocations(companyProfileDTO.getLocations());
            companyProfile.setProfileImagePath(profile);
            companyProfile.setBannerImagePath(banner);
            companyProfile.setUser(user);

            companyProfileRepository.save(companyProfile);
        } catch (RuntimeException e) {
            System.out.println("save une na");
            return "can't save company profile";
        }


    return "can't find user Id";

    }


    public String saveImage(String base64Data){
        if(base64Data == null || base64Data.isEmpty()) return null;

        // Ensure format: data:image/png;base64,xxxx
        String[] parts = base64Data.split(",");
        String imageData = parts.length > 1 ? parts[1] : parts[0];

        byte[] imageBytes = Base64.getDecoder().decode(imageData);
        String extension = "png"; // optionally detect from base64 prefix
        String filename = "uploads/company_" + System.currentTimeMillis() + "." + extension;

        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(imageBytes);
            System.out.println("Image saved to file system: " + filename);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CompanyProfile getAll(String userName) {
        Optional<User> userId = userRepository.findByUsername(userName);
        if(userId.isEmpty()) return null;
        User user = userId.get();
        System.out.println("user: " + user.getId());
        System.out.println("userNmae" + userName);

        CompanyProfile companyProfile = companyProfileRepository.findByuser(user);
        String profileImage = getBase64FromFile(companyProfile.getProfileImagePath());
        if (profileImage == null) return null;
        companyProfile.setProfileImagePath(profileImage);

        String bannerImage = getBase64FromFile(companyProfile.getBannerImagePath());
        if (bannerImage == null) return null;
        companyProfile.setBannerImagePath(bannerImage);

        return companyProfile;
    }


    public String getBase64FromFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File does not exist: " + filePath);
                return null;
            }

            // Read file bytes
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            // Encode to base64
            String base64 = Base64.getEncoder().encodeToString(fileBytes);

            // Optionally add data URI prefix (detect extension)
            String extension = "";
            int dotIndex = filePath.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = filePath.substring(dotIndex + 1);
            }

            return "data:image/" + extension + ";base64," + base64;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
