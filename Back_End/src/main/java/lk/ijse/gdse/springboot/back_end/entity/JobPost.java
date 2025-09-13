package lk.ijse.gdse.springboot.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String companyLogoInitials;
    private String jobTitle;
    private String location;
    private String experienceRequired;
    private String salaryRange;
    private String jobType;
    private String workMode;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    private String jobImagePath;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "username") // foreign key
    private User user;
}
