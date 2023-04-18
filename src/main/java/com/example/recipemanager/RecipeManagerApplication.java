package com.example.recipemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.example.recipemanager.*")
@EnableJpaRepositories(basePackages = "com.example.recipemanager.*")
@SpringBootApplication   
@EnableTransactionManagement
@EntityScan(basePackages="com.example.recipemanager.*")
public class RecipeManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeManagerApplication.class, args);
    }

}
