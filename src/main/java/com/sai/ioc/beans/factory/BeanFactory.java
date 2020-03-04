package com.sai.ioc.beans.factory;

public interface BeanFactory<T> {

    T getBean(String name) throws Exception;

}
