package com.example.model;

import com.example.enums.EmailType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmailHistory extends BaseEntity{

    private String email;
    private String code;

    @Enumerated(EnumType.STRING)
    private EmailType emailType;

    private Integer attemptCount;

}
