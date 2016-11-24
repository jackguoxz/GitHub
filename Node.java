package com.dianping.workflow;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by yuchao on 16/11/24.
 */
public class Node {
    private int id;
    private volatile boolean started = false;
    private List<Node> postNodeList; // 后继节点
    private volatile CountDownLatch countDownLatch; // n个前驱，countDownLatch就设置为n

    public Node(int id, int nPreNode) {
        this.id = id;
        this.countDownLatch = new CountDownLatch(nPreNode);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getId() {
        return id;
    }

    public List<Node> getPostNodeList() {
        return postNodeList;
    }

    public void setPostNodeList(List<Node> postNodeList) {
        this.postNodeList = postNodeList;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

}
