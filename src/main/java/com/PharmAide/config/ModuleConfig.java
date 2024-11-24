package com.PharmAide.config;


import com.PharmAide.Infrastructure.AppHibernate;
import com.PharmAide.domain.repository.*;
import com.PharmAide.domain.repository.interfaces.*;
import com.PharmAide.domain.service.*;
import com.google.inject.AbstractModule;
import jakarta.inject.Singleton;

public class ModuleConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class);
        bind(IUserRepository.class).to(UserRepository.class).in(Singleton.class);

        bind(TransactionService.class);
        bind(ITransactionRepository.class).to(TransactionRepository.class).in(Singleton.class);

        bind(AuthenticationService.class);

        bind(CategoryService.class);
        bind(ICategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);

        bind(ProductService.class);
        bind(IProductRepository.class).to(ProductRepository.class).in(Singleton.class);

        bind(AppHibernate.class).in(Singleton.class);
    };
}
