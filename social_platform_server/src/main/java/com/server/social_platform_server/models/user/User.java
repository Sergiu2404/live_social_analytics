package com.server.social_platform_server.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

enum UserRoles{
    ADMIN, NORMAL
}

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private ZonedDateTime registrationDate;
    private LocalDate birthDate;
    private ZonedDateTime lastLoginDate;

    private String phoneNumber;
    private String profilePictureURL;

    @ManyToOne
    private UserLocation userLocation;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="current_location_id")
    private UserCurrentLocation userCurrentLocation;

    @Enumerated(EnumType.STRING)
    private UserRoles userRole;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
