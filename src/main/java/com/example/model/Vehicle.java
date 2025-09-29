package com.example.model;

import com.example.enums.VehicleStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Vehicle extends BaseEntity {
    private String plateNumber;
    private String type;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    @ManyToOne
    private Company company;
}
