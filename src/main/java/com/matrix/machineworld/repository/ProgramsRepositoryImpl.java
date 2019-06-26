package com.matrix.machineworld.repository;

import com.matrix.machineworld.datamodel.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProgramsRepositoryImpl implements ProgramsRepository {
    Logger logger = LoggerFactory.getLogger(ProgramsRepositoryImpl.class);

    final ProgramCrudRepository programCrudRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProgramsRepositoryImpl(ProgramCrudRepository programCrudRepository) {
        this.programCrudRepository = programCrudRepository;
    }

    @Override
    public Program insert(Program program) {
        return programCrudRepository.save(program);
    }

    @Override
    public Program getProgram(int programId) {
        return tryFindingInDB(programId).orElse(null);
    }

    @Retryable
    private Optional<Program> tryFindingInDB(int programId) {
        return programCrudRepository.findById(programId);
    }

    @Override
    public void setInactive(String id) {
        jdbcTemplate.update("UPDATE program set active = 'false' WHERE ID = " + id);
    }

    @Override
    public void delete(String programId) {
        jdbcTemplate.update("DELETE FROM program WHERE ID = " + programId);
    }

    @Override
    public void deletePrograms(List<Integer> ids) {
        for (Integer id : ids) {
            delete(String.valueOf(id));
        }
    }
}
