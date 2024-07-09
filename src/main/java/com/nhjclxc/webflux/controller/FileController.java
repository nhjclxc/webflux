package com.nhjclxc.webflux.controller;

import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;
import java.util.stream.BaseStream;

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


}
