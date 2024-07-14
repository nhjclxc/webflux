package com.nhjclxc.webflux.webfluxclient;

import com.nhjclxc.webflux.dto.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IUserAPI {

    @GetMapping("findAll")
    public Flux<User> findAll();

    @GetMapping("getUserById/{id}")
    public Mono<User> getUserById(@PathVariable("id") String id);

    @DeleteMapping("deleteById/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id);

    @DeleteMapping("save")
    public Mono<User> save(@RequestBody Mono<User> user);
}
