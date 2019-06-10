package model;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Représente un hexagone sur la map.
 * On utilise un système de coordonnées cubiques pour le stockage.
 * On stocke donc notamment les coordonnées de l'hexagone sur la map.
 * @see Unite
 */
public class Hex implements Serializable {

	/**
	 * Coordonnée x.
	 */
	private int x;

	/**
	 * Coordonnée y.
	 */
	private int y;

	/**
	 * Coordonnée z.
	 */
	private int z;

	/**
	 * Indique si la case est accessible.
	 */
	private Boolean empty = true;

	/**
	 * Poucentage de défense de la case.
	 */
	protected int defense = 1;

	/**
	 * Coût de déplacement vers cette case.
	 */
	protected int cost = 1;

	/**
	 * Référence de l'unité se trouvant sur la case.
	 */
	private Unite unit = null;

	/**
	 * Utilisé pour la propagation du biome dans la génération de map.
	 */
	protected float rarity;

	/**
	 * Permet de renseigner l'état du brouillard de guerre sur la map.
	 * L'indice représente l'ID du joueur concerné.
	 * La valeur est un booléen indiquant si la case a été découverte.
	 */
	public HashMap<Integer, Boolean> discovered = new HashMap<Integer, Boolean>();

	/**
     * Constructeur utilisant les coordonnées cubiques.
	 * @param newX coordonnée x
	 * @param newY coordonnée y
	 * @param newZ coordonnée z
	 * @throws IllegalArgumentException Si la somme des coordonnées est différente de 0.
     */
	Hex(int newX, int newY, int newZ) {

		if (newX + newY + newZ != 0) {
			throw new IllegalArgumentException("x+y+z is not null");
		}

		x = newX;
		y = newY;
		z = newZ;
	}

	/**
	* Constructeur utilisant les coordonnées axiales.
	* Les coordonnées sont converties en cubique pour le stockage.
	* @param newX coordonnée x
	* @param newY coordonnée y
	*/
	Hex(int newX, int newY) {
		x = newX;
		y = newY;
		z = -newX - newY;
	}

	/**
	* Constructeur utilisant les coordonnées axiales.
	* Les coordonnées sont converties en cubique pour le stockage.
	* Initialise aussi le coût de déplacement. Valeur < 1 pour une case inaccessible.
	* @param newX coordonnée x
	* @param newY coordonnée y
	* @param newCost Coût de déplacement
	*/
	Hex(int newX, int newY, short newCost) {
		this(newX, newY);

		if (cost < 1) {
			empty = false;
			newCost = -1;
		} else {
			cost = (int) newCost;
		}
	}

	/**
     * Constructeur utilisant les coordonnées cubiques.
	 * Initialise aussi le coÃ»t de déplacement. Valeur < 1 pour une case inaccessible.
	 * @param newX coordonnée x
	 * @param newY coordonnée y
	 * @param newZ coordonnée z
	 * @param newCost Coût de déplacement
	 * @throws IllegalArgumentException Si la somme des coordonnées
	 * est différente de 0.
     */
	Hex(int newX, int newY, int newZ, short newCost) {
		this(newX, newY, newZ);

		if (cost < 1) {
			empty = false;
			newCost = -1;
		} else {
			cost = (int) newCost;
		}
	}

	/**
	* Retourne la coordonnée en x.
	* @return int
	*/
	public int getX() {
		return x;
	}

	/**
	* Retourne la coordonnée en y.
	* @return int
	*/
	public int getY() {
		return y;
	}

	/**
	* Retourne la coordonnée en z.
	* @return int
	*/
	public int getZ() {
		return z;
	}

	/**
	* Retourne le coût de déplacement de la case.
	* @return int
	*/
	public int getCost() {
		return cost;
	}

	/**
	* Modifie le coût de déplacement de la case.
	* @param newCost int
	*/
	public void setCost(int newCost) {
		cost = newCost;
	}

	/**
	* Retourne le poucentage de défense de la case.
	* @return int
	*/
	public int getDefense() {
		return defense;
	}

	/**
	* Indique si les deux hexagones sont géométriquement identiques.
	* Utilisé dans l'implémentation de A*.
	* @param a hexagone
	* @return Boolean
	*/
	public Boolean isMatch(Hex a) {
		return x == a.x && y == a.y && z == a.z;
	}

	/**
	* Calcule la somme de 2 hexagones.
	* @param b hexagone
	* @return Hex
	*/
	public Hex add(Hex b) {
		return new Hex(x + b.x, y + b.y, z + b.z);
	}

