package com.PharmAide.domain.repository;

import com.PharmAide.Infrastructure.AppHibernate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void testUserRepository() {
        AppHibernate hibernate = new AppHibernate();
        UserRepository userRepository = new UserRepository(hibernate);

        var res = userRepository.getbyUsername("Administrator");
        System.out.println(res.get());
    }

}