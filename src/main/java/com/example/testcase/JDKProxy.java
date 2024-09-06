package com.example.testcase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lx
 * @data 2022/11/25 17:47
 */
public class JDKProxy {
    public static <T> T getProxy(Class<T> tClass) {
        //newProxyInstance 创建代理对象
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), tClass.getInterfaces(), new InvocationHandler() {
            @Override
            //InvocationHandler 对目标方法进行拦截
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("开启事务");
                Object obj = method.invoke(tClass.newInstance(), args);
                System.out.println("提交事务");
                return obj;
            }
        });
    }

}
