package model;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Repr�sente un hexagone sur la map.
 * On utilise un syst�me de coordonn�es cubiques pour le stockage.
 * On stocke donc notamment les coordonn�es de l'hexagone sur la map.
 * @see Unite
 */
public class Hex implements Serializable {

	/**
	 * Coordonn�e x.
	 */
	private int x;

	/**
	 * Coordonn�e y.
	 */
	private int y;

	/**
	 * Coordonn�e z.
	 */
	private int z;

	/**
	 * Indique si la case est accessible.
	 */
	private Boolean empty = true;

	/**
	 * Poucentage de d�fense de la case.
	 */
	protected int defense = 1;

	/**
	 * Co�t de d�placement vers cette case.
	 */
	protected int cost = 1;

	/**
	 * R�f�rence de l'unit� se trouvant sur la case.
	 */
	private Unite unit = null;

	/**
	 * Utilis� pour la propagation du biome dans la g�n�ration de map.
	 */
	protected float rarity;

	/**
	 * Permet de renseigner l'�tat du brouillard de guerre sur la map.
	 * L'indice repr�sente l'ID du joueur concern�.
	 * La valeur est un bool�en indiquant si la case a �t� d�couverte.
	 */
	public HashMap<Integer, Boolean> discovered = new HashMap<Integer, Boolean>();

	/**
     * Constructeur utilisant les coordonn�es cubiques.
	 * @param newX coordonn�e x
	 * @param newY coordonn�e y
	 * @param newZ coordonn�e z
	 * @throws IllegalArgumentException Si la somme des coordonn�es est diff�rente de 0.
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
	* Constructeur utilisant les coordonn�es axiales.
	* Les coordonn�es sont converties en cubique pour le stockage.
	* @param newX coordonn�e x
	* @param newY coordonn�e y
	*/
	Hex(int newX, int newY) {
		x = newX;
		y = newY;
		z = -newX - newY;
	}

	/**
	* Constructeur utilisant les coordonn�es axiales.
	* Les coordonn�es sont converties en cubique pour le stockage.
	* Initialise aussi le co�t de d�placement. Valeur < 1 pour une case inaccessible.
	* @param newX coordonn�e x
	* @param newY coordonn�e y
	* @param newCost Co�t de d�placement
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
     * Constructeur utilisant les coordonn�es cubiques.
	 * Initialise aussi le coût de d�placement. Valeur < 1 pour une case inaccessible.
	 * @param newX coordonn�e x
	 * @param newY coordonn�e y
	 * @param newZ coordonn�e z
	 * @param newCost Co�t de d�placement
	 * @throws IllegalArgumentException Si la somme des coordonn�es
	 * est diff�rente de 0.
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
	* Retourne la coordonn�e en x.
	* @return int
	*/
	public int getX() {
		return x;
	}

	/**
	* Retourne la coordonn�e en y.
	* @return int
	*/
	public int getY() {
		return y;
	}

	/**
	* Retourne la coordonn�e en z.
	* @return int
	*/
	public int getZ() {
		return z;
	}

	/**
	* Retourne le co�t de d�placement de la case.
	* @return int
	*/
	public int getCost() {
		return cost;
	}

	/**
	* Modifie le co�t de d�placement de la case.
	* @param newCost int
	*/
	public void setCost(int newCost) {
		cost = newCost;
	}

	/**
	* Retourne le poucentage de d�fense de la case.
	* @return int
	*/
	public int getDefense() {
		return defense;
	}

	/**
	* Indique si les deux hexagones sont g�om�triquement identiques.
	* Utilis� dans l'impl�mentation de A*.
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
	* Calcule la diff�rence de 2 hexagones.
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
	 * Retourne la distance � 0 (l'origine) de l'hexagone.
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
	 * Utilis� dans l'impl�mentation de A* (sert d'op�rateur de comparaison).
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
	 * (plus simplement, permet d'acc�der au tableau directions)
	 * On veut que direction soit compris entre 0 et 5.
	 * @param direction int
	 * @return direction
	 */
	public Hex getDirection(int direction) {

		/**
		* Tableau indiquant les voisins de l'hexagone (0,0,0) et permettant
	 	* de d�terminer les diff�rents voisins d'un hexagone quelconque.
	 	* On part du c�t� en haut � droite et on tourne dans le sens horaire.
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
	 * Retourne les voisins de l'hexagone en suivant la direction donn�e.
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
	* Retourne l'unit� actuellement sur la case.
	* @return int
	* @see Unite
	*/
	public Unite getUnit() {
		return unit;
	}

	/**
	* Modifie l'unit� se trouvant actuellement sur la case.
	* @param newUnit Unite
	* @see Unite
	*/
	public void setUnit(Unite newUnit) {
		unit = newUnit;
	}

	/**
	* Premi�re fonction utilis�e par le hashCode.
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
	* Deuxi�me fonction utilis�e par le hashCode.
	* Bijection de NxN vers N (fonction de couplage de Cantor).
	* @param a int
	* @param b int
	* @return int
	*/
	private int fct2(int a, int b) {
		return b + ((a + b) * (a + b + 1)) / 2;
	}

	/**
	* Hashe l'objet en ne prenant en compte que les coordonn�es.
	* On �vite toutes les collisions.
	* (si d'autres champs sont ajout�s).
	* @return int
	*/
	public int hashCode() {
		return fct2(fct2(fct1(x), fct1(y)), fct1(z));
	}

	/**
	 * Retour la raret� du biom.
	 * @return rarity float
	 */
	public float getRarity() {
		return rarity;
	}

};
