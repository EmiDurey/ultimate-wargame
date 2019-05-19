package model;

public class Archer extends Unite {

	/* attributs de base */
	private final int puissance = 6; //gros c'est la puissance
	private final int defense = 3; //gros c'est la defense
	private final int deplacement = 5; //fais le mouv, fais le mouv
	private final int vie = 35; //c'est la vie, lalalalala
	private final int vue = 7; //perception humaine des rayonnements lumineux
	private final int porteeTir = 5; //portée des tirs de l'archer
	private int portee;

	public Archer(Hex hex) {
		super(hex);
		this.pointsAttaque = this.puissance;
		this.pointsDefense = this.defense;
		this.pointsDeplacement = this.deplacement;
		this.pointsDeVie = this.vie;
		this.vision = this.vue;
		this.portee = this.porteeTir;
	}

	public Archer() {
	}

	public void heal() {
		if (this.pointsDeplacement == deplacement) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	public void initialize() {
		this.pointsDeplacement = deplacement;
	}

	public void combat(HexMap map, Joueur joueur, Unite unite){
		portee = (int) pointsDeVie / 7;
		if (map.pathfinding(this.hex, unite.hex).size() < portee) {
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque * Math.random()));
		}
	}

	public int getPV() {
		return vie;
	}
}
