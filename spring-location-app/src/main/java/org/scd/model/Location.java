package org.scd.model;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    @NotNull
    private Date date;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Location() {
    }

    public Location(String latitude, String longitude, Date date, User user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = new Date();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
