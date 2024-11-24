package com.PharmAide.security;

import io.javalin.security.RouteRole;

public enum RoleUser implements RouteRole {
    ANYONE,
    RETAILER,
    CUSTOMER,
    ADMIN
}
