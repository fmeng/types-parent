package me.fmeng.types.annotation;

import me.fmeng.anstore.StoreAnnotation;
import me.fmeng.types.EnumTypeHandler;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mybatis自动序列化成枚举
 *
 * @author fmeng
 * @since 2018/01/25
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@StoreAnnotation
public @interface EnumType {

    /**
     * 默认值
     */
    @AliasFor("desc")
    String value() default "";

    /**
     * 业务属性
     */
    @AliasFor("value")
    String desc() default "";

    /**
     * 获取Code的方法
     */
    String getCodeMethod() default "getCode";

    /**
     * 获取文本描述的方法
     */
    String getTextMethod() default "getText";

    /**
     * code映射成枚举的方法
     */
    String codeOfMethod() default "codeOf";

    /**
     * 类型处理器
     */
    Class<? extends EnumTypeHandler> typeHandler() default EnumTypeHandler.class;
}