package com.travel.travel.RequestDto;

import java.util.List;

import lombok.Data;

@Data
public class GuideSearchFilters {
    private List<String> languages;
    private List<String> specialties;
    private Double minPrice;
    private Double maxPrice;
    private Double minRating;
    private Boolean availableOnly;
}