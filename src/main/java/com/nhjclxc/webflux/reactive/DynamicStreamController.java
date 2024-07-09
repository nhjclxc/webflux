package com.nhjclxc.webflux.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RestController
public class DynamicStreamController {

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
