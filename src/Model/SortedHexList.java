package model;

import java.util.ArrayList;

/**
 * Classe SortedHexList.
 * @see Hex
 */
public class SortedHexList {
	/**
	 * Agit comme une priority queue sur les Hex.
	 * Utilisé dans l'implémentation de l'algo A*.
	 */
	private ArrayList<Hex> hexList = new ArrayList<Hex>();

	/**
	* List des co�ts.
	*/
	private ArrayList<Integer> costList = new ArrayList<Integer>();

	/**
	* Indique si la liste contient encore des éléments.
	* @return Boolean
	*/
	Boolean hasElements() {
		return hexList.size() > 0;
	}

	/**
	* Ajoute a à la liste avec un coût de cost.
	* @param a hexagone
	* @param cost int
	* @see Hex
	*/
	void put(Hex a, int cost) {
		hexList.add(a);
		costList.add(cost);
	}

	/**
	* Retourne l'élément à la priorité la plus élevée / coût le plus faible et
	* le supprime de la liste.
	* @return Hex
	* @see Hex
	*/
	Hex pop() {
		if (hexList.size() == 0) {
			return null;
		}

		int minIndex = 0;
		int minCost = costList.get(0);
		for (int i = 1; i < costList.size(); i++) {
			if (costList.get(i) < minCost) {
				minIndex = i;
				minCost = costList.get(i);
			}
		}

		Hex returnValue = hexList.remove(minIndex);
		costList.remove(minIndex);
		return returnValue;
	}

	/**
	* Indique si l'hexagone est contenu dans la queue, si oui, renvoie son cout.
	* @param a Hex
	* @return int
	* @see Hex
	*/
	int contains(Hex a) {
		for (int i = 0; i < hexList.size(); i++) {
			if (a.isMatch(hexList.get(i))) {
				return costList.get(i);
			}
		}

		return -1;
	}

	/**
	* Retourne taille.
	* @return int
	*/
	int size() {
		return hexList.size();
	}

	/**
	* Supprime un hexagone.
	* @param a Hex
	* @see Hex
	*/
	void remove(Hex a) {
		for (int i = 0; i < hexList.size(); i++) {
			if (a.isMatch(hexList.get(i))) {
				hexList.remove(i);
				costList.remove(i);
			}
		}
	}
}
