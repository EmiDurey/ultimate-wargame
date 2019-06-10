package model;

/**
 *  Classe Foret.
 *  @see Hex
 */
public class Foret extends Hex {

	/**
	 * Construit un objet de type Forêt.
	 * @param newX int
	 * @param newY int
	 */
	Foret(int newX, int newY) {
		super(newX, newY);
		this.cost = 33;
		this.defense = 35;
		rarity = (float) 0.24;
	}

	/**
	 * Construit un objet de type Forêt.
	 * @param newX int
	 * @param newY int
	 * @param newZ int
	 */
	Foret(int newX, int newY, int newZ) {
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
		System.out.print("F");
	}
}
