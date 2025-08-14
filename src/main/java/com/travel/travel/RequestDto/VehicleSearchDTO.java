package com.travel.travel.RequestDto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class VehicleSearchDTO {
    private Long id;
    private String name;
    private String vehicleModel;
    private String vehicleNo;
    private Integer capacity;
    private BigDecimal basePrice;
    private String pricePerKilometer;

    public VehicleSearchDTO(Long id, String name, String vehicleModel, String vehicleNo,
                           Integer capacity, BigDecimal basePrice, String pricePerKilometer) {
        this.id = id;
        this.name = name;
        this.vehicleModel = vehicleModel;
        this.vehicleNo = vehicleNo;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.pricePerKilometer = pricePerKilometer;
    }
}
