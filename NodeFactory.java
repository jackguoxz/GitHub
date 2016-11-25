package WorkFlow;

import java.util.ArrayList;

public class NodeFactory {



	public NodeFactory()
	{
		
	}
	public void init(Node root) {
		// TODO Auto-generated method stub
		
		root.setint(1);
		
		ArrayList<Node> next=new ArrayList<Node>();
		for(int i=2;i<5;i++)
		{
			Node n=new Node();
			n.setint(i);
			n.setnext(null);
			next.add(n);
			//n.setnext(nextnode);
		}
		root.next=next;
		Node n=new Node();
		ArrayList<Node> next2=root.next;
		int i=5;
	    for(Node no:next2)
	    {
	    	
	    	ArrayList<Node> next3=new ArrayList<Node>();
			for( i=6;i<1000;i++)
			{
				Node n2=new Node();
				n2.setint(i);
				n2.setnext(null);
				next3.add(n2);
				//n.setnext(nextnode);
			}
	    	no.next=next3;
	    	next3=null;
	    }
		
	}
}
