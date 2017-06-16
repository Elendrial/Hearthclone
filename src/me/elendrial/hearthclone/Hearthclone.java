package me.elendrial.hearthclone;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import me.elendrial.cardGameBase.cards.StandardCard;
import me.elendrial.cardGameBase.decks.StandardDeck;
import me.elendrial.hearthclone.cardVars.ClassEnum;
import me.elendrial.hearthclone.cardVars.RarityEnum;
import me.elendrial.hearthclone.cardVars.SetEnum;
import me.elendrial.hearthclone.cardVars.TribeEnum;
import me.elendrial.hearthclone.cardVars.TypeEnum;
import me.elendrial.hearthclone.cards.JSonLoader;
import me.elendrial.hearthclone.cards.HearthstoneCard;
import me.elendrial.hearthclone.containers.MainMenuContainer;
import me.elendrial.hearthclone.containers.MatchContainer;

public class Hearthclone {
	
	public static void main(String[] args) throws FileNotFoundException{
		try {
			JSonLoader.loadAllCards();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		HearthController.containers.put("mainMenu", new MainMenuContainer());
		HearthController.containers.put("match", new MatchContainer());
	}
	
}
