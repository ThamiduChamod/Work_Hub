package lk.ijse.gdse.springboot.back_end.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String profileName;
    private String bannerImage;
    private String profileImage;
    private String title;
    private String address;
    @Column(length = 2000)
    private String about;
    private String education;
    private String contact;
    private String experience;
    private String skills;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    @JsonBackReference
    private User user;
}
