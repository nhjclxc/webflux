package com.nhjclxc.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

/**
 * https://www.cnblogs.com/1996-Chinese-Chen/p/17913287.html
 */
@RestController
@RequestMapping("Hello")
public class HelloController {


    @GetMapping("/characters")
    public Flux<Character> getCharacters() {
        String text = "This is a demo text for streaming characters.";
        char[] charArray = text.toCharArray();

        return Flux.interval(Duration.ofSeconds(1))  // 每隔一秒发出一个递增的长整数
                .map(index -> charArray[index.intValue()])  // 根据索引获取字符
                .take(charArray.length);  // 只取字符数组的长度，即所有字符
    }

    @GetMapping(value = "/GetHello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> GetHello() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Event " + sequence);
    }

    @GetMapping(value = "/multiplicationTable", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> multiplicationTable(int input){
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("react-math-service processing : " + i))
                .map(i -> i * input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<Object> monoError(@PathVariable int input) {
        return  Mono.just(input)
                .handle((integer, sink) -> {
                    if (integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new RuntimeException("integer"));
                })
                .cast(Object.class);
//                .flatMap(i -> i)
    }


    @GetMapping("square/{input}/assignment")
    public Mono<ResponseEntity<Integer>> assignment(@PathVariable int input) {
        return  Mono.just(input)
                .filter(i -> i >= 10 && i <= 20)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    /*
    @GetMapping(value = "/GetHellos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void GetHellos(HttpServletResponse response) throws Exception {
        if (response.containsHeader("Content-Type")) {
            response.setHeader("Content-Type", "text/event-stream");
        } else {
            response.setHeader("Content-Type", "text/event-stream");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Connection", "keep-alive");
        }
        String data = "id:" + new Random().nextInt() + " \n" +
                "retry: " + new Random().nextInt() * 30 + "\n" +
                "event: message\n" +
                "data: " + new Random().nextInt() + "\n\n";
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data);
    }
    <!DOCTYPE html>
<html>
<head>
    <title>SSE Example</title>
    <script>
        var eventSource = new EventSource("http://localhost:5203/WeatherForecast/Posta");
        eventSource.addEventListener("message", function(event) {
var a=document.getElementById("aaa");
a.innerHTML+="<a>"+event.data+"</a><br>"
            console.log("Received message: " + event.data);
        });
        eventSource.addEventListener("error", function(event) {
            console.log("Error occurred");
        });
    </script>
</head>
<body>
<div id='aaa'></div>
</body>
</html>
     */

//    public static void main(String[] args) {
//        Flux<String> flux = Flux.just("Hello", "emanjusaka", "!");
//        flux.subscribe(System.out::println);
//    }

}
