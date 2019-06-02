package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import model.HexMap;

/**
 *  Class PanelInformations.
 */
public class PanelInformations extends JPanel implements ActionListener {

	/**
	 * Panel de l'image du perosnnage.
	 */
	private JPanel panelImagePerso;

	/**
	 * Panel de l'aper�u de la carte.
	 */
	private JPanel panelApercu;

	/**
	 * Nombre total d'�quipes.
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
		texte = "Aper�u de la carte";
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
		JLabel label = new JLabel("PV : ");
		label.setFont(new Font("Arial", Font.BOLD, 30));
		label.setForeground(Color.white);
		panel.add(label);
		contrainte.gridx = 1;
		contrainte.gridy = 1;
		this.add(panel, contrainte);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 100));
		panel.setBackground(this.couleurFond);
		border = BorderFactory.createEtchedBorder(Color.WHITE, Color.BLACK);
		texte = "Type du personnage + carat�ristiques";
		border = BorderFactory.createTitledBorder(border, texte, TitledBorder.LEFT, TitledBorder.TOP, font, Color.WHITE);
		panel.setBorder(border);
		contrainte.gridx = 0;
		contrainte.gridy = 2;
		contrainte.gridwidth = 3;
		contrainte.insets = new Insets(30, 0, 0, 0);
		this.add(panel, contrainte);

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
		String sep = File.separator;
	JLabel imagePerso = new JLabel(new ImageIcon("images" + sep + "Unite" + sep + "Info" + sep + "archer.png"));

		this.panelImagePerso.setLayout(new BorderLayout());
		this.panelImagePerso.add(imagePerso, BorderLayout.CENTER);
	}

	/**
	 * Affiche l'aper�u de la carte
	 */
	public void afficheApercu() {
		PanelDessineurApercu dessinCarte;

        dessinCarte = new PanelDessineurApercu(this.totalEquipe, this.map);
        dessinCarte.setPreferredSize(new Dimension(530, 280));
        dessinCarte.setBackground(this.couleurFond);

        this.panelApercu.add(dessinCarte);
	}

	/**
	 *  Permet le traitement des �v�nements.
	 *  @param evt �v�nement
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
