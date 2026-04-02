package com.CodeWithHemant.Fitness_Tracker.paylods;

import com.CodeWithHemant.Fitness_Tracker.entities.ActivityType;
import com.CodeWithHemant.Fitness_Tracker.entities.Recommendation;
import com.CodeWithHemant.Fitness_Tracker.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRequestDto {

    @NotNull(message = "Activity Type Cannot Be Null !!!")
    private ActivityType type;

    @NotNull(message = "startTime Is Required Filed Cannot Be Null !!!")
    private LocalDateTime startTime;

    @NotNull(message = "Duration Is Required Cannot Be Null...")
    private Integer duration;

    @NotNull(message = "caloriesBurned Is Required Cannot Be Null...")
    private Integer caloriesBurned;

    private Map<String,Object> additionalMetrics;

    @NotBlank(message = "User Id Cannot Be Null Or Empty ...")
    private String userId;
}
