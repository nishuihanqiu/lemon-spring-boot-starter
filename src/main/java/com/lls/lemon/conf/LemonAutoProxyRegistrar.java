package com.lls.lemon.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/************************************
 * LemonAutoProxyRegistrar
 * @author liliangshan
 * @date 2020/3/31
 * @see AutoProxyRegistrar
 ************************************/
public class LemonAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {


    private static Logger logger = LoggerFactory.getLogger(LemonAutoProxyRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // source code 中 @see AutoProxyRegistrar
        boolean candidateFound = false;
        Set<String> annTypes = importingClassMetadata.getAnnotationTypes();
        for (String annType : annTypes) {
            AnnotationAttributes candidate = AnnotationAttributes
                    .fromMap(importingClassMetadata.getAnnotationAttributes(annType, false));
            if (candidate == null) {
                continue;
            }
            Object mode = candidate.get("mode");
            Object proxyTargetClass = candidate.get("proxyTargetClass");
            if (mode != null && proxyTargetClass != null && AdviceMode.class == mode.getClass() &&
                    Boolean.class == proxyTargetClass.getClass()) {
                candidateFound = true;
                if (mode == AdviceMode.PROXY) {
                    AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
                    if ((Boolean) proxyTargetClass) {
                        AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
                        return;
                    }
                }
            }
        }
        if (!candidateFound && logger.isInfoEnabled()) {
            String name = getClass().getSimpleName();
            logger.info(String.format("%s was imported but no annotations were found " +
                    "having both 'mode' and 'proxyTargetClass' attributes of type " +
                    "AdviceMode and boolean respectively. This means that auto proxy " +
                    "creator registration and configuration may not have occurred as " +
                    "intended, and components may not be proxied as expected. Check to " +
                    "ensure that %s has been @Import'ed on the same class where these " +
                    "annotations are declared; otherwise remove the import of %s " +
                    "altogether.", name, name, name));
        }

// 自定义逻辑
        //获取所有的注解
        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
        for (String annType : annotationTypes) {
            AnnotationAttributes candidate = AnnotationAttributes.
                    fromMap(importingClassMetadata.getAnnotationAttributes(annType, false));
            if (candidate == null) {
                continue;
            }
            //获取注解属性
            Object mode = candidate.get("mode");   //AdviceMode 枚举类型
            Object proxyTargetClass = candidate.get("proxyTargetClass");  //Boolean类型
            //判断是否是这个注解
            if (mode != null && proxyTargetClass != null
                    && AdviceMode.class == mode.getClass() && Boolean.class == proxyTargetClass.getClass()) {
                //注册Bean(获取BeanName)
                if (registry.containsBeanDefinition("uuService")) {
                    BeanDefinition apcDefinition = registry.getBeanDefinition("uuService");
                    //修改BeanDefinition类型
                    apcDefinition.setBeanClassName(XUserService.class.getName());
                    logger.info("Spring容器中存在bean对象");
                    continue;
                }
                RootBeanDefinition beanDefinition = new RootBeanDefinition(UService.class);
                beanDefinition.setSource(null);
                beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
                //Bean的名字
                registry.registerBeanDefinition("uuService", beanDefinition);
                logger.info("bean已经注册了");
            }
        }

    }

}
