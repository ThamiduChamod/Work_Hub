package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import lk.ijse.gdse.springboot.back_end.repository.JobPostRepository;
import lk.ijse.gdse.springboot.back_end.util.ImagePath;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostService {

    private final JobPostRepository jobPostRepository;
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
}
