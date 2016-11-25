package WorkFlow;


public class Processor implements Task {
	
	public int ID;
	//public Ticks ticks;
	@Override
	public void run()
	{
		System.out.println("Processor "+ID+" is running");
	
	}
	


	public Processor(Node n) {
		// TODO Auto-generated constructor stub
		
		this.ID=n.num;
		
	}

}
