package com.PharmAide.web.controller;

import com.PharmAide.domain.service.ProductService;
import com.PharmAide.domain.service.TransactionService;
import io.javalin.http.Context;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class StoreController {
    ProductService productService;
    TransactionService transactionService;

    @Inject
    StoreController(ProductService productService
    , TransactionService transactionService) {
        this.productService = productService;
        this.transactionService = transactionService;
    }

    public void getAllProducts(Context ctx) {
        var res = transactionService.getAllStockedProducts(ctx.queryParam("store_id"));
        if (res != null) {
            ctx.json(res);
        } else {
            ctx.status(404).json("No Products Found");
        }
    }

    public void getStoreStats(@NotNull Context ctx) {

    }

    public void updateStocks(@NotNull Context ctx, String username) {
    }
}
