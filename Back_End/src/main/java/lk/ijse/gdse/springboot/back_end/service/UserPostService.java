package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfileCardDTO;
import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.*;
import lk.ijse.gdse.springboot.back_end.repository.*;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

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
        // 1. User
        User user = userRepository.findUserByUsername(userName);
        if (user == null) return Collections.emptyList();

        // 2. UserProfile
        UserProfile userProfile = userProfileRepository.findAllByUser(user);
        if (userProfile == null) return Collections.emptyList();

        // 3. Companies followed by user
        List<Followers> followedList = followRepository.findAllByUser(userProfile);

        // 4. Make a set of followed company IDs
        Set<Long> followedCompanyIds = followedList.stream()
                .map(f -> f.getCompany().getId())
                .collect(Collectors.toSet());

        // 5. All company cards
        List<ProfileCardDTO> profileCards = companyProfileRepository.findProfileCard();
        List<ProfileCardDTO> profileCardDTOS = new ArrayList<>();

        // 6. Filter: add only companies NOT followed by user
        for (ProfileCardDTO profileCard : profileCards) {
            if (!followedCompanyIds.contains(profileCard.getId())) {
                profileCard.setProfileImagePath(imagePath.getBase64FromFile(profileCard.getProfileImagePath()));
                profileCardDTOS.add(profileCard);
            }
        }

        return profileCardDTOS;
    }


}
