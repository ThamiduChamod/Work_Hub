package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfileCardDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.Followers;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.FollowRepository;
import lk.ijse.gdse.springboot.back_end.repository.JobPostRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

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

    public List<ProfileCardDTO> getAllProfiles(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        List<ProfileCardDTO> profileCard = companyProfileRepository.findProfileCard();
        List<Followers> follow = followRepository.findAllByUser(user.get());

        List<ProfileCardDTO> profileCardDTOS = new ArrayList<>();
        for (ProfileCardDTO profiles : profileCard) {
            for (Followers followers : follow) {
                if (profiles.getId() == followers.getId()){
                    profiles.setProfileImagePath(imagePath.getBase64FromFile(profiles.getProfileImagePath()));
                    profileCardDTOS.add(profiles);

                }
            }
        }
        return profileCardDTOS;
    }
}
