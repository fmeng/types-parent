package me.fmeng.types.test;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.fmeng.types.EnumTrait;

import java.util.Arrays;
import java.util.Map;

/**
 * 权限
 *
 * @author fmeng
 * @since 2019/01/13
 */
@Getter
@AllArgsConstructor
public enum PmsEnum implements EnumTrait {
    /**
     * 增
     */
    ADD(2 << 1, "ADD"),
    /**
     * 删
     */
    DEL(2 << 2, "DEL"),
    /**
     * 改
     */
    UPT(2 << 3, "UPT"),
    /**
     * 查
     */
    GET(2 << 4, "GET"),
    ;
    private final int code;
    private final String text;

    public static final PmsEnum DEFAULT = null;
    private static final Map<Integer, PmsEnum> CODE_ENUM_MAP = Maps.uniqueIndex(Arrays.asList(values()), PmsEnum::getCode);

    /**
     * 根据code获取枚举, 子类需要覆盖
     */
    public static PmsEnum codeOf(int code) {
        return CODE_ENUM_MAP.get(code);
    }
}