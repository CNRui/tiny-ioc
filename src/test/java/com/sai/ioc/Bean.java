package com.sai.ioc;

import com.sai.ioc.beans.annotation.Autowired;
import com.sai.ioc.beans.annotation.Component;
import com.sai.ioc.beans.annotation.Value;

@Component("b")
public class Bean {

    @Value("bean b")
    private String bbb;

    @Autowired
    private Out out;

    public void b() {
        out.print(bbb);
    }


}
