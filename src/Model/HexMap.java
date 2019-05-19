package model;

import java.util.HashMap;
import static java.lang.Math.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;


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
				Hex newHex = new Hex(x, y, -x - y);
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
				Hex newHex = new Hex(x, y, -x - y);
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
				Hex newHex = new Hex(x, y, -x - y);
	        	map.put(newHex.hashCode(), newHex);
    		}
		}
	}


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
	* Retourne le chemin de l'hexagone start vers l'hexagone goal.
	* Si il n'existe pas de chemin ou que l'un des deux hexagones
	* n'est pas dans la HashMap, retourne un tableau vide.
	* On utilise une implémentation de l'algorithme A*.
	* @param start hexagone
	* @param goal hexagone
	* @return ArrayList<Hex>
	*/
	public ArrayList<Hex> pathfinding(Hex start, Hex goal) {
		SortedHexList frontier = new SortedHexList();
		HashMap<Hex, Hex> cameFrom = new HashMap<Hex, Hex>();
		HashMap<Hex, Integer> cost = new HashMap<Hex, Integer>();

		frontier.put(start, 0);
		cameFrom.put(start, null);
		cost.put(start, 0);

		Boolean pathFound = false;

		while (frontier.hasElements()) {
			Hex current = frontier.pop();

			Hex[] neighbours = current.getNeighbours();
			for (int i = 0; i < 6; i++) {
				Hex next = getHex(neighbours[i].getX(), neighbours[i].getY(), neighbours[i].getZ());

				//If hex is in map and accessible
				if (next != null && next.getCost() > 0) {

					if(next.isMatch(goal)) {
						cameFrom.put(goal, current);
						pathFound = true;
						break;
					}

					int newCost = cost.get(current) + next.getCost();

					if (!cost.containsKey(next) || newCost < cost.get(next)) {
						cost.put(next, newCost);
						int priority = newCost + next.distance(goal);
						frontier.put(next, priority);
						cameFrom.put(next, current);
					}
				}
			}
		}

		ArrayList<Hex> solution = new ArrayList<Hex>();

		if (pathFound) {
			Hex hex = cameFrom.get(goal);
			System.out.println(hex);

			while (hex != null) {
				solution.add(hex);
				hex = cameFrom.get(hex);
			}

			Collections.reverse(solution);
		}

		return solution;
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
	* Indique si l'hexagone est contenu dans la queue.
	* @param a Hex
	* @return Boolean
	*/
	Boolean contains(Hex a) {
		for (int i = 0; i < hexList.size(); i++) {
			if (a.isMatch(hexList.get(i))) {
				return true;
			}
		}

		return false;
	}
}
