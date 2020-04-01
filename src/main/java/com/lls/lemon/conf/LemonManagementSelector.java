package com.lls.lemon.conf;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/************************************
 * LemonManagementSelector
 * @author liliangshan
 * @date 2020/3/31
 ************************************/
public class LemonManagementSelector extends AdviceModeImportSelector<EnableLemon> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        //mode类型的不同
        System.out.println("根据传入的mode，选择注册相应的Bean");
        //注意Configuration的proxyBeanMethods 属性为false
        //实现了ImportBeanDefinitionRegistrar接口，即自动注册
        if (adviceMode == AdviceMode.PROXY) {
            return new String[]{LemonAutoProxyRegistrar.class.getName()};
        }

        if (adviceMode == AdviceMode.ASPECTJ) {
            return new String[]{LemonAutoProxyRegistrar.class.getName()};
        }

        return new String[0];
    }

}
