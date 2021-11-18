package com.unicap.tcc.usability.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartCityResponse implements Serializable {

    private Double smartCityPercentage;
}
