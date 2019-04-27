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

	public void combat(Unite unite){
		if(this.pos.estVoisin(unite.pos)){
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque * Math.random()));
		}
		else {
			 //on peut ajouter un combat longue distance
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

}