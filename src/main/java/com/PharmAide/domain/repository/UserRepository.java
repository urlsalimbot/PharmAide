package com.PharmAide.domain.repository;


import com.PharmAide.Infrastructure.AppHibernate;
import com.PharmAide.domain.dao.User;
import com.PharmAide.domain.dao.User_;
import com.PharmAide.domain.repository.interfaces.IUserRepository;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;

import java.util.List;
import java.util.Optional;

/**
 * Repository dealing with Users.
 */
public class UserRepository implements IUserRepository {

    SessionFactory sessionFactory;

    @Inject
    public UserRepository(AppHibernate hibernate) {
        this.sessionFactory = hibernate.app;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Optional<User> addUser(User user) {
        var res = sessionFactory.fromTransaction(session -> {
            session.persist(user);
            return session.get(User.class, user);
        });
        return Optional.ofNullable(res);
    }

    @Override
    public List<User> getAllUsers() {
        return sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, User.class) {
                {
                    var user = from(User.class);
                }
            };
            return session.createQuery(query).getResultList();
        });
    }


    /**
     * @param id
     * @return
     */
    @Override
    public Optional<User> getbyID(int id) {
        var res = sessionFactory.fromTransaction(session ->
                session.get(User.class, id));
        return Optional.of(res);
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Optional<User> getbyUsername(String username) {
        var res = sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, User.class) {{
                var user = from(User.class);
                where(like(user.get(User_.USERNAME), username));
            }};
            return session.createQuery(query).getSingleResult();
        });
        return Optional.ofNullable(res);
    }

    /**
     * @param newuser
     * @param s
     * @return
     */
    @Override
    public Optional<User> updateUser(User newuser, String s) {
        var res = sessionFactory.fromTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, User.class) {{
                var user = from(User.class);
                select(user);
                where(like(user.get(User_.USERNAME), s));
            }};
            session.merge(newuser);
            return newuser;
        });
        return Optional.ofNullable(res);
    }

    /**
     * @param username
     */
    @Override
    public void deleteUser(String username) {
        sessionFactory.inTransaction(session -> {
            var query = new CriteriaDefinition<>(sessionFactory, User.class) {{
                var user = from(User.class);
//                var join = user.join("userAuth");
                select(user);
                where(like(user.get(User_.USERNAME), username));
            }};
            var obj = session.createQuery(query).getSingleResult();
            session.remove(obj);
        });
    }
}
