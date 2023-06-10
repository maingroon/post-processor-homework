package com.borovyk.processor;

import com.borovyk.trimmed.annotation.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.Objects;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Objects.requireNonNull(bean);
        Objects.requireNonNull(beanName);

        var beanType = bean.getClass();
        if (beanType.isAnnotationPresent(Trimmed.class)) {
            return wrapBeanLogic(bean, beanType);
        }
        return bean;
    }

    private Object wrapBeanLogic(Object bean, Class<?> beanType) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(beanType);
        enhancer.setClassLoader(beanType.getClassLoader());

        MethodInterceptor interceptor = ((obj, method, args, methodProxy) -> {
            for (int i = 0; i < args.length; i++) {
                args[i] = trimString(args[i]);
            }

            var result = methodProxy.invokeSuper(obj, args);
            return trimString(result);
        });
        enhancer.setCallback(interceptor);
        return enhancer.create();
    }

    private Object trimString(Object result) {
        if (result instanceof String) {
            result = ((String) result).trim();
        }
        return result;
    }

}
