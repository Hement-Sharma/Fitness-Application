package com.CodeWithHemant.Fitness_Tracker.paylods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
}
