package model;

import java.lang.Math;

/**
 * Représente un hexagone sur la map.
 * On utilise un système de coordonnées cubiques pour le stockage.
 * On stocke donc notamment les coordonnées de l'hexagone sur la map.
 */
public class Hex {

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
     * Constructeur utilisant les coordonnées cubiques.
	 * @param newX coordonnée x
	 * @param newY coordonnée y
	 * @param newZ coordonnée z
	 * @throws IllegalArgumentException Si la somme des coordonnées
	 * est différente de 0.
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
	 * Teste l'égalité entre 2 Hex.
	 * @param b hexagone
	 * @return Boolean
	 */
	public Boolean equals(Hex b) {
		if (x == b.x && y == b.y && z == b.z) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retourne la distance à 0 (l'origine) de l'hexagone.
	 * @return int
	 */
	public int zeroDistance() {
		return Math.abs((x + y + z) / 2);
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
	 * Indique si les deux hexagones sont adjacents
	 * (cas particulier de "distance").
	 * @param b hexagone
	 * @return Boolean
	 */
	public Boolean isNeighbour(Hex b) {
		if (distance(b) == 1) {
			return true;
		} else {
			return false;
		}
	}

	//TODO getNeighbour() en fonction de la direction
	//		Stockage de map, ...
};
