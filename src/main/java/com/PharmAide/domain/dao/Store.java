package com.PharmAide.domain.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "STORE_ID")),
        @AssociationOverride(name = "pk.product",
                joinColumns = @JoinColumn(name = "PRODUCT_ID"))})
public class Store implements Serializable {

    @EmbeddedId
    private StoreProductID pk = new StoreProductID();
    private int stock;
}
