package com.example.testcase;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Cglib implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();
    public <T> T getProxy(Class <T> clazz){
        //设置目标类
        enhancer.setSuperclass(clazz);
        //设置回调类（必须实现MethodInterceptor接口的intercept 方法对目标方法进行拦截实现增强）
        enhancer.setCallback(this);
        //创建代理类
        return (T)enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开启事务");
        Object obj = methodProxy.invokeSuper(o,objects);
        System.out.println("提交事务");
        return obj;
    }


}
