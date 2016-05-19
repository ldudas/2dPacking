package packer;

import java.util.Arrays;
import java.util.Comparator;

public class Main
{

	public static void main(String[] args)
	{
		Block[] blocks = {	
							new Block(30,30),
							new Block(60,60),
							new Block(40,40),
							new Block(40,40),
							new Block(2,3),
							new Block(10,23),
							new Block(30,45)
						 };
		//blocks sort max(w,h)
		Comparator<Block> comparator =  (Block b1,Block b2) -> new Integer(Math.max(b2.getW(), b2.getH())).compareTo(Math.max(b1.getW(), b1.getH()));
	    Arrays.sort(blocks, comparator);
	    
	    
	    Packer packer = new Packer (100,100);
	    packer.fit(blocks);
	    
	    int counter=0;
	    for(Block b: blocks){
	    	System.out.print(counter+": ("+b.getW()+","+b.getH()+"): ");
	    	if (b.fit!=null)
	    	{
	    		System.out.println(b.getFit().getX()+","+b.getFit().getY());
	    	}
	    	else
	    	{
	    		System.out.println("nie zmiescil sie");
	    	}
	    	counter++;
	    }

	}

}
