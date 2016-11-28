package WorkFlow;


public class Processor implements Task {
	
	public int ID;
	private Node node;
	//public Ticks ticks;
	@Override
	public void run()
	{
		//System.out.println("Processor "+ID+" is running");
		
		try {
            //System.out.println("processing node[" + node.num + "]");
            Thread.sleep(node.num);
			//Thread.sleep(1);
            //System.out.println("end process node[" + node.num + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally
        {
        	node.isCompleted=true;
        	node.isVisited=true;
        }
	
	}
	


	public Processor(Node n) {
		// TODO Auto-generated constructor stub
		
		node=n;
		this.ID=n.num;
		
	}

}
