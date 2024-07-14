package com.nhjclxc.webflux.webflux_mysql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author LuoXianchao
 * @since 2024/07/12 22:27
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tab_user")
public class TabUser {
    @Id
    private Integer id;
    private String code;
    private String name;
    private Integer age;
}
