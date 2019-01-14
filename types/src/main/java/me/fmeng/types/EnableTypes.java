package me.fmeng.types;

import me.fmeng.types.configure.TypesAutoConfigure;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动Mybatis自动类型注入
 *
 * @author fmeng
 * @since 2018/01/25
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TypesAutoConfigure.class)
public @interface EnableTypes {

    /**
     * 默认值
     */
    @AliasFor("appEnumPackages")
    String[] value() default {};

    /**
     * 需要扫描的app属性
     */
    @AliasFor("value")
    String[] appEnumPackages() default {};

}