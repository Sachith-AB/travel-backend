package com.travel.travel.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "guides")

public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;
}
