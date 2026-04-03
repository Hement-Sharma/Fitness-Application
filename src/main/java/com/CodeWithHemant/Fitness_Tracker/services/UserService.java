package com.CodeWithHemant.Fitness_Tracker.services;

import com.CodeWithHemant.Fitness_Tracker.entities.*;
import com.CodeWithHemant.Fitness_Tracker.exceptions.ResourceNotFoundException;
import com.CodeWithHemant.Fitness_Tracker.mappers.UserMapper;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserResponse;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserResponseDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserUpdateDto;
import com.CodeWithHemant.Fitness_Tracker.repositories.ActivityRepo;
import com.CodeWithHemant.Fitness_Tracker.repositories.RecommendationRepo;
import com.CodeWithHemant.Fitness_Tracker.repositories.RoleRepo;
import com.CodeWithHemant.Fitness_Tracker.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserResponseDto register(UserRequestDto userRequestDto) {
       User user = UserMapper.userRequestDtoToUser(userRequestDto);
       List<Role> roles = userRequestDto.getRoleIds()
                             .stream().map(roleId -> roleRepo.findById(roleId)
                         .orElseThrow(()->new ResourceNotFoundException("Role","RoleId",roleId))).collect(Collectors.toList());
       user.setRoles(roles);
       User savedUser = userRepo.save(user);
       return UserMapper.userToUserResponseDto(user);
    }

    public UserResponse getAllUsers(Integer pageSize,Integer pageNum,String sortBy,String sortDir) {

        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }else {
            sort = Sort.by(sortBy); //by default ascending
        }

        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
       Page<User> pageaUsers = userRepo.findAll(pageable);
       List<User> users = pageaUsers.toList();
       List<UserResponseDto> userResponseDtos = users.stream().map(user -> UserMapper.userToUserResponseDto(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(userResponseDtos);
        userResponse.setPageNumber(pageaUsers.getNumber());
        userResponse.setPageSize(pageaUsers.getSize());
        userResponse.setTotalPages(pageaUsers.getTotalPages());
        userResponse.setTotalElements(pageaUsers.getTotalElements());
        userResponse.setLastPage(pageaUsers.isLast());

        return userResponse;
    }

    public UserResponseDto getSingleUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
       return UserMapper.userToUserResponseDto(user);
    }

    public UserResponseDto updateUser(UserUpdateDto userUpdateDto, String userId) {

       User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","UserId",userId));

       user.setEmail(userUpdateDto.getEmail());
       user.setPassword(userUpdateDto.getPassword());
       user.setFirstName(userUpdateDto.getFirstName());
       user.setLastName(userUpdateDto.getLastName());

       if(userUpdateDto.getRoleIds() != null){ //if user is sending roles then update role other wilse not

           if(userUpdateDto.getRoleIds().size() != 0) {

               List<Role> roles = userUpdateDto.getRoleIds()
                       .stream().map(roleId -> roleRepo.findById(roleId).
                               orElseThrow(() -> new ResourceNotFoundException("Role", "RoleId", roleId))).collect(Collectors.toList());
               user.setRoles(roles);

           }
       }

       User updatedUser = userRepo.save(user);

       return UserMapper.userToUserResponseDto(updatedUser);
    }

    public void deleteUser(String uId) {
       User user = userRepo.findById(uId).orElseThrow(()-> new ResourceNotFoundException("User","UserId",uId));
       userRepo.delete(user);
    }
}
