package com.CodeWithHemant.Fitness_Tracker.repositories;

import com.CodeWithHemant.Fitness_Tracker.entities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity,String> {
    List<Activity> findByUserId(String userId);
    Page<Activity> findByUserId(String userId, Pageable pageable);
}
