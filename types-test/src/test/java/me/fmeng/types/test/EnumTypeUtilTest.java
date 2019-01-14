package me.fmeng.types.test;

import me.fmeng.types.utils.EnumTypeUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.function.Function;

/**
 * @author fmeng
 * @since 2019/01/13
 */
public class EnumTypeUtilTest {

    @BeforeClass
    public static void initProp() {
        System.setProperty("storeanns.base.package", "me.fmeng.types");
    }

    @Test
    public void checkGetCodeTest() throws Exception {
        EnumTypeUtil.checkGetCode(PmsEnum.class);
        EnumTypeUtil.checkGetCode(UserTypeEnum.class);
        EnumTypeUtil.checkGetText(PmsEnum.class);
        EnumTypeUtil.checkGetText(UserTypeEnum.class);
        EnumTypeUtil.checkCodeOf(PmsEnum.class);
        EnumTypeUtil.checkCodeOf(UserTypeEnum.class);
    }

    @Test
    public void getCodeFun() throws Exception {
        Function<Enum, Integer> getCodeFunction = EnumTypeUtil.createGetCodeFunction(PmsEnum.class);
        Assert.assertEquals(PmsEnum.GET.getCode(), getCodeFunction.apply(PmsEnum.GET).intValue());
        Function<Enum, Integer> getCode2Function = EnumTypeUtil.createGetCodeFunction(UserTypeEnum.class);
        Assert.assertEquals(UserTypeEnum.ADMIN.getCode(), getCode2Function.apply(UserTypeEnum.ADMIN).intValue());
    }

    @Test
    public void getTextFun() throws Exception {
        Function<Enum, String> getTextFunction = EnumTypeUtil.createGetTextFunction(PmsEnum.class);
        Assert.assertEquals(PmsEnum.GET.getText(), getTextFunction.apply(PmsEnum.GET));
        Function<Enum, String> getTextFunction1 = EnumTypeUtil.createGetTextFunction(UserTypeEnum.class);
        Assert.assertEquals(UserTypeEnum.ADMIN.getText(), getTextFunction1.apply(UserTypeEnum.ADMIN));
    }

    @Test
    public void codeOfFun() throws Exception {
        Function<Integer, Enum> codeOfFunction = EnumTypeUtil.createCodeOfFunction(PmsEnum.class);
        Assert.assertEquals(PmsEnum.GET, codeOfFunction.apply(PmsEnum.GET.getCode()));
        Function<Integer, Enum> codeOfFunction1 = EnumTypeUtil.createCodeOfFunction(UserTypeEnum.class);
        Assert.assertEquals(UserTypeEnum.ADMIN, codeOfFunction1.apply(UserTypeEnum.ADMIN.getCode()));
    }

    @Test
    public void powTest() {
        EnumTypeUtil.checkIntPow(1);
        EnumTypeUtil.checkIntPow(2);
        EnumTypeUtil.checkIntPow(4);
        // EnumTypeUtil.checkIntPow(6);
    }
}
