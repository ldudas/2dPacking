package packer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Upakowuje bloki i przechwuje kolekcj� rozwi�za� w postaci drzew
 */
public class Packer
{
	
	private ArrayList<Node> roots; //korzenie drzew powsta�ych na kolejnych etapach programowanbia dynamicznego
	
	public Packer()
	{
		Node root = new Node(0,0,Integer.MAX_VALUE,Integer.MAX_VALUE); //poczatkowo jeden obszar wolny
		roots = new ArrayList<>();
		roots.add(root);
	}
	
	public void fit(Block[] blocks)
	{
		Block block;
		
		for(int n=0;n<blocks.length;n++) //kazdy blok
		{
			block = blocks[n];
			
			ArrayList<Node> newRoots = new ArrayList<>(); //nowa kolekcja korzeni (zast�pi roots)
			
			for(int k=0;k<roots.size();k++) //sprobuj dodac w kazdej z opcji
			{
				ArrayList<Node> putOptions = new ArrayList<>(); //opcje wlozenia aktualnego bloku w aktualne drzewo
				findNodes(roots.get(k),block.getW(),block.getH(),putOptions); //znajdz te opcje
				
				if(!putOptions.isEmpty())
				{
					for(int l=0;l<putOptions.size();l++) //dla ka�dej z opcji
					{
					  putBlockInNode(block,putOptions.get(l)); //w aktualnym drzewie zastosuj opcj� (ustaw blok w obszarze)
					  if(l!=putOptions.size()-1) //w kazdym kroku procz ostatnim
					  {
					  newRoots.add(new Node(roots.get(k))); //kopiuj drzewo z opcja (ustawieniem bloku w obszarze)
					  unputBlockInNode(putOptions.get(l)); //cofnij zostosowanie opcji
					  }
					  else //w ostatnim
					  {
						  newRoots.add(roots.get(k)); //dodaj stare drzewo z now� opcj� (u�ycie starego drzewa - w ostatnim kroku ju� nie trzeba go kopiowa�)
					  }
					}
				} 
				else
				{
					System.out.println("Nie da�o si� umie�ci� bloku: ("+block.getH()+","+block.getH()+")");
				}
			}
			
			roots.clear(); //wyczysc kolekcje korzeni
			roots = newRoots; //nadpisz kolekcj� nowych korzeni na miejsce starej
			
		}
		
	}
	
	/**
	 * @return Kolekcja korzeni, dla kt�rych drzewa (ustawienia blok�w) zajmuj� najmniejszy obszar
	 */
	public ArrayList<Node> findRootsWithMinArea()
	{
		ArrayList<Node> minRoots = new ArrayList<>();
		ArrayList<Integer> areas = new ArrayList<>();
		Integer minArea = Integer.MAX_VALUE;
		
		
		for(int i=0;i<roots.size();i++) //dla kazdego z korzeni
		{
			Integer area = calcArea(roots.get(i)); //oblicz pole zjaetego obszaru jaki generuje drzewo
			areas.add(area); //dodaj pole do kolekcji p�l obszar�w
			
			if(area.compareTo(minArea)<0) minArea=area; //szukanie min obszaru
		}
		
		for(int j=0;j<areas.size();j++) //znajdowanie obszar�w z polem r�wnym minArea
		{
			if(areas.get(j).equals(minArea))
			{
				minRoots.add(roots.get(j));
			}
		}
		
		System.out.println("min pole: "+minArea);
		
		return minRoots;
	}
	
	/**
	 * Znajduje ci�g decyzji w porgramowniu dynamicznym i zachowuje go w decisions
	 * @param n w�ze� drzewa, z kt�rego chcemy pozyska� decyzje
	 * @param decisions HashMap<krok w pd, para(x,y) ustawienia bloku w danym kroku>
	 */
	public void getDecisions(Node n,HashMap<Integer,Pair<Integer>> decisions)
	{
		if(n.isUsed())
		{
			decisions.put(n.getBlock().getId(), new Pair<Integer>(n.getX(),n.getY()));
		}
		if(n.getDown()!=null) getDecisions(n.getDown(),decisions);
		if(n.getRight()!=null) getDecisions(n.getRight(),decisions);
	}
	
	/**
	 * @param node Korze� drzewa obszaru
	 * @return Pole jakie generuje obszar zaj�ty opisany przez drzewo
	 */
	private Integer calcArea(Node node)
	{
		Pair<Integer> maxXmaxY = findMaxXAndY(node,new Pair<Integer>(0,0));
		return maxXmaxY.getFirst()*maxXmaxY.getSecond();
	}
	
	/**
	 * @param n w�ze� drzewa dla kt�rego znajudemy dwie warto�ci
	 * @param maxXmaxY aktualny wynik (rekursja)
	 * @return para(maxX,maxY) maxX - wsp x najbardziej wysuni�tego punktu pod wzgl�dem osi X, maxY - wsp y najbardziej wysuni�tego punktu pod wzgl�dem osi Y
	 */
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

	/**
	 * Cofnij ustawienie jakiegokolwiek bloku w w�le
	 */
	private void unputBlockInNode(Node node)
	{
		node.setUsed(false);
		node.setBlock(null);
		node.setDown(null);
		node.setRight(null);
	}

	/**
	 * Ustaw blok w w�le
	 */
	private void putBlockInNode(Block block, Node node)
	{
		node.setUsed(true);
		node.setBlock(block);
		node.setDown(new Node(node.getX(),node.getY()+block.getH(),node.getW(),node.getH()-block.getH()));
		node.setRight(new Node(node.getX()+block.getW(),node.getY(),node.getW()-block.getW(),block.getH()));
	}

	/**
	 * Znajd� w�z�y w kt�rych mo�na umie�ci� blok o wysoko�ci h i szeroko�ci w
	 * @param root Korze� drzewa
	 * @param w szeroko�� bloku
	 * @param h wysoko�� bloku
	 * @param putOptions kolekcja wynik�w
	 */
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
