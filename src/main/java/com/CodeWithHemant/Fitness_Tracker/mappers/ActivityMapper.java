package com.CodeWithHemant.Fitness_Tracker.mappers;

import com.CodeWithHemant.Fitness_Tracker.entities.Activity;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityResponseDto;
import org.springframework.stereotype.Component;


public class ActivityMapper {
    public static Activity activityRequestDtoToActivity(ActivityRequestDto activityRequestDto){

        Activity activity = new Activity();

        activity.setType(activityRequestDto.getType());
        activity.setStartTime(activityRequestDto.getStartTime());
        activity.setDuration(activityRequestDto.getDuration());
        activity.setCaloriesBurned(activityRequestDto.getCaloriesBurned());
        activity.setAdditionalMetrics(activityRequestDto.getAdditionalMetrics());

        return activity;
    }

    public static ActivityResponseDto activityToActivityResponseDto(Activity activity){

        ActivityResponseDto activityResponseDto = new ActivityResponseDto();

        activityResponseDto.setId(activity.getId());
        activityResponseDto.setType(activity.getType());
        activityResponseDto.setStartTime(activity.getStartTime());
        activityResponseDto.setDuration(activity.getDuration());
        activityResponseDto.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponseDto.setAdditionalMetrics(activity.getAdditionalMetrics());
        activityResponseDto.setUserId(activity.getUser().getId());
        activityResponseDto.setCreatedAt(activity.getCreatedAt());
        activityResponseDto.setUpdatedAt(activity.getUpdatedAt());

        return activityResponseDto;
    }
}
