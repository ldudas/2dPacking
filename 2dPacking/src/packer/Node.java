package packer;

public class Node
{
	private int x;
	private int y;
	private int w;
	private int h;
	private boolean used;
	private Node right;
	private Node down;
	
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

}
