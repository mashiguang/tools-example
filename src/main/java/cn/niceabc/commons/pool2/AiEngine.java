package cn.niceabc.commons.pool2;

import java.util.concurrent.TimeUnit;

public class AiEngine {

    public AiEngine() {
    }

    public String sayHello() throws InterruptedException {

        TimeUnit.MILLISECONDS.sleep(500);

        return "hello from " + this;
    }
}
