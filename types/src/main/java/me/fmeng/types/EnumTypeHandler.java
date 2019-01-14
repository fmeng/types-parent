package me.fmeng.types;

import com.google.common.base.Preconditions;
import me.fmeng.types.utils.EnumTypeUtil;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * Mybatis通用枚举处理器
 *
 * @author fmeng
 * @since 2018/01/25
 */
public class EnumTypeHandler<E extends Enum<E>> extends org.apache.ibatis.type.EnumTypeHandler<E> {

    private final Function<Enum, Integer> getCodeFunction;
    private final Function<Integer, Enum> codeOfFunction;

    public EnumTypeHandler(Class<E> enumType) {
        super(enumType);
        EnumTypeUtil.checkGetCode(enumType);
        EnumTypeUtil.checkCodeOf(enumType);
        this.getCodeFunction = Preconditions.checkNotNull(EnumTypeUtil.createGetCodeFunction(enumType));
        this.codeOfFunction = Preconditions.checkNotNull(EnumTypeUtil.createCodeOfFunction(enumType));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Enum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, getCodeFunction.apply(parameter));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return (E) codeOfFunction.apply(rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return (E) codeOfFunction.apply(rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (E) codeOfFunction.apply(cs.getInt(columnIndex));
    }
}
