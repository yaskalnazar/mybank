package ua.yaskal.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ValidationUtil{
    private ResourceBundle sqlRequestsBundle = ResourceBundle.getBundle("regex");


    //TODO stream
    public boolean isContains(HttpServletRequest request, List<String> params){
        for (String param: params) {
            if (Objects.isNull(request.getParameter(param)) || request.getParameter(param).isEmpty())
                return false;
        }
        return true;
    }


    public boolean isRequestValid(HttpServletRequest request, List<String> paramNames){
        for (String paramName:paramNames){
            if (!isParamValid(request.getParameter(paramName), paramName))
                return false;
        }
        return true;
    }

    public boolean isParamValid(String param, String paramName){
        return param.matches(sqlRequestsBundle.getString("regex."+paramName));
    }
}
