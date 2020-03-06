package com.sai.ioc.context;

import com.sai.ioc.beans.annotation.AnnotatedBeanDefinitionReader;
import com.sai.ioc.beans.factory.AbstractBeanFactory;
import com.sai.ioc.beans.factory.AutowireCapableBeanFactory;

public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private String scanPath;

    public AnnotationConfigApplicationContext(AbstractBeanFactory beanFactory, String scanPath) throws Exception {
        super(beanFactory);
        this.scanPath = scanPath;
        refresh();
    }

    public AnnotationConfigApplicationContext(String scanPath) throws Exception {
        this(new AutowireCapableBeanFactory(), scanPath);
    }

    @Override
    protected void refresh() throws Exception {
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(this.beanFactory, this.scanPath);
        annotatedBeanDefinitionReader.loadBeanDefinition();
    }
}
