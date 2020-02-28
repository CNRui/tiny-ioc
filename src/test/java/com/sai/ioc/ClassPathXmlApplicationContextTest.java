package com.sai.ioc;

import com.sai.ioc.context.ApplicationContext;
import com.sai.ioc.context.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ClassPathXmlApplicationContextTest {


    @Test
    public void test() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
        System.out.println("OK");
    }
}
