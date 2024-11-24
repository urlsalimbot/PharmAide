package com.PharmAide.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDTO implements Serializable {

    private String store_id;
    private String buyer_id;
    public BigDecimal total;
    public BigDecimal payment;
    public List<SaleDTO> sales;
}
