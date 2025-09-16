package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.JobPostRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyPostService {

    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CompanyProfileRepository companyProfileRepository;

    public List<JobPostDTO> getAllPostByUserName(String name) {
        User userByUsername = userRepository.findUserByUsername(name);
        if (userByUsername == null) return null;

        System.out.println(userByUsername.getId());
//        return null;

        try {
            List<JobPost> byUserUsername = jobPostRepository.findByUser_Id(userByUsername.getId());
            List<JobPostDTO> posts = byUserUsername.stream()
                    .map(post -> {
                        JobPostDTO dto = modelMapper.map(post, JobPostDTO.class);
                        dto.setUsername(post.getUser().getId()); // extra field
                        dto.setJobImagePath(getBase64FromFile(post.getJobImagePath()));
                        return dto;
                    })
                    .toList();
            return posts;
        }catch (Exception e){
            return null;
        }

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

    public Object getProfilePitcherAndName(int userName) {
        System.out.println("run");
        CompanyProfile companyProfile  = companyProfileRepository.findProfileImagePathAndCompanyNameByUserId(userName);

        System.out.println(companyProfile);
        return new ProfilePhotoNameDTO(
            companyProfile.getCompanyName(),
            getBase64FromFile(companyProfile.getProfileImagePath())
        );


    }
}
