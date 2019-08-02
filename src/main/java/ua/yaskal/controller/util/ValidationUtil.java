package ua.yaskal.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class ValidationUtil{

    //TODO stream
    public boolean isСontain(HttpServletRequest request, List<String> params){
        for (String param: params) {
            if (Objects.isNull(request.getParameter(param)) || request.getParameter(param).isEmpty())
                return false;
        }
        return true;
    }

    public boolean isCorrect(String param){
        return false;
    }
}
