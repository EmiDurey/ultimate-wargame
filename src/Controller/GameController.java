package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Archer;
import model.Hex;
import model.HexMap;
import model.Joueur;
import model.Pretre;
import model.Unite;

/**
 *  Class GameController.
 *  Classe g�rant la partie de jeu.
 *  @see model.Archer
 *  @see model.Archer
 *  @see model.Hex
 *  @see model.HexMap
 *  @see model.Joueur
 *  @see model.Pretre
 *  @see model.Unite
 */
public class GameController implements Serializable {

	/**
	 * Map de la partie.
	 */
	private HexMap map = null;

	/**
	 * Liste de joueurs de la partie.
	 */
    private List<Joueur> joueurs = new ArrayList<>();

    /**
     * Permet d'envoyer des strings � la vue (affichage d�gats et heal).
     */
    private List<String> annonce = new ArrayList<>();

    /**
     * Permet d'envoyer une position d'affichage � la vue.
     */
    private List<Hex> hexAnnonce = new ArrayList<>();

    /**
     * Hex sur lesquels on peut se d�placer.
     */
    private List<Hex> surligne = new ArrayList<>();

    /**
     * Hex sur lesquels on peut tirer.
     */
    private List<Hex> surligneArc = new ArrayList<>();

    /**
     * Joueur actuel.
     */
    public Joueur joueurAct = null;
    /**
     * Unit� s�lectionn�e.
     */
    private Unite uniteSelectionne = null;

    /**
     * Hexagone s�lectionn�.
     */
    private Hex hexSelectionne = null;

    /**
     * Booleen g�rant la fin de la partie.
     */
    private boolean fin = false;

    /**
     * OffsetX selon la map.
     */
    private int offsetX;

    /**
     * OffsetY selon la map.
     */
    private int offsetY;

    /**
     * Id de la Map.
     */
    private int idMap;

    /**
     * Booleen permettant de savoir la source du clic.
     * true = premier clic (il a choisit l'unit�)
     * false = deuxieme clic (il a choisit l'hexagone sur lequel il veut d�placer l'unit�)
     */
    private boolean source = true;

    /**
     * Contruit un onjet de type GameController.
     * @param joueurs ArrayList<Joueur>
     * @see model.Joueur
     * @see model.HexMap
     */
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
        	this.offsetX = 40 + 38;
        	this.offsetY = 70 + 33;
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
	 *  @see model.HexMap
	 */
    public HexMap getMap() {
    	return this.map;
    }

    /**
     * Change le tour des joueurs.
     * Le changement se fait en boucle sur la liste des joueurs.
     * @throws InterruptedException
     * @see model.Unite
     * @see model.Pretre
     */
    public void changeTour() throws InterruptedException {
    	this.verif();
    	annonce.clear();
    	hexAnnonce.clear();

    	for (Unite unite: joueurAct.getUnite()) {

    		int pvInit = unite.getPointsDeVie();
        	unite.heal();
			unite.setPointsDeplacement(unite.getPointsDeplacementInit());
			unite.setHasAttacked(false);
        	int pvFin = unite.getPointsDeVie();
        	annonce.add(String.valueOf(pvFin - pvInit));
        	hexAnnonce.add(unite.getHex());

    		unite.initialize();
    		if (unite instanceof Pretre) {
    			((Pretre) unite).soigne(map, joueurAct);
    		}
    	}
    	int lastIndexJoueur = joueurs.indexOf(joueurAct);
    	if (lastIndexJoueur < joueurs.size() - 1) {
    		joueurAct = joueurs.get(lastIndexJoueur + 1);
    	} else {
    		joueurAct = joueurs.get(0);
    	}
    	if (joueurAct.isIA()) {
			tourIA();
    	}
    }

    /**
     * V�rifie l'�tat des joueurs et la fin de partie.
     * @see model.Joueur
     */
    private void verif() {
    	for (Joueur joueur : joueurs) {
    		if (joueur.getUnite().isEmpty()) {
    			joueurs.remove(joueur);
    		}
    	}
    	if (joueurs.size() == 1) {
    		annonce.clear();
    		fin = true;
    	}
    }

