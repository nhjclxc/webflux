package com.nhjclxc.webflux.controller;

import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class FileController {

    private static final DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();


    @GetMapping("/stream-file")
    public Flux<String> streamFile() {
        Path filePath = Paths.get("LICENSE");
        return Flux.using(
                () -> Files.lines(filePath),
                Flux::fromStream,
                BaseStream::close
        ).delayElements(Duration.ofMillis(new Random().nextInt(200) + 80));
    }


    @GetMapping("/stream-file2")
    public Flux<ServerSentEvent<String>> streamFile2() {
        Path filePath = Paths.get("pom.xml");
        return Flux.using(
                        () -> Files.lines(filePath),
                        Flux::fromStream,
                        BaseStream::close
                ).delayElements(Duration.ofMillis(new Random().nextInt(3000) + 100))
                .map(data -> ServerSentEvent.builder(data).build());
    }

    @GetMapping("/stream-file3")
    public Flux<ServerSentEvent<Integer>> streamFile3() {
        return Flux.using(
                        () -> Stream.generate(() -> new Random().nextInt()).limit(5),
                        Flux::fromStream,
                        BaseStream::close
                ).delayElements(Duration.ofMillis(new Random().nextInt(300) + 100))
                .map(data -> ServerSentEvent.builder(data).build());
    }


    @GetMapping("/stream-dynamic")
    public Flux<Object> streamDynamic() {
        return Flux.create(sink -> {
                    String[] dynamicData = {
                            "This", "is", "a", "dynamically", "added", "text", ".",
                            "Enjoy", "the", "stream", "!"};
                    int index = 0;
                    while (index < dynamicData.length) {
                        if (sink.isCancelled()) {
                            break;
                        }
                        sink.next(dynamicData[index]);
                        index++;
                        try {
                            Thread.sleep(1000); // 模拟每秒生成一个数据
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            sink.error(e);
                        }
                    }
                    sink.complete();
                }).delayElements(Duration.ofSeconds(1))
                .subscribeOn(Schedulers.boundedElastic()); // 在异步线程池中执行
    }


}
