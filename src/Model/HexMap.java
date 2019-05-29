package model;

import java.util.HashMap;
import static java.lang.Math.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Stack;


public class HexMap {
	/**
 	* HashMap contenant l'ensemble des Hex de la map et servant à son stockage.
	* Chaque Hex h aura pour clef Object.hashCode(h)
	* Cela permet un stockage efficace de la map, en permettant toutes
	* formes pour celle-ci.
	*/
	private HashMap<Integer, Hex> map = new HashMap<>();


	/**
	* Retourne la HashMap contenant la carte.
	* @return HashMap<Integer, Hex>
	*/
	public HashMap<Integer, Hex> getHashMap() {
		return map;
	}

	/**
	* Permet d'accéder à un Hex de la hashmap à partir de ses coordonnées cubiques.
	* @param x int
	* @param y int
	* @param z int
	* @return Hex
	*/
	public Hex getHex(int x, int y, int z) {
		return map.get(new Hex(x, y, z).hashCode());
	}


	/**
	* Permet d'accéder à un Hex de la hashmap à partir de ses coordonnées axiales.
	* @param x int
	* @param y int
	* @return Hex
	*/
	public Hex getHex(int x, int y) {
		return map.get(new Hex(x, y).hashCode());
	}


	/**
	* Permet d'ajouter un Hex à la hashmap, retourne le hash
	* @param a hexagone
	* @return Hex
	*/
	public Hex addHex(Hex a) {
		return map.put(a.hashCode(), a);
	}


	public void deleteMap() {
		map = new HashMap<Integer, Hex>();
	}


	/**
	* Génère une map en forme de triangle équilatéral de côté mapSize.
	* @param mapSize int
	*/
	public void setTriangleMap(int mapSize) {
		for (int x = 0; x <= mapSize; x++) {
    		for (int y = 0; y <= mapSize - x; y++) {
				Plaine newHex = new Plaine(x, y, -x - y);
				map.put(newHex.hashCode(), newHex);
    		}
		}
	}


	/**
	* Génère une map en forme d'hexagone de côté mapSize.
	* @param mapSize int
	*/
	public void setHexagonMap(int mapSize) {
		for (int x = -mapSize; x <= mapSize; x++) {

			int y1 = max(-mapSize, -x - mapSize);
    		int y2 = min(mapSize, -x + mapSize);

			for (int y = y1; y <= y2; y++) {
				Plaine newHex = new Plaine(x, y, -x - y);
	        	map.put(newHex.hashCode(), newHex);
			}

		}
	}


	/**
	* Génère une map rectangulaire de taille width*height.
	* @param height int
	* @param width int
	*/
	public void setRectangleMap(int height, int width) {

		for (int y = 0; y < height; y++) {

			int yOffset = (int) floor(y / 2);

    		for (int x = -yOffset; x < width - yOffset; x++) {
				Plaine newHex = new Plaine(x, y, -x - y);
	        	map.put(newHex.hashCode(), newHex);
    		}
		}
	}


	private void setBiome(Hex hex, float biomeValue, int n) {
		double popValue = Math.random();

		if(popValue < hex.getRarity()){
			if(biomeValue < 0.225)
				addHex(new Eau(hex.getX(), hex.getY()));

			else if(biomeValue < 0.450)
				addHex(new Foret(hex.getX(), hex.getY()));

			else if(biomeValue < 0.675)
				addHex(new Montagne(hex.getX(), hex.getY()));

			else if(biomeValue < 0.9)
				addHex(new Neige(hex.getX(), hex.getY()));

			else
				addHex(new Forteresse(hex.getX(), hex.getY()));

			propagate(hex, biomeValue, n);
		}
	}


