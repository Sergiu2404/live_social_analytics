package com.server.social_platform_server.controllers.authentication;

import com.server.social_platform_server.dto.auth_dto.LoginRequest;
import com.server.social_platform_server.dto.auth_dto.RegisterRequest;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.services.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        User user = authenticationService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok("User registered, please check your email to verify your account");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        String jwtToken = authenticationService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("User logged in successfully, token " + jwtToken);
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("token") String token){
        authenticationService.verifyAccount(token);
        return ResponseEntity.ok("Account verified successfully");
    }
}
