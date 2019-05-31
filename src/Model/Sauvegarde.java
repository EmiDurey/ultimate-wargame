package model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *  Class Sauvegarde.
 */
public class Sauvegarde {
	
	/**
	 *  Chemin du fichier contenant les joueurs sauvegardés.
	 */
	private static String path_joueurs = "saves/joueurs";
	
	/**
	 *  Chemin du fichier contenant la map sauvegardée.
	 */
	private static String path_map = "saves/map";
	
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
	 *  Appelle la fonction de sauvegarde avec des joueurs.
	 *  @param map HexMap
	 */
	public static void saveJoueurs(Joueur joueur) {
		ecriture(getFichierJoueurs(), joueur);
	}
	
	/**
	 *  Appelle la fonction de sauvegarde avec une map.
	 *  @param map HexMap
	 */
	public static void saveMap(HexMap map) {
		ecriture(getFichierMap(), map);
	}
	
	/**
	 *  Retourne le fichier demandé.
	 *  @return newFile(path_joueurs)
	 */
	public static File getFichierJoueurs() {
		return new File(path_joueurs);
	}
	
	/**
	 *  Retourne le fichier demandé.
	 *  @return newFile(path_map)
	 */
	public static File getFichierMap() {
		return new File(path_map);
	}
	
}
