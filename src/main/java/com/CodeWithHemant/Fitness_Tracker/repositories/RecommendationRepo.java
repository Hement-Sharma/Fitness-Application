package com.CodeWithHemant.Fitness_Tracker.repositories;

import com.CodeWithHemant.Fitness_Tracker.entities.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepo extends JpaRepository<Recommendation,String> {
    List<Recommendation> findByUserId(String userId);
    List<Recommendation> findByActivityId(String activityId);
    Page<Recommendation> findByUserId(String userId, Pageable pageable);

    Page<Recommendation> findByActivityId(String activityId, Pageable pageable);
}
