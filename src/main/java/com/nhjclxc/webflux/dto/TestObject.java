package com.nhjclxc.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestObject {
    LocalDateTime localDateTime;
    LocalDate localDate;
    LocalTime localTime;
    Date date;
    String string;
    Integer integer;
    Float aFloat;
    Double aDouble;
    Long aLong;
    BigDecimal bigDecimal;
    Boolean aBoolean;
}
