package com.petz.challenge.address.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleGeoCodingResult {
    private GoogleGeometry geometry;
    @JsonProperty("partial_match")
    private boolean partialMatch;
}
