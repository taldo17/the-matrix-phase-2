package com.matrix.machineworld.service;

import com.matrix.machineworld.datamodel.DRequest;
import com.matrix.machineworld.datamodel.DResponse;
import com.matrix.machineworld.datamodel.Program;
import com.matrix.machineworld.repository.ProgramsRepository;
import com.matrix.machineworld.service.notrelevant.LoginResponse;
import com.matrix.machineworld.service.notrelevant.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class MachineServiceImpl implements MachineService {
    private Logger logger = LoggerFactory.getLogger(MachineServiceImpl.class);

    private final ProgramsRepository programsRepository;
    private LoginService loginService;


    @Autowired
    public MachineServiceImpl(ProgramsRepository programsRepository, LoginService loginService) {
        this.programsRepository = programsRepository;
        this.loginService = loginService;
    }


    @Override
    public int insert(Program program) {

        try {
            validateProgram(program);
            logger.info("inserting program: " + program.toString());
            Program savedProgram = programsRepository.insert(program);
            logger.info("inserted program: " + program.toString());
            return savedProgram.getId();
        }
        catch (DataAccessException e) {
            logger.error("a data access error occurred", e);
            throw new MachineWorldGeneralException(e);
        }
        catch (Exception e) {
            logger.error("an error occurred while trying to insert a program", e);
            throw new MachineWorldGeneralException(e);
        }
    }

    @Override
    public Program getProgram(int programId) {
        Program program = new Program();
        try {
            validateId(programId);
            logger.info("fetching program with program id: " + programId);
            program = programsRepository.getProgram(programId);
            if (program != null) {
                logger.info("fetched program: " + program.toString());
            }
        }
        catch (DataAccessException e) {
            logger.error("an error occurred while accessing the DB", e);
            throw new MachineWorldUnavailableException(e);
        }
        catch (Exception e) {
            logger.error("an error occurred while trying to get a program", e);
            throw new MachineWorldGeneralException(e);
        }
        return program;
    }

    @Override
    public DResponse deactivate(DRequest dRequest) {
        DResponse dResponse = new DResponse();
        try {
            validateDR(dRequest);
            tryToLogin(dRequest.toString(),
                    dRequest.getUserName(),
                    dRequest.getUserSecret());
            logger.info("setting program +" +
                    dRequest.getProgramId() + " inactive");
            programsRepository.setInactive(dRequest.getProgramId());
            dResponse.setSucceeded(true);
            dResponse.setActionReport("deactivated successfully");
            logger.info(" program +" +
                    dRequest.getProgramId() + " is deactivated");
        }
        catch (IllegalAccessException e) {
            logger.error("failed to login in order to perform deactivation");
            throw new MachineWorldUnauthorizedException(e);
        }
        catch (DataAccessException e) {
            logger.error("failed to access the DB", e);
            throw new MachineWorldUnavailableException(e);
        }
        catch (Exception e) {
            logger.error("failed to deactivate the program", e);
            throw new MachineWorldGeneralException(e);
        }

        return dResponse;
    }

    @Override
    public DResponse delete(DRequest dRequest) {
        DResponse dResponse = new DResponse();
        try {
            validateDR(dRequest);
            tryToLogin(dRequest.toString(),
                    dRequest.getUserName(),
                    dRequest.getUserSecret());
            logger.info("deleting program +" +
                    dRequest.getProgramId());
            programsRepository.delete(dRequest.getProgramId());
            dResponse.setSucceeded(true);
            dResponse.setActionReport("deleted successfully");
            logger.info("program +" +
                    dRequest.getProgramId() + " is deleted");
        }
        catch (IllegalAccessException e) {
            logger.error("failed to login in order to perform deletion");
            throw new MachineWorldUnauthorizedException(e);
        }
        catch (DataAccessException e) {
            logger.error("failed to access the DB", e);
            throw new MachineWorldUnavailableException(e);
        }
        catch (Exception e) {
            logger.error("failed to delete the program, e");
            throw new MachineWorldGeneralException(e);
        }
        return dResponse;
    }

    @Override
    public DResponse deletePrograms(DRequest dRequest) {
        DResponse dResponse = new DResponse();
        try {
            validateDR(dRequest);
            tryToLogin(dRequest.toString(),
                    dRequest.getUserName(),
                    dRequest.getUserSecret());
            logger.info("deleting " + dRequest.getIds().size() + " programs");
            programsRepository.deletePrograms(dRequest.getIds());
            dResponse.setSucceeded(true);
            dResponse.setActionReport("deleted successfully");
            logger.info("deleting " + dRequest.getIds().size() + " programs successfully");
        }
        catch (IllegalAccessException e) {
            logger.error("failed to login in order to perform deletion", e);
            throw new MachineWorldUnauthorizedException(e);
        }
        catch (DataAccessException e) {
            logger.error("failed to access the DB", e);
            throw new MachineWorldUnavailableException(e);
        }
        catch (Exception e) {
            logger.error("failed to delete the program", e);
            throw new MachineWorldGeneralException(e);
        }
        return dResponse;
    }

    private void tryToLogin(String requestString, String userName, String userSecret) throws IllegalAccessException {
        logger.info("performing login for deactivation request: " +
                requestString);
        LoginResponse loginResponse = loginService.login(userName,
                userSecret);
        if (loginResponse == null) {
            throw new IllegalAccessException("login failed");
        }
        logger.info("logged in successfully");
    }

    private void validateDR(DRequest dRequest) {
        if ((dRequest.getEmail() == null
                || dRequest.getEmail().isEmpty())
                && (null == dRequest.getUserName() ||
                dRequest.getUserName().isEmpty())) {
            throw new IllegalArgumentException("Both email and user name are empty");
        }
        if (null == dRequest.getUserSecret()
                || dRequest.getUserSecret().isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }
        if (null == dRequest.getProgramId() && null == dRequest.getIds()) {
            throw new IllegalArgumentException("identifiers are not valid");
        }
    }

    private void validateId(int programId) {
        if (programId <= 0) {
            throw new IllegalArgumentException("Program id must be positive");
        }
    }


    private void validateProgram(Program program) {
        if (program.getName() == null || program.getName().isEmpty()) {
            throw new ValidationException("The Name cannot be empty");
        }
        if (program.getCreator() == null || program.getCreator().isEmpty()) {
            throw new ValidationException("The creator cannot be empty");
        }
        if (program.getPurpose() == null || program.getPurpose().isEmpty()) {
            throw new ValidationException("The purpose cannot be empty");
        }
        if (program.getCpuUsage() > 30) {
            throw new ValidationException("cpu is over 30");
        }

        if (program.getMemoryConsumption() > 30) {
            throw new ValidationException("memory is over 30");
        }
    }
}
