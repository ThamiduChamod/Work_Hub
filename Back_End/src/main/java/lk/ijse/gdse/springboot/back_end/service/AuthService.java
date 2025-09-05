package lk.ijse.gdse.springboot.back_end.service;

import lk.ijse.gdse.springboot.back_end.dto.AuthDTO;
import lk.ijse.gdse.springboot.back_end.dto.AuthResponseDTO;
import lk.ijse.gdse.springboot.back_end.dto.OtpDTO;
import lk.ijse.gdse.springboot.back_end.dto.UserDto;
import lk.ijse.gdse.springboot.back_end.entity.Role;
import lk.ijse.gdse.springboot.back_end.entity.User;
import lk.ijse.gdse.springboot.back_end.repository.UserRepository;
import lk.ijse.gdse.springboot.back_end.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public  String register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(Role.valueOf(userDto.getRole()))
                    .build();
            userRepository.save(user);
            return "User Register Success";
        }
        throw new RuntimeException("Username already exists");
    }

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        Optional<User> byUsername = Optional.ofNullable(userRepository.findByUsername(authDTO.getUsername()).orElseThrow(() -> new RuntimeException("UAER name note found")));

        if (!passwordEncoder.matches(authDTO.getPassword(), byUsername.get().getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtUtil.generateToken(authDTO.getUsername());
        return new AuthResponseDTO(token);

    }

    public void sendOTP(OtpDTO otpDTO){

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
        simpleMailMessage.setText("Your OTP is: " + random.nextInt(1000)+100+ "\nThis code will expire in 1 minutes.");

        mailSender.send(simpleMailMessage);
    }
}
