package me.fmeng.types;

/**
 * 为了统一枚举的 json序列化,展现,数据库存储 而抽象出的行为
 * <p>
 * 需要实现静态方法 codeOf
 *
 * @author fmeng
 * @since 2018/03/13
 */
public interface EnumTrait {

    /**
     * 获取枚举的Code
     */
    int getCode();

    /**
     * 获取枚举的文本信息
     */
    String getText();
}
