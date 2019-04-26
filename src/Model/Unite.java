public abstract class Unite {
	private final int POWER; //gros c'est la puissance
	private int pointsDeVie;
	private Hex hex;
	private Position pos;
	
	public Unite(Hex hex, int pts, Position pos) {
		this.pos = pos;
		this.hex = hex; 
		this.pos = pos;
	}
	
	

	public int getPoints(){
		return this.pointsDeVie;
	}
	
      //IA
		public void joueTour(int tour)
		{
			
		}

	
	public void combat(Unite unite){
		if(this.pos.estVoisin(unite.pos)){
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.POWER * Math.random()));
		}
		else {
			 //on peut ajouter un combat longue distance
		}
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