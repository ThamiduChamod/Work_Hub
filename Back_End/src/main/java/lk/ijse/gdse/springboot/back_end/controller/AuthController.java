package lk.ijse.gdse.springboot.back_end.controller;


import jakarta.security.auth.message.AuthStatus;
import lk.ijse.gdse.springboot.back_end.dto.APIResponse;
import lk.ijse.gdse.springboot.back_end.dto.AuthDTO;
import lk.ijse.gdse.springboot.back_end.dto.UserDto;
import lk.ijse.gdse.springboot.back_end.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(@RequestBody UserDto userDto) {
        System.out.println("ssss");
         return ResponseEntity.ok(
                 new APIResponse(
                         200,
                         "USER Register Successfully",
                         authService.register(userDto)
                 )
         );
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new APIResponse(200, "OK", authService.authenticate(authDTO)));
    }
}