	private void propagate(Hex source, float biomeValue, int n) {
		n++;
		if(n > 10)
			return;

		Hex[] neighbours = source.getNeighbours();

		for(int i=0; i<6; i++) {
			Hex next = getHex(neighbours[i].getX(), neighbours[i].getY(), neighbours[i].getZ());

			if(next == null)
				continue;

			if(biomeValue < 0.225)
				next = new Eau(next.getX(), next.getY());

			else if(biomeValue < 0.450)
				next = new Foret(next.getX(), next.getY());

			else if(biomeValue < 0.675)
				next = new Montagne(next.getX(), next.getY());

			else if(biomeValue < 0.9)
				next = new Neige(next.getX(), next.getY());

			else
				next = new Forteresse(next.getX(), next.getY());


			setBiome(next, biomeValue, n);
		}
	}


	public void populate() {
		int nBiomes = (int) Math.sqrt(getWidth() * getHeight() / 4);

		Random generator = new Random();
		Object [] values = map.values().toArray();


		for(int i=0; i<nBiomes; i++) {

			Hex next = (Hex) values[generator.nextInt(values.length)];

			float biomeValue = (float) Math.random();

			if(biomeValue < 0.225)
				addHex(new Eau(next.getX(), next.getY()));

			else if(biomeValue < 0.450)
				addHex(new Foret(next.getX(), next.getY()));

			else if(biomeValue < 0.675)
				addHex(new Montagne(next.getX(), next.getY()));

			else if(biomeValue < 0.9)
				addHex(new Neige(next.getX(), next.getY()));

			else
				addHex(new Forteresse(next.getX(), next.getY()));

			propagate(next, biomeValue, 0);

		}
	}



	/**
	* Retourne les voisins de l'hexagone en prenant compte
	* de la gémoétrie de la carte
	* @param a Hex
	* @return ArrayList
	*/
	public ArrayList getNeighbours(Hex a) {
		Hex[] geometricNeighbours = a.getNeighbours();

		ArrayList<Hex> neighbours = new ArrayList<Hex>();

		for (int i = 0; i < 6; i++) {
			Hex curr = geometricNeighbours[i];

			Hex neighbour = getHex(curr.getX(), curr.getY(), curr.getZ());
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
		}

		return neighbours;
	}


	/**
	* Retourne la largeur de la carte
	* @return int
	*/
	public int getWidth() {
		Iterator it = map.entrySet().iterator();

		//WARNING Valide seulement si la map possède une origine
		int min = 0;
		int max = 0;

		for (Hex value : map.values()) {
		    if(value.getX() < min) min = value.getX();
			if(value.getX() > max) max = value.getX();
		}

		return abs(max - min);
	}


	/**
	* Retourne la hauteur de la carte
	* @return int
	*/
	public int getHeight() {
		Iterator it = map.entrySet().iterator();

		//WARNING Valide seulement si la map possède une origine
		int min = 0;
		int max = 0;

		for (Hex value : map.values()) {
		    if(value.getY() < min) min = value.getY();
			if(value.getY() > max) max = value.getY();
		}

		return abs(max - min);
	}


	/**
	* Retourne la plus petite coordonnée en X
	* @return int
	*/
	public int getMinX() {
		Iterator it = map.entrySet().iterator();

		//WARNING Valide seulement si la map possède une origine
		int min = 0;

		for (Hex value : map.values()) {
		    if(value.getX() < min) min = value.getX();
		}

		return min;
	}


	/**
	* Retourne la plus grande coordonnée en X
	* @return int
	*/
	public int getMaxX() {
		Iterator it = map.entrySet().iterator();

		//WARNING Valide seulement si la map possède une origine
		int max = 0;

		for (Hex value : map.values()) {
		    if(value.getX() > max) max = value.getX();
		}

		return max;
	}


	/**
	* Retourne la plus petite coordonnée en Y
	* @return int
	*/
	public int getMinY() {
		Iterator it = map.entrySet().iterator();

		//WARNING Valide seulement si la map possède une origine
		int min = 0;

		for (Hex value : map.values()) {
		    if(value.getY() < min) min = value.getY();
		}

		return min;
	}


