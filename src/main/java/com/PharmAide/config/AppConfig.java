package com.PharmAide.config;

import com.PharmAide.domain.repository.TransactionRepository;
import com.PharmAide.domain.repository.interfaces.ITransactionRepository;
import com.PharmAide.domain.service.TransactionService;
import com.PharmAide.security.RoleUser;
import com.PharmAide.security.util.AuthHandler;
import com.PharmAide.web.controller.AuthenticationController;
import com.PharmAide.web.controller.StoreController;
import com.PharmAide.web.controller.TransactionController;
import com.PharmAide.web.controller.UserController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;


public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private static final Injector injector = Guice.createInjector(new ModuleConfig());

    private static AuthenticationController authenticationController = injector.getInstance(AuthenticationController.class);
    private static StoreController storeController = injector.getInstance(StoreController.class);
    private static TransactionController transactionController = injector.getInstance(TransactionController.class);
    private static UserController userController = injector.getInstance(UserController.class);

    @NotNull
    public static Javalin mainSetup() {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableSslRedirects();
            config.staticFiles.add("/static", Location.CLASSPATH);
            config.spaRoot.addFile("/", "/static/index.html");
            config.bundledPlugins.enableCors(corsPluginConfig -> {
                corsPluginConfig.addRule(it -> {
                    it.allowCredentials = true;
                    it.reflectClientOrigin = true;
                    it.exposeHeader("Access-Control-Allow-Origin");
                });
            });

            //routes
            config.router.apiBuilder(() -> {
                post("/login", ctx -> authenticationController.login(ctx));
                post("/register", ctx -> authenticationController.register(ctx));

                path("/app", () -> {
                    //users api
                    path("/user", () -> {
                        get("/", ctx -> userController.getOne(ctx, ctx.queryParam("username")),
                                RoleUser.CUSTOMER, RoleUser.ADMIN, RoleUser.RETAILER);
                    });

                    //transactions API
                    path("/transactions", () -> {
                        get("/", ctx -> transactionController.getAllTransac(ctx, ctx.queryParam("store_id")),
                                RoleUser.CUSTOMER, RoleUser.ADMIN, RoleUser.RETAILER);
                        post("/", ctx -> transactionController.saveTransac(ctx),
                                RoleUser.CUSTOMER, RoleUser.ADMIN, RoleUser.RETAILER);
                    });

                    //store API
                    path("/store", () -> {
                        get("/", ctx -> storeController.getAllProducts(ctx),
                                RoleUser.CUSTOMER, RoleUser.ADMIN, RoleUser.RETAILER);
                        get("/stats", ctx -> storeController.getStoreStats(ctx),
                                RoleUser.CUSTOMER, RoleUser.ADMIN, RoleUser.RETAILER);
                        put("/",ctx -> storeController.updateStocks(ctx, ctx.header("username")),
                                RoleUser.ADMIN, RoleUser.RETAILER);
                    });
                });
            });
        });


        app.before(AuthHandler.decode);
        app.beforeMatched("/app", AuthHandler.decode);
        return app;
    }
}
