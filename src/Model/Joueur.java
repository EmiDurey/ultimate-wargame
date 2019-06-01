package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Class Joueur.
 */
public class Joueur implements Serializable {

	/**
	 *  Nom du joueur.
	 */
	private String nom;
	/**
	 *  Liste d'unités d'un joueur.
	 */
        
        /**
         * Détermine si un joueur est encore en vie
         */
        private boolean isAlive;
         
	private ArrayList<Unite> unites = new ArrayList<Unite>();
	/**
	 *  Identifiant unique du joueur
	 */
	public int id;

	/**
	 *  Constructeur d'un joueur.
	 *  @param nom String
	 */
	public Joueur(String nom, int newId) {
		this.setNom(nom);
		this.id = newId;
	}

	/**
	 *  Récupère le nom du joueur.
	 *  @return nom String
	 */
	public String getNom() {
		System.out.println("ID in getter:"+id);
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

	/**
	 *  Ajoute une unite à celles possédées par le joueur.
	 *  @param unit String
	 */
	public void addUnit(Unite unit) {
		unites.add(unit);
	}



	/**
	 *  Assigne un id au joueur.
	 *  @param id ind
	 */
	public void setID(int id) {
		this.id = id;
	}

    public boolean getIsIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
        

}
