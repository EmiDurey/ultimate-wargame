package model;
public abstract class Unite {
	
	protected int pointsAttaque;
	protected float pointsDefense;
	protected int pointsDeplacement;
	protected int pointsDeVie;
	protected int vision;
	protected Hex hex;
	protected Position pos;
	
	public Unite(Hex hex, Position pos) { 
		this.pos = pos;
		this.hex = hex; 
	}
	
	public Unite() { 
		
	}

	public void combat(Unite unite){
		if(this.pos.estVoisin(unite.pos)){
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque * Math.random()));
		}
		else if(this instanceof Archer) {
			/*unite.
			 * ici on s'occupe du tir à distance si l'unité est de type archer
			 */
		}
	}
	
	public int getPoints(){
		return this.pointsDeVie;
	}
	
      //IA
		public void joueTour(int tour)
		{
			
		}

	
	public void seDeplace(Position newPos){
		setPos(newPos);
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public Hex getHex() {
		return hex;
	}
	
	public void setHex(Hex hex) {
		this.hex = hex;
	}
	
	public float getDefense() {
		return pointsDefense;
	}
	
	public void setDefense(float pointsDefense) {
		this.pointsDefense = pointsDefense;
	}

	public float getPointsDeplacement() {
		return pointsDeplacement;
	}

	public void setPointsDeplacement(int pointsDeplacement) {
		this.pointsDeplacement = pointsDeplacement;
	}
	

}