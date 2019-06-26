package com.matrix.machineworld.repository;

import com.matrix.machineworld.datamodel.Program;

import java.util.List;

public interface ProgramsRepository {
    Program insert(Program program);
    Program getProgram(int programId);
    void setInactive(String id);

    void delete(String programId);

    void deletePrograms(List<Integer> ids);
}
