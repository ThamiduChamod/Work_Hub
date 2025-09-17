package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.JobPostRepository;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;

    public List<JobPostDTO> getAllPost(Pageable pageable) {
        Page<JobPost> all = jobPostRepository.findAll(pageable);



        List<JobPostDTO> jobPostDTOS = all.getContent().stream()


                .map(post -> {
                    JobPostDTO dto = modelMapper.map(post, JobPostDTO.class);
                    dto.setUsername(post.getUser().getId());
                    dto.setJobImagePath(imagePath.getBase64FromFile(post.getJobImagePath()));
                    return dto;
                })
                .toList();


        return jobPostDTOS;
    }


    public ProfilePhotoNameDTO getProfilePitcherAndName(int userId) {
        System.out.println("run");
        CompanyProfile companyProfile  = companyProfileRepository.findProfileImagePathAndCompanyNameByUserId(userId);

        System.out.println(companyProfile);
        return new ProfilePhotoNameDTO(
                companyProfile.getCompanyName(),
                imagePath.getBase64FromFile(companyProfile.getProfileImagePath())
        );


    }
}
