package com.server.social_platform_server.services;

import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        String encodedPassword = passwordEncoder.encode(password);
        return null;
    }
}
