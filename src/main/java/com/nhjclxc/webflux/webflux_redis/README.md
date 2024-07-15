# webflux
springboot项目实现流式输出 - webflux-mysql

参考：
    https://github.com/JeffLi1993/springboot-learning-example.git


1. 创建项目添加相关依赖【[pom.xml](..%2F..%2F..%2F..%2F..%2F..%2F..%2Fpom.xml)】
```xml
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
     </dependency>
```
2. 添加配置信息
```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    password:
    timeout: 5000
```
3. 创建缓存管理器【[CacheConfig.java](CacheConfig.java)】
4. 编写代码【[ReactiveRedisController.java](ReactiveRedisController.java)】
3. 
