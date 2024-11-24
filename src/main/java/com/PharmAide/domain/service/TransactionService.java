package com.PharmAide.domain.service;


import com.PharmAide.domain.dao.Product;
import com.PharmAide.domain.dao.Sale;
import com.PharmAide.domain.dao.Store;
import com.PharmAide.domain.dao.Transaction;
import com.PharmAide.domain.repository.interfaces.IProductRepository;
import com.PharmAide.domain.repository.interfaces.ITransactionRepository;
import com.PharmAide.domain.repository.interfaces.IUserRepository;
import com.PharmAide.web.dto.DataDTO;
import com.PharmAide.web.dto.ProductDTO;
import com.PharmAide.web.dto.SaleDTO;
import com.PharmAide.web.dto.TransactionDTO;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private final ITransactionRepository transactions;
    private final IProductRepository products;
    private final IUserRepository users;

    @Inject
    public TransactionService(ITransactionRepository transactions,
                              IProductRepository products,
                              IUserRepository users) {
        this.transactions = transactions;
        this.products = products;
        this.users = users;
    }

    public SaleDTO getSalesbyProductID(int id){
        var res = transactions.getsalesbyID(id);
        var topass = new SaleDTO();
        topass.setId(id);

        var tot = 0;
        for(Sale sale : res){
            tot = tot + sale.getQuantity();

        }
        topass.setQuantity(tot);
        return topass;
    }


    public Transaction getbyID(int i) {
        var res = transactions.findById(i);
        return res.orElse(null);
    }

    public List<Sale> getAll() {
        var res =transactions.findAllTransactions();

        List<Sale> filteredSales = res.stream()
                .filter(t -> t.getTransactiondate().isBefore(Instant.now()))
                .flatMap(t -> t.getSales().stream())
                .collect(Collectors.toList());

        return filteredSales;
    }

    public List<ProductDTO> getAllStockedProducts(String username) {
        var res = transactions.findInStockProduct(username);

        var result = new ArrayList<ProductDTO>();
        for(Store store : res){
            var product = store.getPk().getProduct();
            var productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .brandname(product.getBrandname())
                    .genericname(product.getGenericname())
                    .price(product.getPrice())
                    .store_id(store.getPk().getUser().getUsername())
                    .category(product.getCategoryid().getId().toString())
                    .build();
            result.add(productDTO);
        }
        return result;
    }


    public List<DataDTO> getMoneyLine(){
        var res = transactions.findAllTransactions();

        ArrayList<DataDTO> l = new ArrayList<>();
        Instant instant = Instant.now();
        ZonedDateTime here = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
        Instant parser = Instant.from(here);
        Instant yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM");

        int i = 0;
        for(i = 0; i < 8; i++) {
            float x = 0;
            var todayts = parser.minus(i,ChronoUnit.DAYS);
            var yesterparser = yesterday.minus(i,ChronoUnit.DAYS);
            for(Transaction t : res) {

                if(t.getTransactiondate().isBefore(todayts) && t.getTransactiondate().isAfter(yesterparser)) {
                    x = t.getTotal().floatValue();

                }
            }
            l.add(new DataDTO(yesterparser.atZone(ZoneId.systemDefault()).format(fmt).toString(), new BigDecimal(x)));
        }

        return l;
    }

    public Transaction processTransac(TransactionDTO transactionDTO) {
        List<Sale> sales = new ArrayList<>();
        for (SaleDTO saleDTO : transactionDTO.getSales()) {
            var newsale = saleDTOtoSale(saleDTO);
            var prod = products.getbyId(saleDTO.getId()).get();
            newsale.setProduct(prod);
            sales.add(newsale);
        }

        var buyer = users.getbyUsername(transactionDTO.getBuyer_id());
        var seller = users.getbyUsername(transactionDTO.getStore_id());;

        var trans = Transaction.builder()
                .store_id(seller.get())
                .buyer_id(buyer.get())
                .total(transactionDTO.getTotal())
                .payment(transactionDTO.getPayment())
                .change(transactionDTO.getPayment().subtract(transactionDTO.getTotal()))
                .fulfilled(false)
                .sales(sales)
                .build();

        var res = transactions.save(trans);

        return res;
    }

    private Sale saleDTOtoSale(SaleDTO saleDTO) {
        return Sale.builder()
                .quantity(saleDTO.getQuantity())
                .build();
    }
}
