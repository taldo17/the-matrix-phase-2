package com.matrix.machineworld;

import com.matrix.machineworld.matrix.Drawing;
import com.matrix.machineworld.notrelevant.DataInitializer;
import com.matrix.machineworld.repository.ProgramsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

import javax.swing.*;
import java.net.URL;

@SpringBootApplication
@ComponentScan(basePackages = "com.matrix.machineworld.*")
@EnableRetry
public class MachineWorldApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(MachineWorldApplication.class, args);
        ProgramsRepository programsRepository = applicationContext.getBean(ProgramsRepository.class);
        DataInitializer dataInitializer = new DataInitializer(programsRepository);
        dataInitializer.insertRandomData(20);

        System.setProperty("java.awt.headless", "false");
        SwingUtilities.invokeLater(()->{
            showMatrixCode();
        });

    }
    private static void showMatrixCode() {
        JFrame jf = new JFrame("The matrix is all around us");
        URL url = MachineWorldApplication.class.getResource("/images/Matrix.png");
        ImageIcon imageIcon = new ImageIcon(url);
        jf.setIconImage(imageIcon.getImage());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(700,700);
        jf.setResizable(false);
        jf.add(new Drawing());
        jf.setVisible(true);
    }

}
