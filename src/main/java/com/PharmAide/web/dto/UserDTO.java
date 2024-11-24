package com.PharmAide.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable
{
    private String username;
    private String password;
    private String role;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String usertype;
}
