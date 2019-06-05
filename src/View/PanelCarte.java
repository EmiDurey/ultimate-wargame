package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Hex;
import model.HexMap;
import controller.GameController;

/**
 * Class PanelCarte.
 */
public class PanelCarte extends JPanel implements MouseListener {

	/**
	 * Fenetre.
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
     * Nombre total d'�quipes.
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
	 * Controller.
	 */
	private GameController controller;

	/**
	 * Construit un objet de type Carte.
	 * @param fenetre InterfaceJeu
	 * @param totalEquipe int
	 * @param map HexMap
	 * @param controller GameController
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
        //scroll.getVerticalScrollBar().setUnitIncrement(25);
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
	 * Ajoute le scroll � la map hexagonale.
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
	 * Ecoute les clique de la souris et agit en cons�quence.
	 * @param event MouseEvent
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

		ArrayList<String> annonces = (ArrayList<String>) controller.getAnnonce();
		//ArrayList<Hex> hexAnnonce = (ArrayList<Hex>) controller.getHexAnnonce();

		String annonce = null;
		Iterator<String> iteratorAnnonce = null;
		
		
		if (!(annonces.isEmpty())) {
			iteratorAnnonce = annonces.iterator();
			while (iteratorAnnonce.hasNext()) {
				annonce = iteratorAnnonce.next();
				new AutoCloseDialog(this.fenetre, x, y, annonce);
			}
		}

		//this.dessinCarte.afficheMap(g, 170, 15);

        //this.dessinCarte = new PanelDessineurMap(totalEquipe, this.map, this.controller);
        //add(dessinCarte);

		//Rectangle rect = new Rectangle(x, y, W, H);

		//this.dessinCarte.scrollRectToVisible(rect);

		//this.dessinCarte.repaint();
	}

	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
}
