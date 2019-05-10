package model;

import java.awt.Color;
import java.util.ArrayList;

public class Joueur {
		
	private String nom;
	private int argent = 10000;
	private Color couleur;
	private ArrayList<Unite> unites = new ArrayList<Unite>();
	private ArrayList<Unite> enAttente = new ArrayList();

	public Joueur(String name) {
		this.setNom(name);
	}
	
	public String toString() {
		return nom;
	}

	public void setNom(String name) {
		this.nom = name.toUpperCase();
	}

	public int getMoney() {
		return this.argent;
	}
	
	public Color getCouleur() {
		return this.couleur;
	}

	public void setColor(Color color) {
		this.couleur = color;
	}
	
	public ArrayList<Unite> getUnite() {
		return unites;
	}
	
	public void ajouterCavalerie() {
		Cavalerie cavalerie = new Cavalerie();
		enAttente.add(cavalerie);
	}
	
	public void ajouterArcher() {
		Archer archer = new Archer();
		enAttente.add(archer);
	}
	
	public void acheterArcher(String t) {
		int prixArcher = 100;
		if (t.compareTo("argent") == 0) {
			if (argent >= prixArcher) {
				ajouterArcher();
				argent -= prixArcher;
			}
		}
	}
	
	public void acheterCavalier(String t) {
		int prixCavalier = 500;
		if (t.compareTo("argent") == 0) {
			if (argent >= prixCavalier) {
				ajouterArcher();
				argent -= prixCavalier;
			}
		}
	}
	
	public int getArgent() {
		return argent;
	}

	public void setArgent(int argent) {
		this.argent = argent;
	}
	
	public ArrayList<Unite> getEnAttente() {
		return enAttente;
	}

	public void setEnAttente(ArrayList<Unite> enAttente) {
		this.enAttente = enAttente;
	}
}