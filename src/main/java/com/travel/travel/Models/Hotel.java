package com.travel.travel.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hotels")

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String location;
}
