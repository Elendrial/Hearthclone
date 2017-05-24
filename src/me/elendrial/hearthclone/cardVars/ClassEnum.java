package me.elendrial.hearthclone.cardVars;

public enum ClassEnum {
	NEUTRAL,
	WARRIOR,
	PRIEST,
	DRUID,
	MAGE,
	WARLOCK,
	HUNTER,
	PALADIN,
	ROGUE,
	SHAMAN,
	DEATHKNIGHT,
	MONK,
	GRIMYGOONS(new ClassEnum[]{WARRIOR, HUNTER, PALADIN}),
	JADELOTUS(new ClassEnum[]{DRUID, ROGUE, SHAMAN}),
	KABAL(new ClassEnum[]{MAGE, PRIEST, WARLOCK});
	
	public ClassEnum[] inferredClass;
	
	private ClassEnum(){}
	private ClassEnum(ClassEnum[] inferredClass){
		this.inferredClass = inferredClass;
	}
}
