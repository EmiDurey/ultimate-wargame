package view;

import Controller.PanelCarteListener;
import java.awt.Color;
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
    private int totalEquipe;
    private String sep;

    public PanelDessineur(int totalEquipe) {
        this.totalEquipe = totalEquipe;
        this.sep = File.separator;
        this.map = new HexMap();
        this.creeMap();
        this.map.populate();
        //this.map.ASCIIDisplay();
    }

    public void creeMap() {
        if (this.totalEquipe <= 3) {
            this.map.setTriangleMap(13);
        } else if (this.totalEquipe == 4) {
            this.map.setRectangleMap(12, 18);
        } else {
            this.map.setHexagonMap(15);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.totalEquipe <= 3) {   // triangle
            this.afficheMapTriangle(g);
        } else if (this.totalEquipe == 4) {   // rectangle
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
                    imageX = 170 + 54 * (3 / 2 * current.getY());
                    imageY = (int) (15 + 33 * (Math.sqrt(3 / 2) * current.getY() + Math.sqrt(3) * current.getX()) + current.getX() * 7);
                    image = associeImageHex(current);
                    this.afficheImage(g, image, imageX, imageY, current.getColor());
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
                    imageX = 20 + 54 * (3 / 2 * current.getY());
                    if (j % 2 == 0) {
                        imageY = (int) (384 + 33 * (Math.sqrt(3) * current.getX()) + 32 + current.getX() * 7);
                    } else {
                        imageY = (int) (384 + 33 * (Math.sqrt(3) * current.getX()) + current.getX() * 7);
                    }

                    image = associeImageHex(current);
                    
                    this.afficheImage(g, image, imageX, imageY, current.getColor());
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
                    imageX = 755 + 54 * (3 / 2 * current.getY());
                    imageY = (int) (960 + 33 * (Math.sqrt(3 / 2) * current.getY() + Math.sqrt(3) * current.getX()) + current.getX() * 7);

                    image = associeImageHex(current);
                    String[] nomImage = {"mage.png", "archer.png", "priest.png", "dragon.png", "infanterie.png", "infanterieLourde.png", "chevalier.png"};
                    String nom = nomImage[(int) (Math.random() * (7 - 0))];
                    File imagePerso = new File("images" + sep + "Unite" + sep + "TailleMap" + sep + nom);
                    this.afficheImage(g, image, imageX, imageY, current.getColor());
                    //g.drawImage(ImageIO.read(imagePerso), imageX+13, imageY+10, this);
                }
            }
        }
    }

    public File associeImageHex(Object hex) {
        String chemin = "images" + sep + "Terrain" + sep + "TailleMap" + sep;
        File image = null;

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
        } else {
            chemin += "plaine.png";
        }

        image = new File(chemin);
        
        return image;
    }

    public void afficheImage(Graphics g, File image, int imageX, int imageY, Color color) {
        try {
            g.drawImage(ImageIO.read(image), imageX, imageY,color, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajout de la Map au controleur
     */
    public void setMapListener() {
        PanelCarteListener.setListenedHexMap(map); 
    }
}
