package org.qiranlw.easygo.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.qiranlw.easygo.bean.Result;
import org.qiranlw.easygo.bean.ResultEnum;
import org.qiranlw.easygo.utils.EasygoUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @author qiranlw
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String data = request.getContentType();
        if (data != null && data.contains(EasygoUtils.JSON_CONTENT_TYPE)) {
            response.setCharacterEncoding(EasygoUtils.UTF8_ENCODING);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(EasygoUtils.toJson(Result.error(ResultEnum.FORBIDDEN)));
            return;
        }
        response.sendRedirect("/403.html");
    }
}
