package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *  Class InformationJeu.
 */
public class InformationJeu extends JPanel{
	
	/**
	 *  Construit un objet de type InformationJeu.
	 */
	public InformationJeu() {
	    this.setPreferredSize(new Dimension(600, 0));
	    this.setBackground(new Color(206, 206, 206));
	    
	    JPanel apercu = new JPanel();
	    apercu.setPreferredSize(new Dimension(500, 300));
	    apercu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Aperçu de la carte"));
	    this.add(apercu);
	}
}
