package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class Unite.
 */
public abstract class Unite implements Serializable {

	/**
	 *  Joueur possédant l'unitée.
	 */
	protected Joueur joueur = null;
	/**
	 *  Points d'attaque d'une unitée.
	 */
	protected int pointsAttaque;
	/**
	 *  Points de défense intial d'une unitée.
	 */
	protected int pointsDefenseInit;
	/**
	 *  Points de défense d'une unitée.
	 *  Evolue au cours de la partie.
	 */
	protected int pointsDefense;
	/**
	 *  Points de déplacement d'une unitée.
	 *  Evolue au cours du tour.
	 */
	protected int pointsDeplacement;
	/**
	 *  Points de déplacement initial d'une unitée.
	 *  Ne change pas au cours de la partie.
	 */
	protected int pointsDeplacementInit;
	/**
	 *  Points de vie d'une unitée.
	 *  Evolue au cours de la partie.
	 */
	protected int pointsDeVie;
	/**
	 *  Points de vie maximum d'une unitée.
	 *  Ne change pas au cours de la partie.
	 */
	protected int pointsDeVieMax;
	/**
	 *  Points de vision d'une unitée.
	 */

	protected int vision;
	/**
	 *  Position d'une unitée.
	 */
	protected Hex hex;

        /**
         * Si le joueur qui a cette unité l'utilise actuellement (l'a choisie pour se déplacer)
         */
        protected boolean isActive = false;
	/**
	 *  Constructeur d'une unitée.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 */
	public Unite(Hex hex, Joueur joueur) {
		this.hex = hex;
		this.joueur = joueur;
		joueur.getUnite().add(this);
		hex.setUnit(this);
	}

