package com.nhjclxc.webflux.webflux;

import com.nhjclxc.webflux.dto.JsonResult;
import com.nhjclxc.webflux.dto.User;
import com.nhjclxc.webflux.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * SSE  Server-Sent Event
 *
 * @author LuoXianchao
 * @since 2024/07/12 21:51
 */
@Slf4j
@RestController
@RequestMapping("/sse")
public class Test1_SSE_Controller {


    public static String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ignored) {
        }
        return System.currentTimeMillis() + "";
    }
/**
 使用步骤：
 1.到mongodb依赖如下
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
     </dependency>

 2.springWebflux项目开启响应式支持
     在启动类里面@EnableReactiveMongoRepositories
 3.定义实体类
     定义一个实体类com.nhjclxc.webflux.dto.User
 4.定义相关仓库
     com.nhjclxc.webflux.repos.UserRepository
 5.启动mongodb
 6.配置文件配置mongodb
 使用
 */

    @Autowired
    private UserRepository userRepository;

    @GetMapping("user1")
    public Flux<User> getUser1(){
        return userRepository.findAll();
    }

    @GetMapping(value = "stream/user1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetUser1(){
        return userRepository.findAll();
    }


    @GetMapping("save")
    public Flux<User> save(){
        List<User> users = Arrays.asList(User.builder().name("张三").age(16).build(), User.builder().name("里斯").age(25).build(), User.builder().name("王五").age(36).build());
        Flux<User> insert = userRepository.insert(users);
        System.out.println(insert);
        return insert;
    }


    @GetMapping("delete/{id}")
    public Mono<JsonResult<Object>> delete(@PathVariable("id") String id){
        // deleteById没有返回值不知道删除成功还是失败。所以deleteById方法不能直接使用
//        Mono<Void> voidMono = userRepository.deleteById(id);

        //先通过id查找，如果找到了有对应id的数据，则执行删除返回200，否则返回404表示未找到对应id的数据

        // flatMap当要操作数据，并且要返回一个个Mono对象时使用flayMap
        // map 不操作数据，只是对数据进行转化时，使用map
        return this.userRepository.findById(id)
                // 由于delete方法没有返回值，因此删除之后直接使用then方法返回成功
                .flatMap(user -> this.userRepository.delete(user).then(Mono.just(JsonResult.error("未找到对应数据，删除失败"))))
                // defaultIfEmpty表示userRepository.findById(id)没有找到对应的数据的返回执行defaultIfEmpty，此时返回404表示未找到对应的数据
                .defaultIfEmpty(JsonResult.success("成功"));
    }

    @GetMapping("update/{id}")
    public Mono<JsonResult<User>> update(@PathVariable("id") String id){
        return this.userRepository.findById(id)
                // Repository没有提供update方法，都是统一的save方法，如果没有id就是新增，有id就是修改
                .flatMap(user -> this.userRepository.save(User.builder().id(id).name(user.getName() + System.currentTimeMillis()).age(user.getAge() + 10).build()))
                .map(JsonResult::success)
                .defaultIfEmpty(JsonResult.success("修改失败，未找到对应的数据"));
    }


    @GetMapping("findAll")
    public Flux<User> findAll(){
        return this.userRepository.findAll();
    }

    @GetMapping("findAlld2")
    public Flux<User> findAll2(){
        return this.userRepository.findAll().delayElements(Duration.ofMillis(500));
    }
    @GetMapping(value = "findAlld3")
    public Flux<User> findAll3(){
        return this.userRepository.findAll()
                .flatMap(user -> {
                    int delay = new Random().nextInt(2000) + 100; // 随机生成0到200毫秒的延迟
                    return Mono.just(user).delayElement(Duration.ofMillis(delay));
                });
    }
    @GetMapping(value = "findAlld4", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findAll4(){
        return this.userRepository.findAll()
                .flatMap(user -> {
                    int delay = new Random().nextInt(2000) + 100; // 随机生成0到200毫秒的延迟
                    return Mono.just(user).delayElement(Duration.ofMillis(delay));
                });
    }
    @GetMapping(value = "findAlld5", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findAll5(){
        return this.userRepository.findAll().delayElements(Duration.ofMillis(500));
    }

/*
show dbs;
use webflux;
db.user.find();
*/

}
