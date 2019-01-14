package me.fmeng.types.annotation;

import me.fmeng.anstore.StoreAnnotation;
import me.fmeng.types.CodeTypeHandler;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mybatis自动序列化成Code
 *
 * @author fmeng
 * @since 2018/01/25
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@StoreAnnotation
public @interface CodeType {

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
     * 类型处理器
     */
    Class<? extends CodeTypeHandler> typeHandler() default CodeTypeHandler.class;
}