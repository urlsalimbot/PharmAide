package com.PharmAide.web.controller;

import com.PharmAide.domain.service.TransactionService;
import com.PharmAide.web.dto.SaleDTO;
import com.PharmAide.web.dto.TransactionDTO;
import io.javalin.http.Context;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransactionController {
    private final TransactionService transactionService;

    @Inject
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void getTransac(@NotNull Context context, @NotNull String id) {
        try {
            String[] myIds = id.split("-");

            List<SaleDTO> sales = new ArrayList<>();
            for (String string : myIds) {
                sales.add(transactionService.getSalesbyProductID(Integer.parseInt(string)));
            }
            if (!sales.isEmpty()) {
                context.json(sales).status(200);
            } else {
                context.json(Map.of("error", "Transaction not found")).status(404);
            }
        } catch (Exception e) {
            context.json(Map.of("error", e.getMessage()))
                    .status(500);
        }
        return;
    }

    public void getAllTransac(@NotNull Context context, String query) {
        try {
            var transaction = transactionService.getAll();
            if (!transaction.isEmpty()) {
                context.json(transaction).status(200);
            } else {
                context.json(Map.of("error", "Transactions are empty"))
                        .status(404);
            }
        } catch (Exception e) {
            context.json(Map.of("Error", e.getMessage()))
                    .status(500);
        }


    }

    public void getAllSales(@NotNull Context context) {
        try {
            var transaction = transactionService.getAll();
            if (!transaction.isEmpty()) {
                context.json(transaction).status(200);
            } else {
                context.json(Map.of("error", "Transactions are empty"))
                        .status(404);
            }
        } catch (Exception e) {
            context.json(Map.of("Error", e.getMessage()))
                    .status(500);
        }
    }

    public void getMoneyline(@NotNull Context context) {
        var res = transactionService.getMoneyLine();
        context.json(res).status(200);
    }

    public void saveTransac(@NotNull Context ctx) {
        try {
            var transac = ctx.bodyValidator(TransactionDTO.class)
                    .check(it -> !it.getSales().isEmpty(), "Sales list is empty")
                    .check(it -> it.getTotal().floatValue() > 0.0, "total is empty or not valid")
                    .check(it -> it.getPayment().floatValue() > 0.0, "payment is empty or not valid");
            if (transac.errors().isEmpty()) {
                System.out.println(transac.get().toString());

                var res = transactionService.processTransac(transac.get());
                ctx.json(Map.of("Status", "Transaction processed and saved",
                                "Transactions", res))
                        .status(200);
                ///END OF FIX-ME!!

            } else {
                ctx.json(Map.of("error", transac.errors()))
                        .status(500);
            }
        } catch (
                Exception e) {
            ctx.json(Map.of("error", e.getMessage()))
                    .status(422);
        }
    }
}

