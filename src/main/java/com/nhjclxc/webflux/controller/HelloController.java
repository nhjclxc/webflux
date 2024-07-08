package com.nhjclxc.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.*;

/**
 * https://www.cnblogs.com/1996-Chinese-Chen/p/17913287.html
 */
@RestController
@RequestMapping("Hello")
public class HelloController {

    public HelloController() { }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
    public static void main(String[] args) {

        Map<String, String> map = new LinkedHashMap<>();
        map.put("localDateTime", "localDateTime数据");
        map.put("localDate", "localDate数据");
        map.put("localTime", "localTime数据");
        map.put("date", "date数据");
        map.put("string", "string数据");
        map.put("integer", "integer数据");
        map.put("aFloat", "aFloat数据");
        map.put("aDouble", "aDouble数据");
        map.put("aLong", "aLong数据");
        map.put("bigDecimal", "bigDecimal数据");
        map.put("aBoolean", "aBoolean数据");
        List<String> attributeList = new ArrayList<>(map.keySet());


        String[] array = attributeList.toArray(new String[0]);
        System.out.println(array);
    }

    @GetMapping(value = "/GetHello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> GetHello() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Event " + sequence);
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