	/**
	* Retourne la plus grande coordonnée en Y
	* @return int
	*/
	public int getMaxY() {
		Iterator it = map.entrySet().iterator();

		//WARNING Valide seulement si la map possède une origine
		int max = 0;

		for (Hex value : map.values()) {
		    if(value.getY() > max) max = value.getY();
		}

		return max;
	}


	/**
	* Affiche la map dans la console
	*/
	public void ASCIIDisplay() {

		Hex current;

		for(int i = getMinX(); i < getMaxX(); i++) {
			for(int j = getMinY(); j < getMaxY(); j++) {
				current = getHex(i, j);
				if(current == null) {
					System.out.print(" ");
				}
				else {
					//Existing tile, can be detailed with other cases
					getHex(i, j).print();
				}
				System.out.print(" ");
			}
			System.out.println("");
		}
	}



	public ArrayList<Hex> viewHighlight(Hex source, int viewDist) {
		Stack<Hex> stack = new Stack<Hex>();
		HashMap<Integer, Integer> costList = new HashMap<Integer, Integer>();
		ArrayList<Hex> returnValue = new ArrayList<Hex>();

		stack.push(source);
		costList.put(source.hashCode(), 0);

		while(!stack.empty()) {
			Hex current = stack.pop();

			returnValue.add(current);

			ArrayList<Hex> neighbours = getNeighbours(current);

			for(int i=0; i<neighbours.size(); i++) {


				int newCost = costList.get(current.hashCode())+1;

				if(newCost <= viewDist && costList.get(neighbours.get(i).hashCode()) == null){
					stack.push(neighbours.get(i));
					costList.put(neighbours.get(i).hashCode(), newCost);
				}
			}
		}

		return returnValue;

	}


	public ArrayList<Hex> movementHighlight(Hex source, int viewDist) {
		Stack<Hex> stack = new Stack<Hex>();
		HashMap<Integer, Integer> costList = new HashMap<Integer, Integer>();
		ArrayList<Hex> returnValue = new ArrayList<Hex>();

		stack.push(source);
		costList.put(source.hashCode(), 0);

		while(!stack.empty()) {
			Hex current = stack.pop();

			returnValue.add(current);

			ArrayList<Hex> neighbours = getNeighbours(current);

			for(int i=0; i<neighbours.size(); i++) {


				int newCost = costList.get(current.hashCode())+neighbours.get(i).getCost();

				if(newCost <= viewDist && costList.get(neighbours.get(i).hashCode()) == null){
					stack.push(neighbours.get(i));
					costList.put(neighbours.get(i).hashCode(), newCost);
				}
			}
		}

		return returnValue;

	}


	public Unite getClosestAlly(Hex source, Joueur owner) {
		int minDist = getHeight() + getWidth();
		Unite returnValue = null;

		System.out.println(map.values().size());

		for (Hex value : map.values()) {

			if(value.getUnit() == null)
				continue;


			if(value.getUnit().getJoueur() == null)
				continue;

			Joueur joueur = value.getUnit().getJoueur();

			if(source.distance(value) < minDist && joueur.id == owner.id ){
				minDist = source.distance(value);
				returnValue = value.getUnit();
			}

		}

		return returnValue;
	}



	public Unite getClosestEnemy(Hex source, Joueur owner) {
		int minDist = getHeight() + getWidth();
		Unite returnValue = null;

		System.out.println(map.values().size());

		for (Hex value : map.values()) {

			if(value.getUnit() == null)
				continue;


			if(value.getUnit().getJoueur() == null)
				continue;

			Joueur joueur = value.getUnit().getJoueur();

			if(source.distance(value) < minDist && joueur.id != owner.id ){
				minDist = source.distance(value);
				returnValue = value.getUnit();
			}

		}

		return returnValue;
	}



