package com.petz.challenge.address.common;

import lombok.Data;

@Data
public class GoogleGeoCoding {
    private String status;
    private GoogleGeoCodingResult[] results;
}
