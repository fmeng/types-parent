package me.fmeng.types.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import me.fmeng.anstore.MarkedUnit;
import me.fmeng.anstore.StoreAnnotationUtil;
import me.fmeng.types.CodeTypeHandler;
import me.fmeng.types.EnumTypeHandler;
import me.fmeng.types.JsonTypeHandler;
import me.fmeng.types.annotation.CodeType;
import me.fmeng.types.annotation.EnumType;
import me.fmeng.types.annotation.JsonType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.Set;

/**
 * 注入Handler帮助类
 *
 * @author fmeng
 * @since 2018/01/26
 */
@Slf4j
public class HandlerRegisterUtil {

    static {
        initAnnotationsToStore();
    }

    /**
     * 初始化工具类
     */
    public static void initAnnotationsToStore() {
        System.setProperty(StoreAnnotationUtil.SCAN_PACKAGE_ARGS_NAME_PREFIX
                + ".types.HandlerRegisterUtil", "me.fmeng.types.annotation");
    }

    /**
     * 注入注解标记的枚举Handler
     *
     * @param typeHandlerRegistry 注册器
     */
    public static void registerMarkedEnumTypeHander(final TypeHandlerRegistry typeHandlerRegistry) {
        Preconditions.checkNotNull(typeHandlerRegistry);
        typeHandlerRegistry.setDefaultEnumTypeHandler(EnumTypeHandler.class);
        Set<Class<?>> registeredClassSet = Sets.newHashSet();
        for (MarkedUnit markedUnit : StoreAnnotationUtil.getMarkedUnits()) {
            Class<?> typeClass = null;
            Class<? extends EnumTypeHandler> typeHandlerClass = null;
            if (markedUnit.isClassMarked()
                    && markedUnit.getAnnotation() instanceof EnumType
                    && !registeredClassSet.contains(markedUnit.getMarkedClass())) {
                typeClass = markedUnit.getMarkedClass();
                typeHandlerClass = ((EnumType) markedUnit.getAnnotation()).typeHandler();
            } else if (markedUnit.isFieldMarked()
                    && markedUnit.getAnnotation() instanceof EnumType
                    && !registeredClassSet.contains(markedUnit.getField().getType())) {
                typeClass = markedUnit.getField().getType();
                typeHandlerClass = ((EnumType) markedUnit.getAnnotation()).typeHandler();
            }
            if (typeClass != null && typeHandlerClass != null) {
                registerByInstance(typeHandlerRegistry, typeClass, typeHandlerClass);
                registeredClassSet.add(typeClass);
            }
        }
        registeredClassSet.clear();
    }

    /**
     * 注入注解标记的Json Handler
     *
     * @param typeHandlerRegistry 注册器
     */
    public static void registerMarkedJsonTypeHander(final TypeHandlerRegistry typeHandlerRegistry) {
        Preconditions.checkNotNull(typeHandlerRegistry);
        Set<Class<?>> registeredClassSet = Sets.newHashSet();
        for (MarkedUnit markedUnit : StoreAnnotationUtil.getMarkedUnits()) {
            Class<?> typeClass = null;
            Class<? extends JsonTypeHandler> typeHandlerClass = null;
            if (markedUnit.isClassMarked()
                    && markedUnit.getAnnotation() instanceof JsonType
                    && !registeredClassSet.contains(markedUnit.getMarkedClass())) {
                typeClass = markedUnit.getMarkedClass();
                typeHandlerClass = ((JsonType) markedUnit.getAnnotation()).typeHandler();
            } else if (markedUnit.isFieldMarked()
                    && markedUnit.getAnnotation() instanceof JsonType
                    && !registeredClassSet.contains(markedUnit.getField().getType())) {
                typeClass = markedUnit.getField().getType();
                typeHandlerClass = ((JsonType) markedUnit.getAnnotation()).typeHandler();
            }
            if (typeClass != null && typeHandlerClass != null) {
                registerByInstance(typeHandlerRegistry, typeClass, typeHandlerClass);
                registeredClassSet.add(typeClass);
            }
        }
        registeredClassSet.clear();
    }

    /**
     * 注入注解标记的枚举Handler
     *
     * @param typeHandlerRegistry 注册器
     * @param typeClass           业务类型信息
     * @param typeHandlerClass    mybatis TypeHandler
     */
    public static void registerByInstance(final TypeHandlerRegistry typeHandlerRegistry
            , Class<?> typeClass, Class<? extends TypeHandler> typeHandlerClass) {
        Preconditions.checkNotNull(typeHandlerRegistry);
        Preconditions.checkNotNull(typeClass);
        Preconditions.checkNotNull(typeHandlerClass);
        typeHandlerRegistry.register(typeClass, typeHandlerClass);
        log.info("注册TypeHandler到Mybaits,typeClass={}, TypeHandlerClass={}", typeClass, typeHandlerClass);
    }

    /****************************** Code ******************************/

    /**
     * 注入注解标记的枚举Handler
     *
     * @param typeHandlerRegistry 注册器
     */
    public static void registerMarkedCodeTypeHander(final TypeHandlerRegistry typeHandlerRegistry) {
        Preconditions.checkNotNull(typeHandlerRegistry);
        Set<Class<?>> registeredClassSet = Sets.newHashSet();
        for (MarkedUnit markedUnit : StoreAnnotationUtil.getMarkedUnits()) {
            Class<?> typeClass = null;
            Class<? extends CodeTypeHandler> typeHandlerClass = null;
            if (markedUnit.isClassMarked()
                    && markedUnit.getAnnotation() instanceof CodeType
                    && !registeredClassSet.contains(markedUnit.getMarkedClass())) {
                typeClass = markedUnit.getMarkedClass();
                typeHandlerClass = ((CodeType) markedUnit.getAnnotation()).typeHandler();
            } else if (markedUnit.isFieldMarked()
                    && markedUnit.getAnnotation() instanceof CodeType
                    && !registeredClassSet.contains(markedUnit.getField().getType())) {
                typeClass = markedUnit.getField().getType();
                typeHandlerClass = ((CodeType) markedUnit.getAnnotation()).typeHandler();
            }
            if (typeClass != null && typeHandlerClass != null) {
                registerByInstance(typeHandlerRegistry, typeClass, typeHandlerClass);
                registeredClassSet.add(typeClass);
            }
        }
        registeredClassSet.clear();
    }
}
