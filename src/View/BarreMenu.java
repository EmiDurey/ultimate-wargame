package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *  Class BarreMenu.
 */
public class BarreMenu extends JMenuBar implements ActionListener {

	/**
	 * Fenetre.
	 */
	private InterfaceJeu fenetre;

	/**
	 * Menu.
	 */
	private JMenu menu;

	/**
	 *  Construit un objet de type BarreMenu.
	 *  @param fenetre InterfaceJeu
	 */
	public BarreMenu(InterfaceJeu fenetre) {
		this.fenetre = fenetre;
		initComposant();
	}

	/**
	 *  Initialise les composants graphiques.
	 */
	public void initComposant() {
        // Menu "Fichier"
        this.menu = new JMenu("Fichier");
        // Sous_menu "Nouvelle partie"
        this.ajouterMenuItem("Nouvelle partie", "nouveau.png");
        this.menu.addSeparator();
		// Sous_menu "Ouvrir sauvegarde"
        this.ajouterMenuItem("Ouvrir sauvegarde", "ouvrir.png");
        // Sous_menu "Sauvegarder"
		this.ajouterMenuItem("Sauvegarde", "sauvegarder.png");
		this.menu.addSeparator();
        // Sous_menu "Quitter"
		this.ajouterMenuItem("Quitter", "quitter.png");

        this.add(this.menu);

        // Menu "Aide"
        this.menu = new JMenu("Aide");
        // Sous_menu "Règles"
        this.ajouterMenuItem("Règles", "regles.png");
		// Sous_menu "Aide"
		this.ajouterMenuItem("Aide", "aide.png");

        this.add(this.menu);
	}

	/**
	 *  Ajoute un nouveau item de menu.
	 *  @param nom string
	 *  @param nomFichier string
	 */
	public void ajouterMenuItem(String nom, String nomFichier) {
		String chemin = "images" + File.separator + "JMenuBar" + File.separator;
		JMenuItem menuItem;

		menuItem = new JMenuItem(nom);
		menuItem.setIcon(new ImageIcon(chemin + nomFichier));
		menuItem.addActionListener((ActionListener) this);
		menuItem.setActionCommand(nom);
		this.menu.add(menuItem);
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
				String contenu, entete;
				contenu = "Etes-vous sûr de vouloir quitter le jeu ?";
				entete = "Confirmation";
	        	switch (JOptionPane.showConfirmDialog(this.fenetre,  contenu, entete, JOptionPane.OK_CANCEL_OPTION)) {
	        		case JOptionPane.CLOSED_OPTION: break;
	        		case JOptionPane.CANCEL_OPTION: break;
	        		case JOptionPane.OK_OPTION:
	        			System.exit(0);
	        			break;
	        		default :
	    	        	break;
	        	}
	        default :
	        	break;
		}
	}
}