    /**
     * G�re le clic et agit en cons�quence.
     * @param cordX int
     * @param cordY int
     */
    public void handleMove(int cordX, int cordY) {
    	annonce.clear();
    	hexAnnonce.clear();
        this.hexSelectionne = pixelToHex(cordX, cordY);


		if (this.hexSelectionne == null) {
			return;
		}

        if (source) {
        	if (!(hexSelectionne.getUnit() == null)) {
        		if (joueurAct.getUnite().contains(hexSelectionne.getUnit())) {
        			uniteSelectionne = hexSelectionne.getUnit();
        			surligne = map.movementHighlight(uniteSelectionne.getHex(), uniteSelectionne.getPointsDeplacement());
        			if (uniteSelectionne instanceof Archer) {
        				if (uniteSelectionne.getPointsDeplacement() != 0) {
        					surligneArc.addAll(map.viewHighlight(uniteSelectionne.getHex(), ((Archer) uniteSelectionne).getPortee()));
        				}
        			}
        			toggleSource();
        		} else {
        			uniteSelectionne = hexSelectionne.getUnit();
        		}
        	}
        } else {
            if (hexSelectionne.getUnit() == null) {
            	uniteSelectionne.seDeplace(this.map, this.hexSelectionne);
            	surligne.clear();
            	surligneArc.clear();
            } else if (joueurAct.getUnite().contains(hexSelectionne.getUnit())) {
            	surligne.clear();
            	surligneArc.clear();
            	uniteSelectionne = hexSelectionne.getUnit();
            	surligne = map.movementHighlight(uniteSelectionne.getHex(), uniteSelectionne.getPointsDeplacement());
    			if (uniteSelectionne instanceof Archer) {
    				if (uniteSelectionne.getPointsDeplacement() != 0) {
    					surligneArc.addAll(map.viewHighlight(uniteSelectionne.getHex(), ((Archer) uniteSelectionne).getPortee()));
    				}
    			}
    			toggleSource();
            } else {
            	int pvFin = 0;
            	int pvInit = hexSelectionne.getUnit().getPointsDeVie();
            	uniteSelectionne.combat(map, joueurAct, hexSelectionne.getUnit());
            	if (!(hexSelectionne.getUnit() == null)) {
            		pvFin = hexSelectionne.getUnit().getPointsDeVie();
            	} else {
            		this.verif();
            	}
            	annonce.add(String.valueOf(pvFin - pvInit));
            	hexAnnonce.add(hexSelectionne);
            	surligne.clear();
            	surligneArc.clear();
            }
        toggleSource();
        }
    }

    /**
     * Retourne l'hexagone sur lequel l'utilisateur � cliqu�.
     * @param x int
     * @param y int
     * @return : L'hexagone
     * @see model.Hex
     *
     */
    Hex pixelToHex(int x, int y) {

		//Substract offset
        x -= this.offsetX;
        y -= this.offsetY;

        //Calculating Hex coords
        double yHex = (double) (2. / 3 * x) / 37;
        double xHex = (double) (-1. / 3 * x  +  Math.sqrt(3) / 3 * y) / 37;

        //Rounding
        double zHex = -xHex - yHex;

        double rx = Math.round(xHex);
        double ry = Math.round(yHex);
        double rz = Math.round(zHex);

        double xDiff = Math.abs(rx - xHex);
        double yDiff = Math.abs(ry - yHex);
        double zDiff = Math.abs(rz - zHex);

        if (xDiff > yDiff && xDiff > zDiff) {
            rx = -ry - rz;
        } else if (yDiff > zDiff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }

        return this.map.getHex((int) rx, (int) ry);
    }

    /**
	 * G�re les tours de l'IA.
	 * @throws InterruptedException
	 * @see model.Unite
	 * @see model.Pretre
	 */
    public void tourIA() throws InterruptedException {
    	List<Unite> unitTri = new ArrayList<Unite>();
    	for (Unite unit : joueurAct.getUnite()) {
    		if (unit instanceof Pretre) {
    			unitTri.add(unit);
    		} else {
    			unitTri.add(0, unit);
    		}
    	}
    	for (Unite unit : unitTri) {
    		unit.joueurIA(joueurAct, map);
    	}
    	changeTour();

    }

    /**
	 * R�cup�re la liste des terrains surlign�s.
	 * @return List<Hex>
	 * @see model.Hex
	 */
    public List<Hex> getSurligne() {
    	return this.surligne;
    }

    /**
	 * R�cup�re la liste des terrains surlign�s de l'archer.
	 * @return List<Hex>
	 * @see model.Hex
	 */
    public List<Hex> getSurligneArc() {
    	return this.surligneArc;
    }

    /**
	 * R�cup�re l'unit� s�lectionn�e.
	 * @return Unite
	 * @see model.Unite
	 */
    public Unite getUniteSelectionne() {
    	return this.uniteSelectionne;
    }

    /**
	 * R�cup�re la liste des annonces.
	 * @return List<String>
	 */
    public List<String> getAnnonce() {
    	return this.annonce;
    }

    /**
	 * R�cup�re la liste des hexagonnes poss�dant une annonce.
	 * @return List<Hex>
	 * @see model.Hex
	 */
    public List<Hex> getHexAnnonce() {
    	return this.hexAnnonce;
    }

    /**
	 * R�cup�re le joueur actuel.
	 * @return Joueur
	 * @see model.Joueur
	 */
	public Joueur getJoueurAct() {
		return this.joueurAct;
	}

	/**
	 * R�cup�re la liste des joueurs.
	 * @return List<Joueur>
	 * @see model.Joueur
	 */
	public List<Joueur> getJoueurs() {
		return this.joueurs;
	}

	/**
	 * R�cup�re le boolean de fin.
	 * @return Boolean
	 */
	public Boolean getFin() {
		return this.fin;
	}

	/**
	 * Change la valeur du booleen source.
	 */
    public void toggleSource() {
        if (source) {
        	source = false;
        } else {
        	source = true;
        }
    }

}
