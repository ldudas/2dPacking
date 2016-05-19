package packer;

public class Pair<T>
{
	T first;
	T second;
	
	public Pair()
	{}
	
	public Pair(T f, T s)
	{
		first=f;
		second = s;
	}

	public T getFirst()
	{
		return first;
	}

	public void setFirst(T first)
	{
		this.first = first;
	}

	public T getSecond()
	{
		return second;
	}

	public void setSecond(T second)
	{
		this.second = second;
	}
	
	
}
