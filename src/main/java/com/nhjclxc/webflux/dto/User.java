package com.nhjclxc.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author LuoXianchao
 * @since 2024/07/12 22:27
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user") //注意：collection
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
}
