package cl.villegas.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RutTypeHandler implements TypeHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(RutTypeHandler.class);

    @Override
    public void setParameter(PreparedStatement preparedStatement, int columnIndex, String parameter, JdbcType jdbcType) {
        if (parameter != null && !parameter.trim().isEmpty()) {
            StringBuilder parameterBuilder = new StringBuilder(parameter);
            while (parameterBuilder.length() < 11) {
                parameterBuilder.insert(0, "0");
            }
            parameter = parameterBuilder.toString();
        }
        try {
            preparedStatement.setString(columnIndex, parameter);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getResult(ResultSet resultSet, String columnName) {
        String rut = "";
        try {
            rut = resultSet.getString(columnName);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return parse(rut);
    }

    @Override
    public String getResult(ResultSet resultSet, int columnIndex) {
        String rut = "";
        try {
            rut = resultSet.getString(columnIndex);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return parse(rut);
    }

    @Override
    public String getResult(CallableStatement callableStatement, int columnIndex) {
        String rut = "";
        try {
            rut = callableStatement.getString(columnIndex);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return parse(rut);
    }

    private String parse(String rut) {
        if (rut == null || rut.trim().isEmpty())
            return rut;
        String[] rutSplit = rut.split("-");
        return Long.parseLong(rutSplit[0]) + "-" + rutSplit[1];
    }
}