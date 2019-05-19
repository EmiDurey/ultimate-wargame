package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	 * Menu.
	 */
	private JMenu menu;
	
	/**
	 *  Construit un objet de type BarreMenu.
	 */
	public BarreMenu() {
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
		String chemin = "images\\JMenuBar\\";
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
}
