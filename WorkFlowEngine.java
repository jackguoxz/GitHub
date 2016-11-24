package com.dianping.workflow;

import java.util.concurrent.Executor;

/**
 * Created by yuchao on 16/11/24.
 */
public class WorkFlowEngine {
    private Executor pool;

    public WorkFlowEngine(Executor pool) {
        this.pool = pool;
    }

    public void walk(Node root, final NodeTask task) throws InterruptedException {
        if (root == null) {
            return;
        }

        root.getCountDownLatch().await();

        task.doTask(root);

        for (final Node currentNode : root.getPostNodeList()) {
            currentNode.getCountDownLatch().countDown();

            if (!currentNode.isStarted()) {
                currentNode.setStarted(true);
                pool.execute(new Runnable() {
                    public void run() {
                        try {
                            WorkFlowEngine.this.walk(currentNode, task);
                        } catch (InterruptedException ignored) {
                        }
                    }
                });
            }
        }

    }
}
