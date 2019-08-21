package com.lls.lemon.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/************************************
 * DbSwitcher
 * @author liliangshan
 * @date 2019-08-21
 ************************************/
@Configuration
public class DbSwitcher {

    private static final Logger log = LoggerFactory.getLogger(DbSwitcher.class);

    @Autowired
    private Environment evn;
    //私有库数据源key
    private static String  ljyunDataSourceKey = "ljyun_" ;

    /**
     * 切换数据库对外方法,如果私有库id参数非0,则首先连接私有库，否则连接其他已存在的数据源
     * @param dbName 已存在的数据库源对象
     * @param ljyunId 私有库主键
     * @return 返回当前数据库连接对象对应的key
     */
    public String change(String dbName,int ljyunId)
    {
        if( ljyunId == 0){
            toDB(dbName);
        }else {
            toYunDB(ljyunId);
        }
        //获取当前连接的数据源对象的key
        String currentKey = DynamicDataSourceContextHolder.getDataSourceKey();
        log.info("＝＝＝＝＝当前连接的数据库是:" + currentKey);
        return currentKey;
    }

    /**
     * 切换已存在的数据源
     * @param dbName
     */
    private void toDB(String dbName)
    {
        //如果不指定数据库，则直接连接默认数据库
        String dbSourceKey = dbName.trim().isEmpty() ? "default" : dbName.trim();
        //获取当前连接的数据源对象的key
        String currentKey = DynamicDataSourceContextHolder.getDataSourceKey();
        //如果当前数据库连接已经是想要的连接，则直接返回
        if(currentKey.equals(dbSourceKey)) return;
        //判断储存动态数据源实例的map中key值是否存在
        if( DynamicDataSource.isExistDataSource(dbSourceKey) ){
            DynamicDataSourceContextHolder.setDataSourceKey(dbSourceKey);
            log.info("＝＝＝＝＝普通库: "+dbName+",切换完毕");
        }else {
            log.info("切换普通数据库时，数据源key=" + dbName + "不存在");
        }
    }

    /**
     * 创建新的私有库数据源
     * @param ljyunId
     */
    private void  toYunDB(int ljyunId){
        //组合私有库数据源对象key
        String dbSourceKey = ljyunDataSourceKey+String.valueOf(ljyunId);
        //获取当前连接的数据源对象的key
        String currentKey = DynamicDataSourceContextHolder.getDataSourceKey();
        if(dbSourceKey == currentKey) return;

        //创建私有库数据源
        createLjyunDataSource(ljyunId);

        //切换到当前数据源
        DynamicDataSourceContextHolder.setDataSourceKey(dbSourceKey);
        log.info("＝＝＝＝＝私有库: "+ljyunId+",切换完毕");
    }

    /**
     * 创建私有库数据源，并将数据源赋值到targetDataSources中，供后切库用
     * @param ljyunId
     * @return
     */
    private DruidDataSource createLjyunDataSource(int ljyunId){
        //创建新的数据源
        if(ljyunId == 0)
        {
            log.info("动态创建私有库数据时，私有库主键丢失");
        }
        String yunId = String.valueOf(ljyunId);
        DruidDataSource dataSource = new DruidDataSource();
        String prefix = "db.privateDB.";
        String dbUrl = evn.getProperty( prefix + "url-base")
                + evn.getProperty( prefix + "host") + ":"
                + evn.getProperty( prefix + "port") + "/"
                + evn.getProperty( prefix + "dbname").replace("{id}",yunId) + evn.getProperty( prefix + "url-other");
        log.info("+++创建云平台私有库连接url = " + dbUrl);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(evn.getProperty( prefix + "username"));
        dataSource.setPassword(evn.getProperty( prefix + "password"));
        dataSource.setDriverClassName(evn.getProperty( prefix + "driver-class-name"));

        //将创建的数据源，新增到targetDataSources中
        Map<Object,Object> map = new HashMap<>();
        map.put(ljyunDataSourceKey+yunId, dataSource);
        DynamicDataSource.getInstance().setTargetDataSources(map);
        return dataSource;
    }

}
