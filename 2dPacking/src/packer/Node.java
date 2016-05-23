package packer;

/**
 * Wêze³ drzewa konstuowanego w algorytmie ustawiania bloków na p³aszczyŸnie
 * Reprezentuje obszar na p³aszczyŸnie
 */
public class Node
{
	private int x; //wsp. x pocz¹tku obszaru
	private int y; //wsp. y pocz¹tku obszaru
	private int w; //szerokoœæ obszaru
	private int h; //wysokoœæ obszaru
	private boolean used; // czy w obszarze znajduje siê prostok¹t/blok
	private Node right; //pole na prawo (powstaje kiedy used==true)
	private Node down; //pole na dole (powstaje kiedy used==true)
	private Block block; //blok zajmuj¹cy obszar (!=null kiedy used==true)
	
	public Node()
	{
		x=0;
		y=0;
		w=0;
		h=0;
		used=false;
		right = null;
		down = null;
	}
	
	public Node(int x, int y, int w, int h)
	{
		this.x=x;
		this.y=y;
		this.h=h;
		this.w=w;
		used=false;
		right = null;
		down = null;
	}
	
	public Node(Node node)
	{
		this.x = node.x;
		this.y = node.y;
		this.h= node.h;
		this.w = node.w;
		this.used = node.used;
		this.block = node.block;
		
		if(node.right!=null) this.right = new Node(node.right);
		if(node.down!=null) this.down = new Node(node.down);
	}

	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public int getW()
	{
		return w;
	}
	public void setW(int w)
	{
		this.w = w;
	}
	public int getH()
	{
		return h;
	}
	public void setH(int h)
	{
		this.h = h;
	}
	public boolean isUsed()
	{
		return used;
	}
	public void setUsed(boolean used)
	{
		this.used = used;
	}
	public Node getRight()
	{
		return right;
	}
	public void setRight(Node right)
	{
		this.right = right;
	}
	public Node getDown()
	{
		return down;
	}
	public void setDown(Node down)
	{
		this.down = down;
	}

	public Block getBlock()
	{
		return block;
	}

	public void setBlock(Block block)
	{
		this.block = block;
	}

}
