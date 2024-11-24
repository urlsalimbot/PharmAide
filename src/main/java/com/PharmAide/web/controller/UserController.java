package com.PharmAide.web.controller;


import com.PharmAide.domain.dao.User;
import com.PharmAide.domain.service.UserService;
import com.google.inject.Inject;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.validation.BodyValidator;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class UserController {

    private final UserService userServ;

    @Inject
    public UserController(UserService userServ) {
        this.userServ = userServ;
    }

    public void create(@NotNull Context context) {
        try {
            BodyValidator<User> validator = context.bodyValidator(User.class)
                    .check(it -> it.getFname() != null, "user must have first name")
                    .check(it -> it.getLname() != null, "user must have last name");
            if (validator.errors().isEmpty()) {
                try {
                    var res = userServ.addUser(validator.get());
                    context.json(res).status(201);
                } catch (Exception e) {
                    context.json(Map.of("error", e,
                            "User", "Does not Exist")).status(500);
                }
            } else {
                context.status(422).json(validator.errors());
            }

        }catch (Exception e) {
            context.status(422).json(Map.of("error", e.getMessage()));
        }
    }

    /**
     * @param context
     */

    public void getAll(@NotNull Context context) {
        try {
            var result = userServ.getAll();
            if (!result.isEmpty()){
                context.json(result).status(200);
            } else {
                context.json(Map.of("status", "error")).status(500);
            }
        } catch (Exception e) {
            context.json(Map.of("error", e,
                    "Users", "Does not Exist")).status(500);
        }
    }

    public void getOne(@NotNull Context context, @NotNull String s) {
        try {
            var res = userServ.getbyUsername(s);
            if (res != null) {
                context.json(res).status(200);
            } else {
                context.json(Map.of("status", "error")).status(500);
            }
        } catch (Exception e) {
            context.json(Map.of("status", "error",
                    "User", "Does not Exist")).status(500);
        }

    }

    public void getbyID(@NotNull Context context, @NotNull String s) {
        try {
            var res = userServ.getUserbyID(Integer.parseInt(s));
            if (res != null) {
                context.json(res).status(200);
            } else {
                context.json(Map.of("status", "error")).status(500);
            }
        } catch (Exception e) {
            context.json(Map.of("User", "Does not exist")).status(500);
        }

    }

    public void update(@NotNull Context context, @NotNull String s) {
        try {
            BodyValidator<User> validator = context.bodyValidator(User.class)
                    .check(it -> it.getFname() != null, "user must have first name")
                    .check(it -> it.getEmail() != null, "user must have email")
                    .check(it -> it.getPhone() != null, "user must have job");
            if (validator.errors().isEmpty()) {
                try {
                    var res = userServ.update(validator.get(), s);
                    if (res != null) {
                        context.json(res).status(201);
                    } else {
                        context.json(Map.of("status", "error")).status(500);
                    }
                } catch (Exception e) {
                    context.json(Map.of("error", e,
                            "User", "Does not Exist")).status(500);
                }
            } else {
                context.json(validator.errors()).status(422);
            }
        }catch (Exception e) {
            context.json(Map.of("error", e.getMessage())).status(422);
        }
    }


    public void delete(@NotNull Context context, @NotNull String s) {
        try {
            userServ.delete(s);
            context.json(Map.of("status", "success")).status(204);
        } catch (Exception e) {
            context.json(Map.of("status", "error",
                    "User", "Does not Exist")).status(500);
        }
    }
}
