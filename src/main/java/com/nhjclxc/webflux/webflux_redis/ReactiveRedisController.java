package com.nhjclxc.webflux.webflux_redis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;

@Slf4j
@RestController
@RequestMapping(value = "/redis")
public class ReactiveRedisController {

    @Resource
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @GetMapping(value = "/set")
    public void setTest(String key, String value){
        reactiveRedisTemplate.opsForValue().set(key, value, Duration.ofMinutes(5))
                .subscribe(res -> {
                            log.info("新增缓存信息，信息内容key：{}，value：{}，结果：{}", key, value, res);
                        }
                );
    }

    @GetMapping(value = "/get")
    public Mono<String> getTestRedis(String key){
        return reactiveRedisTemplate.opsForValue().get(key);
    }



    @GetMapping("saveCity")
    public Mono<City> saveCity() {
        City city = City.builder().cityName("北京").description("中国首都").provinceId(1L).id(System.currentTimeMillis()).build();
        String key = "city_" + city.getId();
        return reactiveRedisTemplate.opsForValue().getAndSet(key, JSON.toJSONString(city))
                .map(cityStr -> JSONObject.parseObject(cityStr, City.class));
    }

    @GetMapping(value = "/findCityById/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        String key = "city_" + id;
        return reactiveRedisTemplate.opsForValue().get(key)
                .map(cityStr -> JSONObject.parseObject(cityStr, City.class));
    }

    @GetMapping(value = "/delete/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        String key = "city_" + id;
        // 先检查是否有改key对应的数据
//        reactiveRedisTemplate.opsForValue().get(key)
//                .flatMap(cityCity -> reactiveRedisTemplate.delete(key).then(Mono.just(JSONObject.parseObject(cityCity, City.class))))
//                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue("xx"));

        return reactiveRedisTemplate.delete(key); // 返回0表示没对应key的数据，返回 > 0表示删除的数量
    }




    // 要想使用以下注解，必须先在启动类上面开启@EnableCaching


    @GetMapping(value = "/testCacheable")
    @Cacheable(cacheNames = "city", key = "#id")
    public City testCacheable(Long id){
        return City.builder().cityName("北京").description("中国首都").provinceId(id).id(System.currentTimeMillis()).build();
    }

    /**
     * @Cacheable 用于读取缓存，
     */
    @GetMapping(value = "/getCityById")
    @Cacheable(cacheNames = "citys", key = "#id")
    public Mono<String> getCityById(String id) {
        return null;
    }

    /**
     * @CachePut 用于更新缓存，
     *      这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中，实现缓存与数据库的同步更新。
     */
    @PostMapping(value = "/saveCity2")
    @CachePut(cacheNames = {"citys"}, key = "#city.id")
    public Mono<Boolean> saveCity2(@RequestBody City city) {
//        {
//            "cityName": "北京",
//                "description": "中国首都",
//                "id": 1721052409128,
//                "provinceId": 1
//        }
        System.out.println(city);
        return null;
    }

    /**
     * @CacheEvict 用于清除缓存
     */
    @GetMapping(value = "/deleteCityById")
    @CacheEvict(cacheNames = "citys", key = "#id")
    public Mono<Boolean> deleteCityById(String id) {
        return null;
    }
}
