package com.zhuangpo.mqtt.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *  ApplicationContextUtil工具类 封装成静态
 * 
 * @author xub
 * @since 2023/7/31 上午11:23
 */
@Component
public class ApplicationContextUtil {

    private static ApplicationContextUtil instance;
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void applicationContextUtil() {
        instance = this;
    }

    /**
     * 根据字节码获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }

    /**
     * 根据名字获取bean
     * @param name
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(String name) throws BeansException {
        return (T) instance.applicationContext.getBean(name);
    }
}
