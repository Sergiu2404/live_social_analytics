package com.server.social_platform_server.services;

import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.models.user.UserRoles;
import com.server.social_platform_server.models.verification_token.VerificationToken;
import com.server.social_platform_server.repositories.UserRepository;
import com.server.social_platform_server.repositories.VerificationTokenRepository;
import com.server.social_platform_server.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    private boolean checkAllCharactersSameType(String password){
        char[] characters = password.toCharArray();
        char firstCharacter = characters[0];

        for(int i = 1; i < characters.length; i++)
            if(characters[i] != firstCharacter)
                return false;

        return true;
    }
    private double checkPasswordSafety(String password){
        if(password == null || password.equals(""))
        {
            return 0;
        }
        if(password.length() < 5 || checkAllCharactersSameType(password)){
            return 0.2;
        }

        boolean hasLower = false, hasUpper = false, hasDigit = false, hasSpecial = false;
        for(char character : password.toCharArray()){
            if(Character.isLowerCase(character)) hasLower = true;
            else if(Character.isUpperCase(character)) hasUpper = true;
            else if(Character.isDigit(character)) hasDigit = true;
            else hasSpecial = true;
        }

        int score = 0;
        if(hasLower) score++;
        if(hasUpper) score++;
        if(hasDigit) score++;
        if(hasSpecial) score++;

        if(password.length() >= 12 && score >= 3)
            return 1.0;
        else if(password.length() >= 10 && score >= 3)
            return 0.8;
        else if(password.length() >= 8 && score >= 2)
            return 0.6;
        else if(password.length() >= 6)
            return 0.4;

        return 0.2;
    }

    public User registerUser(String username, String email, String password){
        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("An account with this email already exists");
        }
        if(username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()){
            throw new IllegalArgumentException("Please fill all the fields");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setEmail(email);
        newUser.setUserRole(UserRoles.USER);
        newUser.setEnabled(false);
        User savedUser = userRepository.save(newUser);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        verificationTokenRepository.save(verificationToken);

        //emailService.send...
        return savedUser;
    }

    public String loginUser(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email address"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Incorrect password");
        }
//        if(!user.isEnabled()){
//            throw new IllegalStateException("Account not verified, please check firstly your email");
//        }

        return jwtUtil.generateToken(user.getEmail(), user.getUserRole());
    }

    public void verifyAccount(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        if(verificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Verification token has expired");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);
    }
}
