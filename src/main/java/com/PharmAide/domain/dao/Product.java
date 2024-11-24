package com.PharmAide.domain.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "public")
public class    Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_productid_seq", allocationSize = 1)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Column(name = "brandname", length = 75)
    private String brandname;

    @Column(name = "genericname", length = 75)
    private String genericname;

    @Column(name = "price", precision = 8, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category categoryid;

}