package com.PharmAide.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private Integer id;
    private String brandname;
    private String genericname;
    private String store_id;
    private BigDecimal price;
    private String category;
}