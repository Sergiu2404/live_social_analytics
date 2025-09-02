package com.server.social_platform_server.controllers.authentication;

import com.server.social_platform_server.dto.auth_dto.LoginRequest;
import com.server.social_platform_server.dto.auth_dto.RegisterRequest;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.services.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> response = new HashMap<>();

        response.put("message", "User " + user.getEmail() + " registered succesfully");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        String jwtToken = authenticationService.loginUser(request.getEmail(), request.getPassword());
        Map<String, String> response = new HashMap<>();

        response.put("message", "User logged in succesfully");
        response.put("token", jwtToken);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("token") String token){
        authenticationService.verifyAccount(token);
        return ResponseEntity.ok("Account verified successfully");
    }
}
