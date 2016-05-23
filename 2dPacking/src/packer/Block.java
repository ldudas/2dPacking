package packer;

/**
 * Blok/prostok�t umieszczany na p�aszczy�nie
 */
public class Block
{
	private int w; //szeroko��
	private int h; //wysoko��
	private int id; //numer bloku / numer kroku programowania dynamicznego w ktorym bedzie on ustawiany na plaszczyznie
	
	public Block(int width, int height)
	{
		w=width;
		h=height;
		id = -1;
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
