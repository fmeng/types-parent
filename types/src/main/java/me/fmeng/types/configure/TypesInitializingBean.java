package me.fmeng.types.configure;

import me.fmeng.types.utils.HandlerRegisterUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 注入TypeHandler
 *
 * @author fmeng
 * @since 2019/01/12
 */
public class TypesInitializingBean {

    protected SqlSessionFactory sqlSessionFactory;
    protected TypeAliasRegistry typeAliasRegistry;
    protected TypeHandlerRegistry typeHandlerRegistry;
    private volatile boolean registerDone = false;

    public TypesInitializingBean(@Autowired SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.typeAliasRegistry = this.sqlSessionFactory.getConfiguration().getTypeAliasRegistry();
        this.typeHandlerRegistry = this.sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
    }

    @PostConstruct
    public void init() throws Exception {
        if (registerDone) {
            return;
        }
        HandlerRegisterUtil.registerMarkedEnumTypeHander(typeHandlerRegistry);
        HandlerRegisterUtil.registerMarkedJsonTypeHander(typeHandlerRegistry);
        HandlerRegisterUtil.registerMarkedCodeTypeHander(typeHandlerRegistry);
        registerDone = true;
    }
}
