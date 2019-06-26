package com.matrix.machineworld;

import com.matrix.machineworld.datamodel.DRequest;
import com.matrix.machineworld.datamodel.Program;
import com.matrix.machineworld.repository.ProgramsRepository;
import com.matrix.machineworld.service.MachineService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MachineWorldApplication.class)
public class MachineWorldTest {

    @Autowired
    MachineService machineService;
    @Autowired
    ProgramsRepository programsRepository;


    @Test
    public void insertedProgramExistsInTheDB() {
        String uniqueName = "Very Unique Name";
        Program program = createProgramObject(uniqueName);
        int programId = machineService.insert(program);
        Program insertedProgram = machineService.getProgram(programId);
        Assert.assertEquals(uniqueName, insertedProgram.getName());
    }

    @Test
    public void deactivatingAnActiveProgram() {
        Program program = createProgramObject("unique");
        int programId = machineService.insert(program);
        DRequest dRequest = createDRequest(programId);
        machineService.deactivate(dRequest);
        Program fetchedProgram = machineService.getProgram(programId);
        Assert.assertFalse(fetchedProgram.isActive());
    }

    @Test
    public void deletingAProgram() {
        Program program = createProgramObject("unique");
        int programId = machineService.insert(program);
        DRequest dRequest = createDRequest(programId);
        machineService.delete(dRequest);
        Program fetchedProgram = machineService.getProgram(programId);
        Assert.assertNull(fetchedProgram);
    }

    @Test
    public void getProgramThatDoesNotExists() {
        Program fetchedProgram = machineService.getProgram(Integer.MAX_VALUE);
        Assert.assertNull(fetchedProgram);
    }

    private Program createProgramObject(String uniqueName) {
        Program program = new Program();
        program.setName(uniqueName);
        program.setCreator("Creator");
        program.setPurpose("Purpose");
        program.setActive(true);
        return program;
    }

    private DRequest createDRequest(int programId) {
        DRequest dRequest = new DRequest();
        dRequest.setUserName("taldo");
        dRequest.setUserSecret("Password1");
        dRequest.setProgramId(String.valueOf(programId));
        return dRequest;
    }
}
