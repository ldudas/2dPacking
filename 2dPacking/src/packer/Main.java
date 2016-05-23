package packer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.xml.sax.HandlerBase;

public class Main
{

	public static void main(String[] args)
	{
		Block[] blocks = {	
							new Block(30,30),
							new Block(60,60),
							new Block(2,2),
							new Block(2,2)
						 };
		//blocks sort max(w,h)
		Comparator<Block> comparator =  (Block b1,Block b2) -> new Integer(Math.max(b2.getW(), b2.getH())).compareTo(Math.max(b1.getW(), b1.getH()));
	    Arrays.sort(blocks, comparator);
	    
	    
	    for(int i=0;i<blocks.length;i++) //ustawianie id/kolejnoœci w pd
	    {
	    	blocks[i].setId(i+1);
	    }
	    
	    Packer packer = new Packer();
	    packer.fit(blocks);
	    ArrayList<Node> minRoots = packer.findRootsWithMinArea();
	    int index=1;
	    for(Node n:minRoots) //dla kazdego korzenia z minimalna powierzchnia
	    {
	    	HashMap<Integer,Pair<Integer>> decisions = new HashMap<>();
	    	packer.getDecisions(n,decisions); //pobierz ci¹g decyzji w pd
	    	System.out.println("Min ci¹g decyzji nr: "+index);
	    	for(int j=1;j<=decisions.size();j++) //wypisz decyzje
	    	{
	    		Pair<Integer> decision = decisions.get(j);
	    		System.out.println(j+": ("+blocks[j-1].getW()+","+blocks[j-1].getH()+"): "+decision.getFirst()+","+decision.getSecond());
	    	}
	    	index++;
	    }
	   

	}

}
