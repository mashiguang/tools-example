package cn.niceabc.commons.pool2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    public static void main(String[] args) {
        int threads = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threads);


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
    }
}
