package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.HexMap;

public class PanelPartie extends JPanel {

	private InterfaceJeu fenetre;
	private int totalEquipe;
	private HexMap map;
	
	public PanelPartie(InterfaceJeu fenetre, int totalEquipe) {
		this.fenetre = fenetre;
		this.totalEquipe = totalEquipe;
		
		this.map = new HexMap();
	    this.creeMap();
	    this.map.populate();
	    //this.map.ASCIIDisplay();
	    
	    this.fenetre.add(new PanelCarte(totalEquipe, this.map), BorderLayout.WEST);
		this.fenetre.add(new PanelInformations(totalEquipe, this.map), BorderLayout.EAST);
	}
	
    public void creeMap() {
    	if(this.totalEquipe == 3) {
    		this.map.setTriangleMap(13);
		}
		else if(this.totalEquipe == 2 || this.totalEquipe == 4) {
			this.map.setRectangleMap(12, 18);
		}
		else { 
			this.map.setHexagonMap(15);
		}
    }
}
