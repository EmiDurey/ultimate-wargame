package view;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Eau;
import model.Foret;
import model.Forteresse;
import model.Hex;
import model.HexMap;
import model.Montagne;
import model.Neige;
import model.Plaine;

/**
 * Classe PanelDessineurApercu.
 * @see model.HexMap
 * @see model.Hex
 * @see model.Eau
 * @see model.Plaine
 * @see model.Foret
 * @see model.Forteresse
 * @see model.Montagne
 * @see model.Neige
 */
public class PanelDessineurApercu extends JPanel {

	/**
	 * Map du jeu.
	 */
	private HexMap map;

	/**
	 * Nombre total d'équipes.
	 */
	private int totalEquipe;

	/**
	 * Séparateur chemin.
	 */
	private String separateur;

	/**
	 * Construit un objet de type PanelDessineurApercu.
	 * @param totalEquipe int
	 * @param map HexMap
	 * @see model.HexMap
	 */
    public PanelDessineurApercu(int totalEquipe, HexMap map) {
    	this.totalEquipe = totalEquipe;
		this.map = map;
    	this.separateur = File.separator;
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
	 * Affichage la map triangulaire.
	 * @param g Graphics
	 * @see model.Hex
	 */
    public void afficheMapTriangle(Graphics g) {
		Hex current;
        int imageX, imageY;
        File image;

        for (int i = this.map.getMinX(); i <= this.map.getMaxX(); i++) {
            for (int j = this.map.getMinY(); j <= this.map.getMaxY(); j++) {
                current = this.map.getHex(i, j);
                if (current != null) {

					imageX = (int) (190 + 10 * (3./2 * current.getY()) );
					imageY = (int) (20 + 10 * (Math.sqrt(3)/2 * current.getY() + Math.sqrt(3) * current.getX()) );

					image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY);

                }
            }
        }
    }

    /**
	 * Affichage la map rectangulaire.
	 * @param g Graphics
	 * @see model.Hex
	 */
    public void afficheMapRectangle(Graphics g) {
		Hex current;
        int imageX, imageY;
        File image;

        for (int i = this.map.getMinX(); i <= this.map.getMaxX(); i++) {
            for (int j = this.map.getMinY(); j <= this.map.getMaxY(); j++) {
                current = this.map.getHex(i, j);
                if (current != null) {

					imageX = (int) (120 + 10 * (3./2 * current.getY()) );
					imageY = (int) (20 + 10 * (Math.sqrt(3)/2 * current.getY() + Math.sqrt(3) * current.getX()) );

					image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY);

                }
            }
        }
    }

    /**
	 * Affichage la map hexagonale.
	 * @param g Graphics
	 * @see model.Hex
	 */
    public void afficheMapHexagone(Graphics g) {
		Hex current;
        int imageX, imageY;
        File image;

        for (int i = this.map.getMinX(); i <= this.map.getMaxX(); i++) {
            for (int j = this.map.getMinY(); j <= this.map.getMaxY(); j++) {
                current = this.map.getHex(i, j);
                if (current != null) {

					imageX = (int) (270 + 5 * (3./2 * current.getY()) );
					imageY = (int) (140 + 5 * (Math.sqrt(3)/2 * current.getY() + Math.sqrt(3) * current.getX()) );

					image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY);

                }
            }
        }
    }

    /**
	 * Associe l'image à l'haxagone.
	 * @param hex Hex
	 * @return File
	 * @see Hex
	 * @see Eau
	 * @see Plaine
	 * @see Foret
	 * @see Forteresse
	 * @see Montagne
	 * @see Neige
	 */
    public File associeImageHex(Hex hex) {
 		File image = null;
 		String chemin = "images" + separateur + "Terrain" + separateur + "Apercu" + separateur;
 		if (this.totalEquipe > 4) {
 			chemin += "Hex";
 		} else {
 			chemin += "Rect_Tri";
 		}
 		chemin +=  separateur;

 		if (hex instanceof Eau) {
 			chemin += "eau.png";
 		} else if (hex instanceof Plaine) {
 			chemin += "plaine.png";
 		} else if (hex instanceof Foret) {
 			chemin += "foret.png";
 		} else if (hex instanceof Forteresse) {
 			chemin += "forteresse.png";
 		} else if (hex instanceof Montagne) {
 			chemin += "montagne.png";
 		} else if (hex instanceof Neige) {
 			chemin += "neige.png";
 		}

 		image = new File(chemin);

 		return image;
 	}

    /**
	 * Affiche une Image.
	 * @param g Graphics
	 * @param image File
	 * @param imageX int
	 * @param imageY int
	 */
    public void afficheImage(Graphics g, File image, int imageX, int imageY) {
    	try {
            g.drawImage(ImageIO.read(image), imageX, imageY, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
