package me.fmeng.types;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import me.fmeng.types.utils.EnumTypeUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.function.Function;

/**
 * Mybatis通用枚举处理器
 *
 * @author fmeng
 * @since 2018/01/25
 */
public class CodeTypeHandler extends BaseTypeHandler<Collection<Enum>> {

    private final Function<Enum, Integer> getCodeFunction;
    private final Class<Enum> enumType;

    @SuppressWarnings("unchecked")
    public CodeTypeHandler(Class<Collection<? extends Enum>> collectionEnumClass) {
        Class<Enum> enumType = (Class<Enum>) TypeToken.of(Collection.class).resolveType(collectionEnumClass.getTypeParameters()[0]).getRawType();
        EnumTypeUtil.checkGetCode(enumType);
        this.getCodeFunction = Preconditions.checkNotNull(EnumTypeUtil.createGetCodeFunction(enumType));
        this.enumType = enumType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Collection<Enum> parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, EnumTypeUtil.toMergeCode(getCodeFunction, parameter));
    }

    @Override
    public Collection<Enum> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return EnumTypeUtil.mergeCodeOf(getCodeFunction, enumType, rs.getInt(columnName));
    }

    @Override
    public Collection<Enum> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return EnumTypeUtil.mergeCodeOf(getCodeFunction, enumType, rs.getInt(columnIndex));
    }

    @Override
    public Collection<Enum> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EnumTypeUtil.mergeCodeOf(getCodeFunction, enumType, cs.getInt(columnIndex));
    }
}
