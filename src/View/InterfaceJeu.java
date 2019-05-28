package view;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *  Class InterfaceJeu.
 */
public class InterfaceJeu extends JFrame {

	/**
	 * Longueur de la fen�tre.
	 */
	private final int longueur = 1700;

	/**
	 * Largeur de la fen�tre.
	 */
	private final int largeur = 1000;

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
	 *  @param titre titre de l'InterfaceJeu
	 */
	public InterfaceJeu(String titre) {
		super(titre);
		this.panelCarte = new PanelCarte();
		this.panelAccueil = new PanelAccueil(this);
		this.panelInformations = new PanelInformations();
		this.barreMenu = null;

		this.setSize(longueur, largeur);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.setContentPane(this.creerFond());
		this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
	    this.getContentPane().add(this.panelAccueil);

	    this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 *  Retourne le panel de la carte.
	 *  @return PanelCarte
	 */
	public PanelCarte getPanelCarte() {
		return this.panelCarte;
	}

	/**
	 *  Retourne le panel des informations.
	 *  @return PanelInformations
	 */
	public PanelInformations getPanelInformations() {
		return this.panelInformations;
	}

	/**
	 *  Modifie la barre de menu.
	 *  @param barreMenu BarreMenu
	 */
	public void setBarreMenu(BarreMenu barreMenu) {
		this.barreMenu = barreMenu;
		this.setJMenuBar(this.barreMenu);
	}

	/**
	 *  Cr�e l'image de fond.
	 *  @return JLabel
	 */
	public JLabel creerFond() {
		this.fond = new JLabel(new ImageIcon("images\\carte.png")) {
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
