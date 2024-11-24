package com.PharmAide.domain.repository;


import com.PharmAide.Infrastructure.AppHibernate;
import com.PharmAide.domain.dao.Product;
import com.PharmAide.domain.dao.Product_;
import com.PharmAide.domain.repository.interfaces.IProductRepository;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;

import java.util.List;
import java.util.Optional;

public class ProductRepository implements IProductRepository {
    SessionFactory sessionFactory;

    @Inject
    public ProductRepository(AppHibernate hibernate) {
        this.sessionFactory = hibernate.app;
    }

    /**
     * @param product
     * @return
     */
    @Override
    public Optional<Product> addProduct(Product product) {
        var res = sessionFactory.fromTransaction(session -> {
            session.persist(product);
            return session.get(Product.class, product);
        });
        return Optional.ofNullable(res);
    }

    /**
     * @return
     */
    @Override
    public List<Product> getallProducts() {
        return sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Product.class) {
                {
                    var user = from(Product.class);
                    orderBy(asc(user.get(Product_.ID)));
                }
            };
            return session.createQuery(query).getResultList();
        });
    }

    /**
     * @param id@return
     */
    @Override
    public Optional<Product> getbyId(int id) {
        var res = sessionFactory.fromTransaction(session ->
                session.get(Product.class, id));
        return Optional.ofNullable(res);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Optional<Product> getbyName(String name) {
        var res = sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Product.class) {{
                var user = from(Product.class);
                where(like(user.get(Product_.genericname), name));
            }};
            return session.createQuery(query).getSingleResult();
        });
        return Optional.ofNullable(res);
    }


    /**
     * @param product
     * @param name
     * @return
     */
    @Override
    public Optional<Product> updateProduct(Product product, String name) {
        return Optional.empty();
    }

    /**
     * @param product
     * @param id
     * @return
     */
    @Override
    public Optional<Product> updatebyID(Product product, Integer id) {
       var res = sessionFactory.fromTransaction(session -> {
           var old = session.get(Product.class, id);
           product.setId(old.getId());
           session.merge(product);
           return product;
       });
       return Optional.ofNullable(res);
    }

    /**
     * @param name
     */
    @Override
    public void delete(String name) {
        sessionFactory.inTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Product.class) {{
                var user = from(Product.class);
                where(like(user.get(Product_.genericname), name));
            }};
            var obj = session.createQuery(query).getSingleResult();
            session.remove(obj);
        });
    }
}

