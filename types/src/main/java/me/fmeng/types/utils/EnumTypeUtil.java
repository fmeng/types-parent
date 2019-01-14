package me.fmeng.types.utils;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import me.fmeng.types.EnumTrait;
import me.fmeng.types.annotation.EnumType;
import me.fmeng.types.exception.IllegalAccessOfTypesException;
import me.fmeng.types.exception.IllegalParamOfTypesException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author fmeng
 * @since 2019/01/12
 */
@SuppressWarnings("all")
public class EnumTypeUtil {

    /**
     * 检查getCode方法是否正确
     *
     * @param enumClass
     */
    public static void checkGetCode(Class<? extends Enum> enumClass) {
        // 实现接口
        if (EnumTrait.class.isAssignableFrom(enumClass)) {
            return;
        }
        // 从注解中获取
        EnumType annotation = enumClass.getAnnotation(EnumType.class);
        if (annotation != null) {
            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            final String getCodeMethodName = (String) annotationAttributes.get("getCodeMethod");
            if (StringUtils.isNotBlank(getCodeMethodName)) {
                try {
                    Method method = enumClass.getMethod(getCodeMethodName);
                    if (method.getReturnType().equals(int.class)) {
                        int modifiers = method.getModifiers();
                        if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    throw new IllegalAccessOfTypesException(e, enumClass + "获取getCode方法失败");
                }
            }
        }
        throw new IllegalAccessOfTypesException(enumClass + "获取getCode方法失败");
    }

    /**
     * 检查getText方法是否正确
     *
     * @param enumClass
     */
    public static void checkGetText(Class<? extends Enum> enumClass) {
        // 从接口中获取
        if (EnumTrait.class.isAssignableFrom(enumClass)) {
            return;
        }
        // 从注解中获取
        EnumType annotation = enumClass.getAnnotation(EnumType.class);
        if (annotation != null) {
            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            final String getTextMethodName = (String) annotationAttributes.get("getTextMethod");
            if (StringUtils.isNotBlank(getTextMethodName)) {
                try {
                    Method method = enumClass.getMethod(getTextMethodName);
                    if (method.getReturnType().equals(String.class)) {
                        int modifiers = method.getModifiers();
                        if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    throw new IllegalAccessOfTypesException(e, enumClass + "获取getText方法失败");
                }
            }
        }
        throw new IllegalAccessOfTypesException(enumClass + "获取getText方法失败");
    }

    /**
     * 检查CodeOf方法是否正确
     *
     * @param enumClass
     */
    public static void checkCodeOf(Class<? extends Enum> enumClass) {
        // 实现接口
        if (EnumTrait.class.isAssignableFrom(enumClass)) {
            Method method = null;
            try {
                method = enumClass.getMethod("codeOf", int.class);
            } catch (NoSuchMethodException e) {
                throw new IllegalAccessOfTypesException(e, enumClass + "获取codeOf方法失败");
            }
            if (!method.getReturnType().equals(enumClass)) {
                throw new IllegalAccessOfTypesException(enumClass + "获取codeOf方法失败");
            }
            int modifiers = method.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers)) {
                throw new IllegalAccessOfTypesException(enumClass + "获取codeOf方法失败");
            }
            return;
        }
        // 从注解中获取
        EnumType annotation = enumClass.getAnnotation(EnumType.class);
        if (annotation != null) {
            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            final String getTextMethodName = (String) annotationAttributes.get("codeOfMethod");
            if (StringUtils.isNotBlank(getTextMethodName)) {
                Method method = null;
                try {
                    method = enumClass.getMethod(getTextMethodName, int.class);
                } catch (NoSuchMethodException e) {
                    throw new IllegalAccessOfTypesException(e, enumClass + "获取getText方法失败");
                }
                if (method.getReturnType().equals(enumClass)) {
                    int modifiers = method.getModifiers();
                    if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                        return;
                    }
                }
            }
        }
        throw new IllegalAccessOfTypesException(enumClass + "获取codeOf方法失败");
    }

    /**
     * 合并成2的幂次方 方便数据库存储
     *
     * @return code
     */
    public static int toMergeCode(final Function<Enum, Integer> getCodeFunction, Collection<Enum> enums) {
        if (CollectionUtils.isEmpty(enums)) {
            return 0;
        }
        Preconditions.checkNotNull(getCodeFunction, "getCodeFunction不能为空");
        // 返回合并结果
        return enums.stream()
                .filter(Objects::nonNull)
                .mapToInt(e -> getCodeFunction.apply(e))
                .filter(c -> c > 0)
                .peek(EnumTypeUtil::checkIntPow).sum();
    }

    /**
     * 2的幂次方转换成集合 方便应用层使用
     *
     * @return 获取枚举集合
     */
    public static Set<Enum> mergeCodeOf(final Function<Enum, Integer> getCodeFunction, Class<Enum> enumClass, int mergeCode) {
        // 参数校验
        if (mergeCode == 0) {
            return Collections.emptySet();
        }
        Preconditions.checkNotNull(getCodeFunction, "getCodeFunction不能为空");
        Preconditions.checkArgument(mergeCode > 0, "mergeCode需要大于0");
        List<Enum> enumList = EnumUtils.getEnumList(enumClass);
        Preconditions.checkState(CollectionUtils.isNotEmpty(enumList), "枚举和长度要大于0");
        return enumList.stream()
                .filter(e -> {
                    Integer code = getCodeFunction.apply(e);
                    return (code & mergeCode) == code;
                })
                .collect(Collectors.toSet());
    }

    /**
     * 封装获取枚举类中获取code的方法
     * 1. 先判断是否实现EnumTrait,通过接口getCode方法获取
     * 2. 在判断是否有@EnumType,通过@EnumType指定的getCodeMethod属性方法获取
     *
     * @param enumClass 枚举类
     * @return 获取code的函数 可能返回空
     */
    public static Function<Enum, Integer> createGetCodeFunction(Class<? extends Enum> enumClass) {
        // 实现接口
        if (EnumTrait.class.isAssignableFrom(enumClass)) {
            return t -> ((EnumTrait) t).getCode();
        }
        // 从注解中获取
        EnumType annotation = enumClass.getAnnotation(EnumType.class);
        if (annotation != null) {
            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            final String getCodeMethodName = (String) annotationAttributes.get("getCodeMethod");
            if (StringUtils.isNotBlank(getCodeMethodName)) {
                return t -> {
                    try {
                        return Integer.parseInt(MethodUtils.invokeExactMethod(t, getCodeMethodName).toString());
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalAccessOfTypesException(e, enumClass + "#" + getCodeMethodName + "实现不合法");
                    }
                };
            }
        }
        return null;
    }

    /**
     * 封装获取枚举类中获取text的方法
     * 1. 先判断是否实现EnumTrait,通过接口getText方法获取
     * 2. 在判断是否有@EnumType,通过@EnumType指定的getTextMethod属性方法获取
     *
     * @param enumClass 枚举类
     * @return 获取code的函数 可能返回空
     */
    public static Function<Enum, String> createGetTextFunction(Class<? extends Enum> enumClass) {
        // 实现接口
        if (EnumTrait.class.isAssignableFrom(enumClass)) {
            return t -> ((EnumTrait) t).getText();
        }
        // 从注解中获取
        EnumType annotation = enumClass.getAnnotation(EnumType.class);
        if (annotation != null) {
            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            final String getTextMethodName = (String) annotationAttributes.get("getTextMethod");
            if (StringUtils.isNotBlank(getTextMethodName)) {
                return t -> {
                    try {
                        return (String) MethodUtils.invokeExactMethod(t, getTextMethodName);
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalAccessOfTypesException(e, enumClass + "#" + getTextMethodName + "实现不合法");
                    }
                };
            }
        }
        return null;
    }

    /**
     * 封装获取枚举类中获取codeOf的方法
     * 1. 先判断是否实现EnumTrait,通过接口codeOf方法获取
     * 2. 在判断是否有@EnumType,通过@EnumType指定的codeOfMethod属性方法获取
     * 3. 逐个判断getCode
     *
     * @param enumClass 枚举类
     * @return 获取code的函数 可能返回空
     */
    public static Function<Integer, Enum> createCodeOfFunction(Class<? extends Enum> enumClass) {
        // 从接口中获取
        if (EnumTrait.class.isAssignableFrom(enumClass)) {
            return code -> {
                try {
                    return (Enum) MethodUtils.invokeStaticMethod(enumClass, "codeOf", code);
                } catch (ReflectiveOperationException ex) {
                    // 没有codeOf方法
                    Function<Enum, Integer> getCodeFunction = createGetCodeFunction(enumClass);
                    Optional<Enum> any = EnumUtils.getEnumList(enumClass).stream()
                            .filter(e -> getCodeFunction.apply((Enum) e).intValue() == code)
                            .map(e -> ((Enum) e))
                            .findAny();
                    return any.isPresent() ? any.get() : null;
                }
            };
        }
        // 从注解中获取
        EnumType annotation = enumClass.getAnnotation(EnumType.class);
        if (annotation != null) {
            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            final String getCodeOfMethodName = (String) annotationAttributes.get("codeOfMethod");
            if (StringUtils.isNotBlank(getCodeOfMethodName)) {
                return code -> {
                    try {
                        return (Enum) MethodUtils.invokeStaticMethod(enumClass, getCodeOfMethodName, code);
                    } catch (ReflectiveOperationException ex) {
                        // 没有codeOf方法
                        Function<Enum, Integer> getCodeFunction = createGetCodeFunction(enumClass);
                        Optional<Enum> any = EnumUtils.getEnumList(enumClass).stream()
                                .filter(e -> getCodeFunction.apply((Enum) e).intValue() == code)
                                .map(e -> ((Enum) e))
                                .findAny();
                        return any.isPresent() ? any.get() : null;
                    }
                };
            }
        }
        return null;
    }

    /**
     * code必须为2的幂
     *
     * @param check 被检查的数
     */
    public static void checkIntPow(int check) {
        if (!IntMath.isPowerOfTwo(check)) {
            throw new IllegalParamOfTypesException("code必须为2的幂");
        }
    }
}

