package me.fmeng.types.test;

import me.fmeng.anstore.StoreAnnotationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author fmeng
 * @since 2018/03/17
 */
//@EnumType("me.fmeng.types.test")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TypesApplication {
    static {
        System.setProperty(StoreAnnotationUtil.SCAN_PACKAGE_ARGS_NAME_PREFIX
                + ".types.TypesApplication", "me.fmeng.types.test");
    }

    public static void main(String[] args) {
        SpringApplication.run(TypesApplication.class, args);
    }
}
