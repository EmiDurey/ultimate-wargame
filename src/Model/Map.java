package model;

import java.util.HashMap;
import static java.lang.Math.*;


public class Map {
	/**
 	* HashMap contenant l'ensemble des Hex de la map et servant à son stockage.
	* Chaque Hex h aura pour clef Object.hashCode(h)
	* Cela permet un stockage efficace de la map, en permettant toutes
	* formes pour celle-ci.
	*/
	private HashMap<Integer, Hex> map = new HashMap<>();

	
	/**
	* Permet d'accéder à un Hex de la hashmap à partir de ses coordonnées cubiques
	* @param x y z
	* @return Hex
	*/
	public Hex getHex(int x, int y, int z) {
		return map.get(new Hex(x, y, z).hashCode());
	}


	/**
	* Permet d'accéder à un Hex de la hashmap à partir de ses coordonnées axiales
	* @param x y
	* @return Hex
	*/	
	public Hex getHex(int x, int y) {
		return map.get(new Hex(x, y).hashCode());
	}


	/**
	* Permet d'ajouter un Hex à la hashmap, retourne le hash
	* @param a
	* @return Hex
	*/	
	public Hex addHex(Hex a) {
		return map.put(a.hashCode(), a);
	}

	public void deleteMap() {
		map = new HashMap<Integer, Hex>();
	}

	
	//TODO Testing (console?, graphique?)


	/**
	* Génère une map en forme de triangle équilatéral de côté mapSize
	* @param mapSize
	*/
	public void setTriangleMap(int mapSize) {
		for (int x = 0; x <= mapSize; x++) {
    		for (int y = 0; y <= mapSize - x; y++) {
				Hex newHex = new Hex(x, y, -x-y);
				map.put( newHex.hashCode(), newHex );
    		}
		}
	}


	/**
	* Génère une map en forme d'hexagone de côté mapSize
	* @param mapSize
	*/
	public void setHexagonMap(int mapSize) {
		for (int x = -mapSize; x <= mapSize; x++) {

			int y1 = max(-mapSize, -x - mapSize);
    		int y2 = min(mapSize, -x + mapSize);

			for (int y = y1; y <= y2; y++) {
				Hex newHex = new Hex(x, y, -x-y);
	        	map.put( newHex.hashCode(), newHex );
			}

		}
	}


	/**
	* Génère une map rectangulaire de taille width*height
	* @param height width
	*/
	public void setRectangleMap(int height, int width) {

		for (int y = 0; y < height; y++) {

			int yOffset = (int) floor(y/2);

    		for (int x = -yOffset; x < width - yOffset; x++) {
				Hex newHex = new Hex(x, y, -x-y);
	        	map.put( newHex.hashCode(), newHex );
    		}

		}

	}
}
	
