package com.goldenawards;

import com.goldenawards.services.FileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class AwardsApplication implements CommandLineRunner {

	@Autowired
	private FileReaderService fileReaderService;

	public static void main(String[] args) {
		SpringApplication.run(AwardsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileReaderService.readFile(null);
	}

}
