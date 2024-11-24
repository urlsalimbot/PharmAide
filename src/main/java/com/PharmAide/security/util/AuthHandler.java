package com.PharmAide.security.util;



import com.PharmAide.domain.dao.User;
import com.PharmAide.security.RoleUser;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import javalinjwt.JWTAccessManager;
import javalinjwt.JWTProvider;
import javalinjwt.JavalinJWT;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;


public class AuthHandler {

    public static JWTProvider<User> refprovider = ProviderUtil.provider;

    static Map<String, RouteRole> rolesMapping = new HashMap<>() {{
        put("CUSTOMER", RoleUser.CUSTOMER);
        put("RETAILER", RoleUser.RETAILER);
        put("ADMIN", RoleUser.ADMIN);
    }};

    public static JWTAccessManager accessManager = new ExtAccessManager("role", rolesMapping, RoleUser.ANYONE);

    public static Handler decode = JavalinJWT.createCookieDecodeHandler(refprovider);

    public static String hashPassword(@NotNull String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(5));
    }

    public static Boolean checkPassword(@NotNull String password, @NotNull String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }


    public static void append(@NotNull Context context) {
        DecodedJWT jwt = JavalinJWT.getDecodedFromContext(context);
        if(jwt!=null){
            var user = jwt.getClaim("user").asString();
            var role = jwt.getClaim("role").asString();
            context.header("user", user);
            context.header("role", role);
        }
    }
}
