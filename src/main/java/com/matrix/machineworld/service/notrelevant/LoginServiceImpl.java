package com.matrix.machineworld.service.notrelevant;

import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResponse login(String userName, String userSecret) {
        return new LoginResponse();
    }
}
