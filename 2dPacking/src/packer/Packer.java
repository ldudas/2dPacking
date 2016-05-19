package packer;

public class Packer
{
	private Node root;
	
	public Packer(int w, int h)
	{
		root = new Node(0,0,w,h);
	}
	
	public void fit(Block[] blocks)
	{
		Node node;
		Block block;
		
		for(int n=0;n<blocks.length;n++)
		{
			block = blocks[n];
			node = findNode(root,block.getW(),block.getH());
			
			if(node!=null)
			{
				block.setFit(splitNode(node,block.getW(),block.getH()));
			}
		}
	}
	
	private Node findNode(Node root,int w, int h)
	{
		if(root.isUsed())
		{
			Node rightAnswer = findNode(root.getRight(), w, h);
		    return rightAnswer!=null?rightAnswer:findNode(root.getDown(), w, h);
		}
		else if (w<=root.getW() && h<=root.getH())
		{
			return root;
		}
		else return null;
	}
	
	private Node splitNode(Node n, int w, int h)
	{
		n.setUsed(true);
		n.setDown(new Node(n.getX(),n.getY()+h,n.getW(),n.getH()-h));
		n.setRight(new Node(n.getX()+w,n.getY(),n.getW()-w,h));
		return n;
	}
}
