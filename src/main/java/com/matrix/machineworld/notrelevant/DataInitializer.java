package com.matrix.machineworld.notrelevant;

import com.matrix.machineworld.datamodel.Program;
import com.matrix.machineworld.datamodel.ProgramCategory;
import com.matrix.machineworld.repository.ProgramsRepository;
import org.fluttercode.datafactory.impl.DataFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DataInitializer {

    private ProgramsRepository programsRepository;
    private DataFactory dataFactory = new DataFactory();

    public DataInitializer(ProgramsRepository programsRepository) {
        this.programsRepository = programsRepository;
    }

    public void insertRandomData(int amount) {
        for (int i = 1; i <= amount; i++) {
            insertProgram();
        }
    }



    private Program insertProgram() {
        Program program = new Program();
        program.setName(dataFactory.getFirstName() + " " + dataFactory.getLastName());
        program.setCreator(dataFactory.getFirstName() + " " + dataFactory.getLastName());
        LocalDate creationDate = DateUtils.asLocalDate(dataFactory.getBirthDate());
        program.setCreationDate(creationDate);
        Date lastActivationDate = dataFactory.getDateBetween(DateUtils.asDate(creationDate), DateUtils.asDate(LocalDate.now()));
        program.setLastActive(DateUtils.asLocalDate(lastActivationDate));
        ProgramCategory programCategory = dataFactory.getItem(ProgramCategory.values(), 80, ProgramCategory.values()[0]);
        program.setCategory(programCategory);
        program.setPurpose(dataFactory.getRandomWord(30, 100));
        program.setMemoryConsumption(dataFactory.getNumberBetween(1, 100));
        program.setActive(true);
        Program persistProgram = programsRepository.insert(program);
        return persistProgram;
    }

    public static class DateUtils {
        public static Date asDate(LocalDate localDate) {
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }

        public static Date asDate(LocalDateTime localDateTime) {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }

        public static LocalDate asLocalDate(Date date) {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        }

        public static LocalDateTime asLocalDateTime(Date date) {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }
}