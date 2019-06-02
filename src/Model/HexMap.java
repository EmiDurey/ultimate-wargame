package model;

import java.util.HashMap;
import static java.lang.Math.*;

import java.io.Serializable;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Stack;


public class HexMap implements Serializable {
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
        for (int x = -mapSize; x < mapSize-1; x++) {
            int y1 = max(-mapSize+1, -x - mapSize);
            int y2 = min(mapSize, -x + mapSize-1);

            for (int y = y1; y < y2; y++) {
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
		for (int x = 0; x < width; x++) {
		    int x_offset = (int) Math.floor(x/2); // or r>>1
		    for (int y = -x_offset; y < height - x_offset; y++) {
				Plaine newHex = new Plaine(y, x);
				map.put(newHex.hashCode(), newHex);
		    }
		}
    }


	/**
	* Définit le nouveau biome d'une case pendant la génération de map
	* @param Hex source
	* @param float biomeValue
	* @param int n
	*/
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


	/**
	* Permet de propager aléatoriement le biome à ses voisins
	* @param Hex source
	* @param float biomeValue
	* @param int n
	*/
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


	/**
	* Remplit aléatoirement la map avec différents biomes.
	*/
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
	* Initialise la map et les unités à partir du tableau des joueurs
	* de la partie.
	* @param a Hex
	* @return ArrayList
	*/
	public void initMap(ArrayList<Joueur> players) {
		ArrayList<ArrayList<Hex>> unitsPositions = new ArrayList<ArrayList<Hex>>();

		switch(players.size()) {
			case 2:
				setRectangleMap(8, 12);
				populate();
				for(int i=0; i<2; i++){
					unitsPositions.add(new ArrayList<Hex>());
				}

				addHex(new Forteresse( 3,0));
                addHex(new Forteresse( 4,0));
                addHex(new Forteresse( 5,0));
                addHex(new Forteresse( 6,0));
                addHex(new Forteresse( 7,0));
                addHex(new Forteresse( 8,0));
                addHex(new Forteresse( 9,0));
                unitsPositions.get(0).add(getHex(3,0));
                unitsPositions.get(0).add(getHex(4,0));
                unitsPositions.get(0).add(getHex(5,0));
                unitsPositions.get(0).add(getHex(6,0));
                unitsPositions.get(0).add(getHex(7,0));
                unitsPositions.get(0).add(getHex(8,0));
                unitsPositions.get(0).add(getHex(9,0));

                addHex(new Forteresse( 1,17));
                addHex(new Forteresse( 0,17));
                addHex(new Forteresse( -1,17));
                addHex(new Forteresse( -2,17));
                addHex(new Forteresse( -3,17));
                addHex(new Forteresse( -4,17));
                addHex(new Forteresse( -5,17));
                unitsPositions.get(1).add(getHex(1,17));
                unitsPositions.get(1).add(getHex(0,17));
                unitsPositions.get(1).add(getHex(-1,17));
                unitsPositions.get(1).add(getHex(-2,17));
                unitsPositions.get(1).add(getHex(-3,17));
                unitsPositions.get(1).add(getHex(-4,17));
                unitsPositions.get(1).add(getHex(-5,17));

				break;

			case 3:
				setTriangleMap(13);
                populate();
                for(int i=0; i<3; i++){
                    unitsPositions.add(new ArrayList<Hex>());
                }

                addHex(new Forteresse(1, 0));
                addHex(new Forteresse(0, 1));
                addHex(new Forteresse(1, 1));
                addHex(new Forteresse(3, 0));
                addHex(new Forteresse(2, 1));
                addHex(new Forteresse(1, 2));
                addHex(new Forteresse(0, 3));
                unitsPositions.get(0).add(getHex(1,0));
                unitsPositions.get(0).add(getHex(0,1));
                unitsPositions.get(0).add(getHex(1,1));
                unitsPositions.get(0).add(getHex(3,0));
                unitsPositions.get(0).add(getHex(2,1));
                unitsPositions.get(0).add(getHex(1,2));
                unitsPositions.get(0).add(getHex(0,3));

                addHex(new Forteresse(0, 12));
                addHex(new Forteresse(1, 12));
                addHex(new Forteresse(1, 11));
                addHex(new Forteresse(0, 10));
                addHex(new Forteresse(1, 10));
                addHex(new Forteresse(2, 10));
                addHex(new Forteresse(3, 10));
                unitsPositions.get(1).add(getHex(0,12));
                unitsPositions.get(1).add(getHex(1,12));
                unitsPositions.get(1).add(getHex(1,11));
                unitsPositions.get(1).add(getHex(0,10));
                unitsPositions.get(1).add(getHex(1,10));
                unitsPositions.get(1).add(getHex(2,10));
                unitsPositions.get(1).add(getHex(3,10));

                addHex(new Forteresse(12, 1));
                addHex(new Forteresse(12, 0));
                addHex(new Forteresse(11, 1));
                addHex(new Forteresse(10, 3));
                addHex(new Forteresse(10, 2));
                addHex(new Forteresse(10, 1));
                addHex(new Forteresse(10, 0));
                unitsPositions.get(2).add(getHex(12,1));
                unitsPositions.get(2).add(getHex(12,0));
                unitsPositions.get(2).add(getHex(11,1));
                unitsPositions.get(2).add(getHex(10,3));
                unitsPositions.get(2).add(getHex(10,2));
                unitsPositions.get(2).add(getHex(10,1));
                unitsPositions.get(2).add(getHex(10,0));

                break;

			case 4:
				setRectangleMap(12, 18);
				populate();
				for(int i=0; i<4; i++){
					unitsPositions.add(new ArrayList<Hex>());
				}

				addHex(new Forteresse( -1,2));
                addHex(new Forteresse( 0,1));
                addHex(new Forteresse( 1,0));
                addHex(new Forteresse( 3,0));
                addHex(new Forteresse( 2,1));
                addHex(new Forteresse( 0,3));
                addHex(new Forteresse( -1,4));
                unitsPositions.get(2).add(getHex(-1,2));
                unitsPositions.get(2).add(getHex(0,1));
                unitsPositions.get(2).add(getHex(1,0));
                unitsPositions.get(2).add(getHex(3,0));
                unitsPositions.get(2).add(getHex(2,1));
                unitsPositions.get(2).add(getHex(0,3));
                unitsPositions.get(2).add(getHex(-1,4));

                addHex(new Forteresse( -7,15));
                addHex(new Forteresse( -7,16));
                addHex(new Forteresse( -7,17));
                addHex(new Forteresse( -5,17));
                addHex(new Forteresse( -5,16));
                addHex(new Forteresse( -5,14));
                addHex(new Forteresse( -5,13));
                unitsPositions.get(3).add(getHex(-7,15));
                unitsPositions.get(3).add(getHex(-7,16));
                unitsPositions.get(3).add(getHex(-7,17));
                unitsPositions.get(3).add(getHex(-5,17));
                unitsPositions.get(3).add(getHex(-5,16));
                unitsPositions.get(3).add(getHex(-5,14));
                unitsPositions.get(3).add(getHex(-5,13));

                addHex(new Forteresse( 10,0));
                addHex(new Forteresse( 10,1));
                addHex(new Forteresse( 10,2));
                addHex(new Forteresse( 8,4));
                addHex(new Forteresse( 8,3));
                addHex(new Forteresse( 8,1));
                addHex(new Forteresse( 8,0));
                unitsPositions.get(0).add(getHex(10,0));
                unitsPositions.get(0).add(getHex(10,1));
                unitsPositions.get(0).add(getHex(10,2));
                unitsPositions.get(0).add(getHex(8,4));
                unitsPositions.get(0).add(getHex(8,3));
                unitsPositions.get(0).add(getHex(8,1));
                unitsPositions.get(0).add(getHex(8,0));

                addHex(new Forteresse( 4,15));
                addHex(new Forteresse( 3,16));
                addHex(new Forteresse( 2,17));
                addHex(new Forteresse( 0,17));
                addHex(new Forteresse( 1,16));
                addHex(new Forteresse( 3,14));
                addHex(new Forteresse( 4,13));
                unitsPositions.get(1).add(getHex(4,15));
                unitsPositions.get(1).add(getHex(3,16));
                unitsPositions.get(1).add(getHex(2,17));
                unitsPositions.get(1).add(getHex(0,17));
                unitsPositions.get(1).add(getHex(1,16));
                unitsPositions.get(1).add(getHex(3,14));
                unitsPositions.get(1).add(getHex(4,13));

				break;


			default:
				setHexagonMap(13);
				populate();
				for(int i=0; i<6; i++){
					unitsPositions.add(new ArrayList<Hex>());
				}

				addHex(new Forteresse( -4,14));
                addHex(new Forteresse( 0,11));
                addHex(new Forteresse( -1,12));
                addHex(new Forteresse( -2,12));
                addHex(new Forteresse( -3,13));
                addHex(new Forteresse( 1,11));
                addHex(new Forteresse( 2,11));
                unitsPositions.get(0).add(getHex(-4,14));
                unitsPositions.get(0).add(getHex(0,11));
                unitsPositions.get(0).add(getHex(-1,12));
                unitsPositions.get(0).add(getHex(-2,12));
                unitsPositions.get(0).add(getHex(-3,13));
                unitsPositions.get(0).add(getHex(1,11));
                unitsPositions.get(0).add(getHex(2,11));

                addHex(new Forteresse( 11,-11));
                addHex(new Forteresse( 10,-13));
                addHex(new Forteresse( 11,-12));
                addHex(new Forteresse( 12,-11));
                addHex(new Forteresse( 10,-12));
                addHex(new Forteresse( 10,-14));
                addHex(new Forteresse( 13,-11));
				unitsPositions.get(1).add(getHex(11,-11));
				unitsPositions.get(1).add(getHex(10,-13));
				unitsPositions.get(1).add(getHex(11,-12));
				unitsPositions.get(1).add(getHex(12,-11));
				unitsPositions.get(1).add(getHex(10,-12));
				unitsPositions.get(1).add(getHex(10,-14));
				unitsPositions.get(1).add(getHex(13,-11));

				addHex(new Forteresse( -13,0));
                addHex(new Forteresse( -12,-3));
                addHex(new Forteresse( -12,-2));
                addHex(new Forteresse( -12,-1));
                addHex(new Forteresse( -13,1));
                addHex(new Forteresse( -14,2));
                addHex(new Forteresse( -15,3));
				unitsPositions.get(2).add(getHex(-13,-0));
				unitsPositions.get(2).add(getHex(-12,-3));
				unitsPositions.get(2).add(getHex(-12,-2));
				unitsPositions.get(2).add(getHex(-12,-1));
				unitsPositions.get(2).add(getHex(-13,1));
				unitsPositions.get(2).add(getHex(-14,2));
				unitsPositions.get(2).add(getHex(-15,3));

				addHex(new Forteresse( -4,-11));
                addHex(new Forteresse( 0,-12));
                addHex(new Forteresse( -1,-12));
                addHex(new Forteresse( -2,-11));
                addHex(new Forteresse( -3,-11));
                addHex(new Forteresse( 1,-13));
                addHex(new Forteresse( 2,-14));
                unitsPositions.get(3).add(getHex(-4,-11));
                unitsPositions.get(3).add(getHex(0,-12));
                unitsPositions.get(3).add(getHex(-1,-12));
                unitsPositions.get(3).add(getHex(-2,-11));
                unitsPositions.get(3).add(getHex(-3,-11));
                unitsPositions.get(3).add(getHex(1,-13));
                unitsPositions.get(3).add(getHex(2,-14));

                addHex(new Forteresse( -13,12));
                addHex(new Forteresse( -15,11));
                addHex(new Forteresse( -14,11));
                addHex(new Forteresse( -13,11));
                addHex(new Forteresse( -12,12));
                addHex(new Forteresse( -12,13));
                addHex(new Forteresse( -12,14));
				unitsPositions.get(4).add(getHex(-13,12));
				unitsPositions.get(4).add(getHex(-15,11));
				unitsPositions.get(4).add(getHex(-14,11));
				unitsPositions.get(4).add(getHex(-13,11));
				unitsPositions.get(4).add(getHex(-12,12));
				unitsPositions.get(4).add(getHex(-12,13));
				unitsPositions.get(4).add(getHex(-12,14));

				addHex(new Forteresse( 11,0));
                addHex(new Forteresse( 11,-1));
                addHex(new Forteresse( 12,-2));
                addHex(new Forteresse( 13,-3));
                addHex(new Forteresse( 10,1));
                addHex(new Forteresse( 10,2));
                addHex(new Forteresse( 10,3));
				unitsPositions.get(5).add(getHex(11,0));
				unitsPositions.get(5).add(getHex(11,-1));
				unitsPositions.get(5).add(getHex(12,-2));
				unitsPositions.get(5).add(getHex(13,-3));
				unitsPositions.get(5).add(getHex(10,1));
				unitsPositions.get(5).add(getHex(10,2));
				unitsPositions.get(5).add(getHex(10,3));
		}

		for(int i=0; i<players.size(); i++) {
            players.get(i).addUnit(new Dragon(unitsPositions.get(i).get(0), players.get(i)));
            players.get(i).addUnit(new Cavalerie(unitsPositions.get(i).get(1), players.get(i)));
            players.get(i).addUnit(new Pretre(unitsPositions.get(i).get(2), players.get(i)));
            players.get(i).addUnit(new Infanterie(unitsPositions.get(i).get(3), players.get(i)));
            players.get(i).addUnit(new Archer(unitsPositions.get(i).get(4), players.get(i)));
            players.get(i).addUnit(new Mage(unitsPositions.get(i).get(5), players.get(i)));
            players.get(i).addUnit(new InfanterieLourde(unitsPositions.get(i).get(6), players.get(i)));
            for(Unite unit : players.get(i).getUnite()) {
            	reveal(players.get(i), unit.getHex(), unit.getVision());
            }
        }

		/*Hex hexInit = players.get(0).getUnite().get(0).getHex();
		for(int i=1; i<players.size(); i++) {
			Hex hexFin = players.get(i).getUnite().get(0).getHex();
			if(pathfinding(hexInit,hexFin).size() == 0) {
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				initMap(players);
			}
		}	*/
	}

	/**
	* Retourne les voisins de l'hexagone en prenant compte
	* de la gémoétrie de la carte
	* @param a Hex
	* @return ArrayList
	*/
	public ArrayList<Hex> getNeighbours(Hex a) {
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

		for(int i = getMinX(); i <= getMaxX(); i++) {
			for(int j = getMinY(); j <= getMaxY(); j++) {
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



	/**
	* Retourne tout les hexagones à une dintance viewDist de source
	* @param source hexagone
	* @param viewDist int
	* @return ArrayList<Hex>
	*/
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


	/**
	* Révèle pour le joueur les cases aux alentours de source
	* @param Joueur player
	* @param Hex source
	*/
	public void reveal(Joueur player, Hex source , int dist) {
		ArrayList<Hex> tiles = viewHighlight(source, dist);

		for(int i=0; i<tiles.size(); i++) {
			tiles.get(i).discovered.put(player.id, true);
		}
	}


	/**
	* Retourne tout les hexagones à une dintance viewDist de source
	* (prend en compte les coûts de déplacemen)
	* @param source hexagone
	* @param viewDist int
	* @return ArrayList<Hex>
	*/
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


	/**
	* Retourne l'allié le plus proche de la source
	* @param source hexagone
	* @param owner Joueur
	* @return Unite
	*/
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


	/**
	* Retourne l'ennemi le plus proche de la source
	* @param source hexagone
	* @param owner Joueur
	* @return Unite
	*/
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


				if(closed.contains(next) != -1 || next.getCost() <= 0 || !next.isEmpty())
					continue;


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
		//TODO Optimization
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
