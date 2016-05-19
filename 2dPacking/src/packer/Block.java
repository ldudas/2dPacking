package packer;

public class Block
{
	private int w;
	private int h;
	private int id;
	
	public Block(int width, int height)
	{
		w=width;
		h=height;
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
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	@Override
	public String toString()
	{
		return "[w=" + w + ", h=" + h + "]";
	}
	
	
}
