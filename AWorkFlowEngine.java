package com.dianping.workflow;

import java.util.concurrent.Executor;

public class AWorkFlowEngine {
    private Executor pool;

    public AWorkFlowEngine(Executor pool) {
        this.pool = pool;
    }

    public void walk(ANode root, final ANodeTask task) throws InterruptedException {
        if (root == null) {
            return;
        }

        root.getCountDownLatch().await();

        
        task.doTask(root);

        for (final ANode currentNode : root.getPostNodeList()) {
            currentNode.getCountDownLatch().countDown();

            if (!currentNode.isStarted()) {
                currentNode.setStarted(true);
                pool.execute(new Runnable() {
                    public void run() {
                        try {
                            //WorkFlowEngine.this.walk(currentNode, task);
                        	walk(currentNode, task);
                        } catch (InterruptedException ignored) {
                        }
                    }
                });
            }
        }

    }
}
