package me.elendrial.hearthclone.ruleSets;

public class RuleSet {
	
	public String name;
	
	public int maxTotalDeckSize;
	public int minStartingDeckSize;
	public int maxStartingDeckSize;
	
	public FatigueEnum onFatigue;
	public int startingFatigueDamage = 1;

	public boolean setLimit;
	public String[] allowedSets;
	
	public enum FatigueEnum{
		Increasing, Static, InstantLoss;
	}
	
}
