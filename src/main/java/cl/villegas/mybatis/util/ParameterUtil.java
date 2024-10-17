package cl.villegas.mybatis.util;

import cl.villegas.mybatis.constants.Constants;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ParameterUtil {
    public static Map<String, Object> getParameters(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, Object> parameters = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            String valueType = request.getParameter(parameterName).substring(0, 2).toUpperCase();
            int length = request.getParameter(parameterName).length();
            String value = request.getParameter(parameterName).substring(2, length);
            setParametersByValueType(value, valueType, parameterName, parameters);
        }
        return parameters;
    }

    private static void setParametersByValueType(String value, String valueType, String parameterName, Map<String, Object> parameters) {
        switch (valueType) {
            case Constants.ValueType.BO: // Boolean Types
                parameters.put(parameterName, value.equalsIgnoreCase(Constants.Boolean.TRUE));
                break;
            case Constants.ValueType.BY: // Byte Types
                parameters.put(parameterName, Byte.parseByte(value));
                break;
            case Constants.ValueType.CH: // Char Types
                parameters.put(parameterName, value.charAt(0));
                break;
            case Constants.ValueType.SH: // Short Types
                parameters.put(parameterName, Short.parseShort(value));
                break;
            case Constants.ValueType.IN: // Integer Types
                parameters.put(parameterName, Integer.parseInt(value));
                break;
            case Constants.ValueType.LO: // Long Types
                parameters.put(parameterName, Long.parseLong(value));
                break;
            case Constants.ValueType.FL: // Float Types
                parameters.put(parameterName, Float.parseFloat(value));
                break;
            case Constants.ValueType.DO: // Double Types
                parameters.put(parameterName, Double.parseDouble(value));
                break;
            case Constants.ValueType.ST: // String Types
                parameters.put(parameterName, value);
                break;
        }
    }
}