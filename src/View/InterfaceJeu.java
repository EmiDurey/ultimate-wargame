package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *  Class InterfaceJeu.
 */
public class InterfaceJeu extends JFrame implements ActionListener {

	/**
	 * Longueur de la fenêtre.
	 */
	private final int longueur = 1700;

	/**
	 * Largeur de la fenêtre.
	 */
	private final int largeur = 1000;

	/**
	 *  Construit un objet de type InterfaceJeu.
	 *  @param titre titre de l'InterfaceJeu
	 *  @see Carte
	 *  @see InformationJeu
	 */
	public InterfaceJeu(String titre) {
		super(titre);
		this.setSize(longueur, largeur);
		this.setJMenuBar(this.createMenuBar());
	    
		Carte carte = new Carte();
		this.add(carte, BorderLayout.WEST);
	    
	    InformationJeu information = new InformationJeu();
	    this.add(information, BorderLayout.EAST);
	    
	    this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 *  Crée la barre de menu.
	 *  @return JMenuBar
	 */
	private JMenuBar createMenuBar() {

		String chemin = "images\\JMenuBar\\";

		JMenuBar menuBar = new JMenuBar();

        JMenu menuFichier = new JMenu("Fichier");

        JMenuItem nouvellePartie = new JMenuItem("Nouvelle partie");
        nouvellePartie.setIcon(new ImageIcon(chemin + "new.png"));
		nouvellePartie.addActionListener((ActionListener) this);
		nouvellePartie.setActionCommand("Nouvelle partie");
		menuFichier.add(nouvellePartie);

		menuFichier.addSeparator();

        JMenuItem ouvrir = new JMenuItem("Ouvrir sauvegarde");
        ouvrir.setIcon(new ImageIcon(chemin + "open.png"));
        ouvrir.addActionListener((ActionListener) this);
        ouvrir.setActionCommand("Ouvrir sauvegarde");
        menuFichier.add(ouvrir);

        JMenuItem sauvegarder = new JMenuItem("Sauvegarder");
        sauvegarder.setIcon(new ImageIcon(chemin + "save.png"));
        sauvegarder.addActionListener((ActionListener) this);
        sauvegarder.setActionCommand("Sauvegarde");
        menuFichier.add(sauvegarder);

        menuFichier.addSeparator();

        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.setIcon(new ImageIcon(chemin + "exit.png"));
        quitter.addActionListener((ActionListener) this);
        quitter.setActionCommand("Quitter");
        menuFichier.add(quitter);

        menuBar.add(menuFichier);

        JMenu menuAide = new JMenu("Aide");

        JMenuItem regles = new JMenuItem("Règles");
        regles.setIcon(new ImageIcon(chemin + "rules.png"));
		regles.addActionListener((ActionListener) this);
		regles.setActionCommand("Règles");
		menuAide.add(regles);

		JMenuItem aide = new JMenuItem("Aide");
		aide.setIcon(new ImageIcon(chemin + "help.png"));
		aide.addActionListener((ActionListener) this);
		aide.setActionCommand("Aide");
		menuAide.add(aide);

        menuBar.add(menuAide);

        return menuBar;
	}

	/**
	 *  Permet le traitement des évènements.
	 *  @param evt évènement
	 */
	public void actionPerformed(ActionEvent evt) {

		String actionCommand = evt.getActionCommand();

		switch (actionCommand) {
			case "Nouvelle partie":
				System.out.println("Nouvelle partie");
				break;
			case "Ouvrir sauvegarde":
				System.out.println("Ouvrir sauvegarde");
				break;
			case "Sauvegarder":
				System.out.println("Sauvegarder");
				break;
			case "Règles":
				System.out.println("Règles");
				break;
			case "Aide":
				System.out.println("Aide");
				break;
			case "Quitter":
				int reponse = JOptionPane.showConfirmDialog(this,  "Etes-vous sûr de vouloir quitter le jeu ?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
	        	switch (reponse) {
	        		case JOptionPane.CLOSED_OPTION: break;
	        		case JOptionPane.CANCEL_OPTION: break;
	        		case JOptionPane.OK_OPTION:
	        			System.exit(0);
	        			break;
	        	}
		}
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
