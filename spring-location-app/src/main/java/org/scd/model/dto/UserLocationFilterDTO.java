package org.scd.model.dto;

import java.util.Date;

public class UserLocationFilterDTO {

    private Date startDate;
    private Date endDate;

    public UserLocationFilterDTO() {
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
