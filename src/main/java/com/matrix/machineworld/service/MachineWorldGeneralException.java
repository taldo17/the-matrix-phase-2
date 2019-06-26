package com.matrix.machineworld.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class MachineWorldGeneralException extends RuntimeException {
    public MachineWorldGeneralException(Exception e) {
        this.initCause(e);
    }
}
