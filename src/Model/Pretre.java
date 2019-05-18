package model;

public class Prêtre extends Unite{

	/*attributs de base */
	private final int POWER = 2; //gros c'est la puissance
	private final int DEFENSE = 2; //gros c'est la defense
	private final int MOVE = 6; //fais le mouv, fais le mouv
	private final int PV = 60; //c'est la vie, lalalalala
	private final int VISION = 4;//perception humaine des rayonnements lumineux
	private int POWERHEALINGGGGG = 15; //Vivre c'est bien
	
	public Prêtre(Hex hex) {
		super(hex);
		this.pointsAttaque = this.POWER;
		this.pointsDefense = this.DEFENSE;
		this.pointsDeplacement = this.MOVE;
		this.pointsDeVie = this.PV;
		this.vision = this.VISION;
	}
	
	public void soigne(Joueur joueur) {
		Hex[] voisins = new Hex[6];
		voisins = this.hex.getNeighbours();
		for(int i = 0;i<6;i++) {
			if(!voisins[i].isEmpty()) {
				if(joueur.getUnite().contains(voisins[i].unit)) {
					voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie + this.POWERHEALINGGGGG * Math.random());
					if(voisins[i].unit.pointsDeVie>voisins[i].unit.getPV) {
						voisins[i].unit.pointsDeVie = voisins[i].unit.getPV;
					}
				}
			}
		}
	}
	
	public void heal() {
		if(this.pointsDeplacement==MOVE) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}
	
	public void initialize() {
		this.pointsDeplacement = MOVE;
	}
	
	public int getPV() {
		return PV;
	}
	
}
