package me.elendrial.hearthclone.general;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import me.elendrial.hearthclone.cards.HearthstoneCard;
import me.elendrial.hearthclone.decks.HearthstoneDeck;
import me.elendrial.hearthclone.ruleSets.RuleSet;

public class JsonHandler {
	
	//TODO: Ensure all cards load in correctly (Priority: Medium, probably needs to be done AFTER making the game playable).
	public static ArrayList<HearthstoneCard> cards = new ArrayList<HearthstoneCard>();
	
	public static void loadAllCards() throws IOException {
		Gson gson = new Gson();
		JsonReader json = null;
		
		HearthstoneCard[] cardsList;
		for(File f : (new File("resources/json/cards")).listFiles()){
			json = new JsonReader(new FileReader(f.getAbsolutePath()));
			cardsList = gson.fromJson(json, HearthstoneCard[].class);
			for(HearthstoneCard c : cardsList){ c.identifiers.put("fileSet", f.getName().replace(".json", "")); cards.add(c);}
		}
		
		json.close();
	}
	
	public static RuleSet loadRuleSet(String ruleSet) throws IOException{
		Gson gson = new Gson();
		JsonReader json = new JsonReader(new FileReader("resources/json/ruleSets/" + ruleSet));
		RuleSet rs = gson.fromJson(json, RuleSet.class);
		
		json.close();
		return rs;
	}
	
	public static void saveRuleSet(String ruleSetJson){
		
	}
	
	public static RuleSet getRuleSet(String ruleSet){
		return null;
	}
	
	public static HearthstoneDeck loadDeck(String deckName) throws IOException{
		Gson gson = new Gson();
		JsonReader json = new JsonReader(new FileReader("resources/json/decks/" + deckName));
		HearthstoneDeck deck = gson.fromJson(json, HearthstoneDeck.class);
		
		json.close();
		return deck;
	}
	
	public static void saveDeck(String deckJson){
		
	}
	
	public static void printObject(Object o, boolean prettyPrinting) {
		Gson gson;
		if(prettyPrinting) gson = new GsonBuilder().setPrettyPrinting().create();
		else gson = new Gson();
		System.out.println(gson.toJson(o));
	}

}
