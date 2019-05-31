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

public class PanelDessineur extends JPanel {
	
	private HexMap map;

    public PanelDessineur() {
    	this.map = new HexMap();
	    this.map.setHexagonMap(15);
	    this.map.populate();
	    //this.map.ASCIIDisplay();
    }
    
    public File attribuImageHex(Object hex) {
		String chemin = "images\\Terrain\\";
		File image = null;
		
		if(hex instanceof Eau) {
			chemin += "eau.png";
		}
		else if(hex instanceof Plaine) {
			chemin += "plaine.png";
		}
		else if(hex instanceof Foret) {
			chemin += "foret.png";
		}
		else if(hex instanceof Forteresse) {
			chemin += "forteresse.png";
		}
		else if(hex instanceof Montagne) {
			chemin += "montagne.png";
		}
		else if(hex instanceof Neige) {
			chemin += "neige.png";
		}
		else {
			chemin += "plaine.png";
		}
		
		image = new File(chemin);
		
		return image;		
	}
    
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Hex current;

		for (int i = this.map.getMinX(); i < this.map.getMaxX(); i++) {
			for (int j = this.map.getMinY(); j < this.map.getMaxY(); j++) {
				current = this.map.getHex(i, j);
				if (current != null) {
					int imageX, imageY;
					imageX = 310 + (current.getX() + 8) * 52;
					if(i%2 != 0) {
						imageY = 455 + (current.getY() + 8) * 65 + 32;
					}
					else {
						imageY = 455 + (current.getY() + 8) * 65;
					}
					File image = attribuImageHex(current);
					try {
						g.drawImage(ImageIO.read(image), imageX, imageY, this);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
