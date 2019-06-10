package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.HexMap;
import controller.GameController;

/**
 * Class PanelCarte.
 *  @see InterfaceJeu
 *  @see PanelDessineurMap
 *  @see controller.GameController
 *  @see model.HexMap
 *  @see AutoCloseDialog
 */
public class PanelCarte extends JPanel implements MouseListener {

	/**
	 * Fenêtre de l'application.
	 */
	private InterfaceJeu fenetre;

	/**
	 * Dimension du PanelDessineurMap.
	 */
	private Dimension dim;

	/**
	 * PanelDedsineurMap.
	 */
    private PanelDessineurMap dessinCarte;

    /**
     * Nombre total d'équipes.
     */
    private int totalEquipe;

    /**
     * Map du jeu.
     */
	private HexMap map;

	/**
	 * Scroll de la map.
	 */
	private JScrollPane scroll;

	/**
	 * Contrôleur.
	 */
	private GameController controller;

	/**
	 * Construit un objet de type PanelCarte.
	 * @param fenetre InterfaceJeu
	 * @param totalEquipe int
	 * @param map HexMap
	 * @param controller GameController
	 * @see InterfaceJeu
	 * @see GameController
	 * @see model.HexMap
	 */
	public PanelCarte(InterfaceJeu fenetre, int totalEquipe, HexMap map, GameController controller) {
		this.fenetre = fenetre;
		this.totalEquipe = totalEquipe;
		this.map = map;
		this.controller = controller;
	    this.setPreferredSize(new Dimension(1095, 0));
        this.setBackground(new Color(48, 48, 48));
	    this.setVisible(true);

	    this.dim = new Dimension(0, 0);

        this.dessinCarte = new PanelDessineurMap(totalEquipe, this.map, this.controller);
        this.dessinCarte.setBackground(new Color(48, 48, 48));

        this.scroll = new JScrollPane(this.dessinCarte);

        this.scroll.setPreferredSize(new Dimension(1095, 935));
        this.scroll.getVerticalScrollBar().setPreferredSize (new Dimension(5, 0));
        this.scroll.getHorizontalScrollBar().setPreferredSize (new Dimension(0, 5));
        this.scroll.setWheelScrollingEnabled(false);
        this.scroll.addMouseListener(this);

        if (this.totalEquipe > 4) {
	        this.ajoutScrollHex();
        }

        this.add(this.scroll);

		try {
			controller.changeTour();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le scroll de la map.
	 * @return JScrollPane
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}

	/**
	 * Retourne le dessinateur de map.
	 * @return PanelDessineurMap
	 * @see PanelDessineurMap
	 */
	public PanelDessineurMap getDessinCarte() {
		return this.dessinCarte;
	}

	/**
	 * Ajoute le scroll à la map hexagonale.
	 */
	public void ajoutScrollHex() {
		final int W = 585;
		final int H = 860;
		boolean changed = true;

		int x = 1080;
		int y = 1030;
		Rectangle rect = new Rectangle(W, H);
		this.dessinCarte.scrollRectToVisible(rect);

		int thisWidth = (x + W);
		if (thisWidth > this.dim.width) {
			this.dim.width = thisWidth;
		    changed = true;
		}

		int thisHeight = (y + H);
		if (thisHeight > this.dim.height) {
			this.dim.height = thisHeight;
		    changed = true;
		}

		if (changed) {
		    this.dessinCarte.setPreferredSize(this.dim);
		    this.dessinCarte.revalidate();
		}
		this.dessinCarte.repaint();
	}

	/**
	 * Ecoute les cliques de la souris et agit en conséquence.
	 * @param event MouseEvent
	 * @see AutoCloseDialog
	 */
	public void mouseClicked(MouseEvent event) {

		Point clik = event.getPoint().getLocation();
		final int W = 585;
		final int H = 860;

		int x = (int) clik.getX() + this.scroll.getHorizontalScrollBar().getValue();
		int y = (int) clik.getY() + this.scroll.getVerticalScrollBar().getValue();


		controller.handleMove(x, y);
		this.dessinCarte.repaint();
		this.fenetre.getPanelInformations().affichePerso();
		this.fenetre.getPanelInformations().affichePV();
		this.fenetre.getPanelInformations().afficheCaracteristiques();

		ArrayList<String> annonces = (ArrayList<String>) controller.getAnnonce();

		String annonce = null;
		Iterator<String> iteratorAnnonce = null;

		if (!(annonces.isEmpty())) {
			iteratorAnnonce = annonces.iterator();
			while (iteratorAnnonce.hasNext()) {
				annonce = iteratorAnnonce.next();
				new AutoCloseDialog(this.fenetre, x, y, annonce);
			}
		}

		if (controller.getFin()) {
			String contenu = "Le joueur ";
			contenu += controller.getJoueurs().get(0).getID() + " ";
			contenu += controller.getJoueurs().get(0).getCouleur() + " gagne la partie !";
			contenu += "\nVoulez-vous recommencer une partie ?";

	    	int reponse = JOptionPane.showConfirmDialog(this.fenetre, contenu, "Fin de la partie", JOptionPane.YES_NO_OPTION);
			switch (reponse) {
				case JOptionPane.CLOSED_OPTION:
					System.exit(0);
					break;
				case JOptionPane.CANCEL_OPTION:
					System.exit(0);
					break;
				case JOptionPane.OK_OPTION:
					this.fenetre.getContentPane().removeAll();
					this.fenetre.initComposant();
					break;
				default :
		        	break;
			}
		}

		//Rectangle rect = new Rectangle(x, y, W, H);

		//this.dessinCarte.scrollRectToVisible(rect);

		//this.dessinCarte.repaint();
	}

	/**
	 * Ecoute lorsque la souris a été cliquée sur un composant.
	 * @param event MouseEvent
	 */
	public void mouseEntered(MouseEvent event) { }

	/**
	 * Ecoute lorsque la souris sort d'un composant.
	 * @param event MouseEvent
	 */
	public void mouseExited(MouseEvent event) { }

	/**
	 * Ecoute lorsque le bouton de la souris a été enfoncé sur un composant.
	 * @param event MouseEvent
	 */
	public void mousePressed(MouseEvent event) { }

	/**
	 * Ecoute lorsque le bouton de la souris a été relâché sur un composant.
	 * @param event MouseEvent
	 */
	public void mouseReleased(MouseEvent event) { }
}
