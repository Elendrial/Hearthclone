package me.elendrial.hearthclone;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import me.elendrial.cardGameBase.Controller;
import me.elendrial.cardGameBase.cards.StandardCard;
import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.decks.StandardDeck;
import me.elendrial.hearthclone.cardVars.ClassEnum;
import me.elendrial.hearthclone.cardVars.RarityEnum;
import me.elendrial.hearthclone.cardVars.SetEnum;
import me.elendrial.hearthclone.cardVars.TribeEnum;
import me.elendrial.hearthclone.cardVars.TypeEnum;
import me.elendrial.hearthclone.cards.HearthstoneCard;

public class Hearthclone {
	
	public static void main(String[] args) throws FileNotFoundException{
		//cardSetup();
		
		//CardLoader.printCard(cardA);
		
	//	Gson gson = new Gson();
	//	cardB = gson.fromJson("{\"cost\":4,\"flavor\":\"flaavour\",\"cardClass\":\"MONK\",\"set\":\"BASIC\",\"race\":\"PANDAREN\",\"rarity\":\"COMMON\",\"type\":\"SPELL\",\"name\":\"Sample\",\"desc\":\"Some Desc\",\"code\":\"abcd\",\"textureName\":\"textures/cards/HearthstoneClassicBack2.png\",\"backTextureName\":\"textures/cards/HearthstoneClassicBack2.png\"}", HearthstoneCard.class);
	//	System.out.println(cardB.type);
	//	JsonReader json = new JsonReader(new FileReader("resources/json/cards/cards.json"));
		
	//	HearthstoneCard[] cards = gson.fromJson(json, HearthstoneCard[].class);
	//	cardA = cards[0];
	//	System.out.println(cardA.name);
		
		//	deckSetup();
		containerSetup();
		
		HearthController.startGame("Hearthclone", 1200, 750);
	}
	
	public static HearthstoneCard cardA, cardB;
	public static StandardDeck deckA, deckB;
	
	public static void cardSetup(){
		cardA = new HearthstoneCard("Sample", "Some Desc", "abcd", "HearthstoneClassicBack2.png", "HearthstoneClassicBack2.png", null, 4, "flaavour", null, ClassEnum.MONK, SetEnum.BASIC, TribeEnum.PANDAREN, RarityEnum.COMMON, TypeEnum.SPELL);
		
	//	cardB = new HearthstoneCard("Sample2", "Some Desc2", "abcd2", "sample/SampleFront.png", "sample/HearthstoneClassicBack.png", null, 7, 8, 5);
	}
	
	@SuppressWarnings("unchecked")
	public static void deckSetup(){
		ArrayList<StandardCard> cards = new ArrayList<StandardCard>();
		cards.add(cardA.clone()); cards.add(cardA.clone()); cards.add(cardA.clone()); cards.add(cardA.clone()); cards.add(cardA.clone());
		deckA = new StandardDeck(5, (ArrayList<StandardCard>) cards.clone(), new Point(30,50), new Point(100,160));
		
		cards.clear();
		cards.add(cardB); cards.add(cardB.clone()); cards.add(cardB.clone()); cards.add(cardB.clone()); cards.add(cardB.clone());
		deckB = new StandardDeck(5, (ArrayList<StandardCard>) cards.clone(), new Point(650,50), new Point(100,160));
		cards.clear();
	}
	
	public static void containerSetup(){
		Controller.mainContainer = new BaseContainer(new StandardDeck[]{/*deckA, deckB*/});
	}
	
}
