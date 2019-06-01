package model;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {

		HexMap map = new HexMap();
		ArrayList<Joueur> players = new ArrayList<Joueur>();
		players.add(new Joueur("Brandon", 1));
		players.add(new Joueur("Brandon", 2));
		players.add(new Joueur("Brandon", 3));
		players.add(new Joueur("Brandon", 4));
		players.add(new Joueur("Brandon", 5));
		players.add(new Joueur("Brandon", 6));

		map.initMap(players);

		map.ASCIIDisplay();
	}
}
