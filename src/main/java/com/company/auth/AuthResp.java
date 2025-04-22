package com.company.auth;

import lombok.Data;

@Data
public class AuthResp {

    private String gmail;
    private String token;

    public AuthResp(String gmail, String token) {
        this.gmail = gmail;
        this.token = token;
    }
}
