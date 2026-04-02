package com.CodeWithHemant.Fitness_Tracker.paylods;

import com.CodeWithHemant.Fitness_Tracker.entities.Activity;
import com.CodeWithHemant.Fitness_Tracker.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequestDto {
    @NotBlank(message = "Recommendation Type Cannot Be Null Or Empty !!!")
    private String type;

    @NotBlank(message = "Recommendation Cannot Be Null Or Empty !!!")
    @Size(min = 5, max = 255 , message = "Recommendation Must Be in B/w 5 to 255 chars !!!")
    private String recommendation;

    private List<String> improvements;   //this column will store json data in DB.
    private List<String> suggestions;
    private List<String> safety;

    @NotBlank(message = "User Id Cannot Be Null Or Empty !!!")
    private String userId;

    @NotBlank(message = "Activity Id Cannot Be Null Or Empty !!!")
    private String activityId;
}
