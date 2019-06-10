package view;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.GameController;
import model.Archer;
import model.Cavalerie;
import model.Dragon;
import model.Eau;
import model.Foret;
import model.Forteresse;
import model.Hex;
import model.HexMap;
import model.Infanterie;
import model.InfanterieLourde;
import model.Mage;
import model.Montagne;
import model.Neige;
import model.Plaine;
import model.Pretre;
import model.Unite;

/**
 * Class PanelDessineurMap.
 * @see model.HexMap
 * @see controller.GameController
 */
public class PanelDessineurMap extends JPanel {

	/**
	 * Carte de jeu.
	 */
	private HexMap map;

	/**
	 * Nombre total d'équipes.
	 */
	private int totalEquipe;

	/**
	 * Séparateur de fichier.
	 */
	private String sep;

	/**
	 * Contrôleur de l'application.
	 */
	private GameController controller;

	/**
	 * Construit un objet de type PanelDessineurMap.
	 * @param totalEquipe int
	 * @param map HexMap
	 * @param controller GameController
	 * @see model.HexMap
	 * @see controller.GameController
	 */
    public PanelDessineurMap(int totalEquipe, HexMap map, GameController controller) {
    	this.totalEquipe = totalEquipe;
		this.controller = controller;
		this.map = controller.getMap();
    	this.sep = File.separator;
    }

