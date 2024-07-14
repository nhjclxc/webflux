package com.nhjclxc.webflux.repos;

import com.nhjclxc.webflux.dto.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LuoXianchao
 * @since 2024/07/12 22:30
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    // ReactiveMongoRepository<对应的表的实体类对象, 改表的注解类型>


}
