package com.project2.BankingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.project2") //we need this to scan for Spring Beans
@EnableJpaRepositories("com.project2.daos") //register our DAO as a bean (since it is an interface)
@EntityScan("com.project2") //register our model classes as DB entities
public class BankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);

	}
}
