package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.ChatDTO;
import lk.ijse.gdse.springboot.back_end.dto.CompanyProfileDTO;
import lk.ijse.gdse.springboot.back_end.entity.Chat;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.entity.UserProfile;
import lk.ijse.gdse.springboot.back_end.repository.*;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final JobPostRepository jobPostRepository;
    private final ModelMapper modelMapper;
    private final ImagePath imagePath;
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public CompanyProfileDTO findCompanyByJobId(int jobId) {
        JobPost bId = jobPostRepository.findById(jobId);
        CompanyProfile profile = companyProfileRepository.findCompanyProfileByUser(bId.getUser());
        profile.setProfileImagePath(imagePath.getBase64FromFile(profile.getProfileImagePath()));

        return modelMapper.map(profile, CompanyProfileDTO.class);


    }

    public ChatDTO getChatData(int jobId, String userId) {
        CompanyProfile companyProfile = companyProfileRepository.findById(jobId);

        UserProfile userProfile = userProfileRepository.findAllByUser(userRepository.findUserByUsername(userId));

        Chat chat = chatRepository.findByUserProfileAndCompanyProfile(userProfile, companyProfile);

//        return modelMapper.map(chat, ChatDTO.class);
        if (chat == null) {
            Chat newChat = new Chat();
                newChat.setCompanyProfile(companyProfile);
                newChat.setUserProfile(userProfile);
            Chat save = chatRepository.save(newChat);
            return modelMapper.map(save, ChatDTO.class);
        }

        return new ChatDTO(
                chat.getId(),
                chat.getUserProfile().getId(),
                chat.getUserProfile().getId(),
                chat.getMessage(),
                chat.getImage()
        );

    }

    public ChatDTO saveMessage(ChatDTO dto) {
        Chat chat = chatRepository.findById(dto.getId());
        if (chat.getMessage() == null) {
            chat.setMessage(dto.getMessage());
            chatRepository.save(chat);
        }
        String messages = chat.getMessage();
        messages+=dto.getMessage();
        chat.setMessage(messages);
        Chat save = chatRepository.save(chat);

        return modelMapper.map(save, ChatDTO.class);
    }
}
