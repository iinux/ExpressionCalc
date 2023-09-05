package cn.iinux.java.alpha.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * my factory bean<p>
 * 代理一个类，拦截该类的所有方法，在方法的调用前后进行日志的输出
 *
 */
@Component
public class MyFactoryBean implements FactoryBean<Object>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(MyFactoryBean.class);
    private String interfaceName;
    private Object target;
    private Object proxyObj;

    @Override
    public void destroy() {
        logger.info("destroy......");
    }

    @Override
    public void afterPropertiesSet() {
        proxyObj = Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                this.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        logger.info("invoke method......" + method.getName());
                        logger.info("invoke method before......" + System.currentTimeMillis());
                        Object result = method.invoke(target, args);
                        logger.info("invoke method after......" + System.currentTimeMillis());
                        return result;
                    }
                });
        logger.info("afterPropertiesSet......");
    }

    @Override
    public Object getObject() {
        logger.info("getObject......");
        return proxyObj;
    }

    @Override
    public Class<?> getObjectType() {
        return proxyObj == null ? Object.class : proxyObj.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getProxyObj() {
        return proxyObj;
    }

    public void setProxyObj(Object proxyObj) {
        this.proxyObj = proxyObj;
    }

}
