package com.travel.travel.RequestDto;

import lombok.Data;

@Data
public class HotelSearchDTO {
    private Long id;
    private String name;
    private String city;
    private String description;
    private String type;

    public HotelSearchDTO(Long id, String name, String city, String description, String type) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.description = description;
        this.type = type;
    }
}
