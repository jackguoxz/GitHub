package com.dianping.workflow;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuchao on 16/11/24.
 */
public class AMain {
    public static void main(String[] args) throws InterruptedException {
        ANode root = ANodeFactory.createNode();
        final CountDownLatch countDownLatch = new CountDownLatch(ANodeFactory.getNNode());

        ExecutorService pool = Executors.newFixedThreadPool(10);
        AWorkFlowEngine workFlowEngine = new AWorkFlowEngine(pool);

        System.out.println("-------begin---------");
        long begin = System.currentTimeMillis();
        workFlowEngine.walk(root, new ANodeTask() {
            @Override
            public void doTask(ANode node) {
                quietRandomSleep(node);
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
        System.out.println("-------begin---------");
        System.out.println("cost " + (System.currentTimeMillis() - begin) + "ms");

        pool.shutdown();
    }

    private static void quietRandomSleep(ANode node) {
        try {
            System.out.println("processing node[" + node.getId() + "]");
            Thread.sleep(node.getId() * 10);
            System.out.println("end process node[" + node.getId() + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
