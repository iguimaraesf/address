package com.petz.challenge.address.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoogleLocation {
    private BigDecimal lat;
    private BigDecimal lng;
}
