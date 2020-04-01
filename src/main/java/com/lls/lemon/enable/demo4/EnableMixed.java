package com.lls.lemon.enable.demo4;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/************************************
 * EnableMixed
 * @author liliangshan
 * @date 2020/4/1
 ************************************/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MixedAdviceModeImportSelector.class)
public @interface EnableMixed {

    Class<? extends Annotation> annotation() default Annotation.class;

    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;


}
