package com.nhjclxc.webflux.controller;

import com.nhjclxc.webflux.dto.TestObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.BaseStream;

/**
 * @author LuoXianchao
 * @since 2024/07/09 22:25
 */
@RestController
@RequestMapping("/mf")
public class Test1_MonoAndFluxController {

//    @GetMapping(value = "/chatgpt-like2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)


    private static final Map<Integer, TestObject> map = new HashMap<>();

    static {
        map.put(1, TestObject.builder().localDateTime(LocalDateTime.now()).localDate(LocalDate.now()).localTime(LocalTime.now()).date(new Date()).string("String").integer(666).aFloat(2.5f).aDouble(22.33).aLong(888L).bigDecimal(new BigDecimal("666.888")).aBoolean(true).build());
        map.put(2, TestObject.builder().localDateTime(LocalDateTime.now()).localDate(LocalDate.now()).localTime(LocalTime.now()).date(new Date()).string("String").integer(666).aFloat(2.5f).aDouble(22.33).aLong(888L).bigDecimal(new BigDecimal("666.888")).aBoolean(true).build());
        map.put(3, TestObject.builder().localDateTime(LocalDateTime.now()).localDate(LocalDate.now()).localTime(LocalTime.now()).date(new Date()).string("String").integer(666).aFloat(2.5f).aDouble(22.33).aLong(888L).bigDecimal(new BigDecimal("666.888")).aBoolean(false).build());
        map.put(4, TestObject.builder().localDateTime(LocalDateTime.now()).localDate(LocalDate.now()).localTime(LocalTime.now()).date(new Date()).string("String").integer(666).aFloat(2.5f).aDouble(22.33).aLong(888L).bigDecimal(new BigDecimal("666.888")).aBoolean(false).build());
        map.put(5, TestObject.builder().localDateTime(LocalDateTime.now()).localDate(LocalDate.now()).localTime(LocalTime.now()).date(new Date()).string("String").integer(666).aFloat(2.5f).aDouble(22.33).aLong(888L).bigDecimal(new BigDecimal("666.888")).aBoolean(true).build());
    }

    @GetMapping("/mono0")
    public Mono<Object> mono0(){
        return Mono.empty().delayElement(Duration.ofMillis(500));
    }
    @GetMapping("/mono1")
    public Mono<String> mono1(){
        return Mono.just("mono1").delayElement(Duration.ofMillis(1000));
    }

    @GetMapping(value = "/mono00", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Object> mono00(){
        return Mono.empty().delayElement(Duration.ofMillis(500));
    }

    @GetMapping( value = "/mono11", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> mono11(){
        return Mono.just("mono11").delayElement(Duration.ofMillis(1000));
    }

    @GetMapping( value = "/mono111", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<TestObject> mono111(){
        return Mono.just(map.get(1)).delayElement(Duration.ofMillis(1000));
    }
    @GetMapping( value = "/mono1111", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TestObject> mono1111(){
        return Mono.just(map.get(1)).delayElement(Duration.ofMillis(1000));
    }

//    @GetMapping("/flux1")
//    public Flux<TestObject> flux1(){
//        map.values()
//    }

    @GetMapping( value = "/flux2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<TestObject> flux2(){
        return Mono.just(map.get(1)).delayElement(Duration.ofMillis(1000));
    }
    @GetMapping( value = "/flux3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TestObject> flux3(){
        return Mono.just(map.get(1)).delayElement(Duration.ofMillis(1000));
    }


}
