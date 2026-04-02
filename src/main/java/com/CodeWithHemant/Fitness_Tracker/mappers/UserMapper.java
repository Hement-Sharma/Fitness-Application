package com.CodeWithHemant.Fitness_Tracker.mappers;

import com.CodeWithHemant.Fitness_Tracker.entities.Activity;
import com.CodeWithHemant.Fitness_Tracker.entities.User;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityResponseDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserResponseDto;
import org.springframework.stereotype.Component;


public class UserMapper {

    public static User userRequestDtoToUser(UserRequestDto userRequestDto){

        User user = new User();

        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());

        return user;
    }

    public static UserResponseDto userToUserResponseDto(User user){

       UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setUpdatedAt(user.getUpdatedAt());

        return userResponseDto;
    }
}
