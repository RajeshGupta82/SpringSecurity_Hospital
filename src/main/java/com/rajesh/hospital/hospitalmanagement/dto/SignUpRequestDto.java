package com.rajesh.hospital.hospitalmanagement.dto;

import java.util.Set;
import com.rajesh.hospital.hospitalmanagement.entity.type.Roletype;


public class SignUpRequestDto {
    

    private String userName;

    
    private String password;

    private String name;

    private Set<Roletype> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roletype> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roletype> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}   