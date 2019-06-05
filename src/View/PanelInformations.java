package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import controller.GameController;
import model.Archer;
import model.Cavalerie;
import model.Dragon;
import model.HexMap;
import model.Infanterie;
import model.InfanterieLourde;
import model.Mage;
import model.Pretre;
import model.Unite;

/**
 *  Class PanelInformations.
 */
public class PanelInformations extends JPanel implements ActionListener {

	/**
	 * Label contenant les informations du joueur.
	 */
	private JLabel labelCaracteristiques;

	/**
	 * Panel de l'image du perosnnage.
	 */
	private JPanel panelCaracteristiques;

	/**
	 * Label contenant les PV du personnage.
	 */
	private JLabel label_pv;

	/**
	 * Panel de l'image du perosnnage.
	 */
	private JPanel panelImagePerso;

	/**
	 * Panel de l'aperçu de la carte.
	 */
	private JPanel panelApercu;

	/**
	 * Nombre total d'équipes.
	 */
	private int totalEquipe;

	/**
	 * Carte de jeu.
	 */
	private HexMap map;

	/**
	 * Couleur des composants graphiques.
	 */
	private Color couleurFond;

	/**
	 * Controleur de l'application.
	 */
	private GameController controleur;

	/**
	 *  Construit un objet de type PanelInformations.
	 *  @param totalEquipe int
	 *  @param map HexMap
	 *  @param controleur GameController
	 */
	public PanelInformations(int totalEquipe, HexMap map, GameController controleur) {
		this.totalEquipe = totalEquipe;
		this.map = map;
		this.couleurFond = new Color(48, 48, 48);
		this.controleur = controleur;
	    this.setPreferredSize(new Dimension(600, 0));
	    this.setBackground(this.couleurFond);
	    this.setVisible(true);
	    this.initComposant();
	    this.affichePerso();
	    this.afficheApercu();
	}

	/**
	 *  Initialise les composants graphiques.
	 */
	public void initComposant() {
		JPanel panel;
		String texte;
		Font font = new Font("Arial", Font.BOLD, 20);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints contrainte = new GridBagConstraints();
		this.setLayout(layout);

		this.panelApercu = new JPanel();
		this.panelApercu.setPreferredSize(new Dimension(500, 300));
		this.panelApercu.setBackground(this.couleurFond);
		Border border = BorderFactory.createEtchedBorder(Color.WHITE, Color.BLACK);
		texte = "Aperçu de la carte";
		border = BorderFactory.createTitledBorder(border, texte, TitledBorder.LEFT, TitledBorder.TOP, font, Color.WHITE);
		this.panelApercu.setBorder(border);
		contrainte.ipadx = 40;
		contrainte.ipady = 20;
		contrainte.gridx = 0;
		contrainte.gridy = 0;
		contrainte.gridwidth = 3;
		this.add(this.panelApercu, contrainte);

		this.panelImagePerso = new JPanel();
		this.panelImagePerso.setPreferredSize(new Dimension(150, 150));
		this.panelImagePerso.setBackground(this.couleurFond);
		border = BorderFactory.createEtchedBorder(Color.WHITE, Color.BLACK);
		texte = "Image perso";
		border = BorderFactory.createTitledBorder(border, texte, TitledBorder.LEFT, TitledBorder.TOP, font, Color.WHITE);
		this.panelImagePerso.setBorder(border);
		contrainte.gridx = 0;
		contrainte.gridy = 1;
		contrainte.gridwidth = 1;
		contrainte.insets = new Insets(30, 0, 0, 0);
		this.add(this.panelImagePerso, contrainte);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(90, 20));
		panel.setBackground(this.couleurFond);
		this.label_pv = new JLabel();
		this.label_pv.setFont(new Font("Arial", Font.BOLD, 30));
		this.label_pv.setForeground(Color.white);
		panel.add(this.label_pv);
		contrainte.gridx = 1;
		contrainte.gridy = 1;
		this.add(panel, contrainte);

