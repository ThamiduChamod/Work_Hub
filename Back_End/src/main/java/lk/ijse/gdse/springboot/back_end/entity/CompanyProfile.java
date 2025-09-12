package lk.ijse.gdse.springboot.back_end.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String tagline;
    private String industry;
    private String followersCount;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(columnDefinition = "TEXT")
    private String mission;

    @Column(columnDefinition = "TEXT")
    private String vision;

    @Column(columnDefinition = "TEXT")
    private String locations;

    private String profileImagePath;
    private String bannerImagePath;

        // One-to-One Relationship with User

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;


    public CompanyProfile(String banner, String companyName, String industry, String locations, String mission, String overview, String profile, String tagline, String vision, int id) {
    }
}

