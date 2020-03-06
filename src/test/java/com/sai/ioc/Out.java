package com.sai.ioc;


import com.sai.ioc.beans.annotation.Component;

@Component
public class Out {

    public void print(String s) {
        System.out.println("Hello " + s);
    }
}
