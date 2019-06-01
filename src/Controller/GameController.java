package controller;

import java.util.ArrayList;
import java.util.List;
import model.HexMap;
import model.Joueur;

public class GameController {

	private HexMap map;
    private List<Joueur> joueurs = new ArrayList<>();
    
    private Joueur tourDeJouer;
    
    public GameController(List<Joueur> joueurs, HexMap map) {
        this.joueurs.addAll(joueurs);
        this.tourDeJouer = joueurs.get(0);
        this.map = map;
    }

    /**
     * Change le tour des joueurs.
     * Le changement se fait en boucle sur la liste des joueurs
     */
    private void changeTour() {
    	
    	this.verif();
    	int lastIndexJoueur = joueurs.indexOf(tourDeJouer);
    	if (lastIndexJoueur < joueurs.size()) {
    		tourDeJouer = joueurs.get(lastIndexJoueur + 1);
    	} else {
    		tourDeJouer = joueurs.get(0);
    	}
    }

    /**
     * Vérifie l'état des joueurs et la fin de partie. 
     */
    private void verif() {
    	for(Joueur joueur : joueurs) {
    		if(joueur.getUnite().isEmpty()) {
    			joueurs.remove(joueur);
    		}
    	}
    	if(joueurs.size() == 1) {
    		//TODO FIN DE PARTIE
    	}
    }
    
    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Joueur getTourDeJouer() {
        return tourDeJouer;
    }

    public void setTourDeJouer(Joueur tourDeJouer) {
        this.tourDeJouer = tourDeJouer;
    }

    
}
