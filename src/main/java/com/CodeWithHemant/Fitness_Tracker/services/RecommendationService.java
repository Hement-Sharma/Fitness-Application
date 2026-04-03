package com.CodeWithHemant.Fitness_Tracker.services;

import com.CodeWithHemant.Fitness_Tracker.entities.Activity;
import com.CodeWithHemant.Fitness_Tracker.entities.Recommendation;
import com.CodeWithHemant.Fitness_Tracker.entities.User;
import com.CodeWithHemant.Fitness_Tracker.exceptions.ResourceNotFoundException;
import com.CodeWithHemant.Fitness_Tracker.mappers.RecommendationMapper;
import com.CodeWithHemant.Fitness_Tracker.paylods.ActivityResponseDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.RecommendationRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.RecommendationResponse;
import com.CodeWithHemant.Fitness_Tracker.paylods.RecommendationResponseDto;
import com.CodeWithHemant.Fitness_Tracker.repositories.ActivityRepo;
import com.CodeWithHemant.Fitness_Tracker.repositories.RecommendationRepo;
import com.CodeWithHemant.Fitness_Tracker.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepo recommendationRepo;
    private final UserRepo userRepo;
    private final ActivityRepo activityRepo;

    public RecommendationResponseDto createRecommendation(RecommendationRequestDto recommendationRequestDto){
        User user = userRepo.findById(recommendationRequestDto.getUserId()).orElseThrow(()->new ResourceNotFoundException("User","UserId",recommendationRequestDto.getUserId()));
        Activity activity = activityRepo.findById(recommendationRequestDto.getActivityId()).orElseThrow(()->new ResourceNotFoundException("Activity","ActivityId",recommendationRequestDto.getActivityId()));

        Recommendation recommendation = RecommendationMapper.recommendationRequestDtoToRecommendation(recommendationRequestDto);
        recommendation.setUser(user);
        recommendation.setActivity(activity);
        Recommendation savedRecommendation = recommendationRepo.save(recommendation);

        return RecommendationMapper.recommendationToRecommendationResponseDto(savedRecommendation);
    }

    public RecommendationResponse getUserRecommendation(String userId,Integer pageSize,Integer pageNum,String sortBy,String sortDir) {

        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }else {
            sort = Sort.by(sortBy); //by default ascending
        }

        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Recommendation> pageRecommendation = recommendationRepo.findByUserId(userId,pageable);
        List<Recommendation> recommendations = pageRecommendation.toList();
        List<RecommendationResponseDto> recommendationResponseDtos = recommendations.stream().map(recommendation -> RecommendationMapper.recommendationToRecommendationResponseDto(recommendation)).collect(Collectors.toList());

        RecommendationResponse recommendationResponse = new RecommendationResponse();
        recommendationResponse.setRecommendations(recommendationResponseDtos);
        recommendationResponse.setPageNumber(pageRecommendation.getNumber());
        recommendationResponse.setPageSize(pageRecommendation.getSize());
        recommendationResponse.setTotalPages(pageRecommendation.getTotalPages());
        recommendationResponse.setTotalElements(pageRecommendation.getTotalElements());
        recommendationResponse.setLastPage(pageRecommendation.isLast());

        return recommendationResponse;
    }

    public RecommendationResponse getActivityRecommendation(String activityId,Integer pageSize,Integer pageNum,String sortBy,String sortDir) {

        Sort sort;

        if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy); //by default ascending
        }

        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Recommendation> pageRecommendation = recommendationRepo.findByActivityId(activityId, pageable);
        List<Recommendation> recommendations = pageRecommendation.toList();
        List<RecommendationResponseDto> recommendationResponseDtos = recommendations.stream().map(recommendation -> RecommendationMapper.recommendationToRecommendationResponseDto(recommendation)).collect(Collectors.toList());

        RecommendationResponse recommendationResponse = new RecommendationResponse();
        recommendationResponse.setRecommendations(recommendationResponseDtos);
        recommendationResponse.setPageNumber(pageRecommendation.getNumber());
        recommendationResponse.setPageSize(pageRecommendation.getSize());
        recommendationResponse.setTotalPages(pageRecommendation.getTotalPages());
        recommendationResponse.setTotalElements(pageRecommendation.getTotalElements());
        recommendationResponse.setLastPage(pageRecommendation.isLast());

        return recommendationResponse;
    }

    public  RecommendationResponse getAllRecommendations(Integer pageSize,Integer pageNum,String sortBy,String sortDir) {

        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }else {
            sort = Sort.by(sortBy); //by default ascending
        }

        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Recommendation> pageRecommendation = recommendationRepo.findAll(pageable);
        List<Recommendation> recommendations = pageRecommendation.toList();
        List<RecommendationResponseDto> recommendationResponseDtos = recommendations.stream().map(recommendation -> RecommendationMapper.recommendationToRecommendationResponseDto(recommendation)).collect(Collectors.toList());

        RecommendationResponse recommendationResponse = new RecommendationResponse();
        recommendationResponse.setRecommendations(recommendationResponseDtos);
        recommendationResponse.setPageNumber(pageRecommendation.getNumber());
        recommendationResponse.setPageSize(pageRecommendation.getSize());
        recommendationResponse.setTotalPages(pageRecommendation.getTotalPages());
        recommendationResponse.setTotalElements(pageRecommendation.getTotalElements());
        recommendationResponse.setLastPage(pageRecommendation.isLast());

       return recommendationResponse;
    }

    public RecommendationResponseDto getSingleRecommendationById(String recomId) {
       Recommendation recommendation = recommendationRepo.findById(recomId).orElseThrow(()->new ResourceNotFoundException("Recommendation","RecommendationId",recomId));
       return RecommendationMapper.recommendationToRecommendationResponseDto(recommendation);
    }

    public RecommendationResponseDto updateRecommendation(RecommendationRequestDto recommendationRequestDto, String recomId) {
        Recommendation recommendation = recommendationRepo.findById(recomId).orElseThrow(()->new ResourceNotFoundException("Recommendation","RecommendationId",recomId));
        recommendation.setRecommendation(recommendationRequestDto.getRecommendation());
        recommendation.setType(recommendationRequestDto.getType());
        recommendation.setSafety(recommendationRequestDto.getSafety());
        recommendation.setSuggestions(recommendationRequestDto.getSuggestions());
        recommendation.setImprovements(recommendationRequestDto.getImprovements());

        Recommendation updatedRecommendation = recommendationRepo.save(recommendation);
        return RecommendationMapper.recommendationToRecommendationResponseDto(updatedRecommendation);
    }

    public void deleteRecommendation(String recomId) {
        Recommendation recommendation = recommendationRepo.findById(recomId).orElseThrow(()->new ResourceNotFoundException("Recommendation","RecommendationId",recomId));
        recommendationRepo.delete(recommendation);
    }
}
