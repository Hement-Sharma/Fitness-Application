package com.CodeWithHemant.Fitness_Tracker;

import com.CodeWithHemant.Fitness_Tracker.entities.Activity;
import com.CodeWithHemant.Fitness_Tracker.entities.Role;
import com.CodeWithHemant.Fitness_Tracker.repositories.RoleRepo;
import com.CodeWithHemant.Fitness_Tracker.services.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class FitnessTrackerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FitnessTrackerApplication.class, args);
		RoleRepo roleRepo = context.getBean(RoleRepo.class);
		Role role1 = new Role();
		role1.setId(1);
		role1.setRole("ROLE_ADMIN");


		Role role2 = new Role();
		role2.setId(2);
		role2.setRole("ROLE_USER");

		roleRepo.findById(1).orElse(roleRepo.save(role1));
		roleRepo.findById(2).orElse(roleRepo.save(role2));
	}

	@Bean          //to convert java object to json data or vice versa
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}
}
