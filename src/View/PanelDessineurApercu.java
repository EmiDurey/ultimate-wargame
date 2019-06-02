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

public class PanelDessineurApercu extends JPanel {

	private HexMap map;
	private int totalEquipe;
	private String separateur;

    public PanelDessineurApercu(int totalEquipe, HexMap map) {
    	this.totalEquipe = totalEquipe;
		this.map = map;
    	this.separateur = File.separator;
    }

    public void creeMap() {
    	if (this.totalEquipe <= 3) {
    		this.map.setTriangleMap(13);
		} else if (this.totalEquipe == 4) {
			this.map.setRectangleMap(10, 18);
		} else {
			this.map.setHexagonMap(15);
		}
    }

    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.totalEquipe == 3) {   // triangle
			this.afficheMapTriangle(g);
		} else if (this.totalEquipe == 2 || this.totalEquipe == 4) {   // rectangle
			this.afficheMapRectangle(g);
		} else {   // hexagone
			this.afficheMapHexagone(g);
		}
	}

    public void afficheMapTriangle(Graphics g) {
    	Hex current;
        int imageX, imageY;
        File image;

        for (int i = this.map.getMinX(); i <= this.map.getMaxX(); i++) {
            for (int j = this.map.getMinY(); j <= this.map.getMaxY(); j++) {
                current = this.map.getHex(i, j);
                if (current != null) {
                	imageX = 200 + 13 * (3/2 * current.getY());
                    imageY = (int) (30 + 5 *  (Math.sqrt(3/2) * current.getY() + Math.sqrt(3) * current.getX()) + current.getX() * 7);
                    image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY);
                }
            }
        }
    }

    public void afficheMapRectangle(Graphics g) {
    	Hex current;
        int imageX, imageY;
    	File image;

        for (int i = this.map.getMinX(); i <= this.map.getMaxX(); i++) {
            for (int j = this.map.getMinY(); j <= this.map.getMaxY(); j++) {
                current = this.map.getHex(i, j);
                if (current != null) {
                    imageX = 150 + 13 * (3/2 * current.getY());
                    if (j % 2 == 0) {
                        imageY = (int) (110 + 5 *  (Math.sqrt(3) * current.getX()) + 4 + current.getX() * 7);
                    } else {
                        imageY = (int) (110 + 5 *  (Math.sqrt(3) * current.getX()) + current.getX() * 7);
                    }

                    image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY);
                }
            }
        }
    }

    public void afficheMapHexagone(Graphics g) {
    	Hex current;
    	int imageX, imageY;
    	File image;

    	for (int i = this.map.getMinX(); i < this.map.getMaxX(); i++) {
			for (int j = this.map.getMinY(); j < this.map.getMaxY(); j++) {
				current = this.map.getHex(i, j);
				if (current != null) {
					imageX = 270 + 10 * (3/2 * current.getY());
	                imageY = (int) (144 + 5 *  (Math.sqrt(3/2) * current.getY() + Math.sqrt(3) * current.getX()) + current.getX());

					image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY);
				}
			}
		}
    }

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

    public void afficheImage(Graphics g, File image, int imageX, int imageY) {
    	try {
            g.drawImage(ImageIO.read(image), imageX, imageY, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
