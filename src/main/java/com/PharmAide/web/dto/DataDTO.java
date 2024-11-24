package com.PharmAide.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DataDTO implements Serializable {
    private String label;
    private BigDecimal data;
}
