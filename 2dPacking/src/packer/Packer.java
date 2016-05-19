package packer;

import java.util.ArrayList;
import java.util.HashMap;

public class Packer
{
	
	private ArrayList<Node> roots;
	
	public Packer(int w, int h)
	{
		Node root = new Node(0,0,w,h);
		roots = new ArrayList<>();
		roots.add(root);
	}
	
	public void fit(Block[] blocks)
	{
		Block block;
		
		for(int n=0;n<blocks.length;n++) //kazdy blok
		{
			block = blocks[n];
			
			ArrayList<Node> newRoots = new ArrayList<>();
			
			for(int k=0;k<roots.size();k++) //sprobuj dodac w kazdej z opcji
			{
				ArrayList<Node> putOptions = new ArrayList<>(); //opcje wlozenia aktualnego bloku w aktualne drzewo
				findNodes(roots.get(k),block.getW(),block.getH(),putOptions); //znajdz te opcje
				
				if(!putOptions.isEmpty())
				{
					for(int l=0;l<putOptions.size();l++)
					{
					  putBlockInNode(block,putOptions.get(l)); //w aktualnym drzewie zastosuj jedna z opcji
					  if(l!=putOptions.size()-1) //w kazdym kroku procz ostatnim
					  {
					  newRoots.add(new Node(roots.get(k))); //kopiuj drzewo z opcja
					  unputBlockInNode(block,putOptions.get(l)); //cofnij zostosowanie opcji
					  }
					  else //w ostatnim
					  {
						  newRoots.add(roots.get(k)); //dodaj stare drzewo z now¹ opcj¹
					  }
					}
				} 
				else
				{
					System.out.println("Nie da³o siê umieœciæ klocka: ("+block.getH()+","+block.getH()+")");
				}
			}
			
			roots.clear();
			roots = newRoots;
			
		}
		
	}
	
	public ArrayList<Node> findRootsWithMinArea()
	{
		ArrayList<Node> minRoots = new ArrayList<>();
		ArrayList<Integer> areas = new ArrayList<>();
		Integer minArea = Integer.MAX_VALUE;
		
		
		for(int i=0;i<roots.size();i++)
		{
			Integer area = calcArea(roots.get(i));
			areas.add(area);
			
			if(area<minArea) minArea=area;
		}
		
		for(int j=0;j<areas.size();j++)
		{
			if(areas.get(j).equals(minArea))
			{
				minRoots.add(roots.get(j));
			}
		}
		
		System.out.println("min pole: "+minArea);
		
		return minRoots;
	}
	
	public void getDecisions(Node n,HashMap<Integer,Pair<Integer>> decisions)
	{
		if(n.isUsed())
		{
			decisions.put(n.getBlock().getId(), new Pair<Integer>(n.getX(),n.getY()));
		}
		if(n.getDown()!=null) getDecisions(n.getDown(),decisions);
		if(n.getRight()!=null) getDecisions(n.getRight(),decisions);
	}
	
	private Integer calcArea(Node node)
	{
		Pair<Integer> maxXmaxY = findMaxXAndY(node,new Pair<Integer>(0,0));
		return maxXmaxY.getFirst()*maxXmaxY.getSecond();
	}
	
	private Pair<Integer> findMaxXAndY(Node n, Pair<Integer> maxXmaxY)
	{
		if(n.isUsed())
		{
			if(maxXmaxY.getFirst()<n.getX()+n.getBlock().getW()) maxXmaxY.setFirst(n.getX()+n.getBlock().getW());
			if(maxXmaxY.getSecond()<n.getY()+n.getBlock().getH()) maxXmaxY.setSecond(n.getY()+n.getBlock().getH());
			findMaxXAndY(n.getDown(), maxXmaxY);
			findMaxXAndY(n.getRight(), maxXmaxY);
		}
		
		return maxXmaxY;

	}

	private void unputBlockInNode(Block block, Node node)
	{
		node.setUsed(false);
		node.setBlock(null);
		node.setDown(null);
		node.setRight(null);
	}

	private void putBlockInNode(Block block, Node node)
	{
		node.setUsed(true);
		node.setBlock(block);
		node.setDown(new Node(node.getX(),node.getY()+block.getH(),node.getW(),node.getH()-block.getH()));
		node.setRight(new Node(node.getX()+block.getW(),node.getY(),node.getW()-block.getW(),block.getH()));
	}

	private void findNodes(Node root,int w, int h, ArrayList<Node> putOptions)
	{
		if(root.isUsed())
		{
			findNodes(root.getRight(), w, h,putOptions);
		    findNodes(root.getDown(), w, h,putOptions);
		}
		else if (w<=root.getW() && h<=root.getH())
		{
			putOptions.add(root);
		}

	}
	
}
