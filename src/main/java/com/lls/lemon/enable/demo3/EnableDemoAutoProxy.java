package com.lls.lemon.enable.demo3;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/************************************
 * EnableDemoAutoProxy
 * @author liliangshan
 * @date 2020/4/1
 ************************************/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DemoAutoProxyRegistrar.class)
public @interface EnableDemoAutoProxy {

    boolean proxyTargetClass() default false;

    boolean exposeProxy() default false;

}
