package me.elendrial.hearthclone.action;

public enum TriggeredTimeEnum {
	afterX("After"),
	wheneverX("Whenever");
	
	public String text;
	
	TriggeredTimeEnum(String text){
		this.text = text;
	}
	
}
