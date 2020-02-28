package com.sai.ioc;

import com.sai.ioc.context.ApplicationContext;
import com.sai.ioc.context.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
