package com.nhjclxc.webflux.reactive;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@RestController
public class ChatGptLikeController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> hello() {
        return Flux.just("你好", "世界");
    }

    private final String text = "\"Legal Ent你的ity\" shall mean 阿萨 the union\n";


    @GetMapping("/chatgpt-like")
    public Flux<String> streamText() {
        return Flux.create(sink -> {
            new Thread(() -> {
                for (int i = 0; i < text.length(); i++) {
                    if (sink.isCancelled()) {
                        break;
                    }
                    char c = text.charAt(i);
                    System.out.println(c);
                    sink.next(c);
//                    try {
//                        Thread.sleep(50); // 可选: 模拟处理时间
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        sink.error(e);
//                    }
                }
                sink.complete();
            }).start();
        }).flatMap(character -> {

            System.out.println("character = " + character.toString());
            int delay = new Random().nextInt(3000) + 100; // 随机生成0到200毫秒的延迟
            return Mono.just(character.toString()).delayElement(Duration.ofMillis(delay));
        });
    }


    @GetMapping(value = "/chatgpt-like2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamText2() {
        return Flux.create(sink -> {
            new Thread(() -> {
                for (char c : text.toCharArray()) {
                    if (sink.isCancelled()) {
                        break;
                    }
                    sink.next(String.valueOf(c));
                    try {
                        Thread.sleep(50); // 可选: 模拟处理时间
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        sink.error(e);
                    }
                }
                sink.complete();
            }).start();
        }).flatMap(character -> {
            int delay = new Random().nextInt(3000) + 100; // 随机生成0到200毫秒的延迟
            return Mono.just(character.toString()).delayElement(Duration.ofMillis(delay));
        });
    }
}
