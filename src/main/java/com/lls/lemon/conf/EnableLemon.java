package com.lls.lemon.conf;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/************************************
 * EnableLemon
 * @author liliangshan
 * @date 2020/3/31
 ************************************/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LemonManagementSelector.class)
public @interface EnableLemon {

    boolean proxyTargetClass() default false;
    AdviceMode mode() default AdviceMode.PROXY;

}
