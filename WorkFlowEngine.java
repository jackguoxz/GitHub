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
        this.pool=Executors.newFixedThreadPool(1000);
		this.end=latch;

	}

	public void walk(final Node n) {
		// TODO Auto-generated method stub
		
		n.processor.run();
		ticks.num++;

		selectDownNodes(n);
				
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
				
				execute(n2);
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