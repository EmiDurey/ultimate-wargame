package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

public class OptionPaneAide {

	/**
	 * Chemins menant au fichiers désirés.
	 */
	private static String path_aide = "aide.txt";
	private static String path_regles = "regles.txt";
	
	
	/**
	 *  Affiche une JOptionPane concernant l'aide.
	 *  @param fenetre InterfaceJeu
	 */
	public static void afficheAide(InterfaceJeu fenetre) {
		JOptionPane.showMessageDialog(fenetre, 
				contenuFichier(path_aide), 
				"Regles du jeu", 
				JOptionPane.INFORMATION_MESSAGE);
		return;
	}
	
	/**
	 *  Affiche une JOptionPane concernant les règles du jeu.
	 *  @param path String
	 */
	public static void afficheRegles(InterfaceJeu fenetre) {
		JOptionPane.showMessageDialog(fenetre, 
				contenuFichier(path_regles), 
				"Regles du jeu", 
				JOptionPane.INFORMATION_MESSAGE);
		return;
	}
	
	/**
	 *  Lit un fichier et renvoie son contenu en tant que String.
	 *  @param path String
	 *  @return chaine String
	 */
	private static String contenuFichier(String path) {
	    try {
	       InputStreamReader lecture = new InputStreamReader(new FileInputStream(path));
	       BufferedReader buffer = new BufferedReader(lecture);
	       String line;
	       StringBuilder chaine = new StringBuilder("");
	       while((line = buffer.readLine()) !=null) {
	           chaine.append(line+"\n");
	       }
	       buffer.close();
	       lecture.close();
	       return chaine.toString();
	    }
	    catch (IOException ie)
	    {
	         ie.printStackTrace(); 
	    }
		return null;
	}	
}
