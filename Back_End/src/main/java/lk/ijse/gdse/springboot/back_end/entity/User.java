package lk.ijse.gdse.springboot.back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // One-to-One mapping to CompanyProfile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CompanyProfile companyProfile;

}
