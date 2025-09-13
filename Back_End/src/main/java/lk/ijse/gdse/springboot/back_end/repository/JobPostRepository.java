package lk.ijse.gdse.springboot.back_end.repository;

import lk.ijse.gdse.springboot.back_end.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

}
