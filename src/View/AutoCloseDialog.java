package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;

/**
 * Classe AutoCloseDialog.
 */
public class AutoCloseDialog extends JWindow {

	/**
	 * Fenetre.
	 */
    private JWindow window;

    /**
     * Construit un objet de la classe AutoCloseDialog.
     * @param fenetre InterFaceJeu
     * @param x int
     * @param y int
     * @param message String
     */
    public AutoCloseDialog(InterfaceJeu fenetre, int x, int y, String message) {
        super(fenetre);
        Dimension dimension;
        int height, width;
        JLabel messageLabel;

        this.window = this;
        this.setVisible(true);
        this.getContentPane().setBackground(Color.RED);

        dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        height  = (int) dimension.getHeight();
        width  = (int) dimension.getWidth();
        y = y + (height - fenetre.getLargeur());
        x = x + ((width - fenetre.getLongueur()) / 2);
        this.window.setLocation(x, y);

        messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 25));
        messageLabel.setForeground(Color.WHITE);
        this.add(messageLabel);
        this.pack();

        Timer timer = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (window.isDisplayable()) {
                    window.dispose();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
