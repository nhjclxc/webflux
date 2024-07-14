package com.nhjclxc.webflux.webflux_mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tabUser2")
public class TabUserController {

    @Autowired
    private TabUserHandler tabUserHandler;

//    @PostMapping
//    public Mono<TabUser> save(@RequestBody TabUser user) {
//        return tabUserHandler.save(user);
//    }

//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> deleteById(@PathVariable int id) {
//        return tabUserHandler.deleteById(id);
//    }

//    @PutMapping
//    public Mono<ResponseEntity<TabUser>> update(@RequestBody TabUser user) {
//        return tabUserHandler.update(user);
//    }

//    @GetMapping
//    public Flux<TabUser> findAll() {
//        return tabUserHandler.findAll();
//    }

}
