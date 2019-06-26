package com.matrix.machineworld.service;

import com.matrix.machineworld.datamodel.DRequest;
import com.matrix.machineworld.datamodel.DResponse;
import com.matrix.machineworld.datamodel.Program;

public interface MachineService {
    int insert(Program program);

    Program getProgram(int programId);

    DResponse deactivate(DRequest dRequest);

    DResponse delete(DRequest dRequest);

    DResponse deletePrograms(DRequest dRequest);
}
