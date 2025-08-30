package com.resume.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
@RedisHash("PromoCod")
@Data
public class PromoCod {
    @Id
    Long id;
    String name;
    Integer discount;
    LocalDate date;
}


