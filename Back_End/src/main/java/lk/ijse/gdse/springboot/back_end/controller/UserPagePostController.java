package lk.ijse.gdse.springboot.back_end.controller;

import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.JobPostDTO;
import lk.ijse.gdse.springboot.back_end.service.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserPagePostController {

    private final UserPostService userPostService;

    @GetMapping("/allPost")
    public APIResponse allPost( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
// Should accept org.springframework.data.domain.Pageable
        List<JobPostDTO> posts = userPostService.getAllPost(pageable);

        return new APIResponse(
                200,
                "Send All posts",
                posts
        );
    }


}
