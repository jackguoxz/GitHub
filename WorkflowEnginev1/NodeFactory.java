package WorkFlow;

import java.util.ArrayList;

public class NodeFactory {



	public NodeFactory()
	{
		
	}
	public void init(Node root) {
		// TODO Auto-generated method stub
		
		root.setint(1);
		//Node tmp;
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
		for(Node no:root.next)
		{
			no.prev.add(root);
		}
		Node n=new Node();
		ArrayList<Node> end2=new ArrayList<Node>();
		Node end3=new Node();
		end3.setint(199);
		end3.setnext(null);
		//end4.setint(199);
		end2.add(end3);
		ArrayList<Node> next2=root.next;
		int i=5;
	    for(Node no:next2)
	    {
	    	
	    	ArrayList<Node> next3=new ArrayList<Node>();
			for( i=6;i<50;i++)
			{
				Node n2=new Node();
				n2.setint(i);
				n2.setnext(null);
				next3.add(n2);
				//n.setnext(nextnode);
			}
	    	no.next=next3;
	    	for(Node leaf:next3)
	    	{
	    		leaf.next=end2;
	    		leaf.prev.add(no);
	    		end2.get(0).prev.add(leaf);
	    		
	    	}
	    	//next3=null;
	    }
		
	}
}
