package model;

/**
 *  Classe Neige.
 *  @see Hex
 */
public class Neige extends Hex {

	/**
	 * Construit un objet de type Neige.
	 * @param newX int
	 * @param newY int
	 */
	Neige(int newX, int newY) {
		super(newX, newY);
		this.cost = 25;
		this.defense = 5;
		rarity = (float) 0.24;
	}

	/**
	 * Construit un objet de type Neige.
	 * @param newX int
	 * @param newY int
	 * @param newZ int
	 */
	Neige(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.24;
	}

	/**
	 * Retour la rareté du biom.
	 * @return rarity float
	 */
	public float getRarity() {
		return rarity;
	}

	/**
	 * Affiche le type de biom en console.
	 * (pour les tests console)
	 */
	public void print() {
		System.out.print("S");
	}
}