	/**
	* Retourne le chemin de l'hexagone start vers l'hexagone goal.
	* Si il n'existe pas de chemin ou que l'un des deux hexagones
	* n'est pas dans la HashMap, retourne un tableau vide.
	* On utilise une implémentation de l'algorithme A*.
	* @param start hexagone
	* @param goal hexagone
	* @return ArrayList<Hex>
	*/
	public ArrayList<Hex> pathfinding(Hex start, Hex goal) {
		SortedHexList open = new SortedHexList();
		SortedHexList closed = new SortedHexList();
		HashMap<Integer, Hex> cameFrom = new HashMap<Integer, Hex>();
		HashMap<Integer, Integer> cost = new HashMap<Integer, Integer>();


		open.put(start, 0);
		cameFrom.put(start.hashCode(), null);
		cost.put(start.hashCode(), 0);

		Boolean pathFound = false;
		Hex hex = new Hex(0,0,0);

		while (open.hasElements()) {
			Hex current = open.pop();

			closed.put(current, cost.get(current.hashCode()));

			if(current.isMatch(goal)) {
				hex = current;
				pathFound = true;
				break;
			}

			Hex[] neighbours = current.getNeighbours();

			for (int i = 0; i < 6; i++) {
				Hex next = getHex(neighbours[i].getX(), neighbours[i].getY(), neighbours[i].getZ());


				if(next == null)
					continue;

				if(closed.contains(next) != -1) {
					continue;
				}


				int nextCost = cost.get(current.hashCode()) + next.getCost();


				if(open.contains(next) != -1) {
					if(nextCost >= cost.get(next.hashCode()))
						continue;
				}


				cost.put(next.hashCode(), cost.get(current.hashCode()) + next.getCost() + next.distance(goal));
				cameFrom.put(next.hashCode(), current);
				open.put(next, nextCost);

			}

		}



		ArrayList<Hex> solution = new ArrayList<Hex>();

		if (pathFound) {

			while (!hex.isMatch(start)) {
				solution.add(hex);
				hex = cameFrom.get(hex.hashCode());
			}


			Collections.reverse(solution);
		}

		return solution;

	}


	public int moveCost(Hex start, Hex goal) {
		ArrayList<Hex> path = pathfinding(start, goal);

		int cost = 0;

		for(int i=0; i<path.size(); i++) {

			cost += path.get(i).getCost();
		}

		return cost;
	}


}



class SortedHexList {
	/**
	 * Agit comme une priority queue sur les Hex.
	 * Utilisé dans l'implémentation de l'algo A*.
	 */
	private ArrayList<Hex> hexList = new ArrayList<Hex>();
	private ArrayList<Integer> costList = new ArrayList<Integer>();

	/**
	* Indique si la liste contient encore des éléments
	* @return Boolean
	*/
	Boolean hasElements() {
		return hexList.size() > 0;
	}

	/**
	* Ajoute a à la liste avec un coût de cost
	* @param a hexagone
	* @param cost int
	*/
	void put(Hex a, int cost) {
		hexList.add(a);
		costList.add(cost);
	}

	/**
	* Retourne l'élément à la priorité la plus élevée / coût le plus faible et
	* le supprime de la liste.
	* @return Hex
	*/
	Hex pop() {
		if (hexList.size() == 0) {
			return null;
		}

		int minIndex = 0;
		int minCost = costList.get(0);
		for (int i = 1; i < costList.size(); i++) {
			if (costList.get(i) < minCost) {
				minIndex = i;
				minCost = costList.get(i);
			}
		}

		Hex returnValue = hexList.remove(minIndex);
		costList.remove(minIndex);
		return returnValue;
	}

	/**
	* Indique si l'hexagone est contenu dans la queue, si oui, renvoie son cout.
	* @param a Hex
	* @return int
	*/
	int contains(Hex a) {
		for (int i = 0; i < hexList.size(); i++) {
			if (a.isMatch(hexList.get(i))) {
				return costList.get(i);
			}
		}

		return -1;
	}

	int size() {
		return hexList.size();
	}


	void remove(Hex a) {
		for (int i = 0; i < hexList.size(); i++) {
			if (a.isMatch(hexList.get(i))) {
				hexList.remove(i);
				costList.remove(i);
			}
		}
	}

}
