package com.CodeWithHemant.Fitness_Tracker.mappers;

import com.CodeWithHemant.Fitness_Tracker.entities.Recommendation;
import com.CodeWithHemant.Fitness_Tracker.paylods.RecommendationRequestDto;
import com.CodeWithHemant.Fitness_Tracker.paylods.RecommendationResponseDto;
import org.springframework.stereotype.Component;


public class RecommendationMapper {
    public static Recommendation recommendationRequestDtoToRecommendation(RecommendationRequestDto requestDto){

        Recommendation recommendation = new Recommendation();

        recommendation.setRecommendation(requestDto.getRecommendation());
        recommendation.setType(requestDto.getType());
        recommendation.setImprovements(requestDto.getImprovements());
        recommendation.setSafety(requestDto.getSafety());
        recommendation.setSuggestions(requestDto.getSuggestions());

        return recommendation;
    }

    public static RecommendationResponseDto recommendationToRecommendationResponseDto(Recommendation recommendation){

        RecommendationResponseDto recommendationResponse = new RecommendationResponseDto();

        recommendationResponse.setId(recommendation.getId());
        recommendationResponse.setRecommendation(recommendation.getRecommendation());
        recommendationResponse.setType(recommendation.getType());
        recommendationResponse.setImprovements(recommendation.getImprovements());
        recommendationResponse.setSafety(recommendation.getSafety());
        recommendationResponse.setSuggestions(recommendation.getSuggestions());
        recommendationResponse.setCreatedAt(recommendation.getCreatedAt());
        recommendationResponse.setUpdatedAt(recommendation.getUpdatedAt());
        recommendationResponse.setUserId(recommendation.getUser().getId());
        recommendationResponse.setActivityId(recommendation.getActivity().getId());

        return recommendationResponse;
    }
}
