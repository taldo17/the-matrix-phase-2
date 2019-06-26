package com.matrix.machineworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = MachineWorldApplication.class)
public class MachineWorldApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @LocalServerPort
    int localServerPort;


    @Test
    public void negativeId() throws Exception {
        mockMvc.perform(get("/program/-1"))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void notANumberId() throws Exception {
        mockMvc.perform(get("/program/a"))
                .andExpect(status().isBadRequest());
    }

}
