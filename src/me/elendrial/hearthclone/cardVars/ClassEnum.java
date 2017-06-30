package me.elendrial.hearthclone.cardVars;

public enum ClassEnum {
	// TODO: Add other names using @SerializedName (Priority: Low)
	// TODO: For all enums, figure out a way to add custom ones via json. (Priority: Medium)
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
