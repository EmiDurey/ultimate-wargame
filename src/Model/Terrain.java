package model;

public abstract class Terrain {

	protected int coutDeplacement;
	protected int bonusDefense;

	public Terrain() {
	}

	public void appliqueDeplacement(Unite unite) {
		unite.setPointsDeplacement((int) (unite.getPointsDeplacement() - coutDeplacement));
	}

	public void appliqueBonusDefense(Unite unite) {
		int bonus = (int) (float)(100 /bonusDefense);
		unite.setDefense(unite.getDefense() * bonus);
	}
}
