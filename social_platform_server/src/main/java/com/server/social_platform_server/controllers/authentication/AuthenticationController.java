package com.server.social_platform_server.controllers.authentication;

import com.server.social_platform_server.dto.auth_dto.LoginRequest;
import com.server.social_platform_server.dto.auth_dto.RegisterRequest;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
        User user = authenticationService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("User logged in successfully");
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("token") String token){
        authenticationService.verifyAccount(token);
        return ResponseEntity.ok("Account verified successfully");
    }
}
