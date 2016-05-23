package packer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Upakowuje bloki i przechwuje kolekcjê rozwi¹zañ w postaci drzew
 */
public class Packer
{
	
	private ArrayList<Node> roots; //korzenie drzew powsta³ych na kolejnych etapach programowanbia dynamicznego
	
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
			
			ArrayList<Node> newRoots = new ArrayList<>(); //nowa kolekcja korzeni (zast¹pi roots)
			
			for(int k=0;k<roots.size();k++) //sprobuj dodac w kazdej z opcji
			{
				ArrayList<Node> putOptions = new ArrayList<>(); //opcje wlozenia aktualnego bloku w aktualne drzewo
				findNodes(roots.get(k),block.getW(),block.getH(),putOptions); //znajdz te opcje
				
				if(!putOptions.isEmpty())
				{
					for(int l=0;l<putOptions.size();l++) //dla ka¿dej z opcji
					{
					  putBlockInNode(block,putOptions.get(l)); //w aktualnym drzewie zastosuj opcjê (ustaw blok w obszarze)
					  if(l!=putOptions.size()-1) //w kazdym kroku procz ostatnim
					  {
					  newRoots.add(new Node(roots.get(k))); //kopiuj drzewo z opcja (ustawieniem bloku w obszarze)
					  unputBlockInNode(putOptions.get(l)); //cofnij zostosowanie opcji
					  }
					  else //w ostatnim
					  {
						  newRoots.add(roots.get(k)); //dodaj stare drzewo z now¹ opcj¹ (u¿ycie starego drzewa - w ostatnim kroku ju¿ nie trzeba go kopiowaæ)
					  }
					}
				} 
				else
				{
					System.out.println("Nie da³o siê umieœciæ bloku: ("+block.getH()+","+block.getH()+")");
				}
			}
			
			roots.clear(); //wyczysc kolekcje korzeni
			roots = newRoots; //nadpisz kolekcjê nowych korzeni na miejsce starej
			
		}
		
	}
	
	/**
	 * @return Kolekcja korzeni, dla których drzewa (ustawienia bloków) zajmuj¹ najmniejszy obszar
	 */
	public ArrayList<Node> findRootsWithMinArea()
	{
		ArrayList<Node> minRoots = new ArrayList<>();
		ArrayList<Integer> areas = new ArrayList<>();
		Integer minArea = Integer.MAX_VALUE;
		
		
		for(int i=0;i<roots.size();i++) //dla kazdego z korzeni
		{
			Integer area = calcArea(roots.get(i)); //oblicz pole zjaetego obszaru jaki generuje drzewo
			areas.add(area); //dodaj pole do kolekcji pól obszarów
			
			if(area.compareTo(minArea)<0) minArea=area; //szukanie min obszaru
		}
		
		for(int j=0;j<areas.size();j++) //znajdowanie obszarów z polem równym minArea
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
	 * Znajduje ci¹g decyzji w porgramowniu dynamicznym i zachowuje go w decisions
	 * @param n wêze³ drzewa, z którego chcemy pozyskaæ decyzje
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
	 * @param node Korzeñ drzewa obszaru
	 * @return Pole jakie generuje obszar zajêty opisany przez drzewo
	 */
	private Integer calcArea(Node node)
	{
		Pair<Integer> maxXmaxY = findMaxXAndY(node,new Pair<Integer>(0,0));
		return maxXmaxY.getFirst()*maxXmaxY.getSecond();
	}
	
	/**
	 * @param n wêze³ drzewa dla którego znajudemy dwie wartoœci
	 * @param maxXmaxY aktualny wynik (rekursja)
	 * @return para(maxX,maxY) maxX - wsp x najbardziej wysuniêtego punktu pod wzglêdem osi X, maxY - wsp y najbardziej wysuniêtego punktu pod wzglêdem osi Y
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
	 * Cofnij ustawienie jakiegokolwiek bloku w wêŸle
	 */
	private void unputBlockInNode(Node node)
	{
		node.setUsed(false);
		node.setBlock(null);
		node.setDown(null);
		node.setRight(null);
	}

	/**
	 * Ustaw blok w wêŸle
	 */
	private void putBlockInNode(Block block, Node node)
	{
		node.setUsed(true);
		node.setBlock(block);
		node.setDown(new Node(node.getX(),node.getY()+block.getH(),node.getW(),node.getH()-block.getH()));
		node.setRight(new Node(node.getX()+block.getW(),node.getY(),node.getW()-block.getW(),block.getH()));
	}

	/**
	 * ZnajdŸ wêz³y w których mo¿na umieœciæ blok o wysokoœci h i szerokoœci w
	 * @param root Korzeñ drzewa
	 * @param w szerokoœæ bloku
	 * @param h wysokoœæ bloku
	 * @param putOptions kolekcja wyników
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
