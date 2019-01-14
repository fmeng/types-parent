package me.fmeng.types.test;

import me.fmeng.anstore.MarkedUnit;
import me.fmeng.anstore.StoreAnnotationUtil;
import me.fmeng.types.utils.HandlerRegisterUtil;
import me.fmeng.types.utils.JsonUtil;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Test;

import java.util.Set;

/**
 * @author fmeng
 * @since 2019/01/13
 */
public class RegisterUtilTest {

    static {
        System.setProperty(StoreAnnotationUtil.SCAN_PACKAGE_ARGS_NAME_PREFIX
                + ".RegisterUtilTest", "me.fmeng.types.test");
    }

    @Test
    public void anStoreTest() {
        // 触发静态代码初始化
        HandlerRegisterUtil handlerRegisterUtil = new HandlerRegisterUtil();
        Set<MarkedUnit> markedUnits = StoreAnnotationUtil.getMarkedUnits();
        System.out.println(markedUnits);
        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        HandlerRegisterUtil.registerMarkedEnumTypeHander(typeHandlerRegistry);
        HandlerRegisterUtil.registerMarkedJsonTypeHander(typeHandlerRegistry);
        HandlerRegisterUtil.registerMarkedCodeTypeHander(typeHandlerRegistry);
        System.out.println(JsonUtil.encodeQuietly(typeHandlerRegistry));
    }
}
