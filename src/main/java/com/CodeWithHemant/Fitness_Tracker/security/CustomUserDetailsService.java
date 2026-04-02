package com.CodeWithHemant.Fitness_Tracker.security;

import com.CodeWithHemant.Fitness_Tracker.entities.User;
import com.CodeWithHemant.Fitness_Tracker.exceptions.ResourceNotFoundException;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserPrinciple;
import com.CodeWithHemant.Fitness_Tracker.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
   private  UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","Email",username));
        return new UserPrinciple(user);
    }
}
