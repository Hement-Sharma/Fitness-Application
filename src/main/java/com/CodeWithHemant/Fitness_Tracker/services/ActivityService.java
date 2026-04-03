package com.CodeWithHemant.Fitness_Tracker.services;

import com.CodeWithHemant.Fitness_Tracker.entities.Activity;
import com.CodeWithHemant.Fitness_Tracker.entities.User;
import com.CodeWithHemant.Fitness_Tracker.exceptions.ResourceNotFoundException;
import com.CodeWithHemant.Fitness_Tracker.mappers.ActivityMapper;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityResponse;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityResponseDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.UserResponse;
import com.CodeWithHemant.Fitness_Tracker.repositories.ActivityRepo;
import com.CodeWithHemant.Fitness_Tracker.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepo activityRepo;
    private final UserRepo userRepo;

    public ActivityResponseDto createActivity(ActivityRequestDto activityRequestDto) {
        User user = userRepo.findById(activityRequestDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User","UserId",activityRequestDto.getUserId()));
        Activity activity = ActivityMapper.activityRequestDtoToActivity(activityRequestDto);
        activity.setUser(user);
        Activity savedActivity = activityRepo.save(activity);

        ActivityResponseDto savedActivityResponseDto = ActivityMapper.activityToActivityResponseDto(savedActivity);
        return savedActivityResponseDto;
    }

    public ActivityResponse getUserActivities(String userId, Integer pageSize, Integer pageNum, String sortBy, String sortDir) {

        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }else {
            sort = Sort.by(sortBy); //by default ascending
        }

        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Activity> pageActivities = activityRepo.findByUserId(userId,pageable);
       List<Activity> userActivities = pageActivities.toList();
       List<ActivityResponseDto> activityResponseDtos = userActivities.stream().map(ActivityMapper::activityToActivityResponseDto).collect(Collectors.toList());

        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setActivities(activityResponseDtos);
        activityResponse.setPageNumber(pageActivities.getNumber());
        activityResponse.setPageSize(pageActivities.getSize());
        activityResponse.setTotalPages(pageActivities.getTotalPages());
        activityResponse.setTotalElements(pageActivities.getTotalElements());
        activityResponse.setLastPage(pageActivities.isLast());

        return activityResponse;
    }

    public ActivityResponseDto getSingleActivityById(String actId) {
       Activity activity = activityRepo.findById(actId).orElseThrow(()->new ResourceNotFoundException("Activity","ActivityID",actId));
      return ActivityMapper.activityToActivityResponseDto(activity);
    }

    public ActivityResponseDto updateActivity(ActivityRequestDto activityRequestDto, String activityId) {
        Activity activity = activityRepo.findById(activityId).orElseThrow(()->new ResourceNotFoundException("Activity","ActivityID",activityId));
        activity.setType(activityRequestDto.getType());
        activity.setDuration(activityRequestDto.getDuration());
        activity.setAdditionalMetrics(activityRequestDto.getAdditionalMetrics());
        activity.setStartTime(activityRequestDto.getStartTime());
        activity.setCaloriesBurned(activityRequestDto.getCaloriesBurned());

        Activity updatedActivity = activityRepo.save(activity);
        return ActivityMapper.activityToActivityResponseDto(updatedActivity);
    }

    public void deleteActivity(String actId) {
        Activity activity = activityRepo.findById(actId).orElseThrow(()-> new ResourceNotFoundException("Activity","ActivityId",actId));
        activityRepo.delete(activity);
    }

    public  ActivityResponse getAllActivities(Integer pageSize,Integer pageNum,String sortBy,String sortDir) {

        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }else {
            sort = Sort.by(sortBy); //by default ascending
        }

        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Activity> pageActivities = activityRepo.findAll(pageable);
        List<Activity> activities = pageActivities.stream().toList();
        List<ActivityResponseDto> activityResponseDtos = activities.stream().map(activity -> ActivityMapper.activityToActivityResponseDto(activity)).collect(Collectors.toList());

        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setActivities(activityResponseDtos);
        activityResponse.setPageNumber(pageActivities.getNumber());
        activityResponse.setPageSize(pageActivities.getSize());
        activityResponse.setTotalPages(pageActivities.getTotalPages());
        activityResponse.setTotalElements(pageActivities.getTotalElements());
        activityResponse.setLastPage(pageActivities.isLast());

        return activityResponse;
    }
}
