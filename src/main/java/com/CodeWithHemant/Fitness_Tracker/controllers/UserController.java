package com.CodeWithHemant.Fitness_Tracker.controllers;

import com.CodeWithHemant.Fitness_Tracker.paylods.*;
import com.CodeWithHemant.Fitness_Tracker.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection
    ){
      UserResponse userResponse = userService.getAllUsers(pageSize,pageNo,sortBy,sortDirection);
      return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> getSingleUser(@PathVariable String userId){
      UserResponseDto userResponseDto = userService.getSingleUserById(userId);
      return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto,
                                                      @PathVariable String userId){
       UserResponseDto updatedUserResponeDto = userService.updateUser(userUpdateDto,userId);
       return ResponseEntity.ok(updatedUserResponeDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String uId){
       userService.deleteUser(uId);
       return ResponseEntity.ok(new ApiResponse("User Deleted Successfully",true));
    }
}
