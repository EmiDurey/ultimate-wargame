package model;

public class Position {
	private int x, y;
	
	public Position(int x, int y){ 
		this.x = x; this.y = y; 
	}
	
	public int getX(){ 
		return x; 
	}
	
	public int getY(){ 
		return y; 
	}
	
	public void setX(int x){ 
		this.x = x; 
	}
	
	public void setY(int y){ 
		this.y = y; 
	}
	
	public boolean estVoisin(Position pos) {
		return false;
		//en fonction de x et y
	}
}
