package WorkFlow;

import java.util.ArrayList;
import java.util.List;

class Result
{
	int num;
}
class Node 
{
	Node prev;
	//Node cur;
	//ArrayList<Node> next;
	int num;
	public ArrayList<Node> next;
	public Processor processor;
	public int total;
	public void run(Result res)
	{
		//int i=0;
		//System.out.println(this.num);
		List<Node> nodes=next;
		//i++;
		
		if (next == null || next.size() == 0) 
		{
			res.num++;
			return;
		}else
		
		{
			for (Node n : next) {
				//System.out.println("level is="+i);
				
				n.run(res);
			}
		}
		
	}
	
	public void run2(Result res)
	{
		//int i=0;
		//System.out.println(this.num);
		List<Node> nodes=next;
		//i++;
		res.num++;
		if (next == null || next.size() == 0) 
		{
			
			return;
		}else
		
		{
			for (Node n : next) {
				//System.out.println("level is="+i);
				
				n.run2(res);
			}
		}
		
	}
	
	public int count()
	{
		Result res=new Result();
		run2(res);
		return res.num;
	}
			
	public int leaf()
	{
		Result res=new Result();
		run(res);
		return res.num;
	}
	
	public void setint(int i)
	{
		num=i;
		processor.ID=num;
	}
	
	public void setnext(ArrayList<Node> nextnode)
	{
		next=nextnode;
	}
	public Node()
	{
		prev=null;
		//cur=null;
		next=new ArrayList<Node>();
		processor=new Processor(this);
		//next =new Node[100];
	}
}
