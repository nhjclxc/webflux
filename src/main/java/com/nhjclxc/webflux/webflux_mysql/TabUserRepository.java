package com.nhjclxc.webflux.webflux_mysql;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LuoXianchao
 * @since 2024/07/12 22:30
 */
@Repository
public interface TabUserRepository extends ReactiveCrudRepository<TabUser, Integer> {
    // ReactiveMongoRepository<对应的表的实体类对象, 该表的注解类型>


}
