package com.petz.challenge.address.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String streetName;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private String complement;
    @Column(nullable = false)
    private String neighbourhood;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String zipCode;
    @Column(length = 8, scale = 6, nullable = false)
    private BigDecimal latitude;
    @Column(length = 9, scale = 6, nullable = false)
    private BigDecimal longitude;
}
