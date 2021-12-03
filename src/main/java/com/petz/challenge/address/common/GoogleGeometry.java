package com.petz.challenge.address.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleGeometry {
    private GoogleLocation location;
    @JsonProperty("location_type")
    private String locationType;
}
