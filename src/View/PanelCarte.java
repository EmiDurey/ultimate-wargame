package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *  Class PanelCarte.
 */
public class PanelCarte extends JPanel{
	
	/**
	 *  Construit un objet de type Carte.
	 */
	public PanelCarte() {
	    this.setPreferredSize(new Dimension(1095, 0));
	    this.setBackground(new Color(206, 206, 206));
	    this.setVisible(true);
	    
	    Border border = BorderFactory.createEtchedBorder(Color.WHITE, Color.WHITE);
		border = BorderFactory.createTitledBorder(border, "Carte", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 20), Color.WHITE);
		this.setBorder(border);
	}
}
