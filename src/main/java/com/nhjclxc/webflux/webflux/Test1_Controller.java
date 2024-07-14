package com.nhjclxc.webflux.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author LuoXianchao
 * @since 2024/07/12 21:51
 */
@Slf4j
@RestController
@RequestMapping("/webflux1")
public class Test1_Controller {


    public static String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ignored) {
        }
        return System.currentTimeMillis() + "";
    }

    @GetMapping("1")
    public String mvcget(){
        log.info("mvcget.start");
        String str = createStr();
        log.info("mvcget.end");
        return str;
    }

    @GetMapping("2")
    public Mono<String> monoget(){
        log.info("monoget.start");
        Mono<String> stringMono = Mono.fromSupplier(Test1_Controller::createStr);
        log.info("monoget.end");
        return stringMono;
    }

    @GetMapping(value = "3")
    public Flux<String> fluxget(){
        log.info("fluxget.start");
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("Flux1", "Flux2", "Flux3"));
        log.info("fluxget.end");
        return stringFlux;
    }
    @GetMapping(value = "4")
    public Flux<String> fluxget2(){
        log.info("fluxget.start");
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("Flux1", "Flux2", "Flux3"))
                .delaySubscription(Duration.ofMillis(200))
                .delayElements(Duration.ofMillis(500))
                ;
        log.info("fluxget.end");
        return stringFlux;
    }

    @GetMapping(value = "5", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxget3(){
        log.info("fluxget.start");
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("Flux1", "Flux2", "Flux3"));
        log.info("fluxget.end");
        return stringFlux;
    }





}
