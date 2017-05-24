package me.elendrial.hearthclone.cards;

import java.util.HashMap;

import me.elendrial.cardGameBase.cards.StandardCard;
import me.elendrial.hearthclone.cardVars.ClassEnum;
import me.elendrial.hearthclone.cardVars.Effect;
import me.elendrial.hearthclone.cardVars.RarityEnum;
import me.elendrial.hearthclone.cardVars.SetEnum;
import me.elendrial.hearthclone.cardVars.TribeEnum;
import me.elendrial.hearthclone.cardVars.TypeEnum;

public class HearthstoneCard extends StandardCard{
	
//	@SuppressWarnings("unused")
//	private static final long serialVersionUID = 4845249887400409613L;
	public int cost;
	public int attack;
	public int health;
	public String flavor;
	public Effect effect;
	
	public ClassEnum cardClass;
	public SetEnum set;
	public TribeEnum race;
	public RarityEnum rarity;
	public TypeEnum type;
	
	
	public HearthstoneCard(){}
	
	public HearthstoneCard(String name, String text, String id, String textureName, String backTextureName, HashMap<String, Object> identifiers, int cost) {
		super(name, text, id, textureName, backTextureName, identifiers);
		this.cost = cost;
	}

	public HearthstoneCard(String name, String text, String id, String textureName, String backTextureName, HashMap<String, Object> identifiers, int cost, String flavor, Effect effect, ClassEnum cardClass, SetEnum set, TribeEnum race, RarityEnum rarity, TypeEnum type) {
		super(name, text, id, textureName, backTextureName, identifiers);
		this.cost = cost;
		this.flavor = flavor;
		this.effect = effect;
		this.cardClass = cardClass;
		this.set = set;
		this.race = race;
		this.rarity = rarity;
		this.type = type;
	}
	
	public void clean(){
		if(race == null) race = TribeEnum.NONE;
		if(rarity == null) rarity = RarityEnum.UNKNOWN;
		if(type == null) type = TypeEnum.SPELL;
		if(set == null) set = SetEnum.CUSTOM;
	}
	
	
	
}
