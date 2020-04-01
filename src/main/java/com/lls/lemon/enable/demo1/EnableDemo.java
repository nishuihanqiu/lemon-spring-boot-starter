package com.lls.lemon.enable.demo1;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/************************************
 * EnableDemo
 * @author liliangshan
 * @date 2020/4/1
 ************************************/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DemoSchedulingConfiguration.class)
@Documented
public @interface EnableDemo {


}
