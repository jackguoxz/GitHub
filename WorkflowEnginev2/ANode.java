package com.dianping.workflow;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by  on 16/11/24.
 */
public class ANode {
    private int id;
    private volatile boolean started = false;
    private List<ANode> postNodeList; // 后继节点
    public ANode prev;
    private volatile CountDownLatch countDownLatch; // n个前驱，countDownLatch就设置为n
    private volatile Latch latch;
    public boolean completed;
    public ANode(int id, int nPreNode) {
        this.id = id;
        this.countDownLatch = new CountDownLatch(nPreNode);
        this.latch=new Latch(this);
        this.latch.seCountDowntLatch(this.countDownLatch);
        
        
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

    public List<ANode> getPostNodeList() {
        return postNodeList;
    }

    public void setPostNodeList(List<ANode> postNodeList) {
        this.postNodeList = postNodeList;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
    public Latch getLatch()
    {
    	return latch;
    }

}
