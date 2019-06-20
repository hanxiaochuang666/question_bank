package cn.eblcu.questionbank.client;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface CheckToken {}
