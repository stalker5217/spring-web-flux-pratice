package com.stalker5217.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@SpringBootApplication
public class SpringWebFluxPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxPracticeApplication.class, args);
	}

}
