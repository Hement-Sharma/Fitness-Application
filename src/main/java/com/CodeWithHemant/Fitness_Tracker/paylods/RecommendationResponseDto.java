package com.CodeWithHemant.Fitness_Tracker.paylods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponseDto {
    private String id;
    private String type;
    private String recommendation;
    private List<String> improvements;   //this column will store json data in DB.
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;
    private String activityId;
}
