package org.scd.model.dto;

import java.util.Date;

public class UserLocationUpdateDTO {
    private String latitude;
    private String longitude;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public UserLocationUpdateDTO() {
    }

    public UserLocationUpdateDTO(String latitude, String longitude, Date date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }
}
