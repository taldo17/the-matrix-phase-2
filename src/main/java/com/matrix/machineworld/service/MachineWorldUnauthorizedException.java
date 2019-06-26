package com.matrix.machineworld.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "login for authorized action failed")
public class MachineWorldUnauthorizedException extends RuntimeException {

    public MachineWorldUnauthorizedException(IllegalAccessException e) {
        this.initCause(e);

    }
}
