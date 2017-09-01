package com.rainsoft.base.common.dao;

import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;

import java.sql.*;


/**
 * Created with IntelliJ IDEA.
 * User: 13646223842@163.com
 * Date: 2015/12/30
 * Time: 16:26
 */
// 继承自BaseTypeHandler<Object[]> 使用时传入的参数一定要是Object[]，例如 int[]是 Object, 不是Object[]，所以传入int[] 会报错的
@MappedJdbcTypes(JdbcType.ARRAY)
public class ArrayTypeHandler extends BaseTypeHandler<Object[]> {
    /**数据库自定义的类型**/
    private static final String TYPE_NAME_VARCHAR = "VARCHAR_ARRAY";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object[] parameter,
                                    JdbcType jdbcType) throws SQLException {
        String typeName = TYPE_NAME_VARCHAR;
        // 这4行是关键的代码，创建Array，然后 new ARRAY(Descriptor, oracleConn, parameter)就可以了
        C3P0NativeJdbcExtractor cp30NativeJdbcExtractor =  new C3P0NativeJdbcExtractor();
        //c3p0搞死我了 转成oracleConnection可以解决
        OracleConnection oracleConn=(OracleConnection)cp30NativeJdbcExtractor.getNativeConnection(ps.getConnection());
        ArrayDescriptor Descriptor = ArrayDescriptor.createDescriptor(typeName, oracleConn);
        Array array = new ARRAY(Descriptor, oracleConn, parameter);
        ps.setArray(i, array);
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {

        return getArray(rs.getArray(columnName));
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public Object[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {

        return getArray(cs.getArray(columnIndex));
    }

    private Object[] getArray(Array array) {

        if (array == null) {
            return null;
        }

        try {
            return (Object[]) array.getArray();
        } catch (Exception e) {
        }

        return null;
    }
}