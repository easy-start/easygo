package org.qiranlw.easygo.config;

import com.googlecode.aviator.AviatorEvaluator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.qiranlw.easygo.annotation.Check;
import org.qiranlw.easygo.annotation.CheckContainer;
import org.qiranlw.easygo.exception.MethodArgumentNotValidException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author qiranlw
 */
@Aspect
@Configuration
public class AopConfig {

    @Pointcut("@annotation(org.qiranlw.easygo.annotation.CheckContainer) || @annotation(org.qiranlw.easygo.annotation.Check)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public Object before(JoinPoint point) {
        Object[] args = point.getArgs();
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
        String[] paramNames = discoverer.getParameterNames(method);
        if (paramNames == null || paramNames.length == 0) {
            return null;
        }

        CheckContainer checkContainer = method.getDeclaredAnnotation(CheckContainer.class);
        List<Check> checkList = new ArrayList<>();
        if (checkContainer != null) {
            checkList.addAll(Arrays.asList(checkContainer.value()));
        } else {
            Check check = method.getDeclaredAnnotation(Check.class);
            checkList.add(check);
        }
        Map<String, Object> map = new HashMap<>(paramNames.length);
        for (int i=0;i<paramNames.length;i++) {
            map.put(paramNames[i], args[i]);
        }
        Map<String, String> checkMap = new HashMap<>(checkList.size());
        for (Check check : checkList) {
            String key = check.key();
            if (checkMap.containsKey(key)) {
                continue;
            }
            String ex = check.ex();
            Boolean result = (Boolean) AviatorEvaluator.execute(ex, map);
            if (!result) {
                checkMap.put(key, check.msg());
            }
        }
        if (!checkMap.isEmpty()) {
            throw new MethodArgumentNotValidException(checkMap);
        }
        return null;
    }
}
