package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Eau;
import model.Foret;
import model.Forteresse;
import model.Hex;
import model.HexMap;
import model.Montagne;
import model.Neige;
import model.Plaine;
import view.ScrollDemo2.DrawingPane;

/**
 *  Class PanelCarte.
 */
public class PanelCarte extends JPanel implements MouseListener {

	private Dimension area;
    private PanelDessineur drawingPane;
    
	/**
	 *  Construit un objet de type Carte.
	 */
	public PanelCarte() {
	    this.setPreferredSize(new Dimension(1095, 0));
        this.setBackground(new Color(48, 48, 48));
	    this.setVisible(true);
	    
	    area = new Dimension(0,0);
	    
        drawingPane = new PanelDessineur();
        drawingPane.setBackground(new Color(48, 48, 48));
        this.ajoutScroll();
 
        JScrollPane scroller = new JScrollPane(drawingPane) ;
        
        scroller.setPreferredSize(new Dimension(1095, 935));
        //scroller.getVerticalScrollBar().setUnitIncrement(25);
        scroller.setWheelScrollingEnabled(false);
        scroller.addMouseListener(this);
        
        add(scroller);
	}
	
	 public void ajoutScroll() {
    	final int W = 580;
        final int H = 980;
        boolean changed = true;

        int x = 1000;
        int y = 1000;
        Rectangle rect = new Rectangle(W, H);
        drawingPane.scrollRectToVisible(rect);
 
        int this_width = (x + W);
        if (this_width > area.width) {
        	area.width = this_width; changed=true;
        }
        
        int this_height = (y + H);
        if (this_height > area.height) {
        	area.height = this_height; changed=true;
        }
        
        if (changed) {
            drawingPane.setPreferredSize(area);
            drawingPane.revalidate();
        }
        drawingPane.repaint();
    }
	public void mouseClicked(MouseEvent event) {
		Point clik = event.getPoint().getLocation();
		final int W = 580;
        final int H = 980;
        boolean changed = true;

        int x = (int) clik.getX();
        int y = (int) clik.getY();
        System.out.println("X : " + x + " Y : " + y);
        
        Rectangle rect = new Rectangle(x, y, W, H);
        drawingPane.scrollRectToVisible(rect);
 
        int this_width = (x + W);
        if (this_width > area.width) {
        	area.width = this_width; changed=true;
        }
        
        int this_height = (y + H);
        if (this_height > area.height) {
        	area.height = this_height; changed=true;
        }
        
        if (changed) {
            drawingPane.setPreferredSize(area);
            drawingPane.revalidate();
        }
        drawingPane.repaint();
	}

	public void mouseEntered(MouseEvent event) {}

	public void mouseExited(MouseEvent event) {}

	public void mousePressed(MouseEvent event) {}

	public void mouseReleased(MouseEvent event) {}	 
	 
}
