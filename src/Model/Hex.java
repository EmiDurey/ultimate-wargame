package model;

import java.lang.Math;

/**
 * Repr�sente un hexagone sur la map.
 * On utilise un syst�me de coordonn�es cubiques pour le stockage.
 * On stocke donc notamment les coordonn�es de l'hexagone sur la map.
 */
public class Hex {

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
     * Constructeur utilisant les coordonn�es cubiques.
	 * @param newX coordonn�e x
	 * @param newY coordonn�e y
	 * @param newZ coordonn�e z
	 * @throws IllegalArgumentException Si la somme des coordonn�es
	 * est diff�rente de 0.
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
	 * Teste l'�galit� entre 2 Hex.
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
	 * Retourne la distance � 0 (l'origine) de l'hexagone.
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
