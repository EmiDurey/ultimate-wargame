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
import model.HexMap;
import model.Joueur;

/**
 *  Class PanelAccueil.
 */
public class PanelAccueil extends JPanel implements ActionListener {

	/**
	 * Fenêtre principale.
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

		conteneur = new JPanel();
		conteneur.setBackground(new Color(0, 0, 0, 70));
		conteneur.setPreferredSize(new Dimension(500, 570));
	    conteneur.setBorder(BorderFactory.createLoweredBevelBorder());

		// création d'une box à gestion verticale
	    this.boxVerticale = Box.createVerticalBox();
	    this.boxVerticale.add(Box.createRigidArea(new Dimension(0, 10)));
	    //this.boxVerticale.setOpaque(true);

        // création des boxs à gestion horizontale

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
	    this.boxVerticale.add(Box.createRigidArea(new Dimension(0, 50)));
	    this.ajouterPlusMoins(this.labelNbIA, "IA"); // saisie du nombre d'IA

	    this.boxVerticale.add(Box.createRigidArea(new Dimension(0, 80)));
	    this.ajouterBouton(this.boxVerticale, "Jouer", ""); // bouton Jouer
	    this.boxVerticale.add(Box.createRigidArea(new Dimension(0, 80)));
	    this.ajouterBouton(this.boxVerticale, "Aide", ""); // bouton pour le menu Aide

	    conteneur.add(this.boxVerticale);

	    contraint.gridx = 0;
		contraint.gridy = 1;
	    this.add(conteneur, contraint);
	}

	/**
	 *  Ajoute la zone de saisie du nombre d'équipes.
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
		panel.setBackground(new Color(240,255,255));
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
	 *  Crée une nouvelle police.
	 *  @return Font
	 */
	public Font creerPolice() {
		Font police = null;
	    try {
	    	police = Font.createFont(Font.TRUETYPE_FONT, new File("polices\\Fancy_Card.ttf")).deriveFont(200f);
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
	 *  Crée la liste des joueurs.
	 *  @return ArrayList<Joueur>
	 */
	public ArrayList<Joueur> creerListeJoueurs() {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		for (int i=1; i<=this.nbJoueurs; i++) {
			joueurs.add(new Joueur(i));
		}
		for (int i=this.nbJoueurs+1; i<=this.nbIA; i++) {
			Joueur ia = new Joueur(i);
			ia.setIA(true);
			joueurs.add(ia);
		}
		return joueurs;
	}

	/**
	 *  Permet le traitement des évènements.
	 *  @param evt évènement
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
			System.out.println("aide");
			//AutoCloseDialog.showAutoCloseDialog(this.fenetre, "title", "message to display", 1000L);
			new AutoCloseDialog(this.fenetre, "title", "message to display", 1000L);
		}

		if (actionCommand.equals("Jouer")) {
			if (totalEquipe < 2) {
				String contenu = "Pour pouvoir jouer, un minimum de 2 équipes est requis.";
		    	JOptionPane.showMessageDialog(this.fenetre, contenu, "Erreur", JOptionPane.ERROR_MESSAGE);
			} else {
				this.fenetre.getContentPane().removeAll();
				this.fenetre.setLayout(new BorderLayout());
				this.fenetre.setBarreMenu(new BarreMenu(this.fenetre));
				
				GameController controleur = new GameController(this.creerListeJoueurs());
				HexMap map = controleur.getMap();
				
				this.fenetre.setPanelCarte(new PanelCarte(totalEquipe, map));
				this.fenetre.setPanelInformations(new PanelInformations(totalEquipe, map));
				this.fenetre.getContentPane().add(this.fenetre.getPanelCarte(), BorderLayout.WEST);
				this.fenetre.getContentPane().add(this.fenetre.getPanelInformations(), BorderLayout.EAST);
			}
		}
		this.fenetre.setVisible(true);
	}
}
