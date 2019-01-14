package me.fmeng.types.configure;

import com.google.common.base.Preconditions;
import me.fmeng.anstore.StoreAnnotationUtil;
import me.fmeng.types.EnableTypes;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.stream.Stream;

/**
 * 类型注入配置
 *
 * @author fmeng
 * @since 2019/01/12
 */
@Configuration
@ConditionalOnClass(SqlSessionFactory.class)
@Import(TypesAutoConfigure.AnnotationsToStoreInitializingBean.class)
public class TypesAutoConfigure {

    @Bean(initMethod = "init")
    @ConditionalOnBean(SqlSessionFactory.class)
    @ConditionalOnMissingBean(TypesInitializingBean.class)
    public TypesInitializingBean initTypes(@Autowired SqlSessionFactory sqlSessionFactory) {
        return new TypesInitializingBean(sqlSessionFactory);
    }

    public static class AnnotationsToStoreInitializingBean implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            // 初始化配置文件中指定的包
            String[] aPackages = getAppEnumPackages();
            if (ArrayUtils.isNotEmpty(aPackages)) {
                initAnnotationsToStore(aPackages);
            }
            // 从注解中获取
            AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(
                    ClassUtils.resolveClassName(metadata.getClassName(), null), EnableTypes.class);
            if (attrs == null) {
                return;
            }
            String appEnumPackage = attrs.getString("appEnumPackages");
            String[] appEnumPackages = attrs.getStringArray("appEnumPackages");
            Preconditions.checkState(ArrayUtils.isNotEmpty(appEnumPackages), "注解@EnableTypes标注的appEnumPackages属性设置不合法");
            initAnnotationsToStore(appEnumPackage);
        }

        /**
         * 初始化工具类
         */
        private void initAnnotationsToStore(String... appEnumPackages) {
            Stream.of(appEnumPackages).distinct()
                    .forEach(a ->
                            System.setProperty(StoreAnnotationUtil.SCAN_PACKAGE_ARGS_NAME_PREFIX + "." + a, a));
        }

        protected String[] getAppEnumPackages() {
            return null;
        }
    }
}
