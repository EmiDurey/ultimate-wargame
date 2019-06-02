package controller;

import java.util.ArrayList;
import java.util.List;

import model.Archer;
import model.Hex;
import model.HexMap;
import model.Joueur;
import model.Pretre;
import model.Unite;

public class GameController {

	private HexMap map = null;
    private List<Joueur> joueurs = new ArrayList<>();
    private List<String> annonce = new ArrayList<>();
    private List<Hex> hexAnnonce = new ArrayList<>();
    private List<Hex> surligne = new ArrayList<>();
    private Joueur joueurAct = null;
    private Unite uniteSelectionne = null;
    private Hex hexSelectionne = null;
    private boolean fin = false;
    private int offsetX;
    private int offsetY;
    private int idMap;

    /* Booleen permettant de savoir la source du clic.
     * true = premier clic (il a choisit l'unité)
     * false = deuxieme clic (il a choisit l'hexagone sur lequel il veut déplacer l'unité)
     */
    private boolean source = true;

    public GameController(ArrayList<Joueur> joueurs) {

    	this.map = new HexMap();
        this.joueurs.addAll(joueurs);
        this.joueurAct = joueurs.get(0);

        if (this.joueurs.size() == 3) {
        	this.idMap = 1;
        	this.map.setTriangleMap(13);
        	this.offsetX = 170 + 38;
        	this.offsetY = 15 + 33;
        } else if (this.joueurs.size() <= 4) {
        	this.map.setRectangleMap(12, 18);
        	this.idMap = 2;
        	this.offsetX = 40+38;
        	this.offsetY = 70+33;
        } else {
        	this.map.setHexagonMap(15);
        	this.idMap = 3;
        	this.offsetX = 800 + 38;
        	this.offsetY = 980 + 33;
        }
        this.map.initMap(joueurs);
}

    /**
	 *  Retourne la map.
	 *  @return HexMap
	 */
    public HexMap getMap() {
    	return this.map;
    }

    /**
     * Change le tour des joueurs.
     * Le changement se fait en boucle sur la liste des joueurs
     * @throws InterruptedException
     */
    private void changeTour() throws InterruptedException {

    	this.verif();
    	annonce.clear();
    	hexAnnonce.clear();
    	for(Unite unite: joueurAct.getUnite()) {

    		int pvInit = unite.getPointsDeVie();
        	unite.heal();
        	int pvFin = unite.getPointsDeVie();
        	annonce.add(String.valueOf(pvFin-pvInit));
        	hexAnnonce.add(unite.getHex());

    		unite.initialize();
    		if(unite instanceof Pretre) {
    			((Pretre) unite).soigne(map, joueurAct);
    			//AFFICHAGE HEAL ????
    		}
    	}
    	int lastIndexJoueur = joueurs.indexOf(joueurAct);
    	if (lastIndexJoueur < joueurs.size()) {
    		joueurAct = joueurs.get(lastIndexJoueur + 1);
    		if(joueurAct.isIA()) {
    			tourIA();
	    	} else {
	    		joueurAct = joueurs.get(0);
	    	}
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
    		annonce.clear();
    		annonce.add("Fin de la partie !");
    		fin = true;
    	}
    }

    /**
     * Gère le clic et agit en conséquence
     */
    public void handleMove(int Xcord, int Ycord) {
    	annonce.clear();
    	hexAnnonce.clear();
        this.hexSelectionne = pixelToHex(Xcord, Ycord);


		if (this.hexSelectionne == null)
			return;
		System.out.println("###############"+ source +"#########");
        if (source) {
        	System.out.println( hexSelectionne.getUnit() +" Hexagon vide");
        	if(!(hexSelectionne.getUnit() == null)) {
        		if(joueurAct.getUnite().contains(hexSelectionne.getUnit())) {
        			System.out.println("Clique allié");
        			uniteSelectionne = hexSelectionne.getUnit();
        			surligne = map.movementHighlight(uniteSelectionne.getHex(), uniteSelectionne.getVision());
        			if(uniteSelectionne instanceof Archer) {
        				surligne.addAll(map.viewHighlight(uniteSelectionne.getHex(), ((Archer) uniteSelectionne).getPortee()));
        			}
        			toggleSource();
        		} else {
        			System.out.println("Clique ennemi");
        			uniteSelectionne = hexSelectionne.getUnit();
        		}
        	}
        } else {
            if (hexSelectionne.getUnit() == null) {
            	System.out.println("Il se passe rien");
            	//this.hexSelectionne.getUnit().seDeplace(this.map, this.hexSelectionne);
            	surligne.clear();
            } else if (joueurAct.getUnite().contains(hexSelectionne.getUnit())) {
            	System.out.println("Clique allé");
            	surligne.clear();
            	uniteSelectionne = hexSelectionne.getUnit();
            	surligne = map.movementHighlight(uniteSelectionne.getHex(), uniteSelectionne.getVision());
    			if(uniteSelectionne instanceof Archer) {
    				surligne.addAll(map.viewHighlight(uniteSelectionne.getHex(), ((Archer) uniteSelectionne).getPortee()));
    			}
    			toggleSource();
            } else {
            	System.out.println("Clique ennemi");
            	int pvFin = 0;
            	int pvInit = hexSelectionne.getUnit().getPointsDeVie();
            	uniteSelectionne.combat(map, joueurAct, hexSelectionne.getUnit());
            	if(!(hexSelectionne.getUnit()==null)) {
            		pvFin = hexSelectionne.getUnit().getPointsDeVie();
            	}
            	else {
            		this.verif();
            	}
            	annonce.add(String.valueOf(pvFin-pvInit));
            	hexAnnonce.add(hexSelectionne);
            	surligne.clear();
            }
        toggleSource();
        }
    }

    /**
     * Reourne l'hexagone sur lequel l'utilisateur à cliqué
     *
     * @param Xcord : Coordonné X du clic
     * @param Ycord : Coordonné Y du clic
     * @return : L'hexagone
     *
     */
    Hex pixelToHex(int x, int y) {

		//Substract offset
        x -= this.offsetX;
        y -= this.offsetY;

		System.out.println(x + "/" + y);


        //Calculating Hex coords
        double yHex = (double) ( 2./3 * x )/ 37;
        double xHex = (double) (-1./3 * x  +  Math.sqrt(3)/3 * y) / 37;

        //Rounding
        double zHex = -xHex -yHex;

        double rx = Math.round(xHex);
        double ry = Math.round(yHex);
        double rz = Math.round(zHex);

        double xDiff = Math.abs(rx - xHex);
        double yDiff = Math.abs(ry - yHex);
        double zDiff = Math.abs(rz - zHex);


        if (xDiff > yDiff && xDiff > zDiff)
            rx = -ry-rz;
        else if (yDiff > zDiff)
            ry = -rx-rz;
        else
            rz = -rx-ry;

		System.out.println(rx + " " + ry);

        return this.map.getHex((int) rx, (int) ry);
}


    public void tourIA() throws InterruptedException {
    	List<Unite> unitTri = new ArrayList<Unite>();
    	for(Unite unit : joueurAct.getUnite()) {
    		if(unit instanceof Pretre) {
    			unitTri.add(unit);
    		} else {
    			unitTri.add(0,unit);
    		}
    	}
    	for(Unite unit : unitTri) {
    		unit.joueurIA(joueurAct, map);
    		wait(1);
    	}
    	changeTour();

    }

    public void toggleSource() {
        if(source == false) {
        	source = true;
        } else {
        	source = false;
        }
    }

}
