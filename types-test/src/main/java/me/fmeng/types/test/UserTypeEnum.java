package me.fmeng.types.test;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.fmeng.types.annotation.EnumType;

import java.util.Arrays;
import java.util.Map;

/**
 * @author fmeng
 * @since 2019/01/13
 */
@EnumType("用户信息")
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    /**
     *
     */
    GEN(1, "GEN"),
    /**
     *
     */
    ADMIN(2, "ADMIN"),
    ;
    private final int code;
    private final String text;

    public static final UserTypeEnum DEFAULT = null;
    private static final Map<Integer, UserTypeEnum> CODE_ENUM_MAP = Maps.uniqueIndex(Arrays.asList(values()), UserTypeEnum::getCode);

    public static UserTypeEnum codeOf(int code) {
        return CODE_ENUM_MAP.get(code);
    }
}