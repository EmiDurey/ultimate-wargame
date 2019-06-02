package controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.HexMap;
import model.Joueur;

/**
 *  Class Sauvegarde.
 */
public class Sauvegarde {
	
	/**
	 *  Chemin du fichier contenant la partie sauvegardée.
	 */
	private static String path_file = "save/partie";
		
	/**
	 *  Retourne l'objet lu dans un fichier.
	 *  @return objet Object
	 */
	public static Object lecture (File fichier)
	{
		ObjectInputStream lecture;
		Object objet = null;

		try {
			lecture = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichier)));
			objet = (Object) lecture.readObject();
			lecture.close();
		}
		catch (ClassNotFoundException e)
		{
            System.out.println ("Fichier introuvable");
			System.exit(1);
		}
		catch (IOException e)
		{
			System.err.println("Erreur lecture du fichier" + e.toString());
			System.exit(1);
		}
		return objet;
	}
	
	/**
	 *  Ecrit un objet dans un fichier donné.
	 *  @param fichier File
	 *  @param objet Object
	 */
	public static void ecriture (File fichier, Object objet)
	{
		ObjectOutputStream flux = null ;

		try {
			flux = new ObjectOutputStream(new FileOutputStream(fichier)) ;
			flux.writeObject(objet);
			flux.flush();
		}
		catch (IOException e)
		{
			System.out.println("Probleme a l'ecriture/n" + e.toString()) ;
			System.exit(1) ;
		}
	}

	/**
	 *  Appelle la fonction de sauvegarde avec une instance de GameController.
	 *  @param map HexMap
	 */
	public static void savePartie(GameController partie) {
		ecriture(getFichierPartie(), partie);
	}
	
	/**
	 *  Retourne le fichier demandé.
	 *  @return new File(path_file)
	 */
	public static File getFichierPartie() {
		return new File(path_file);
	}
	
}
