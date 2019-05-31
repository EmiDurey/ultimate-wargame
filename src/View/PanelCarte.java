package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
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
public class PanelCarte extends JPanel {

	private Dimension area; //indicates area taken up by graphics
    private PanelDessineur drawingPane;
    
	/**
	 *  Construit un objet de type Carte.
	 */
	public PanelCarte() {
	    this.setPreferredSize(new Dimension(1095, 0));
	    this.setBackground(new Color(206, 206, 206));
	    this.setVisible(true);
	    
	    area = new Dimension(0,0);
	    
        //Set up the drawing area.
        drawingPane = new PanelDessineur();
        drawingPane.setBackground(new Color(48, 48, 48));
        this.ajoutScroll();
 
        //Put the drawing area in a scroll pane.
        //JScrollPane scroller = new JScrollPane(drawingPane);
       // scroller.setPreferredSize(new Dimension(1095, 935));
 
        JScrollPane scroller = new JScrollPane(drawingPane) ;
        scroller.setPreferredSize(new Dimension(1095, 935));

        scroller.addMouseWheelListener(new MouseWheelListener() {
        public void mouseWheelMoved(MouseWheelEvent event) {
              final JScrollBar scrollBar =  scroller.getVerticalScrollBar();
              final int rotation = event.getWheelRotation();
              if (scrollBar!=null) {
            	  System.out.println(scrollBar.getValue());
            	  System.out.println(rotation);
            	  System.out.println(scrollBar.getBlockIncrement(rotation));

                 scrollBar.setValue(scrollBar.getValue() + (scrollBar.getBlockIncrement(rotation)*rotation) - (scrollBar.getBlockIncrement()*5*rotation));
                 // dispatchEvent(event); pas nécessaire
              }
           }
        });
        
	    
        //Lay out this demo.
        add(scroller);
	}
	
	 public void ajoutScroll() {
    	final int W = 1095;
        final int H = 935;
        boolean changed = true;

        int x = 1000;
        int y = 1000;
        Rectangle rect = new Rectangle(x, y, W, H);
        drawingPane.scrollRectToVisible(rect);
 
        int this_width = (x + W + 2);
        if (this_width > area.width) {
        	area.width = this_width; changed=true;
        }
        
        int this_height = (y + H + 2);
        if (this_height > area.height) {
        	area.height = this_height; changed=true;
        }
        
        if (changed) {
            //Update client's preferred size because
            //the area taken up by the graphics has
            //gotten larger or smaller (if cleared).
            drawingPane.setPreferredSize(area);
 
            //Let the scroll pane know to update itself
            //and its scrollbars.
            drawingPane.revalidate();
        }
        drawingPane.repaint();
    }
	 
	 
}
