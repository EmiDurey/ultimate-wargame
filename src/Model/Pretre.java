package model;

import java.util.ArrayList;

/**
 *  Class Pretre.
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
	 * Heal de l'unité si elle n'a pas bougé.
	 * Soigne toutes les unités alliés autour de lui.
	 * @param map HexMap
	 * @param joueur Joueur
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
	 * Déplace une unité en fonction des possibilités de déplacement de cette dernière.
	 * @param joueur Joueur actuelle
	 * @param map HexMap
	 */
	@Override
	public void joueurIA(Joueur joueur, HexMap map) {
		ArrayList<Unite> injuredAllies = new ArrayList<Unite>();
		injuredAllies.add(joueur.getUnite().get(0));

		//Tri des unités alliées par HP décroissants
		for(int i=1; i<joueur.getUnite().size(); i++) {
			for(int j=0; j<injuredAllies.size(); j++) {
				if(injuredAllies.get(j).getPointsDeVie() > joueur.getUnite().get(i).getPointsDeVie()){
					injuredAllies.add(j, joueur.getUnite().get(i));
					break;
				}
			}
			injuredAllies.add(joueur.getUnite().get(i));
		}

		//On essaie de soigner l'allié accessible le plus mal en point
		for(int i=0; i<injuredAllies.size(); i++) {
			if( map.moveCost(hex, injuredAllies.get(i).hex) <= getPointsDeplacement() ) {
				ArrayList<Hex> path = map.pathfinding (getHex(), injuredAllies.get(i).getHex());
				if(path.size() > 1) {
					seDeplace(map, path.get(path.size()-2));
				}
				else if(path.size() > 0) {
					seDeplace(map, path.get(path.size()-1));
				}
			}
		}

		//Aucun allié accessible: on fuit l'ennemi le plus proche
		Hex enemyPosition = map.getClosestEnemy(hex, joueur).getHex();
		Hex goal = flee (enemyPosition, map);
		seDeplace(map, goal);

	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
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
	 * Réinitialise les points de déplacement de l'unité.
	 */
	@Override
	public void initialize() {
		this.pointsDeplacement = this.pointsDeplacementInit;
	}
}
