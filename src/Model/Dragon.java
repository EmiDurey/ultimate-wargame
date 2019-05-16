package model;

public class Dragon extends Unite{

	/* attributs de base */
	private final int POWER = 10; //gros c'est la puissance
	private final int DEFENSE = 10; //gros c'est la defense
	private final int MOVE = 7; //fais le mouv, fais le mouv
	private final int PV = 45; //c'est la vie, lalalalala
	private final int VISION = 7;//perception humaine des rayonnements lumineux
	
	public Dragon(Hex hex) {
		super(hex);
		this.pointsAttaque = this.POWER;
		this.pointsDefense = this.DEFENSE;
		this.pointsDeplacement = this.MOVE;
		this.pointsDeVie = this.PV;
		this.vision = this.VISION;
	}
	
	public void combat(Unite unite){
		Hex[] voisins = new Hex[6];
		voisins = unite.hex.getNeighbours();
		for(int i = 0;i<6;i++) {
			if(!(voisins[i]==this.hex)) {
				if(!voisins[i].isEmpty()) {
					//TODO Recup unité a partir de la case et attaquer
				}
			}
		}
		
		if(this.hex.isNeighbour(unite.hex));
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque * Math.random()));
	}
}
