package com.CodeWithHemant.Fitness_Tracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @CreationTimestamp              //hibernate will automatically set D&T of creation only "once" when creating.
    private LocalDateTime createdAt;

    @UpdateTimestamp              //hibernate will automatically update it when record updated in DB.
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore   //this field will be ignored while converting to json means user cannot see this column in response.
    private List<Activity> activities;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Recommendation> recommendations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId",referencedColumnName = "id"),
                                    inverseJoinColumns = @JoinColumn(name = "roleId",referencedColumnName = "id"))
    List<Role> roles;
}
