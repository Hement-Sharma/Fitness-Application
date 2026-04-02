package com.CodeWithHemant.Fitness_Tracker.controllers;

import com.CodeWithHemant.Fitness_Tracker.paylods.JwtAuthResponse;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserLoginDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserResponseDto;
import com.CodeWithHemant.Fitness_Tracker.security.JwtTokenHelper;
import com.CodeWithHemant.Fitness_Tracker.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor  //this will only create the constructor for field that is final.
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenHelper jwtTokenHelper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto){
      UserResponseDto savedUserResponseDto = userService.register(userRequestDto);
      return new ResponseEntity<>(savedUserResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> userLogin(@Valid @RequestBody UserLoginDto userLoginDto){
        Authentication authentication = authenticationManager
                                         .authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUserName(),userLoginDto.getPassword()));
        String token = null;

        if(authentication.isAuthenticated()){
            token = jwtTokenHelper.generateToken(userLoginDto.getUserName());
        }

        return new ResponseEntity<>(new JwtAuthResponse(token),HttpStatus.OK);
    }
}
