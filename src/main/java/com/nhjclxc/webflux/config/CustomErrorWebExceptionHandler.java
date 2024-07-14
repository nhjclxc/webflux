package com.nhjclxc.webflux.config;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.logging.Handler;

/**
 * @author : cuixiuyin
 * @date : 2019/11/2
 */
@Component
// 由于SpringFulx内部有很多异常处理机制，这些异常处理机制都是有优先级的，因此要把我们自定义的异常处理优先级调高，否则框架的异常处理会优先执行，那么这样的话我们自定义的异常护理机制就没用了
@Order(-2) // 数值越小优先级越高
//public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {
public class CustomErrorWebExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        ServerHttpResponse response = serverWebExchange.getResponse();
        // 设置响应头
        response.setStatusCode(HttpStatus.OK);
        // 设置返回值类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 返回异常信息
        String errorMsg = throwable.toString();
        String json = "{\"msg\": "+errorMsg+"}";
        DataBuffer dataBuffer = response.bufferFactory().wrap(json.getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }


//    /**
//     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
//     *
//     * @param errorAttributes    the error attributes
//     * @param resourceProperties the resources configuration properties
//     * @param errorProperties    the error configuration properties
//     * @param applicationContext the current application context
//     */
//    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
//        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
//    }
//
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        // return RouterFunctions
//        //         .route(aPredicate, aHandler)
//        //         .andRoute(anotherPredicate, anotherHandler);
//        return super.getRoutingFunction(errorAttributes);
//    }



}