	/**
	* Calcule la différence de 2 hexagones.
	* @param b hexagone
	* @return Hex
	*/
	public Hex substract(Hex b) {
		return new Hex(x - b.x, y - b.y, z - b.z);
	}

 	/**
	* Calcule le produit d'un hexagone par un entier.
	* @param k entier
	* @return Hex
	*/
	public Hex multiply(int k) {
		return new Hex(k * x, k * y, k * z);
	}

	/**
	 * Retourne la distance à 0 (l'origine) de l'hexagone.
	 * @return int
	 */
	public int zeroDistance() {
		float sum = (float) Math.abs(x) + Math.abs(y) + Math.abs(z);

		return (int) Math.ceil(sum / 2);
	}

	/**
	 * Calcule la distance entre deux hexagones.
	 * @param b hexagone
	 * @return int
	 */
	public int distance(Hex b) {
		return substract(b).zeroDistance();
	}

	/**
	 * Indique si l'hexagone est plus proche de goal que b.
	 * Utilisé dans l'implémentation de A* (sert d'opérateur de comparaison).
	 * @param b hexagone
	 * @param goal hexagone
	 * @return Boolean
	 */
	public Boolean isBetter(Hex b, Hex goal) {
		return distance(goal) < b.distance(goal);
	}

	/**
	 * Indique si les deux hexagones sont adjacents
	 * (cas particulier de "distance").
	 * @param b hexagone
	 * @return Boolean
	 */
	public Boolean isNeighbour(Hex b) {
			return distance(b) == 1;
	}

	/**
	 * Retourne les voisins de l'hexagone (0,0,0)
	 * (plus simplement, permet d'accéder au tableau directions)
	 * On veut que direction soit compris entre 0 et 5.
	 * @param direction int
	 * @return direction
	 */
	public Hex getDirection(int direction) {

		/**
		* Tableau indiquant les voisins de l'hexagone (0,0,0) et permettant
	 	* de déterminer les différents voisins d'un hexagone quelconque.
	 	* On part du côté en haut à droite et on tourne dans le sens horaire.
	 	*/
		ArrayList<Hex> directions = new ArrayList<Hex>();
		directions.add(new Hex(0, 1, -1));
		directions.add(new Hex(1, 0, -1));
		directions.add(new Hex(1, -1, 0));
		directions.add(new Hex(0, -1, 1));
		directions.add(new Hex(-1, 0, 1));
		directions.add(new Hex(-1, 1, 0));

		return directions.get(direction);
	}

	/**
	 * Retourne les voisins de l'hexagone en suivant la direction donnée.
	 * @param direction int
	 * @return direction
	 */
	public Hex getNeighbour(int direction) {
		return add(getDirection(direction));
	}

	/**
	 * Retourne les voisins de l'hexagone.
	 * @return Hex []
	 */
	public Hex[] getNeighbours() {
		Hex[] neighbours = new Hex[6];

		for (int i = 0; i < 6; i++) {
			neighbours[i] = getNeighbour(i);
		}

		return neighbours;
	}

	/**
	 * Retourne le champ empty.
	 * @return Boolean
	 */
	public Boolean isEmpty() {
		return empty;
	}

	/**
	* Retourne l'unité actuellement sur la case.
	* @return int
	* @see Unite
	*/
	public Unite getUnit() {
		return unit;
	}

	/**
	* Modifie l'unité se trouvant actuellement sur la case.
	* @param newUnit Unite
	* @see Unite
	*/
	public void setUnit(Unite newUnit) {
		unit = newUnit;
	}

	/**
	* Première fonction utilisée par le hashCode.
	* Bijection de Z vers N.
	* @param a int
	* @return int
	*/
	private int fct1(int a) {
		if (a >= 0) {
			return 2 * a;
		} else {
			return -2 * a + 1;
		}
	}

	/**
	* Deuxième fonction utilisée par le hashCode.
	* Bijection de NxN vers N (fonction de couplage de Cantor).
	* @param a int
	* @param b int
	* @return int
	*/
	private int fct2(int a, int b) {
		return b + ((a + b) * (a + b + 1)) / 2;
	}

	/**
	* Hashe l'objet en ne prenant en compte que les coordonnées.
	* On évite toutes les collisions.
	* (si d'autres champs sont ajoutés).
	* @return int
	*/
	public int hashCode() {
		return fct2(fct2(fct1(x), fct1(y)), fct1(z));
	}

	/**
	 * Retour la rareté du biom.
	 * @return rarity float
	 */
	public float getRarity() {
		return rarity;
	}

};
