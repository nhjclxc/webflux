package com.nhjclxc.webflux.webflux_mysql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RouterConfiguration {
    @Bean
    public RouterFunction<ServerResponse> tabUserRouterFunction(TabUserHandler tabUserHandler) {

        // RequestPredicates.path("/tabUser")类似于 @RequestMapping("Hello")
        // 下面的每一个 RouterFunctions.route(RequestPredicates.POST("/save"), tabUserHandler::save) 类似于 @GetMapping("/stream-file")
        return RouterFunctions.nest(RequestPredicates.path("/tabUser"),
                RouterFunctions.route(RequestPredicates.POST("/save").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), tabUserHandler::save)
                        .andRoute(RequestPredicates.DELETE("/deleteById/{id}"), tabUserHandler::deleteById)
                        .andRoute(RequestPredicates.PUT("/update").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), tabUserHandler::update)
                        .andRoute(RequestPredicates.GET("/findAll"), tabUserHandler::findAll)
                        .andRoute(RequestPredicates.GET("/findById/{id}"), tabUserHandler::findById));
    }
}
