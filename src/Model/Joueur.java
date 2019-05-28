package model;

import java.util.ArrayList;

/**
 *  Class Joueur.
 */
public class Joueur {

	/**
	 *  Nom du joueur.
	 */
	private String nom;
	/**
	 *  Liste d'unités d'un joueur.
	 */
	private ArrayList<Unite> unites = new ArrayList<Unite>();

	/**
	 *  Constructeur d'un joueur.
	 *  @param nom String
	 */
	public Joueur(String nom) {
		this.setNom(nom);
	}

	/**
	 *  Récupère le nom du joueur.
	 *  @return nom String
	 */
	public String getNom() {
		return nom;
	}

	/**
	 *  Assigne un nom au joueur.
	 *  @param nom String
	 */
	public void setNom(String nom) {
		this.nom = nom.toUpperCase();
	}

	/**
	 *  Récupère la liste d'unitée d'un joueur.
	 *  @return unites ArrayList<Unite>
	 */
	public ArrayList<Unite> getUnite() {
		return unites;
	}
}
