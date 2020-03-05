package com.sai.ioc.context;

import com.sai.ioc.beans.BeanDefinition;
import com.sai.ioc.beans.factory.AbstractBeanFactory;
import com.sai.ioc.beans.factory.AutowireCapableBeanFactory;
import com.sai.ioc.beans.factory.BeanDefinitionRegistry;
import com.sai.ioc.beans.io.ResourceLoader;
import com.sai.ioc.beans.io.UrlResourceLoader;
import com.sai.ioc.beans.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private String location;

    public ClassPathXmlApplicationContext(AbstractBeanFactory beanFactory, String location) throws Exception {
        super(beanFactory);
        this.location = location;
        refresh();
    }

    public ClassPathXmlApplicationContext(String location) throws Exception {
        this(new AutowireCapableBeanFactory(), location);
    }

    @Override
    protected void refresh() throws Exception {
        ResourceLoader resourceLoader = new UrlResourceLoader(location);
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(resourceLoader,new AutowireCapableBeanFactory());
        xmlBeanDefinitionReader.loadBeanDefinition();
        BeanDefinitionRegistry registry = xmlBeanDefinitionReader.getRegistry();
        for (String beanName : registry.getBeanDefinitionNames()) {
            beanFactory.registerBeanDefinition(beanName, registry.getBeanDefinition(beanName));
        }
//        for (Map.Entry<String, BeanDefinition> entry : xmlBeanDefinitionReader.getRegistry().entrySet())
//            beanFactory.registerBeanDefinition(entry.getKey(), entry.getValue());
    }
}
