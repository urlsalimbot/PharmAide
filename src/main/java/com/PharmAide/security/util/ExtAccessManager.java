package com.PharmAide.security.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;
import javalinjwt.JWTAccessManager;
import javalinjwt.JavalinJWT;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Map;

public class ExtAccessManager extends JWTAccessManager {
    private Boolean before = false;
    private Boolean expired = false;


    public ExtAccessManager(String userRoleClaim, Map<String,
                            RouteRole> rolesMapping,
                            RouteRole defaultRole) {
        super(userRoleClaim, rolesMapping, defaultRole);

    }

    private void checkNotBeforeExpired(Context ctx) {
        if(JavalinJWT.containsJWT(ctx)){
            DecodedJWT jwt = JavalinJWT.getDecodedFromContext(ctx);
            Instant exp = jwt.getExpiresAtAsInstant();
            Instant anchor = jwt.getIssuedAtAsInstant();
            Instant notbefore = jwt.getNotBeforeAsInstant();
            this.before = anchor.isAfter(notbefore);
            this.expired = Instant.now().isAfter(exp);
        }
    }

    @Override
    public void handle(@NotNull Context context) {
        super.handle(context);
        this.checkNotBeforeExpired(context);
        DecodedJWT jwt = JavalinJWT.getDecodedFromContext(context);
        System.out.println(jwt.getClaim("role").asString());
        if(before || expired){
            throw new UnauthorizedResponse();
        }
    }
}
