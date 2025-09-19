package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.FollowersDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.Followers;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.entity.UserProfile;
import lk.ijse.gdse.springboot.back_end.repository.CompanyProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.FollowRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserProfileRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final UserProfileRepository userProfileRepository;

    public boolean addFollower(FollowersDTO followersDTO) {
        User user = userRepository.findUserByUsername(followersDTO.getUserName());

        Optional<CompanyProfile> byId = companyProfileRepository.findById(followersDTO.getCompany_id());
        if (byId.isEmpty()) return false;

        UserProfile userProfile = userProfileRepository.findAllByUser(user);


        try {
            Followers followers = new Followers();
            followers.setUser(userProfile);
            followers.setCompany(byId.get());
            followRepository.save(followers);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
