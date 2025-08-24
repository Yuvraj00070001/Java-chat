package com.YuvrajSingh.chatApp.dto;

public class UserDTO {
    private String userid;
    private char[] password;

    public UserDTO(String userid, char[] password) {
        this.userid = userid;
        this.password = (password == null) ? null : password.clone();
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public char[] getPassword() {
        return (password == null) ? null : password.clone();
    }
    public void setPassword(char[] password) {
        this.password = (password == null) ? null : password.clone();
    }
}
