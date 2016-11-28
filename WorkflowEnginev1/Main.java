package WorkFlow;

import java.util.concurrent.CountDownLatch;



public class Main {
	
	public static void main(String []args)
	{
		Node root=new Node();
		Main instance=new Main();
		NodeFactory factory=new NodeFactory();
		factory.init(root);
		System.out.println("total leaf is "+root.leaf());
		System.out.println("total node is "+root.count());
		CountDownLatch latch=new CountDownLatch(root.leaf());
		WorkFlowEngine  pr=new WorkFlowEngine(root,latch);
		long start = System.currentTimeMillis();
		pr.run2(root);
		root.reset();
		long end = System.currentTimeMillis();
		System.out.println("seq time "+(end-start));
		System.out.println("----------");		
		pr.walk(root);
		
		//System.out.println("end is "+pr.end.getCount());
		try{
            latch.await();            //等待end状态变为0，即为比赛结束
        }catch (InterruptedException e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            //System.out.println("Race ends!");            
        }		
		System.out.println("Race ends here!");
		long end2 = System.currentTimeMillis();
		System.out.println("paralle time "+ (end2-end));
		System.out.println("----------");
		//pr.processorExecutor.shutdown();
		pr.pool.shutdown();
		System.out.println(pr.ticks.num);
		
	}
	
	

}
