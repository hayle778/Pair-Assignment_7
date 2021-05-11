package com.meritamerica.assignment6.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ContextConfiguration
class SpringSecurityJwtApplication {

    public class MeritAmericaBankApp extends SpringSecurityJwtApplication{

        public static void main(String[] args) {
//        MeritBank.readFromFile("src/main/MeritBank_TestFile.txt");
//        MeritBank.writeToFile("src/main/MeritBank_TestWrite.txt");

            SpringApplication.run(MeritAmericaBankApp.class, args);
            SpringApplication.run(SpringSecurityJwtApplication.class, args);


        }
    }
}