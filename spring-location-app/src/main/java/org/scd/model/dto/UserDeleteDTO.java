package org.scd.model.dto;

public class UserDeleteDTO {
    private long id;
    private String email;



    public UserDeleteDTO(String email) {
        this.email = email;
    }

    public UserDeleteDTO(long id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDeleteDTO(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
