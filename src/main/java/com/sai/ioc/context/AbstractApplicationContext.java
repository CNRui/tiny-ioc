package com.sai.ioc.context;

import com.sai.ioc.beans.factory.AbstractBeanFactory;

public abstract class AbstractApplicationContext implements ApplicationContext {


    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }

    protected void refresh() throws Exception{

    }
}
