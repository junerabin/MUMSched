package edu.mum.mumsched;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import edu.mum.mumsched.controllers.EntryController;

@SpringBootApplication
public class MumschedApplication {

	public static void main(String[] args) {
		SpringApplication.run(MumschedApplication.class, args);
	}
}
