package com.CodeWithHemant.Fitness_Tracker.paylods;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    @Email(message = "Wrong Email Format !!!")
    @NotBlank(message = "UserName Cannot Be Null Or Empty !!!")
    private String userName;

    @NotBlank(message = "Password Cannot Be Null Or Empty")
    private String password;
}
