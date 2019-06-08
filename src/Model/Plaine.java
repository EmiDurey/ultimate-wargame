package model;

/**
 *  Classe Plaine.
 */
public class Plaine extends Hex {

	/*
	 * Constructeur.
	 */
	Plaine(int newX, int newY) {
		super(newX, newY);
		this.cost = 15;
		this.defense = 10;
	}

	/*
	 * Constructeur.
	 */
	Plaine(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}
}
