package com.matrix.machineworld.datamodel;

import lombok.Data;

import java.util.List;

@Data
public class DRequest {
    private String email;
    private String userSecret;
    private String userName;
    private String programId;
    private String reason;
    private List<Integer> ids;


    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + userSecret + '\'' +
                ", programId=" + programId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
