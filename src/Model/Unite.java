package model;
public abstract class Unite {
	
	protected int pointsAttaque;
	protected float pointsDefense;
	protected int pointsDeplacement;
	protected int pointsDeVie;
	protected int vision;
	protected Hex hex;
	
	public Unite(Hex hex) { 
		this.hex = hex; 
	}
	
	public Unite() { 
		
	}

	public void combat(Unite unite){
		if(this.hex.isNeighbour(unite.hex))
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque * Math.random()));
	}
	
	public int getPoints(){
		return this.pointsDeVie;
	}
	
      //IA
		public void joueTour(int tour)
		{
			
		}

	
	public void seDeplace(Hex newHex){
		setHex(newHex);
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