package com.zzz.framework.starter.data.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.zzz.framework.starter.data.handler.MyMetaObjectHandler;
import com.zzz.framework.starter.data.props.ZzzMybatisPlusProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author zhouzhanqi
 * @date 2021/7/22 10:11 上午
 * @desc mybatisplus自动装配
 */
@Configuration
@EnableConfigurationProperties(ZzzMybatisPlusProperties.class)
public class MybatisPlusAutoConfiguration {

    @Autowired
    private ZzzMybatisPlusProperties shadowlayoverMybatisPlusProperties;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //多租户插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                //todo 从
                return new LongValue();
            }

            /**
             * 获取多租户字段名
             * @return
             */
            @Override
            public String getTenantIdColumn() {
                return TenantLineHandler.super.getTenantIdColumn();
            }

            /**
             * 过滤不需要根据租户隔离的表
             * @param tableName
             * @return
             */
            @Override
            public boolean ignoreTable(String tableName) {
                Set<String> ignoreTables = shadowlayoverMybatisPlusProperties.getMultiTenant().getIgnoreTables();
                return ignoreTables.stream().anyMatch(ignoreTable -> ignoreTable.equals(tableName));
            }
        }));

        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //sql性能规范
        // interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        //防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MyMetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }






}
