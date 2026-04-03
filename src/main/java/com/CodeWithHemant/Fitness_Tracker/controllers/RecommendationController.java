package com.CodeWithHemant.Fitness_Tracker.controllers;

import com.CodeWithHemant.Fitness_Tracker.entities.Recommendation;
import com.CodeWithHemant.Fitness_Tracker.paylods.*;
import com.CodeWithHemant.Fitness_Tracker.services.RecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/recommendation")
    public ResponseEntity<RecommendationResponseDto> createRecommendation(@Valid @RequestBody RecommendationRequestDto recommendationRequestDto){
       return new ResponseEntity<>(recommendationService.createRecommendation(recommendationRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/recommendations") //find recommendations based on User.
    public ResponseEntity<RecommendationResponse> getUserRecommendation(
            @PathVariable("userId") String userId,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection){
           RecommendationResponse recommendationResponse = recommendationService.getUserRecommendation(userId,pageSize,pageNo,sortBy,sortDirection);
        return new ResponseEntity<>(recommendationResponse,HttpStatus.OK);
    }

    @GetMapping("/activity/{activityId}/recommendations")  //find Recommendation Based On Activity.
    public ResponseEntity<RecommendationResponse> getActivityRecommendation(
            @PathVariable("activityId") String activityId,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection){
        RecommendationResponse recommendationResponse = recommendationService.getActivityRecommendation(activityId,pageSize,pageNo,sortBy,sortDirection);
        return new ResponseEntity<>(recommendationResponse,HttpStatus.OK);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<RecommendationResponse> getAllRecommendations(
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection
    ){
        return ResponseEntity.ok(recommendationService.getAllRecommendations(pageSize,pageNo,sortBy,sortDirection));
    }

    @GetMapping("/recommendation/{recomId}")
    public ResponseEntity<RecommendationResponseDto> getSingleRecommendation(@PathVariable String recomId){
        RecommendationResponseDto recommendationResponseDto = recommendationService.getSingleRecommendationById(recomId);
        return ResponseEntity.ok(recommendationResponseDto);
    }

    @PutMapping("/recommendation/{recomId}")
    public ResponseEntity<RecommendationResponseDto> updateRecommendation(@Valid @RequestBody RecommendationRequestDto recommendationRequestDto,
                                                          @PathVariable String recomId){
        RecommendationResponseDto recommendationResponseDto = recommendationService.updateRecommendation(recommendationRequestDto,recomId);
        return ResponseEntity.ok(recommendationResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/recommendation/{recomId}")
    public ResponseEntity<ApiResponse> deleteRecommendation(@PathVariable String recomId){
        recommendationService.deleteRecommendation(recomId);
        return ResponseEntity.ok(new ApiResponse("Recommendation Deleted Successfully",true));
    }
}