		this.panelCaracteristiques = new JPanel();
		this.panelCaracteristiques.setPreferredSize(new Dimension(500, 100));
		this.panelCaracteristiques.setBackground(this.couleurFond);
		border = BorderFactory.createEtchedBorder(Color.WHITE, Color.BLACK);
		texte = "Carat�ristiques du personnage";
		border = BorderFactory.createTitledBorder(border, texte, TitledBorder.LEFT, TitledBorder.TOP, font, Color.WHITE);
		this.panelCaracteristiques.setBorder(border);
		contrainte.gridx = 0;
		contrainte.gridy = 2;
		contrainte.gridwidth = 3;
		contrainte.insets = new Insets(30, 0, 0, 0);
		this.add(this.panelCaracteristiques, contrainte);

		this.labelCaracteristiques = new JLabel();
		this.labelCaracteristiques.setFont(new Font("Arial", Font.BOLD, 17));
		this.labelCaracteristiques.setForeground(Color.white);
		this.panelCaracteristiques.add(this.labelCaracteristiques);


		JButton boutonFinTour = new JButton("Fin de tour");
		boutonFinTour.setBackground(Color.BLACK);
		boutonFinTour.setFont(new Font("Arial", Font.BOLD, 15));
		boutonFinTour.setForeground(Color.WHITE);
		boutonFinTour.setFocusPainted(false);
		boutonFinTour.addActionListener((ActionListener) this);
		boutonFinTour.setActionCommand("Fin");
		contrainte.gridx = 0;
		contrainte.gridy = 3;
		contrainte.insets = new Insets(200, 0, 0, 0);
		this.add(boutonFinTour, contrainte);
	}

	/**
	 * Affiche l'image du personnage.
	 */
	public void affichePerso() {
		Unite unite = this.controleur.getUniteSelectionne();
		JLabel imagePerso = new JLabel(new ImageIcon(associeImageUnite(unite)));
		this.panelImagePerso.removeAll();
		this.panelImagePerso.setLayout(new BorderLayout());
		this.panelImagePerso.add(imagePerso, BorderLayout.CENTER);
		this.panelImagePerso.repaint();
		this.revalidate();
		this.repaint();
	}

	/**
	 * Affiche PVs du personnage.
	 */
	public void affichePV() {
		this.label_pv.removeAll();
		Unite unite = this.controleur.getUniteSelectionne();
		if (unite != null) {
			this.label_pv.setText("PV : " + unite.getPointsDeVie());
			this.label_pv.repaint();
			this.revalidate();
			this.repaint();
		}
	}

	/**
	 * Affiche caractéristiques du personnage.
	 */
	public void afficheCaracteristiques() {
		this.labelCaracteristiques.removeAll();
		Unite unite = this.controleur.getUniteSelectionne();
		if (unite != null) {
			this.labelCaracteristiques.setText("<html>• Points d'attaque : " + unite.getPointsAttaque()
								+ "<br> • Points de défense : " + unite.getPointsDefense()
								+ "<br> • Points de déplacements : " + unite.getPointsDeplacement()
								+ "<br> • Vision : " + unite.getVision() + "</html>");
			this.panelCaracteristiques.repaint();
			this.revalidate();
			this.repaint();
		}
	}

	 /**
	 * Associe une unité à son image.
	 * @param unite Unite
	 * @return image
	 */
	public String associeImageUnite(Unite unite) {
		String sep = File.separator;
 		String chemin = "images" + sep + "Unite" + sep + "Info" + sep;
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
 		}

 		return chemin;
 	}

	/**
	 * Affiche l'aperçu de la carte
	 */
	public void afficheApercu() {
		PanelDessineurApercu dessinCarte;

        dessinCarte = new PanelDessineurApercu(this.totalEquipe, this.map);
        dessinCarte.setPreferredSize(new Dimension(530, 280));
        dessinCarte.setBackground(this.couleurFond);

        this.panelApercu.add(dessinCarte);
	}

	/**
	 *  Permet le traitement des évènements.
	 *  @param evt évènement
	 */

	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();

		switch (actionCommand) {
			case "Fin":
				System.out.println("Changement de tour");
				try {
					controleur.changeTour();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}
}
