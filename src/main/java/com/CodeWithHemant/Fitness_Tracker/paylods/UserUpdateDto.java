package com.CodeWithHemant.Fitness_Tracker.paylods;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
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

    private List<Integer> roleIds;
}
