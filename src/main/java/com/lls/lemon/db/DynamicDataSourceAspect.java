package com.lls.lemon.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/************************************
 * DynamicDataSourceAspect
 * 动态数据源切换的切面，切 DAO 层，通过 DAO 层方法名判断使用哪个数据源，实现数据源切换
 * 关于切面的 Order 可以可以不设，因为 @Transactional 是最低的，取决于其他切面的设置，
 * 并且在 org.springframework.core.annotation.AnnotationAwareOrderComparator 会重新排序
 *
 * 注意：本项目因为是外部传递进来的云编号，根据动态创建数据源实例，并且进行切换，而这种只用dao层切面的方式，
 *  　适用于进行多个master/slave读写分类用的场景，所以我们的项目用不到这种方式（我们如果使用这种方式，
 *    就需要修改daoAai入参方式，在前置处理器获取dao的方法参数，根据参数切换数据库，这样就需要修改dao接口，
 *    以及对应mapper.xml，需要了解动态代理的知识，所以目前我们没有使用该方式，目前我们使用的是
 *    在service或controller层手动切库）
 * @author liliangshan
 * @date 2019-08-21
 ************************************/
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private final String[] QUERY_PREFIX = {"select"};

    @Pointcut("@annotation(DbSwitch)")
    public void daoAspect() {
    }

    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        Object[] params = point.getArgs();
        System.out.println(params.toString());
        String param = (String) params[0];
        for (Object string:params
        ) {
            System.out.println(string.toString());
        }
        System.out.println("###################################################");
        System.out.println(point.getSignature().getName());
        Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        //DynamicDataSourceContextHolder.setDataSourceKey("slave");
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.setDataSourceKey("slave");
            logger.info("Switch DataSource to [{}] in Method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
        }
    }

    @After("daoAspect())")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.info("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

    private Boolean isQueryMethod(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}
