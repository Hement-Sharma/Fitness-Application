package com.CodeWithHemant.Fitness_Tracker.paylods;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @Email(message = "Wrong Email Format !!!")
    @NotBlank(message = "UserName Cannot Be Null Or Empty !!!")
    private String email;

    @NotBlank(message = "Password Cannot Be Null Or Empty")
    @Size(min = 6,max = 30, message = "Password Size Must In B/W 6 To 30 Char's ")
    private String password;

    @NotBlank(message = "User Name Cannot Be Null Or Empty !!!")
    private String firstName;

    @NotBlank(message = "Last Name Cannot Be Null Or Empty !!!")
    private String lastName;

    @NotNull(message = "Roles cannot be null !!!")
    @NotEmpty(message = "Roles cannot be empty !!!")
    private List<Integer> roleIds;
}
