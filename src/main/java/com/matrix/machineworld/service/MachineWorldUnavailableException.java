package com.matrix.machineworld.service;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "DB unavailable")
public class MachineWorldUnavailableException extends RuntimeException {
    public MachineWorldUnavailableException(DataAccessException e) {
        this.initCause(e);
    }
}
