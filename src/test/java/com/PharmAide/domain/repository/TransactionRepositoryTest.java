package com.PharmAide.domain.repository;

import com.PharmAide.Infrastructure.AppHibernate;
import com.PharmAide.domain.dao.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {
    AppHibernate hibernate = new AppHibernate();
    TransactionRepository repository = new TransactionRepository(hibernate);


    @Test
    void findInStockProduct() {
        String test = null;
        var res = repository.findInStockProduct(test);
        for (Store store : res){
            System.out.println(store);
        }
        System.out.println(res.size());
    }

    @Test
    void findbyIdandStoreId() {
        String test = "RetailerTest";
        int id = 3;
        var res = repository.findbyIdandStoreId(test, id);
        System.out.println(res);
    }
}