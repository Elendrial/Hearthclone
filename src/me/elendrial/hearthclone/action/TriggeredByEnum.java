package me.elendrial.hearthclone.action;

public enum TriggeredByEnum {
	spell("a spell is played"),
	minionPlay("a minion is played"),
	minionSummon("a minion is summoned");
	
	
	public String text;
	
	TriggeredByEnum(String text){
		this.text = text;
	}
	
}
