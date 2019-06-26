package com.matrix.machineworld.controllers;

import com.matrix.machineworld.datamodel.DRequest;
import com.matrix.machineworld.datamodel.DResponse;
import com.matrix.machineworld.datamodel.Program;
import com.matrix.machineworld.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProgramController {

    @Autowired
    MachineService machineService;

    @GetMapping("/program/{id}")
    public Program get(@PathVariable int id) {
        return machineService.getProgram(id);
    }

    @PostMapping("/program")
    public int insert(@RequestBody Program program) {
        return machineService.insert(program);
    }

    @PostMapping("/program/deactivate")
    public DResponse deactivate(@RequestBody DRequest
                                                   dRequest){
        return machineService.deactivate(dRequest);
    }
    @PostMapping("/program/delete")
    public DResponse delete(@RequestBody DRequest dRequest){
        return machineService.delete(dRequest);
    }

    @PostMapping("/programs/delete")
    public void deletePrograms(@RequestBody DRequest dRequest) {
        machineService.deletePrograms(dRequest);
    }


}
