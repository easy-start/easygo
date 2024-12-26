package org.qiranlw.easygo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.qiranlw.easygo.bean.UserDetailsBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author qiranlw
 */
public class EasygoUtils {

    public static int NOT_DELETED_STATUS = 0;

    public static int YES_DELETED_STATUS = 1;

    public static String JSON_CONTENT_TYPE = "application/json";

    public static String JPEG_CONTENT_TYPE = "image/jpeg";

    public static String UTF8_ENCODING = "UTF-8";

    public static String POST_METHOD = "POST";

    public static String GET_METHOD = "GET";

    public static String DEFAULT_PASSWORD = "asdf1234";

    public static boolean userLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return false;
        } else {
            return principal instanceof UserDetailsBean;
        }
    }

    public static UserDetailsBean getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        } else if (principal instanceof UserDetailsBean) {
            return (UserDetailsBean)principal;
        } else {
            return null;
        }
    }

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }
}
