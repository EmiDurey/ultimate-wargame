package model;

import java.util.ArrayList;

import controller.GameController;
import controller.Sauvegarde;

public class Test {

	public static void main(String[] args) {
		ArrayList<Joueur> players = new ArrayList<Joueur>();
		GameController partie = new GameController(players);
		Sauvegarde.savePartie(partie);
	}
}
