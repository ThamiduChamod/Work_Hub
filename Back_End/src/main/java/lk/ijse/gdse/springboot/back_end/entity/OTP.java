package lk.ijse.gdse.springboot.back_end.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    @Id
    private String email;
    private String otp;
    private LocalDateTime time;
}
