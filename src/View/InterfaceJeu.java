package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *  Class InterfaceJeu.
 */
public class InterfaceJeu extends JFrame {

	/**
	 * Longueur de la fenêtre.
	 */
	private final int longueur = 1700;

	/**
	 * Largeur de la fenêtre.
	 */
	private final int largeur = 1000;

	/**
	 * Image de fond.
	 */
	private JLabel fond;

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
		this.barreMenu = null;

		this.setSize(longueur, largeur);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.setContentPane(this.creerFond());
		this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
	    this.getContentPane().add(new PanelAccueil(this));

	    this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

	/**
	 *  Point d'entrée du programme.
	 *  @param args argument
	 *  @see InterfaceJeu
	 */
	public static void main(String[] args) {
		new InterfaceJeu("Wargame");
	}
}
