package me.fmeng.types.test;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Mybatis SqlSession配置
 *
 * @author fmeng
 * @since 2018/03/08
 */
@Configuration
@MapperScan(basePackages = {AppConstant.PROJECT_BASE_PACKAGE})
public class MybatisConfig {


    /**
     * mybatis mapper resource 路径
     */
    private static final String MYBATIS_MAPPER_LOCATIONS = "classpath*:/mapper/**.xml";

    @Bean
    public SqlSessionFactoryBean createSqlSessionFactoryBean(@Autowired DataSource dataSource) throws IOException {
        // 构建结构
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(configuration);
        // 基础配置
        configuration.setCacheEnabled(false);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setSafeRowBoundsEnabled(false);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        configuration.setDefaultStatementTimeout(600);
        configuration.setLogImpl(Slf4jImpl.class);
        // 添加mapper 扫描路径
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MYBATIS_MAPPER_LOCATIONS));
        // 设置datasource
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

}
