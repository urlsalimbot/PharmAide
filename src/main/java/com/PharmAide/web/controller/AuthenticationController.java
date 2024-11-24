package com.PharmAide.web.controller;


import com.PharmAide.domain.service.AuthenticationService;
import com.PharmAide.web.dto.UserAuthDTO;
import com.PharmAide.web.dto.UserDTO;
import io.javalin.http.Context;
import io.javalin.validation.BodyValidator;
import io.javalin.validation.ValidationException;
import jakarta.inject.Inject;
import javalinjwt.JavalinJWT;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class AuthenticationController {
    AuthenticationService authenticationService;

    @Inject
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void login(@NotNull Context context) {
        try {
            BodyValidator<UserAuthDTO> validator = context.bodyValidator(UserAuthDTO.class)
                    .check(obj -> obj.getUsername() != null, "username is required")
                    .check(obj -> obj.getPassword() != null, "password is required");
            if (validator.errors().isEmpty()) {
                var userAuthDTO = validator.getOrThrow(ValidationException::new);
                var details = authenticationService.authenticate(userAuthDTO);
                JavalinJWT.addTokenToCookie(context, details.get("TOKEN"));
                details.remove("TOKEN");
                details.put("status", "success");
                context.json(details).status(201);
            } else {
                context.status(422).json(validator.errors());

            }
        } catch (ValidationException e) {
            context.json(e.getMessage()).status(400);
        }
    }

        public void register (@NotNull Context ctx){
//        System.out.println(ctx.body());
//        System.out.println(ctx.queryString());
//        System.out.println(ctx.bodyAsClass(UserDTO.class));
//        System.out.println(ctx.queryParamMap());
//        System.out.println(ctx.formParam("userauth"));
            try {
                BodyValidator<UserDTO> validator = ctx.bodyValidator(UserDTO.class)
                        .check(obj -> obj.getUsername() != null, "username is required")
                        .check(obj -> obj.getPassword() != null, "password is required")
                        .check(it -> !it.getRole().equals("ANYONE"), "role above ANYONE is required")
                        .check(obj -> obj.getFname() != null, "smile")
                        .check(obj -> obj.getLname() != null, "smile")
                        .check(obj -> obj.getEmail() != null, "smile")
                        .check(obj -> obj.getPhone() != null, "smile")
                        .check(obj -> obj.getUsertype() != null, "smile");
//                        .check(obj -> obj.getAddressRegion() != null, "smile")
//                        .check(obj -> obj.getAddressAdministrativeArea() != null, "smile")
//                        .check(obj -> obj.getAddressSubadministrativeArea() != null, "smile")
//                        .check(obj -> obj.getAddressLocality() != null, "smile")
//                        .check(obj -> obj.getAddressSubLocality() != null, "smile")
//                        .check(obj -> obj.getAddressThoroughfare() != null, "smile")
//                        .check(obj -> obj.getAddressPremises() != null, "smile")
//                        .check(obj -> obj.getAddressPostalCode() != null, "smile");
                if (validator.errors().isEmpty()) {
                    authenticationService.register(validator.get());
                    ctx.json(Map.of("status", "success")).status(201);
                } else {
                    ctx.status(422).json(validator.errors());
                }
            } catch (ValidationException e) {
                ctx.json(e.getMessage()).status(400);
            }
        }
    }
