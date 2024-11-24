package com.PharmAide.domain.repository;


import com.PharmAide.Infrastructure.AppHibernate;
import com.PharmAide.domain.dao.Category_;
import com.PharmAide.domain.dao.Category;
import com.PharmAide.domain.repository.interfaces.ICategoryRepository;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;

import java.util.List;
import java.util.Optional;

public class CategoryRepository implements ICategoryRepository {

    SessionFactory sessionFactory;

    @Inject
    public CategoryRepository(AppHibernate hibernate) {
        this.sessionFactory = hibernate.app;
    }

    /**
     * @param category
     */
    @Override
    public Optional<Category> save(Category category) {
        var res = sessionFactory.fromTransaction(session -> {
            session.persist(category);
            return session.get(Category.class, category);
        });
        return Optional.ofNullable(res);
    }

    /**
     * @return
     */
    @Override
    public List<Category> listCategories() {
        return sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Category.class) {
                {
                    var user = from(Category.class);
                    orderBy(asc(user.get(Category_.ID)));
                }
            };
            return session.createQuery(query).getResultList();
        });
    }

    /**
     * @param categoryName
     * @return
     */
    @Override
    public Optional<Category> getbyName(String categoryName) {
        return Optional.ofNullable(sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Category.class) {{
                var user = from(Category.class);
                where(like(user.get(Category_.catename), categoryName));
            }};
            return session.createQuery(query).uniqueResult();
        }));
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Category> getbyId(Integer id) {
        var res = sessionFactory.fromTransaction(session ->
                session.get(Category.class, id));
        return Optional.ofNullable(res);
    }

    /**
     * @param category
     * @param categoryName
     * @return
     */
    @Override
    public Optional<Category> update(Category category, String categoryName) {
        var res = sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Category.class) {{
                var user = from(Category.class);
                where(like(user.get(Category_.catename), categoryName));
            }};
            var old = session.createQuery(query).getSingleResult();
            category.setId(old.getId());
            session.merge(category);
            return category;
        });
        return Optional.ofNullable(res);
    }

    /**
     * @param name
     */
    @Override
    public void delete(String name) {
        sessionFactory.inTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, Category.class) {{
                var user = from(Category.class);
                where(like(user.get(Category_.catename), name));
            }};
            var obj = session.createQuery(query).getSingleResult();
            session.remove(obj);
        });
    }

}
