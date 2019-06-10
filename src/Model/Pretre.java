package model;

import java.util.ArrayList;

/**
 *  Class Pretre.
 *  @see Hex
 *  @see HexMap
 *  @see Joueur
 *  @see Unite
 */
public class Pretre extends Unite {

	/**
	 *  Points de soin d'un pretre.
	 */
	private int pointsSoin;

	/**
	 *  Constructeur d'un pretre.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 *  @see Hex
	 *  @see Joueur
	 */
	public Pretre(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 13;
		this.pointsDefenseInit = 3;
		this.pointsDefense = this.pointsDefenseInit;
		this.pointsDeplacementInit = 100;
		this.pointsDeplacement = this.pointsDeplacementInit;
		this.pointsDeVieMax = 60;
		this.pointsDeVie = this.pointsDeVieMax;
		this.vision = 4;
		this.pointsSoin = 10;
	}

	/**
	 * Heal de l'unit� si elle n'a pas boug�.
	 * Soigne toutes les unit�s alli�es autour de lui.
	 * @param map HexMap
	 * @param joueur Joueur
	 * @see Hex
	 * @see HexMap
	 * @see Joueur
	 * @see Unite
	 */
	public void soigne(HexMap map, Joueur joueur) {

		ArrayList<Hex> voisins = map.getNeighbours(hex);
		for (Hex voisin : voisins) {
			if (map.getHex(voisin.getX(), voisin.getY()).getUnit() != null) {
				Unite unite = map.getHex(voisin.getX(), voisin.getY()).getUnit();
				if (joueur.getUnite().contains(unite)) {
					unite.pointsDeVie = (int) (unite.pointsDeVie + this.pointsSoin);
					if (unite.pointsDeVie > unite.pointsDeVieMax) {
						unite.pointsDeVie = unite.pointsDeVieMax;
					}
				}
			}
		}
	}

	/**
	 * IA.
	 * D�place une unit� en fonction des possibilit�s de d�placement de cette derni�re.
	 * @param joueur Joueur actuelle
	 * @param map HexMap
	 * @see Unite
	 * @see Joueur
	 * @see HexMap
	 */
	@Override
	public void joueurIA(Joueur joueur, HexMap map) {

		ArrayList<Unite> injuredAllies = new ArrayList<Unite>();
		injuredAllies.add(joueur.getUnite().get(0));

		//Tri des unit�s alli�es par HP d�croissants
		for (int i = 1; i < joueur.getUnite().size(); i++) {
			for (int j = 0; j < injuredAllies.size(); j++) {
				if (injuredAllies.get(j).getPointsDeVie() > joueur.getUnite().get(i).getPointsDeVie()){
					injuredAllies.add(j, joueur.getUnite().get(i));
					break;
				}
			}
			injuredAllies.add(joueur.getUnite().get(i));
		}

		seDeplace(map, injuredAllies.get(0).getHex());
	}

	/**
	 * Heal de l'unit� si elle n'a pas boug�.
	 */
	@Override
	public void heal() {
		if (this.pointsDeplacement == this.pointsDeplacementInit) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.10);
			if (this.pointsDeVie > this.pointsDeVieMax) {
				this.pointsDeVie = this.pointsDeVieMax;
			}
		}
	}

	/**
	 * R�initialise les points de d�placement de l'unit�.
	 */
	@Override
	public void initialize() {
		this.pointsDeplacement = this.pointsDeplacementInit;
	}
}
