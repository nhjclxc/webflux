package com.nhjclxc.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * https://www.cnblogs.com/1996-Chinese-Chen/p/17913287.html
 */
@RestController
@RequestMapping("h2")
public class Hello2Controller {

    @GetMapping(value = "/GetHello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> GetHello() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Event " + sequence);
    }


    @GetMapping("/characters")
    public Flux<Character> getCharacters() {
        String text = "This is a demo text for streaming characters.";
        char[] charArray = text.toCharArray();

        return Flux.interval(Duration.ofSeconds(1))  // 每隔一秒发出一个递增的长整数
                .map(index -> charArray[index.intValue()])  // 根据索引获取字符
                .take(charArray.length);  // 只取字符数组的长度，即所有字符
    }
}
