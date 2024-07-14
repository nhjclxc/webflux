package com.nhjclxc.webflux.router;

import com.nhjclxc.webflux.dto.User;
import com.nhjclxc.webflux.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author LuoXianchao
 * @since 2024/07/13 21:53
 */
@Component
@AllArgsConstructor
public class UserHandler {

    private final UserRepository userRepository;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.findAll(), User.class);
    }

    // 下面这个方法是没有任何业务逻辑的，在现实工作中几乎是寸步难行的
//    public Mono<ServerResponse> save(ServerRequest request){
//        Mono<User> userMono = request.bodyToMono(User.class);
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(userRepository.saveAll(userMono), User.class);
//    }

    /*
{
    "name": "啊啊啊",
    "age": 80
}
     */
    public Mono<ServerResponse> save(ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);
        // 拿到了user的Mono对象，我们要加入我们的业务进去，这样才能应用起来
        // 要对数据进行操作的时候都是使用flatMap方法来操作数据
       return userMono.flatMap(user -> {
            Integer age = user.getAge();
            if (age > 60){
                throw new RuntimeException("年龄太大了！！！");
            }
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(userRepository.saveAll(userMono), User.class);
        });
    }


    public Mono<ServerResponse> deleteById(ServerRequest request){
       String id = request.pathVariable("id"); // @PathVariable
       return this.userRepository.findById(id)
                .flatMap(user -> this.userRepository.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

}
