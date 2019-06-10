package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *  Class InterfaceJeu.
 *  @see PanelAccueil
 *  @see PanelCarte
 *  @see PanelInformations
 *  @see BarreMenu
 */
public class InterfaceJeu extends JFrame {

	/**
	 * Longueur de la fenêtre.
	 */
	private final int longueur = 1700;

	/**
	 * Largeur de la fenêtre.
	 */
	private final int largeur = 1010;

	/**
	 * Image de fond.
	 */
	private JLabel fond;

	/**
	 * Panel d'accueil.
	 */
	private PanelAccueil panelAccueil;

	/**
	 * Panel de la carte.
	 */
	private PanelCarte panelCarte;

	/**
	 * Panel des informaitons du jeu.
	 */
	private PanelInformations panelInformations;

	/**
	 * Barre de menu.
	 */
	private BarreMenu barreMenu;

	/**
	 *  Construit un objet de type InterfaceJeu.
	 *  @param titre String
	 */
	public InterfaceJeu(String titre) {
		super(titre);
		this.barreMenu = null;
		this.panelCarte = null;
		this.panelInformations = null;
		this.panelAccueil = null;

		this.setSize(longueur, largeur);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.initComposant();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 *  Initialise les composants graphiques.
	 *  @see PanelAccueil
	 */
	public void initComposant() {
		this.panelAccueil = new PanelAccueil(this);

		this.setContentPane(this.creerFond());
		this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
 	    this.getContentPane().add(this.panelAccueil);
	    this.setVisible(true);
	}

	/**
	 *  Retourne la longueur de la fenêtre.
	 *  @return int
	 */
	public int getLongueur() {
		return this.longueur;
	}

	/**
	 *  Retourne la largeur de la fenêtre.
	 *  @return int
	 */
	public int getLargeur() {
		return this.largeur;
	}

	/**
	 *  Retourne le panel accueil.
	 *  @return PanelAccueil
	 *  @see PanelAccueil
	 */
	public PanelAccueil getPanelAccueil() {
		return this.panelAccueil;
	}

	/**
	 *  Retourne le panel de la carte.
	 *  @return PanelCarte
	 *  @see PanelCarte
	 */
	public PanelCarte getPanelCarte() {
		return this.panelCarte;
	}

	/**
	 *  Retourne le panel des informations.
	 *  @return PanelInformations
	 *  @see PanelInformations
	 */
	public PanelInformations getPanelInformations() {
		return this.panelInformations;
	}

	/**
	 *  Modifie le panel accueil.
	 *  @param panel PanelAccueil
	 *  @see PanelAccueil
	 */
	public void setPanelAccueil(PanelAccueil panel) {
		this.panelAccueil = panel;
	}

	/**
	 *  Modifie le panel de la carte.
	 *  @param panel PanelCarte
	 *  @see PanelCarte
	 */
	public void setPanelCarte(PanelCarte panel) {
		this.panelCarte = panel;
	}

	/**
	 *  Modifie le panel des informations.
	 *  @param panel PanelInformations
	 *  @see PanelInformations
	 */
	public void setPanelInformations(PanelInformations panel) {
		this.panelInformations = panel;
	}

	/**
	 *  Modifie la barre de menu.
	 *  @param barreMenu BarreMenu
	 *  @see BarreMenu
	 */
	public void setBarreMenu(BarreMenu barreMenu) {
		this.barreMenu = barreMenu;
		this.setJMenuBar(this.barreMenu);
	}

	/**
	 *  Crée l'image de fond.
	 *  @return JLabel
	 */
	public JLabel creerFond() {
		this.fond = new JLabel(new ImageIcon("images" + File.separator + "carte.png")) {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (getIcon() != null) {
					g.drawImage(((ImageIcon) getIcon()).getImage(), 0, 0, getWidth(), getHeight(), null);
				}
			}
		};
		return fond;
	}
}
