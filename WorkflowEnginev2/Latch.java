package com.dianping.workflow;

import java.util.concurrent.CountDownLatch;

public class Latch {
	
	private volatile CountDownLatch latch;
	public int id;
	public ANode node;
	public CountDownLatch getCountDownLatch()
	{
		//System.out.print("Latch"+this.id+"Parent Latch"+this.);
		return latch;
	}
	public void seCountDowntLatch(CountDownLatch latch)
	{
		this.latch=latch;
		
	}
	
	public Latch(ANode node)
	{
		this.node=node;
		this.id=node.getId();
	}

}
