package WorkFlow;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

class Ticks
{
	int num;
}
public class WorkFlowEngine 
{
	private Node node;
	private int size;
	public ExecutorService pool;
	//private volatile int i=0;
	public CountDownLatch end;
	public Ticks ticks;
	public WorkFlowEngine(Node node,CountDownLatch latch) {
		this.node = node;
		this.size=node.count();
		this.ticks=new Ticks();
		//this.pool=new ThreadPoolExecutor(1000, 1000+10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),  
          //      new ThreadPoolExecutor.DiscardOldestPolicy());  
        this.pool=Executors.newFixedThreadPool(10);
		this.end=latch;

	}
	
	public void run2(Node root)
	{
		//int i=0;
		//System.out.println(this.num);
		List<Node> nodes=root.next;
		//i++;
		//res.num++;
		root.processor.run();
		if (root.next == null || root.next.size() == 0) 
		{
			
			return;
		}else
		
		{
			for (Node n : nodes) {
				//System.out.println("level is="+i);
				if(checkPrev(n)&&n.isVisited==false)
				{
				 run2(n);
				}else
				{
					//System.out.println("Skipped");
				}
			}
		}
		
	}

	public void walk(final Node n) {
		// TODO Auto-generated method stub
		
		n.processor.run();
		ticks.num++;

		selectDownNodes(n);
				
	}
	
	public boolean checkPrev(Node n)
	{
		for(Node pre: n.prev)
		{
			if(pre.isCompleted==false)
				return false;
		}
		return true;
		
	}
	
	public void selectDownNodes(Node n)
	{
		List<Node> nodes = n.next;		
		if (n.next == null || n.next.size() == 0) {
			end.countDown();
			return;
		} else

		{
			for (Node n2 : nodes) {
				// System.out.println("level is="+i);
				// i++;
				//n.run();
				if(checkPrev(n2)&&n2.isVisited==false)
				{
					execute(n2);
				}else
				{
					//System.out.println("Skipped");
				}
			}
		}
	}
	
	public void execute( final Node node2) {

		try {
			
			//ProcessorRunnable processorRunnable = new ProcessorRunnable(node,end);
			//processorRunnable.setPriority(node.getNodePriority());
			
			//processorExecutor.execute(processorRunnable);
			
			pool.execute(new Runnable() {
                @Override
				public void run() {
                    //WorkFlowEngine.this.walk(currentNode, task);
					walk(node2);
                }
            });
			

		} catch (RejectedExecutionException e) {
			//logger.log(LogLevel.ERROR, e);

			throw e;
		}
		
		
}
	
}