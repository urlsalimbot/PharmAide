package com.PharmAide.Infrastructure;

import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import java.util.function.Consumer;
import java.util.function.Function;

public class AppHibernate {

    public SessionFactory app = AppHibernateSessionFactory.getSessionFactory();

    public static void inTransaction(Consumer<StatelessSession> consumer) {
        AppHibernateSessionFactory.getSessionFactory().inStatelessTransaction(consumer);
    }

    public static  <R> R fromTransaction(Function<StatelessSession, R> function) {
        return AppHibernateSessionFactory.getSessionFactory().fromStatelessTransaction(function);
    }

}