package com.nhjclxc.webflux.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

/**
 * @author LuoXianchao
 * @since 2024/07/13 21:29
 */

@Configuration
public class AllRouters {

    /**
     * 注册路由
     */
    @Bean
    public RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return RouterFunctions.nest(
                    // 这个路由的统一前缀，相当于springmvc控制器类上面的 @RequestMapping("/user")
                    RequestPredicates.path("/user"),
                    // 具体的一个方法，相当于springmvc控制器类上面某个接口的 @GetMapping("/findAll")
                    RouterFunctions.route(RequestPredicates.GET("/findAll"), userHandler::findAll)
                            .andRoute(RequestPredicates.POST("/save").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::save)
                            .andRoute(RequestPredicates.DELETE("/deleteById/{id}"), userHandler::deleteById)
               );
    }

}
