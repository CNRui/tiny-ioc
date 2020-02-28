package com.sai.ioc.beans.factory;

import com.sai.ioc.beans.BeanDefinition;
import com.sai.ioc.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory, BeanDefinitionRegistry {


    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private List<String> beanDefinitionNames = new ArrayList<String>();

    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            bean = doCreateBean(beanDefinition);
        }
        return bean;
    }


    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) throws Exception {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return StringUtils.toStringArray(this.beanDefinitionNames);
    }

    protected abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;
}
