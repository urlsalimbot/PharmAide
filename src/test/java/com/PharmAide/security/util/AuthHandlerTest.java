package com.PharmAide.security.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthHandlerTest {

    @Test
    void TestGeneratingUsers() {
        var res = AuthHandler.hashPassword("12345");
        System.out.println(res);
        var res1 = AuthHandler.hashPassword("54321");
        System.out.println(res1);
        var res2 = AuthHandler.hashPassword("67890");
        System.out.println(res2);

    }

}