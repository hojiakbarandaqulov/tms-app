package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Company extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Size(min = 9, max = 12)
    private String phone;
}
