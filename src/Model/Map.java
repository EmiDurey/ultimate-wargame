package model;

import java.util.HashMap;



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

	//TODO Fonctions d'ajout de tiles et templates de formes de map simple

}
