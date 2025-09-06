package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.*;
import lk.ijse.gdse.springboot.back_end.entity.OTP;
import lk.ijse.gdse.springboot.back_end.entity.Role;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.OTPRepository;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Random random;
    private final SimpleMailMessage simpleMailMessage;
    private final JavaMailSenderImpl mailSender;
    private final OTPRepository otpRepository;

    public  AuthResponseDTO register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(Role.valueOf(userDto.getRole()))
                    .build();
            userRepository.save(user);
            String token = jwtUtil.generateToken(userDto.getUsername());
            return new AuthResponseDTO(token);
        }
        throw new RuntimeException("Username already exists");
    }

    public AuthResponseDTO authenticate(AuthDTO authDTO) {

        Optional<User> byUsername = Optional.ofNullable(userRepository.findByUsername(authDTO.getUsername()).orElseThrow(() -> new RuntimeException("USER name note found")));

        if (!passwordEncoder.matches(authDTO.getPassword(), byUsername.get().getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtUtil.generateToken(authDTO.getUsername());
        return new AuthResponseDTO(token);

    }

    public String sendOTP(OtpDTO otpDTO){

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("thamiduchamod748@gmail.com");
        mailSender.setPassword("rrqa lwse vwuw kdgp"); // Gmail App Password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");


        System.out.println(otpDTO.getEmail());
        simpleMailMessage.setTo(otpDTO.getEmail());
        simpleMailMessage.setSubject("Your OTP Code");
        String otp = String.valueOf(random.nextInt(10000)+1000);
        simpleMailMessage.setText("Your OTP is: " +otp+"\nThis code will expire in 1 minutes.");

        try {
            mailSender.send(simpleMailMessage);

            otpRepository.save(new OTP(otpDTO.getEmail(),otp, LocalDateTime.now()));

            return "OTP Send Success";

        } catch (RuntimeException e) {
            return "Can't send OTP" ;
        }

    }

    public String validateOTP(ValidateOTPDTO validateOTPDTO){
        try {
            OTP otp = otpRepository.findByEmail(validateOTPDTO.getEmail());

            if (otp.getOtp().equals(validateOTPDTO.getOtp())){

                Duration duration = Duration.between(otp.getTime(), validateOTPDTO.getTime());
                if (duration.toMinutes() <= 1){
                    return "OTP is valid";
                }
                return "End Time";
            }
            return "Invalid OTP";
        } catch (RuntimeException e) {
            return "Can't find email";
        }

    }

}
