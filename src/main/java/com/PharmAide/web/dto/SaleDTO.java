package com.PharmAide.web.dto;

import com.PharmAide.domain.dao.Sale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Sale}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaleDTO implements Serializable {

    private Integer id;
    private String name;

    @JsonIgnore
    private BigDecimal price;

    private Integer quantity;

    @JsonIgnore
    private String type;

    @JsonIgnore
    private String img;
}