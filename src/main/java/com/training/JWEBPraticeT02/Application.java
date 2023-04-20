package com.training.JWEBPraticeT02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return new MyRunner();
    }

    public class MyRunner implements CommandLineRunner{
        @Autowired
        ApplicationContext applicationContext;
        public void run(String... args) throws Exception{
            /*String[] names = applicationContext.getBeanDefinitionNames();
            for(String string : names)
            {
                System.err.println(string);
            }*/
        }
    }
}
