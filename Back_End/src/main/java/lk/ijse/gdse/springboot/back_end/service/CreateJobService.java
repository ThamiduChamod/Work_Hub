package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.CreateJobDTO;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.JobPostRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateJobService {

    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;
    private final ModelMapper modelMapper;

    public String postJob(CreateJobDTO createJobDTO) {
        Optional<User> byUsername = userRepository.findByUsername(createJobDTO.getUserName());
        if (byUsername.isEmpty()) return "cant find user";
        String savedImagePath = saveImage(createJobDTO.getJobImagePath());
        if (savedImagePath == null) return "cant save image";
        createJobDTO.setJobImagePath(savedImagePath);


        try {
//            System.out.println(createJobDTO);
//            JobPost jobPost = modelMapper.map(createJobDTO, JobPost.class);
            JobPost jobPostSaved = new JobPost();

            jobPostSaved.setJobTitle(createJobDTO.getJobTitle());
            jobPostSaved.setAddress(createJobDTO.getAddress());
            jobPostSaved.setLocation(createJobDTO.getLocation());
            jobPostSaved.setExperienceRequired(createJobDTO.getExperienceRequired());
            jobPostSaved.setSalaryRange(createJobDTO.getSalaryRange());
            jobPostSaved.setJobType(createJobDTO.getJobType());
            jobPostSaved.setWorkMode(createJobDTO.getWorkMode());
            jobPostSaved.setSkills(createJobDTO.getSkills());
            jobPostSaved.setJobDescription(createJobDTO.getJobDescription());
            jobPostSaved.setJobImagePath(savedImagePath);
            jobPostSaved.setCreatedAt(createJobDTO.getCreatedAt());
            jobPostSaved.setUser(byUsername.get());
            jobPostRepository.save(jobPostSaved);
//            jobPostRepository.save(new JobPost(
//                    createJobDTO.getJobTitle(),
//                    createJobDTO.getAddress(),
//                    createJobDTO.getLocation(),
//                    createJobDTO.getExperienceRequired(),
//                    createJobDTO.getSalaryRange(),
//                    createJobDTO.getJobType(),
//                    createJobDTO.getWorkMode(),
//                    createJobDTO.getSkills(),
//                    createJobDTO.getJobDescription(),
//                    createJobDTO.getJobImagePath(),
//                    createJobDTO.getCreatedAt(),
//                    byUsername.get()
//            ));
             return "job save success";
        } catch (Exception e) {
            return "cant save job";
        }
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

}
