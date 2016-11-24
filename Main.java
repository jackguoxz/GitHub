package com.dianping.workflow;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuchao on 16/11/24.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Node root = NodeFactory.createNode();
        final CountDownLatch countDownLatch = new CountDownLatch(NodeFactory.getNNode());

        ExecutorService pool = Executors.newFixedThreadPool(10);
        WorkFlowEngine workFlowEngine = new WorkFlowEngine(pool);

        System.out.println("-------begin---------");
        long begin = System.currentTimeMillis();
        workFlowEngine.walk(root, new NodeTask() {
            @Override
            public void doTask(Node node) {
                quietRandomSleep(node);
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
        System.out.println("-------begin---------");
        System.out.println("cost " + (System.currentTimeMillis() - begin) + "ms");

        pool.shutdown();
    }

    private static void quietRandomSleep(Node node) {
        try {
            System.out.println("processing node[" + node.getId() + "]");
            Thread.sleep(node.getId() * 10);
            System.out.println("end process node[" + node.getId() + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
