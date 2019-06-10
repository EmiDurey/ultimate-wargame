package model;

/**
 *  Classe Forteresse.
 *  @see Hex
 */
public class Forteresse extends Hex {

	/**
	 * Construit un objet de type Forteresse.
	 * @param newX int
	 * @param newY int
	 */
	Forteresse(int newX, int newY) {
		super(newX, newY);
		this.cost = 15;
		this.defense = 60;
		rarity = (float) 0.1;
	}

	/**
	 * Construit un objet de type Forteresse.
	 * @param newX int
	 * @param newY int
	 * @param newZ int
	 */
	Forteresse(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.1;
	}

	/**
	 * Retour la rareté du biom.
	 * @return rarity float
	 */
	public float getRarity() {
		return rarity;
	}
}
