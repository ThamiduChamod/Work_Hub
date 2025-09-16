package lk.ijse.gdse.springboot.back_end.repository;

import lk.ijse.gdse.springboot.back_end.dto.ProfilePhotoNameDTO;
import lk.ijse.gdse.springboot.back_end.entity.CompanyProfile;
import lk.ijse.gdse.springboot.back_end.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {

   CompanyProfile findByuser(User user);

   @Query("SELECT c FROM CompanyProfile c WHERE c.user.id = :id")
   CompanyProfile findProfileImagePathAndCompanyNameByUserId(int id);

}
