package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.GameController;
import controller.Sauvegarde;
import model.HexMap;
import model.Joueur;

/**
 *  Class PanelAccueil.
 *  @see InterfaceJeu
 *  @see PanelCarte
 *  @see PanelInformations
 *  @see BarreMenu
 *  @see controller.GameController
 *  @see model.HexMap
 *  @see model.Joueur
 */
public class PanelAccueil extends JPanel implements ActionListener {

	/**
	 * Fen�tre principale.
	 */
	private InterfaceJeu fenetre;

	/**
	 * Box contenant les composants graphiques.
	 */
	private Box boxVerticale;

	/**
	 * Labels du nombre de joueurs et d'IA.
	 */
	private JLabel labelNbJoueur, labelNbIA;

	/**
	 * Nombre de joueurs et d'IA.
	 */
	private int nbJoueurs, nbIA;

	/**
	 * Couleurs du texte et des composants graphiques.
	 */
	private Color colorTexte, colorComp;

	/**
	 *  Construit un objet de type PanelAccueil.
	 *  @param fenetre InterfaceJeu
	 *  @see InterfaceJeu
	 */
	public PanelAccueil(InterfaceJeu fenetre) {
		this.fenetre = fenetre;
		this.fenetre.setJMenuBar(null);
		this.colorTexte = new Color(253, 241, 184);
		this.colorComp = new Color(48, 48, 48);
		this.setOpaque(false);
	    this.setVisible(true);

	    GridBagLayout layout = new GridBagLayout();
	    this.setLayout(layout);

		initComposant();
	}

	/**
	 *  Initialise les composants graphiques.
	 */
	public void initComposant() {
		Box boxHorizontal;
		JLabel texte;
		JPanel conteneur;
		GridBagConstraints contraint = new GridBagConstraints();
		Dimension rigidAreaPlusMoins, rigidAreaBouton;

		if (Sauvegarde.existe()) {
	    	rigidAreaPlusMoins = new Dimension(0, 30);
	    	rigidAreaBouton = new Dimension(0, 50);
	    } else {
	    	rigidAreaPlusMoins = new Dimension(0, 40);
	    	rigidAreaBouton = new Dimension(0, 80);
	    }

		conteneur = new JPanel();
		conteneur.setBackground(new Color(0, 0, 0, 70));
		conteneur.setPreferredSize(new Dimension(500, 570));
	    conteneur.setBorder(BorderFactory.createLoweredBevelBorder());

		// cr�ation d'une box � gestion verticale
	    this.boxVerticale = Box.createVerticalBox();
	    this.boxVerticale.add(Box.createRigidArea(new Dimension(0, 10)));
	    //this.boxVerticale.setOpaque(true);

        // cr�ation des boxs � gestion horizontale

	    // titre
	    boxHorizontal = Box.createHorizontalBox();
	    texte = new JLabel("Wargame");
	    texte.setFont(this.creerPolice());
	    texte.setForeground(Color.BLACK);
	    boxHorizontal.add(texte);
	    this.boxVerticale.add(boxHorizontal);
	    contraint.gridx = 0;
		contraint.gridy = 0;
	    this.add(this.boxVerticale, contraint);

	    this.boxVerticale = Box.createVerticalBox();

	    this.labelNbJoueur = new JLabel("0");
	    this.labelNbIA = new JLabel("0");

	    this.ajouterPlusMoins(this.labelNbJoueur, "Joueurs"); // saisie du nombre de joueurs
	    this.boxVerticale.add(Box.createRigidArea(rigidAreaPlusMoins));
	    this.ajouterPlusMoins(this.labelNbIA, "IA"); // saisie du nombre d'IA

	    this.boxVerticale.add(Box.createRigidArea(rigidAreaBouton));
	    this.ajouterBouton(this.boxVerticale, "Jouer", ""); // bouton Jouer

	    if (Sauvegarde.existe()) {
	    	this.boxVerticale.add(Box.createRigidArea(rigidAreaBouton));
	    	this.ajouterBouton(this.boxVerticale, "Reprendre partie", ""); // bouton reprendre partie
	    }

	    this.boxVerticale.add(Box.createRigidArea(rigidAreaBouton));
	    this.ajouterBouton(this.boxVerticale, "Aide", ""); // bouton pour le menu Aide

	    conteneur.add(this.boxVerticale);

	    contraint.gridx = 0;
		contraint.gridy = 1;
	    this.add(conteneur, contraint);
	}

	/**
	 *  Ajoute la zone de saisie du nombre d'�quipes.
	 *  @param labelNb jlabel
	 *  @param type string
	 */
	public void ajouterPlusMoins(JLabel labelNb, String type) {
		Box box;
		JLabel texte;
		JPanel panel;

		box = Box.createHorizontalBox();
	    texte = new JLabel(type);
	    texte.setForeground(this.colorTexte);
	    texte.setFont(new Font("Arial", Font.BOLD, 35));
	    box.add(texte);
	    box.add(Box.createRigidArea(new Dimension(0, 70)));
	    this.boxVerticale.add(box);

	    box = Box.createHorizontalBox();
		this.ajouterBouton(box, "-", type);
		box.add(Box.createRigidArea(new Dimension(30, 0)));

		panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		panel.setPreferredSize(new Dimension(70, 20));
		panel.setBackground(new Color(240, 255, 255));
		labelNb.setFont(new Font("Arial", Font.BOLD, 30));
		labelNb.setForeground(this.colorTexte);
		panel.setBackground(this.colorComp);
		panel.add(labelNb);
		box.add(panel);
		box.add(Box.createRigidArea(new Dimension(30, 0)));

		this.ajouterBouton(box, "+", type);

		this.boxVerticale.add(box);
	}

