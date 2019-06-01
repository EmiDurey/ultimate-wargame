/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import model.Hex;
import model.HexMap;
import model.Joueur;
import model.Unite;

/**
 *
 * @author pavel
 */
public class PanelCarteListener {

    private static HexMap listenedHexMap;
    private Hex triggeredHex;
    private List<Hex> accessibleHex = new ArrayList<>();
    /*Booleen permettant de savoir la source du clic
    true = premier clic (il a choisit l'unité)
    false = deuxieme clic (il a choisit l'hexagone sur lequel il veut déplacer l'unité)
     */
    private static boolean isSource = true;

    private static Joueur currentPlayer;


    /*Le joueur qui à la main*/
    /**
     * Gère le clic et agit en conséquence
     */
    public void handleMove(int Xcord, int Ycord) {
        this.triggeredHex = getcliquedHex(Xcord, Ycord);
        if (isSource) {
            if (UnitCanMove()) {
                triggeredHex.getUnit().setIsActive(true);
                drawWay();
            }
        } else {
            if (triggeredHex.getEmpty()) {
                triggeredHex.getUnit().seDeplace(listenedHexMap, triggeredHex);
                triggeredHex.getUnit().getHex().setUnit(null);//vide l'unité de l'hexagone
            } else if (currentPlayer.getUnite().contains(triggeredHex.getUnit())) {
                /*
                Il sont alié
                 */
            } else {
                for (Unite currentActiveUnite : currentPlayer.getUnite()) {
                    if (currentActiveUnite.isIsActive()) {
                        //TODO tester la vision avant d'attaquer
                        currentActiveUnite.combat(listenedHexMap, currentPlayer, currentActiveUnite);
                        
                    } else {
                        currentActiveUnite.heal();
                    }
                }
            }
        }
        toggleIsSource();

    }

    /**
     * Reourne le l'hexagone sur lequel l'utilisateur à cliqué
     *
     * @param Xcord : Coordonné X du clic
     * @param Ycord : Coordonné Y du clic
     * @return : L'hexagone
     *
     */
    public Hex getcliquedHex(int Xcord, int Ycord) {
        //System.out.println("clicqked on "+PanelCarteListener.getListenedHexMap().getHex(Xcord, Ycord).getUnit().toString());
        return PanelCarteListener.getListenedHexMap().getHex(Xcord, Ycord);
    }

    /**
     * A l'aide de l'hexagone sur lequel l'utilisateur à cliquer, détermine s'il
     * peut avancer
     *
     */
    public boolean UnitCanMove() {
        boolean canMove = true;
        if (!this.getTriggeredHex().getEmpty()) {//On ne fait rien si l'hexagone est vide
            if (currentPlayer.getUnite().contains(this.triggeredHex.getUnit())) {
                if (this.triggeredHex.getUnit().getPointsDeVie() > 0) {
                    canMove = true;
                } else {
                    System.out.println("vous etes mort");
                }
            } else {
                System.out.println("Case occupée par une autre unité");
            }
        } else {
            System.out.println("Hexagone vide (vous n'avez aucune unité sur cet hexagone");
        }
        return canMove;
    }

    /**
     * Si l'utiliateur a choisie un case de destination: si une unité adverse
     * s'y trouve on attaque si elle est vide on verifie qu'il peut de deplacer
     * jusque là s'il y a un allié ...Kuza
     */
    public void handleDestination() {
        if (this.triggeredHex.getEmpty()) {

        }
    }

    /**
     * Colorie les Hexagone qui font partie d'un chemin possible partant du clic
     * (voisins uniquement)
     */
    public void drawWay() {
        Hex currentHex = triggeredHex;
        for (Hex neighbour : listenedHexMap.getNeighbours(currentHex)) {
            if (neighbour.getEmpty() || currentPlayer.getUnite().contains(neighbour.getUnit())) {
                neighbour.setColor(Color.yellow);
            }
        }
    }

    /**
     * Surligne un hexagone
     *
     * @param hexagone : l'hexagone à surligner
     */
    public void HighLight(Hex hexagone) {
        hexagone.setColor(Color.yellow);
    }

    public void toggleIsSource() {
        isSource = false ? true : false;
    }

    public static HexMap getListenedHexMap() {
        return listenedHexMap;
    }

    public static void setListenedHexMap(HexMap listenedHexMap) {
        PanelCarteListener.listenedHexMap = listenedHexMap;
    }

    public Hex getTriggeredHex() {
        return triggeredHex;
    }

    public void setTriggeredHex(Hex triggeredHex) {
        this.triggeredHex = triggeredHex;
    }

}
