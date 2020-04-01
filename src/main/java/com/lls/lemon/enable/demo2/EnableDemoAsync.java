package com.lls.lemon.enable.demo2;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/************************************
 * EnableDemoAsync
 * @author liliangshan
 * @date 2020/4/1
 ************************************/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DemoAsyncConfigurationSelector.class)
@Documented
public @interface EnableDemoAsync {

    Class<? extends Annotation> annotation() default Annotation.class;

    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;

    int order() default Ordered.LOWEST_PRECEDENCE;

}