	/**
	 *  Ajoute un bouton.
	 *  @param conteneur box
	 *  @param contenu string
	 *  @param type string
	 */
	public void ajouterBouton(Box conteneur, String contenu, String type) {
	    Box box = Box.createHorizontalBox();
	    JButton bouton = new JButton(contenu);

		bouton.setFont(new Font("Arial", Font.BOLD, 30));
		bouton.setForeground(this.colorTexte);
	    bouton.setBackground(this.colorComp);
		bouton.setFocusPainted(false);
		bouton.addActionListener((ActionListener) this);
		bouton.setActionCommand(contenu + type);
		box.add(bouton);
		conteneur.add(box);
	}

	/**
	 *  Cr�e une nouvelle police.
	 *  @return Font
	 */
	public Font creerPolice() {
		Font police = null;
	    try {
	    	String chemin = "polices" + File.separator + "Fancy_Card.ttf";
	    	police = Font.createFont(Font.TRUETYPE_FONT, new File(chemin)).deriveFont(200f);
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(police);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (FontFormatException e) {
	    	e.printStackTrace();
	    }
	    return police;
	}

	/**
	 *  Cr�e la liste des joueurs.
	 *  @return ArrayList<Joueur>
	 *  @see model.Joueur
	 */
	public ArrayList<Joueur> creerListeJoueurs() {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		for (int i = 1; i <= this.nbJoueurs; i++) {
			joueurs.add(new Joueur(i));
		}
		for (int i = 1; i <= this.nbIA; i++) {
			Joueur ia = new Joueur(this.nbJoueurs + i);
			ia.setIA(true);
			joueurs.add(ia);
		}

		return joueurs;
	}

	/**
	 *  Affiche l'interface de jeu.
	 *  @param totalEquipe int
	 *  @param map HexMap
	 *  @param controleur GameController
	 *  @see model.HexMap
	 *  @see controller.GameController
	 *  @see PanelCarte
	 *  @see PanelInformations
	 *  @see BarreMenu
	 */
	public void afficheJeu(int totalEquipe, HexMap map, GameController controleur) {
		this.fenetre.getContentPane().removeAll();
		this.fenetre.setLayout(new BorderLayout());
		PanelCarte panelCarte = new PanelCarte(this.fenetre, totalEquipe, map, controleur);
		PanelInformations panelInfo = new PanelInformations(this.fenetre, totalEquipe, map, controleur);
		this.fenetre.setBarreMenu(new BarreMenu(this.fenetre, panelCarte, panelInfo, controleur));

		this.fenetre.setPanelCarte(panelCarte);
		this.fenetre.setPanelInformations(panelInfo);
		this.fenetre.getContentPane().add(this.fenetre.getPanelCarte(), BorderLayout.WEST);
		this.fenetre.getContentPane().add(this.fenetre.getPanelInformations(), BorderLayout.EAST);
	}

	/**
	 *  Permet le traitement des �v�nements.
	 *  @param evt �v�nement
	 *  @see model.HexMap
	 *  @see controller.GameController
	 */
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();
		final int equipeMax = 6;
		int totalEquipe;
		totalEquipe = this.nbJoueurs + this.nbIA;

		if (actionCommand.contains("Joueurs")) {
			if (actionCommand.equals("-Joueurs") && this.nbJoueurs > 0) {
				this.nbJoueurs--;
			} else if (actionCommand.equals("+Joueurs") && totalEquipe < equipeMax && this.nbJoueurs < equipeMax) {
				this.nbJoueurs++;
			}
			this.labelNbJoueur.setText(Integer.toString(this.nbJoueurs));
		}

		if (actionCommand.contains("IA")) {
			if (actionCommand.equals("-IA") && this.nbIA > 0) {
				this.nbIA--;
			} else if (actionCommand.equals("+IA") && totalEquipe < equipeMax && this.nbIA < equipeMax) {
				this.nbIA++;
			}
			this.labelNbIA.setText(Integer.toString(this.nbIA));
		}

		if (actionCommand.equals("Aide")) {
			OptionPaneAide.afficheAide(this.fenetre);
		}

		if (actionCommand.equals("Reprendre partie")) {
			GameController controleur = (GameController) Sauvegarde.lecture();
			HexMap map = controleur.getMap();
			totalEquipe = controleur.getJoueurs().size();
			this.afficheJeu(totalEquipe, map, controleur);
		}

		if (actionCommand.equals("Jouer")) {
			if (totalEquipe < 2) {
				String contenu = "Pour pouvoir jouer, un minimum de 2 �quipes est requis.";
		    	JOptionPane.showMessageDialog(this.fenetre, contenu, "Erreur", JOptionPane.ERROR_MESSAGE);
			} else {
				GameController controleur = new GameController(this.creerListeJoueurs());
				HexMap map = controleur.getMap();
				this.afficheJeu(totalEquipe, map, controleur);
			}
		}
		this.fenetre.setVisible(true);
	}
}