    /**
	 * Défini l'affichage.
	 * @param g Graphics
	 */
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.totalEquipe == 3) {
			this.afficheMapTriangle(g);
		} else if (this.totalEquipe == 2 || this.totalEquipe == 4) {
			this.afficheMapRectangle(g);
		} else {
			this.afficheMapHexagone(g);
		}
	}

    /**
	 * Affiche la carte du jeu.
	 * @param g Graphics
	 * @param offsetX int
	 * @param offsetY int
	 * @see model.Hex
	 */
	public void afficheMap(Graphics g, int offsetX, int offsetY) {
    	Hex current;
        int imageX, imageY;
        File image;
        this.map = this.controller.getMap();
        for (int i = this.map.getMinX(); i <= this.map.getMaxX(); i++) {
            for (int j = this.map.getMinY(); j <= this.map.getMaxY(); j++) {
                current = this.map.getHex(i, j);
                if (current != null) {

					imageX = (int) (offsetX + 37 * (3./2 * current.getY()) );
					imageY = (int) (offsetY + 37 * (Math.sqrt(3)/2 * current.getY() + Math.sqrt(3) * current.getX()) );

					image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY);

					Boolean discovered = current.discovered.get(this.controller.getJoueurAct().getID());
					if (discovered != null) {
						if (discovered) {
							image = associeImageUnite(current);
							if (image != null) {
								this.afficheImage(g, image, imageX + 13, imageY + 10);
							}
						}
					}
                }
            }
        }
    }

    /**
	 * Affiche la map triangulaire.
	 * @param g Graphics
	 */
    public void afficheMapTriangle(Graphics g) {
		afficheMap(g, 170, 15);
    }

    /**
	 * Affiche la map rectangulaire.
	 * @param g Graphics
	 */
    public void afficheMapRectangle(Graphics g) {
		afficheMap(g, 40, 70);
    }

    /**
	 * Affiche la map hexagonale.
	 * @param g Graphics
	 */
    public void afficheMapHexagone(Graphics g) {
		afficheMap(g, 800, 980);
    }

    /**
	 * Associe un hexagone à son image.
	 * @param hex Hex
	 * @return image
	 * @see Hex
	 * @see Eau
	 * @see Plaine
	 * @see Foret
	 * @see Forteresse
	 * @see Montagne
	 * @see Neige
	 */
    public File associeImageHex(Hex hex) {
 		String chemin = "images" + sep + "Terrain" + sep + "Map" + sep;
 		File image = null;

		Boolean discovered = hex.discovered.get(this.controller.getJoueurAct().getID());

		if (discovered != null) {
			if (!discovered) {
				chemin += "brouillard";
			} else if (hex instanceof Eau) {
				chemin += "eau" + surbrillanceArc(hex, chemin);
			} else if (hex instanceof Plaine) {
				chemin += "plaine" + surbrillance(hex);
				chemin += surbrillanceArc(hex, chemin);
			} else if (hex instanceof Foret) {
				chemin += "foret" + surbrillance(hex);
				chemin += surbrillanceArc(hex, chemin);
			} else if (hex instanceof Forteresse) {
				chemin += "forteresse" + surbrillance(hex);
				chemin += surbrillanceArc(hex, chemin);
			} else if (hex instanceof Montagne) {
				chemin += "montagne" + surbrillance(hex);
				chemin += surbrillanceArc(hex, chemin);
			} else if (hex instanceof Neige) {
				chemin += "neige" + surbrillance(hex);
				chemin +=  surbrillanceArc(hex, chemin);
			}
		} else {
			chemin += "brouillard";
		}

 		chemin += ".png";

 		image = new File(chemin);
 		return image;
 	}

    /**
	 * Retourne le nom de la surbillance.
	 * @param hex Hex
	 * @return String
	 * @see Hex
	 */
    public String surbrillance(Hex hex) {
    	List<Hex> surligne = this.controller.getSurligne();
    	if (surligne.contains(hex)) {
    		return "H";
    	}
    	return "";
    }

    /**
	 * Retourne le nom de la surbillance pour l'archer.
	 * @param hex Hex
	 * @param chemin String
	 * @return string
	 * @see Hex
	 */
    public String surbrillanceArc(Hex hex, String chemin) {
    	List<Hex> surligne = this.controller.getSurligneArc();
    	if (surligne.contains(hex)) {
    		if (chemin.endsWith("H")) {
    			return "3";
        	} else {
        		return "H2";
        	}
    	}
    	return "";
    }

    /**
	 * Associe une unité à son image.
	 * @param hex Hex
	 * @return image
	 * @see model.Hex
	 * @see model.Unite
	 * @see model.Dragon
	 * @see model.Archer
	 * @see model.Cavalerie
	 * @see model.Infanterie
	 * @see model.InfanterieLourde
	 * @see model.Mage
	 * @see model.Pretre
	 */
    public File associeImageUnite(Hex hex) {
    	File image = null;
 		String chemin = "images" + sep + "Unite" + sep + "Map" + sep;
 		Unite unite = hex.getUnit();
 		if (unite != null) {
 			int nbJoueur = unite.getJoueur().getID();
	 		switch (nbJoueur) {
	 			case(1): chemin += "1"; break;
	 			case(2): chemin += "2"; break;
	 			case(3): chemin += "3"; break;
	 			case(4): chemin += "4"; break;
	 			case(5): chemin += "5"; break;
	 			case(6): chemin += "6"; break;
	 			default: break;
	 		}
	 		chemin += sep;

	 		if (unite instanceof Dragon) {
	 			chemin += "dragon.png";
	 		} else if (unite instanceof Cavalerie) {
	 			chemin += "chevalier.png";
	 		} else if (unite instanceof Archer) {
	 			chemin += "archer.png";
	 		} else if (unite instanceof Infanterie) {
	 			chemin += "infanterie.png";
	 		} else if (unite instanceof InfanterieLourde) {
	 			chemin += "infanterieLourde.png";
	 		} else if (unite instanceof Mage) {
	 			chemin += "mage.png";
	 		} else if (unite instanceof Pretre) {
	 			chemin += "pretre.png";
	 		}

	 		image = new File(chemin);
 		}

 		return image;
 	}

    /**
	 * Affiche une image.
	 * @param g Graphics
	 * @param image File
	 * @param imageX int
	 * @param imageY int
	 */
    public void afficheImage(Graphics g, File image, int imageX, int imageY) {
    	try {
            g.drawImage(ImageIO.read(image), imageX, imageY, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
