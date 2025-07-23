package com.server.social_platform_server.models.user;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name="user_current_location")
public class UserCurrentLocation {
    @Id
    @GeneratedValue
    private Long id;
    private Double latitude;
    private Double longitude;

    private ZonedDateTime timestamp;

    @OneToOne(mappedBy = "userCurrentLocation")
    private User user;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
