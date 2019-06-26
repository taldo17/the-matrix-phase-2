package com.matrix.machineworld.datamodel;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Program {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String purpose;
    private LocalDate creationDate;
    private LocalDate lastActive;
    private double memoryConsumption;
    private double cpuUsage;
    private String creator;
    private ProgramCategory category;
    private int markedForDeletionCounter;
    private boolean active;


    @Override
    public String toString() {
        String presentationId = "";
        if(id != 0){
            presentationId = String.valueOf(id);
        }
        return "Program{" +
                "name='" + name + '\'' +
                ", purpose='" + purpose + '\'' +
                ", creator='" + creator + '\'' +
                ", category=" + category +
                ", cpu usage='" + cpuUsage + '\'' +
                ", memory consumption='" + memoryConsumption + '\'' +
                ", id='" + presentationId + '\'' +
                '}';
    }
}
