package com.um.appasistencias;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories
@SpringBootApplication
public class AppasistenciasApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(AppasistenciasApplication.class);

	@Value("${server.port}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(AppasistenciasApplication.class, args);
		System.out.println("\n--------------------------------\nAPLICACIÓN INICIALIZADA\n--------------------------------");
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("http://localhost:"+port);
	}
}