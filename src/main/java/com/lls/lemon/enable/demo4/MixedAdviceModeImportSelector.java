package com.lls.lemon.enable.demo4;

import com.lls.lemon.enable.demo3.DemoAutoProxyRegistrar;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/************************************
 * DemoAdviceModeImportSelector
 * @author liliangshan
 * @date 2020/4/1
 ************************************/
public class MixedAdviceModeImportSelector extends AdviceModeImportSelector<EnableMixed> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        //mode类型的不同
        System.out.println("根据传入的mode，选择注册相应的Bean");
        //注意Configuration的proxyBeanMethods 属性为false
        //实现了ImportBeanDefinitionRegistrar接口，即自动注册
        if (adviceMode == AdviceMode.PROXY) {
            return new String[]{MixedProxyRegistrar.class.getName()};
        }
        return new String[]{DemoAutoProxyRegistrar.class.getName()};
    }

}
