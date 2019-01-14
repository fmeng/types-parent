package me.fmeng.types;

import me.fmeng.types.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mybatis通用Json处理器
 *
 * @author fmeng
 * @since 2018/03/22
 */
public class JsonTypeHandler<E> extends BaseTypeHandler<E> {

    private Class<E> clz;

    public JsonTypeHandler(Class<E> jsonType) {
        this.clz = jsonType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, JsonUtil.encode(parameter));
        } catch (IOException e) {
            throw new SQLException("json encode error : " + parameter, e);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return JsonUtil.decode(value, clz);
        } catch (IOException e) {
            throw new SQLException("json decode error : " + value, e);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return JsonUtil.decode(value, clz);
        } catch (IOException e) {
            throw new SQLException("json decode error : " + value, e);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return JsonUtil.decode(value, clz);
        } catch (IOException e) {
            throw new SQLException("json decode error : " + value, e);
        }
    }
}
