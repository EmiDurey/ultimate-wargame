package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Class Joueur.
 *  @see Unite
 */
public class Joueur implements Serializable {

	/**
	 *  Liste d'unités d'un joueur.
	 */

	private ArrayList<Unite> unites = new ArrayList<Unite>();

	/**
	 *  Identifiant unique du joueur.
	 */
	private int id;

	/**
	 *  Couleur du joueur.
	 */
	private String couleur;

	/**
	 *  Est une IA.
	 */
	private boolean isIA;

	/**
	 *  Constructeur d'un joueur.
	 *  @param newId int
	 */
	public Joueur(int newId) {
		this.id = newId;
		associeCouleurId();
	}

	/**
	 *  Récupère la liste d'unités d'un joueur.
	 *  @return unites ArrayList<Unite>
	 *  @see Unite
	 */
	public ArrayList<Unite> getUnite() {
		return unites;
	}

	/**
	 *  Récupère la couleur du joueur.
	 *  @return String
	 */
	public String getCouleur() {
		return this.couleur;
	}

	/**
	 *  Associe la couleur du joueur selon son ID.
	 */
	public void associeCouleurId() {
		String[] couleurs = {"Bleu", "Rose", "Rouge", "Vert", "Violet", "Jaune"};
		this.couleur = couleurs[this.id - 1];
	}

	/**
	 *  Ajoute une unité à celles possédées par le joueur.
	 *  @param unit String
	 *  @see Unite
	 */
	public void addUnit(Unite unit) {
		unites.add(unit);
	}

	/**
	 *  Récupère l'id du joueur.
	 *  @return id int
	 */
	public int getID() {
		return this.id;
	}

	/**
	 *  Assigne un id au joueur.
	 *  @param id ind
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 *  Indique si le joueur est humain ou non.
	 *  @return nom String
	 */
	public boolean isIA() {
		return isIA;
	}

	/**
	 *  Modifie le statut du joueur (humain / IA).
	 *  @param isIA boolean
	 */
	public void setIA(boolean isIA) {
		this.isIA = isIA;
	}
}
