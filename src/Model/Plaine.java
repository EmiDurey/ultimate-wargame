package model;

/**
 *  Classe Plaine.
 *  @see Hex
 */
public class Plaine extends Hex {

	/**
	 * Construit un objet de type Plaine.
	 * @param newX int
	 * @param newY int
	 */
	Plaine(int newX, int newY) {
		super(newX, newY);
		this.cost = 15;
		this.defense = 10;
	}

	/**
	 * Construit un objet de type Plaine.
	 * @param newX int
	 * @param newY int
	 * @param newZ int
	 */
	Plaine(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}
}
