/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Joueur;

/**
 *
 * @author pavel
 */
public class GameController {

    private List<Joueur> joueurs = new ArrayList<>();
    private Joueur tourDeJouer;
    private Date gameStarted;
    public GameController(List<Joueur> joueurs) {
        joueurs.addAll(joueurs);
        tourDeJouer = joueurs.get(0);
        gameStarted = new Date();
        System.out.println("Au tour de " + this.tourDeJouer.getNom());
    }

    /**
     * Change le tour de o joueur, le changement se fait en boucle sur la liste
     * des joueurs tant que le joueur est en vie
     */
    private void changeTour() {
        int lastIndexJoueur = joueurs.indexOf(tourDeJouer);
        if (lastIndexJoueur < joueurs.size()) {
            if (joueurs.size() > 0 && joueurs.get(0).getIsIsAlive()) {
                tourDeJouer = joueurs.get(0);
            } else {
                System.out.println("Fin de jeu, il ne reste aucun joueur vivant");
            }
        } else {
            if (joueurs.get(lastIndexJoueur + 1).getIsIsAlive()) {
                tourDeJouer = joueurs.get(lastIndexJoueur + 1);
            } else {
                lastIndexJoueur++;
                changeTour();
            }

        }
    }

    private void addJoueur(Joueur joueur) {
        this.joueurs.add(joueur);
    }

    private void removeJoueur(Joueur joueur) {
        this.joueurs.remove(joueur);
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

    public Date getGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(Date gameStarted) {
        this.gameStarted = gameStarted;
    }

    
}
