package com.CodeWithHemant.Fitness_Tracker.services;

import com.CodeWithHemant.Fitness_Tracker.entities.Role;
import com.CodeWithHemant.Fitness_Tracker.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

     @Autowired
     RoleRepo roleRepo;

    public Role saveRole(Role role){
       return roleRepo.save(role);
    }

    public Optional<Role> getRoleById(int i) {
     return roleRepo.findById(i);
    }
}
