package com.sai.ioc.beans.factory;

import com.sai.ioc.beans.BeanDefinition;
import com.sai.ioc.beans.BeanReference;
import com.sai.ioc.beans.PropertyValue;
import com.sai.ioc.beans.PropertyValues;

import java.lang.reflect.Field;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = doCreateBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }


    private Object doCreateBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Field field = bean.getClass().getDeclaredField(propertyValue.getName());
            field.setAccessible(true);
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                value = getBean(((BeanReference) value).getName());
            }
            field.set(bean, value);
        }
    }
}
