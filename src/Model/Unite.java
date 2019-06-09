package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class Unite.
 *  @see Joueur
 *  @see Hex
 *  @see HexMap
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
	 *  Est attaqu�.
	 */
	protected Boolean hasAttacked = false;

    /**
     * Si le joueur qui a cette unité l'utilise actuellement (l'a choisie pour se déplacer).
     */
    protected boolean isActive = false;

	/**
	 *  Constructeur d'une unitée.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 *  @see Hex
	 *  @see Joueur
	 */
	public Unite(Hex hex, Joueur joueur) {
		this.hex = hex;
		this.joueur = joueur;
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
	 * @see HexMap
	 * @see Hex
	 * @see Joueur
	 */
	public void combat(HexMap map, Joueur joueurAct, Unite unite) {

		if (hasAttacked) {
			return;
		}

		final int crit = 2;
		final int chanceCrit = 2;
		int rand = (int) (Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if (this.hex.isNeighbour(unite.hex)) {
			if (rand > chanceCrit) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				hasAttacked = true;
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
				hasAttacked = true;
			}
		} else {
			trajet = map.pathfinding(this.hex, unite.hex);
			if ((!trajet.isEmpty()) && (map.moveCost(this.hex, trajet.get(trajet.size() - 2)) <= this.pointsDeplacement) && trajet.get(trajet.size() - 2).getUnit() == null) {
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
		if (unite.getPointsDeVie() <= 0) {
			unite.joueur.getUnite().remove(unite);
			unite.getHex().setUnit(null);
		}
		this.pointsDeplacement = 0;
		System.out.println(unite + " : " + unite.pointsDeVie + "/" + unite.pointsDeVieMax);
	}

	/**
	 * IA.
	 * Déplace une unité en fonction des possibilités de déplacement de cette dernière.
	 * @param joueur Joueur actuelle
	 * @param map HexMap
	 * @see Joueur
	 * @see HexMap
	 * @see Hex
	 */
	public void joueurIA(Joueur joueur, HexMap map) {

		Unite closestEnemy = map.getClosestEnemy(this.hex, joueur);

		//Si il n'y a pas d'unité à portée
		//FIXME Sometimes throws NullPointerException -> closestEnemy not found on game end ?
		if (pointsDeplacement < map.moveCost(this.hex, closestEnemy.getHex())) {

			//Si les PV sont supérieurs à 50%
			if (this.pointsDeVie > this.pointsDeVieMax / 2) {
				//On se rapproche de closestEnemy
				getCloser(map, closestEnemy.getHex());
				this.combat(map, joueur, closestEnemy);
			}

			//Sinon: On ne bouge pas et on se soigne
		} else {

			//Si l'unité la plus proche peut nous tuer
			//Flemme de calculer avec les points de défense ¯\_(ツ)_/¯
			if (closestEnemy.getPointsAttaque() > this.pointsDeVie) {
				Hex goal = flee(closestEnemy.getHex(), map);
			} else {
				//On attaque et on fuit
				getCloser(map, closestEnemy.getHex());
				this.combat(map, joueur, closestEnemy);
				Hex goal = flee(closestEnemy.getHex(), map);
				seDeplace(map, goal);
			}
		}

	}

	/**
	 * Déplace une unité si c'est possible.
	 * @param map HexMap
	 * @param newHex Hex
	 * @see HexMap
	 * @see Hex
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

	/**
	 * Déplace une unité le plus loin possible.
	 * @param map HexMap
	 * @param newHex Hex
	 * @see HexMap
	 * @see Hex
	 */
	public void getCloser(HexMap map, Hex newHex){
		List<Hex> trajet = map.pathfinding(this.hex, newHex);
		for (int i = 0; i < trajet.size(); i++) {

			if (trajet.get(i).getCost() > pointsDeplacement) {
				return;
			}

			pointsDeplacement -= trajet.get(i).getCost();

			this.getHex().setUnit(null);
			newHex.setUnit(this);
			this.setHex(trajet.get(i));
			map.reveal(getJoueur(), this.hex, this.vision);
			this.setDefense((int) ((float) (this.getHex().getDefense()/100) * this.pointsDefenseInit + this.pointsDefenseInit));
		}
	}

	/**
	 * Fuir.
	 * @param source Hex
	 * @param map HexMap
	 * @return Hex
	 * @see Hex
	 * @see HexMap
	 */
	public Hex flee(Hex source, HexMap map) {
		int moveCapacity = pointsDeplacement;
		Hex currentHex = getHex();
		int minCost;
		int minIndex;
		Boolean nextFound;

		while (moveCapacity > 0) {
			ArrayList<Hex> neighbours = map.getNeighbours(currentHex);
			minCost = neighbours.get(0).getCost();
			minIndex = 0;

			nextFound = false;
			for (int i = 1; i < neighbours.size(); i++) {
				if (minCost > neighbours.get(i).getCost() && neighbours.get(i).isEmpty() && moveCapacity - neighbours.get(i).getCost() > 0) {
					nextFound = true;
					minCost = neighbours.get(i).getCost();
					minIndex = i;
				}

				if (!nextFound) {
					break;
				}
				currentHex = neighbours.get(minIndex);
			}
		}
		return currentHex;
	}

	/**
	 * Récupère la position d'une unité.
	 * @return hex Hex
	 * @see Hex
	 */
	public Hex getHex() {
		return hex;
	}

	/**
	 * Assigne la position d'une unité.
	 * @param hex Hex
	 * @see Hex
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
	public int getPointsDeplacement() {
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
	 * @see Joueur
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * Assigne le joueur d'une unité.
	 * @param joueur Joueur
	 * @see Joueur
	 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	/**
	 * R�cup�re les points d'attaque.
	 * @return pointsAttaque int
	 */
    public int getPointsAttaque() {
        return pointsAttaque;
    }

    /**
	 * Assigne les points d'attaque d'une unit�.
	 * @param pointsAttaque int
	 */
    public void setPointsAttaque(int pointsAttaque) {
        this.pointsAttaque = pointsAttaque;
    }

    /**
	 * R�cup�re les points d'attaque.
	 * @return pointsAttaque int
	 */
    public int getPointsDefenseInit() {
        return pointsDefenseInit;
    }

    /**
	 * Assigne les points de d�fense initials d'une unit�.
	 * @param pointsDefenseInit int
	 */
    public void setPointsDefenseInit(int pointsDefenseInit) {
        this.pointsDefenseInit = pointsDefenseInit;
    }

    /**
	 * R�cup�re les points d'attaque.
	 * @return pointsAttaque int
	 */
    public int getPointsDefense() {
        return pointsDefense;
    }

    /**
	 * Assigne les points de d�fense d'une unit�.
	 * @param pointsDefense int
	 */
    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    /**
	 * R�cup�re les points de d�placement.
	 * @return pointsDeplacement int
	 */
    public int getPointsDeplacementInit() {
        return pointsDeplacementInit;
    }

    /**
	 * Assigne les points de d�placement initials d'une unit�.
	 * @param pointsDeplacementInit int
	 */
    public void setPointsDeplacementInit(int pointsDeplacementInit) {
        this.pointsDeplacementInit = pointsDeplacementInit;
    }

    /**
	 * R�cup�re les points de vision.
	 * @return vision int
	 */
    public int getVision() {
        return vision;
    }

    /**
	 * Assigne les points de vision � une unit�.
	 * @param vision int
	 */
    public void setVision(int vision) {
        this.vision = vision;
    }

    /**
	 * R�cup�re la caract�ristique est active.
	 * @return isActive boolean
	 */
    public boolean isIsActive() {
        return isActive;
    }

    /**
	 * Assigne la caract�ristique est active � une unit�.
	 * @param isActive boolean
	 */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
	 * Assigne la caract�ristique est attaqu� � une unit�.
	 * @param hasAttacked boolean
	 */
	public void setHasAttacked(boolean hasAttacked) {
		this.hasAttacked = hasAttacked;
	}




}
