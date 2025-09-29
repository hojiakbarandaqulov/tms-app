package com.example.model;

import com.example.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "orders")
public class Order extends BaseEntity{
    @ManyToOne
    private Profile shipper; // buyurtmachi

    @ManyToOne
    private Company carrier; // tashuvchi kompaniya

    @ManyToOne
    private Vehicle vehicle;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
}
