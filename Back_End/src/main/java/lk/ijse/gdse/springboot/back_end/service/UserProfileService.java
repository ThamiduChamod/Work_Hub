package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.UserProfileAboutDTO;
import lk.ijse.gdse.springboot.back_end.dto.UserProfileDetailsDTO;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.entity.UserProfile;
import lk.ijse.gdse.springboot.back_end.repository.UserProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;

    public String updateOrSveUserProfile (UserProfileDetailsDTO userProfileDetailsDTO) {
       User byUsername = userRepository.findUserByUsername(userProfileDetailsDTO.getUserName());
       if (byUsername == null) return "Can't find user";


        UserProfile allByUser = userProfileRepository.findAllByUser(byUsername);

        if (allByUser == null) {
            try {
                UserProfile map = new UserProfile();
                map.setProfileName(userProfileDetailsDTO.getProfileName());
                map.setProfileImage(imagePath.saveImage(userProfileDetailsDTO.getProfileImage()));
                map.setAddress(userProfileDetailsDTO.getAddress());
                map.setTitle(userProfileDetailsDTO.getTitle());
                map.setBannerImage(imagePath.saveImage(userProfileDetailsDTO.getBannerImage()));
                map.setUser(byUsername); // important: set the user relation
                userProfileRepository.save(map);
                return "User profile saved successfully";
            }catch (Exception e) {
                return "Can't Save user profile";
            }
        }
        try {
            allByUser.setProfileName(userProfileDetailsDTO.getProfileName());
            allByUser.setProfileImage(imagePath.saveImage(userProfileDetailsDTO.getProfileImage()));
            allByUser.setAddress(userProfileDetailsDTO.getAddress());
            allByUser.setTitle(userProfileDetailsDTO.getTitle());
            allByUser.setBannerImage(imagePath.saveImage(userProfileDetailsDTO.getBannerImage()));
            userProfileRepository.save(allByUser);

            return "User profile updated successfully";
        }catch (Exception e) {
            return "Can't update user profile";
        }
    }

    public String updateOrSveUserProfileAbout(UserProfileAboutDTO dto) {
        User byUsername = userRepository.findUserByUsername(dto.getUserName());
        if (byUsername == null) return "Can't find user";


        UserProfile allByUser = userProfileRepository.findAllByUser(byUsername);
        System.out.println(allByUser);
        if (allByUser == null) {
            try {
                UserProfile map = new UserProfile();
                map.setAbout(dto.getAbout());
                map.setEducation(dto.getEducation());
                map.setContact(dto.getContact());
                map.setUser(byUsername); // important: set the user relation
                userProfileRepository.save(map);
                return "User profile saved successfully";
            }catch (Exception e) {
                return "Can't Save user profile";
            }
        }
        try {
            allByUser.setAbout(dto.getAbout());
            allByUser.setEducation(dto.getEducation());
            allByUser.setContact(dto.getContact());
            userProfileRepository.save(allByUser);

            return "User profile updated successfully";
        }catch (Exception e) {
            return "Can't update user profile";
        }
    }
}
