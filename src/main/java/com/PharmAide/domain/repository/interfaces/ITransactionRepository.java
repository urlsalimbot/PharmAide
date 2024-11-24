package com.PharmAide.domain.repository.interfaces;



import com.PharmAide.domain.dao.Product;
import com.PharmAide.domain.dao.Sale;
import com.PharmAide.domain.dao.Store;
import com.PharmAide.domain.dao.Transaction;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {

    public Transaction save(Transaction transaction);

    public Optional<Transaction> findById(int id);

    public List<Transaction> findUnfulfilled();

    public List<Transaction> findAllTransactions();

    public List<Store> findInStockProduct(String store_id);

    public List<Sale> getsalesbyID(Integer id);

    public List<Store> findbyIdandStoreId(String store_id,Integer id);
}
