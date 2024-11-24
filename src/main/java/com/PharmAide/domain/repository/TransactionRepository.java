package com.PharmAide.domain.repository;


import com.PharmAide.Infrastructure.AppHibernate;
import com.PharmAide.domain.dao.*;
import com.PharmAide.domain.repository.interfaces.ITransactionRepository;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;

import java.util.List;
import java.util.Optional;

public class TransactionRepository implements ITransactionRepository {
    SessionFactory sessionFactory;

    @Inject
    public TransactionRepository(AppHibernate hibernate) {
        this.sessionFactory = hibernate.app;
    }

    /**
     * @param transaction
     */
    @Override
    public Transaction save(Transaction transaction) {
        var res = sessionFactory.fromTransaction(session -> {
            session.persist(transaction);
            return transaction;
        });
        return res;

    }

    @Override
    public List<Sale> getsalesbyID(Integer id) {
        var res = sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Sale.class) {{
                var user = from(Sale.class);
                var join = user.join("product");
                select(user);
                where(equal(join.get(Product_.ID), id));
            }};
            return session.createQuery(query).getResultList();
        });
        return res;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Transaction> findById(int id) {
        var res = sessionFactory.fromTransaction(session ->
                session.get(Transaction.class, id));
        return Optional.of(res);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<Transaction> findUnfulfilled() {
        var res = sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Transaction.class) {{
                var transac = from(Transaction.class);
                where(equal(transac.get(Transaction_.fulfilled), false));
            }};
            return session.createQuery(query).getResultList();
        });
        return res;
    }

    /**
     * @return
     */
    @Override
    public List<Transaction> findAllTransactions() {
        var res = sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Transaction.class) {{
                var user = from(Transaction.class);
                orderBy(asc(user.get(Transaction_.ID)));
            }};
            return session.createQuery(query).getResultList();
        });
        return res;
    }

    /**
     * @return
     */
    @Override
    public List<Store> findInStockProduct(String store_id) {
        return sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Store.class) {
                {
                    var store = from(Store.class);
                    var storeid = store.get("pk");
                    where(greaterThan(store.get(Store_.STOCK), 0));
                    if (store_id != null) {
                        where(like(storeid.get("user").get("username"), store_id));
                    }

                }
            };
            return session.createQuery(query).getResultList();
        });
    }

    @Override
    public List<Store> findbyIdandStoreId(String store_id,Integer id) {
        return sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Store.class) {
                {
                    var store = from(Store.class);
                    var storeid = store.get("pk");
                    where(greaterThan(store.get(Store_.STOCK), 0));
                    if (store_id != null) {
                        where(like(storeid.get("user").get("username"), store_id));
                    }
                    if (id != 0) {
                        where(equal(storeid.get("product").get(Product_.ID), id));
                    }

                }
            };
            return session.createQuery(query).getResultList();
        });
    }
}