	/**
	 *  Constructeur d'une unitée.
	 */
	public Unite() {
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	public void heal() {
	}

	/**
	 * Réinitialise les points de déplacement de l'unité.
	 */
	public void initialize() {
	}

	/**
	 * Méthode combat pour la plupart des unités.
	 * @param map Map
	 * @param joueurAct Joueur Actuel
	 * @param unite Unite
	 */
	public void combat(HexMap map, Joueur joueurAct, Unite unite) {
		final int crit = 3;
		final int chanceCrit = 2;
		int rand = (int) (Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if (this.hex.isNeighbour(unite.hex)) {
			if (rand > chanceCrit) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
			}
		} else {
			trajet = map.pathfinding(this.hex, unite.hex);
			if ((!trajet.isEmpty()) && (map.moveCost(this.hex, trajet.get(trajet.size() - 2)) <= this.pointsDeplacement)) {
			 	this.getHex().setUnit(null);
			 	trajet.get(trajet.size() - 2).setUnit(this);
				this.setHex(trajet.get(trajet.size() - 2));
				map.reveal(joueurAct, this.hex, this.vision);
				this.setDefense((int) ((float) (this.getHex().getDefense()/100) * this.pointsDefenseInit + this.pointsDefenseInit));
				if (rand > chanceCrit) {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				} else {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
				}
			}
		}
		if (unite.getPointsDeVie() < 0) {
			unite.joueur.getUnite().remove(unite);
			unite.getHex().setUnit(null);
		}
		this.pointsDeplacement = 0;
	}

	/**
	 * IA.
	 * Déplace une unité en fonction des possibilités de déplacement de cette dernière.
	 * @param joueur Joueur actuelle
	 * @param map HexMap
	 */
	public void joueurIA(Joueur joueur, HexMap map) {
		List<Hex> positionPossible = new ArrayList<Hex>();// NEED FONCTION
		List<Hex> unitInRange = new ArrayList<Hex>();
		List<Hex> trajet = new ArrayList<Hex>();
		for (Hex hex : positionPossible) {
			if (!hex.isEmpty()) {
				unitInRange.add(hex);
			}
			if (unitInRange.isEmpty()) {
				if (this.pointsDeVie >= this.pointsDeVieMax / 2) {
					//Très sale =/
					Unite enemy = map.getClosestEnemy(this.getHex(), joueur);
					Hex goal = flee (enemy.getHex(), map);
					ArrayList<Hex> path = map.pathfinding( getHex(), map.getClosestEnemy(getHex(), joueur).getHex());
					if(path.size() > 1) {
						seDeplace(map, path.get(path.size()-2));
					}else if(path.size() > 0) {
						seDeplace(map, path.get(path.size()-1));
					}



				} else {
					this.setPointsDeplacement(0);
					break;
				}
			} else {
				for (Hex unitPos : unitInRange) {
					trajet = map.pathfinding(this.hex, unitPos);
					if ((!trajet.isEmpty())
							&& ( map.moveCost(this.hex, trajet.get(trajet.size() - 2)) <= this.pointsDeplacement)
							&& unitPos.getUnit().pointsDeVie < this.pointsAttaque - unitPos.getUnit().getDefense()) {
						this.combat(map, joueur, unitPos.getUnit());
					} else {
						if ((!trajet.isEmpty())
								&& (map.moveCost(this.hex, trajet.get(trajet.size() - 1)) <= this.pointsDeplacement)
								&& this.pointsDeVie > unitPos.getUnit().pointsAttaque - this.getDefense()) {
							this.combat(map, joueur, unitPos.getUnit());
						}
					}
				}
			}
		}
		if (this.pointsDeplacement != 0) {
			Unite enemy = map.getClosestEnemy(this.getHex(), joueur);
			Hex goal = flee (enemy.getHex(), map);
			seDeplace(map, goal);
		}
	}

	/**
	 * Déplace une unité si c'est possible.
	 * @param map HexMap
	 * @param newHex Hex
	 */
	public void seDeplace(HexMap map, Hex newHex){
		List<Hex> trajet = map.pathfinding(this.hex, newHex);
		if ((!trajet.isEmpty()) && (map.moveCost(this.hex, trajet.get(trajet.size() - 1)) <= this.pointsDeplacement)) {
			this.pointsDeplacement -= map.moveCost(this.hex, trajet.get(trajet.size() - 1));
		  	this.getHex().setUnit(null);
			newHex.setUnit(this);
			this.setHex(newHex);
			map.reveal(this.hex.getUnit().getJoueur(), this.hex, this.vision);
			this.setDefense((int) ((float) (this.getHex().getDefense()/100) * this.pointsDefenseInit + this.pointsDefenseInit));
		}
	}


	public Hex flee (Hex source, HexMap map) {
		//TODO Testing

		int moveCapacity = pointsDeplacement;
		Hex currentHex = getHex();
		int minCost;
		int minIndex;
		Boolean nextFound;

		while(moveCapacity > 0) {
			ArrayList<Hex> neighbours = map.getNeighbours(currentHex);
			minCost = neighbours.get(0).getCost();
			minIndex = 0;

			nextFound = false;
			for(int i=1; i<neighbours.size(); i++) {
				if(minCost > neighbours.get(i).getCost() && neighbours.get(i).isEmpty() && moveCapacity - neighbours.get(i).getCost() > 0) {
					nextFound = true;
					minCost = neighbours.get(i).getCost();
					minIndex = i;
				}

				if(!nextFound)
					break;

				currentHex = neighbours.get(minIndex);
			}
		}

		return currentHex;
	}


	/**
	 * Récupère la position d'une unité.
	 * @return hex Hex
	 */
	public Hex getHex() {
		return hex;
	}

	/**
	 * Assigne la position d'une unité.
	 * @param hex Hex
	 */
	public void setHex(Hex hex) {
		this.hex = hex;
	}

	/**
	 * Récupère la défense d'une unité.
	 * @return pointsDefense int
	 */
	public int getDefense() {
		return pointsDefense;
	}

	/**
	 * Assigne la défense d'une unité.
	 * @param pointsDefense int
	 */
	public void setDefense(int pointsDefense) {
		this.pointsDefense = pointsDefense;
	}

	/**
	 * Récupère les points de déplacement d'une unité.
	 * @return pointsDeplacement int
	 */
	public float getPointsDeplacement() {
		return pointsDeplacement;
	}

	/**
	 * Assigne les points de déplacement d'une unité.
	 * @param pointsDeplacement int
	 */
	public void setPointsDeplacement(int pointsDeplacement) {
		this.pointsDeplacement = pointsDeplacement;
	}

	/**
	 * Récupère les points de vie maximum d'une unité.
	 * @return pointsDeVieMax int
	 */
	public int getPointsDeVieMax() {
		return pointsDeVieMax;
	}

	/**
	 * Récupère les points de vie  d'une unité.
	 * @return pointsDeVieMax int
	 */
	public int getPointsDeVie() {
		return pointsDeVie;
	}

	/**
	 * Assigne les points de vie d'une unité.
	 * @param pointsDeVie int
	 */
	public void setPointsDeVie(int pointsDeVie) {
		this.pointsDeVie = pointsDeVie;
	}

	/**
	 * Récupère le joueur d'une unité.
	 * @return joueur Joueur
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * Assigne le joueur d'une unité.
	 * @param joueur Joueur
	 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

    public int getPointsAttaque() {
        return pointsAttaque;
    }

    public void setPointsAttaque(int pointsAttaque) {
        this.pointsAttaque = pointsAttaque;
    }

    public int getPointsDefenseInit() {
        return pointsDefenseInit;
    }

    public void setPointsDefenseInit(int pointsDefenseInit) {
        this.pointsDefenseInit = pointsDefenseInit;
    }

    public int getPointsDefense() {
        return pointsDefense;
    }

    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    public int getPointsDeplacementInit() {
        return pointsDeplacementInit;
    }

    public void setPointsDeplacementInit(int pointsDeplacementInit) {
        this.pointsDeplacementInit = pointsDeplacementInit;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


}
