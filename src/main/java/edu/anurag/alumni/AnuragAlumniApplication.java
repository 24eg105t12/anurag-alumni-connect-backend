package edu.anurag.alumni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnuragAlumniApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnuragAlumniApplication.class, args);
        System.out.println("==================================================");
        System.out.println("🎓 Anurag Alumni Connect Backend Started on :8080");
        System.out.println("Context path: /api/v1");
        System.out.println("==================================================");
    }
}
