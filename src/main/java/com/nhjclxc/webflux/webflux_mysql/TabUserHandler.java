package com.nhjclxc.webflux.webflux_mysql;

import com.nhjclxc.webflux.dto.JsonResult;
import com.nhjclxc.webflux.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author LuoXianchao
 * @since 2024/07/14 17:07
 */
@Component
@AllArgsConstructor
public class TabUserHandler {

    private final TabUserRepository tabUserRepository;

    /**
     * 增加
     */
    public Mono<ServerResponse> save(ServerRequest serverRequest){
        Mono<TabUser> tabUserMono = serverRequest.bodyToMono(TabUser.class);
        return tabUserMono.flatMap(user -> {
            // 这里进行新增的业务逻辑
            System.out.println(user);

            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(this.tabUserRepository.save(user), TabUser.class);
        });
    }

    /**
     * 删除
     */
    public Mono<ServerResponse> deleteById(ServerRequest serverRequest){
        // 先判断是不是有，有则删除，没有则抛出不存在的异常

        // 获取id
        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));
//        return this.tabUserRepository.findById(id)
//                .flatMap(user -> this.tabUserRepository.deleteById(id).then(ServerResponse.ok().build()))
//                .switchIfEmpty(ServerResponse.badRequest().build());
        return this.tabUserRepository.findById(id)
                .flatMap(user -> this.tabUserRepository.deleteById(id)
                        .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(JsonResult.success("删除成功"))))
                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(JsonResult.error("不存在该id数据")));
    }


    /**
     * 修改
     */
    public Mono<ServerResponse> update(ServerRequest serverRequest){
        Mono<TabUser> tabUserMono = serverRequest.bodyToMono(TabUser.class);
        return tabUserMono.flatMap(user ->
                // user对象有id就是修改，没有就是新增
                this.tabUserRepository.save(user)
                        .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(JsonResult.success("修改成功")))
        );
    }

    /**
     * 查询所有
     */
    public Mono<ServerResponse> findAll(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(this.tabUserRepository.findAll(), TabUser.class)
                .delayElement(Duration.ofMillis(1000));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(this.tabUserRepository.findAll());
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(JsonResult.success(this.tabUserRepository.findAll()));
    }

    /**
     * 根据id查询
     */
    public Mono<ServerResponse> findById(ServerRequest serverRequest){
        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(this.tabUserRepository.findById(id), TabUser.class);
    }



}
