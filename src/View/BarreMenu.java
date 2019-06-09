package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.GameController;
import controller.Sauvegarde;
import model.HexMap;

/**
 *  Class BarreMenu.
 *  @see InterfaceJeu
 *  @see PanelCarte
 *  @see PanelInformations
 *  @see GameController
 *  @see model.HexMap
 */
public class BarreMenu extends JMenuBar implements ActionListener {

	/**
	 * Fenetre.
	 */
	private InterfaceJeu fenetre;

	/**
	 * Panel carte.
	 */
	private PanelCarte panelCarte;

	/**
	 * Panel informations.
	 */
	private PanelInformations panelInfo;

	/**
	 * Contr�leur.
	 */
	private GameController controleur;

	/**
	 * Menu.
	 */
	private JMenu menu;

	/**
	 *  Construit un objet de type BarreMenu.
	 *  @param fenetre InterfaceJeu
	 *  @param panelCarte PanelCarte
	 *  @param panelInfo panelInformations
	 *  @param controleur GameController
	 *  @see InterfaceJeu
	 *  @see PanelCarte
	 *  @see PanelInformations
	 *  @see GameController
	 */
	public BarreMenu(InterfaceJeu fenetre, PanelCarte panelCarte, PanelInformations panelInfo, GameController controleur) {
		this.fenetre = fenetre;
		this.panelCarte = panelCarte;
		this.panelInfo = panelInfo;
		this.controleur = controleur;
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
        if (Sauvegarde.existe()) {
        	// Sous_menu "Ouvrir sauvegarde"
            this.ajouterMenuItem("Ouvrir sauvegarde", "ouvrir.png");
        }
        // Sous_menu "Sauvegarder"
		this.ajouterMenuItem("Sauvegarder", "sauvegarder.png");
		this.menu.addSeparator();
        // Sous_menu "Quitter"
		this.ajouterMenuItem("Quitter", "quitter.png");

        this.add(this.menu);

        // Menu "Aide"
        this.menu = new JMenu("Aide");
        // Sous_menu "R�gles"
        this.ajouterMenuItem("R�gles", "regles.png");
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
	 *  Affiche le jeu.
	 *  @param totalEquipe int
	 *  @param map HexMap
	 *  @param controleur GameController
	 *  @see model.HexMap
	 *  @see PanelCarte
	 *  @see PanelInformations
	 *  @see GameController
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
		this.fenetre.setVisible(true);
	}

	/**
	 *  Permet le traitement des �v�nements.
	 *  @param evt �v�nement
	 *  @see HexMap
	 *  @see GameController
	 */
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();
		String entete, contenu;

		switch (actionCommand) {
			case "Nouvelle partie":
				this.fenetre.getContentPane().removeAll();
				this.fenetre.initComposant();
				break;
			case "Ouvrir sauvegarde":
				File fichier = new File("save" + File.separator + "partie");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
				contenu = "Voulez vous reprendre la partie du " + sdf.format(fichier.lastModified()) + " ?";
				entete = "Confirmation";
	        	switch (JOptionPane.showConfirmDialog(this.fenetre,  contenu, entete, JOptionPane.YES_NO_OPTION)) {
	        		case JOptionPane.CLOSED_OPTION: break;
	        		case JOptionPane.CANCEL_OPTION: break;
	        		case JOptionPane.OK_OPTION:
	        			GameController controleur = (GameController) Sauvegarde.lecture();
	    				HexMap map = controleur.getMap();
	    				int totalEquipe = controleur.getJoueurs().size();
	    				this.afficheJeu(totalEquipe, map, controleur);
	        			break;
	        		default :
	    	        	break;
	        	}
				break;
			case "Sauvegarder":
				Sauvegarde.savePartie(this.controleur);
				contenu = "Votre partie a été sauvegard�e.";
		    	JOptionPane.showMessageDialog(this.fenetre, contenu, "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
				break;
			case "R�gles":
				OptionPaneAide.afficheRegles(this.fenetre);
				break;
			case "Aide":
				OptionPaneAide.afficheAide(this.fenetre);
				break;
			case "Quitter":
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
