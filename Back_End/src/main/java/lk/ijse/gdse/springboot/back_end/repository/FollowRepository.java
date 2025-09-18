package lk.ijse.gdse.springboot.back_end.repository;

import lk.ijse.gdse.springboot.back_end.entity.Followers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Followers, Long> {



}
