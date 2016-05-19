package packer;

public class Block
{
	private int w;
	private int h;
	Node fit;
	
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
	public Node getFit()
	{
		return fit;
	}
	public void setFit(Node fit)
	{
		this.fit = fit;
	}
	
	@Override
	public String toString()
	{
		return "[w=" + w + ", h=" + h + "]";
	}
	
	
}
