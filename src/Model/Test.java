package model;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		
		// Création de la map et écriture dans le fichier
		/*HexMap map = new HexMap();
		map.setHexagonMap(5);
        map.populate();
        map.ASCIIDisplay();
		Sauvegarde.ecriture(Sauvegarde.getFichierMap(), map);
		*/
		
		// Création des joueurs et écriture dans le fichier
		/*ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		Joueur j1 = new Joueur("Joueur1", 1);
		Joueur j2 = new Joueur("Joueur2", 2);
		Hex hex = new Hex(0, 0);
		j1.addUnit(new Pretre(hex, j1));
		j2.addUnit(new Infanterie(hex, j2));
		joueurs.add(j1);
		joueurs.add(j2);
		Sauvegarde.ecriture(Sauvegarde.getFichierJoueurs(), joueurs);
		*/
		
		//affichage de la map d'après le fichier
		HexMap map = (HexMap) Sauvegarde.lecture(Sauvegarde.getFichierMap());
        map.ASCIIDisplay();
        
     	//affichage des joueurs d'après le fichier
      	ArrayList<Joueur> joueurs = (ArrayList<Joueur>) Sauvegarde.lecture(Sauvegarde.getFichierJoueurs());
        System.out.print(joueurs.get(0).getNom()+"\n");
        System.out.print(joueurs.get(1).getNom());
		
	}
}
