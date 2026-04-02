package com.CodeWithHemant.Fitness_Tracker.controllers;

import com.CodeWithHemant.Fitness_Tracker.paylods.*;
import com.CodeWithHemant.Fitness_Tracker.services.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping("/activity")
    public ResponseEntity<ActivityResponseDto> createActivity(@Valid @RequestBody ActivityRequestDto activityRequestDto){
        ActivityResponseDto savedActivityResponseDto = activityService.createActivity(activityRequestDto);
        return new ResponseEntity<>(savedActivityResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/userActivities")
    public ResponseEntity<List<ActivityResponseDto>> getUserActivities(
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection,
            @RequestHeader(value = "X-USER-ID") String userId){
        return ResponseEntity.ok(activityService.getUserActivities(userId,pageSize,pageNo,sortBy,sortDirection));
    }

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityResponseDto>> getAllActivities(
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection
    ){
        return ResponseEntity.ok(activityService.getAllActivities(pageSize,pageNo,sortBy,sortDirection));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ActivityResponseDto> getSingleActivity(@PathVariable("activityId") String actId){
        ActivityResponseDto activityResponseDto = activityService.getSingleActivityById(actId);
        return ResponseEntity.ok(activityResponseDto);
    }

    @PutMapping("/activity/{activityId}")
    public ResponseEntity<ActivityResponseDto> updateActivity(@Valid @RequestBody ActivityRequestDto activityRequestDto,
                                                          @PathVariable String activityId){
        ActivityResponseDto updatedActivityResponseDto = activityService.updateActivity(activityRequestDto,activityId);
        return ResponseEntity.ok(updatedActivityResponseDto);
    }

    @DeleteMapping("/activity/{activityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteActivity(@PathVariable("activityId") String actId){
        activityService.deleteActivity(actId);
        return ResponseEntity.ok(new ApiResponse("Activity Deleted Successfully",true));
    }
}

