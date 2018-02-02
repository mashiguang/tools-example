package cn.niceabc.commons.pool2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    public static void main(String[] args) {
        int threads = 20;

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        long begin = System.currentTimeMillis();

        List<Future> futures = new ArrayList<Future>();
        for (int i = 0; i < 100; i++) {
            futures.add(executorService.submit(new Runnable() {
                public void run() {

                    try {
                        AiEngine engine = AiEnginePoolFactory.borrowObj();

                        System.out.println(Thread.currentThread().getName()+" - " + engine.sayHello());

                        AiEnginePoolFactory.returnObj(engine);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, i));

        }

        for (Future f: futures) {
            try {
                System.out.println(Thread.currentThread().getName()+" - " + f.get() + " has done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        AiEnginePoolFactory.close();

        executorService.shutdown();

        long end = System.currentTimeMillis();
        System.out.println("用时ms:" + (end - begin));
        // 20线程用时ms:17152
        // 10线程用时ms:17215
        //  3线程用时ms:17169
        //  1线程用时ms:50168
        // 把服务对象放到池里后，程序性能和服务对象个数有关，和服务对象本身的性能有关，
        // 和有多少个线程调用服务对象关系不大，线程数和服务对象数相等时性能达到上限。

    }
}
