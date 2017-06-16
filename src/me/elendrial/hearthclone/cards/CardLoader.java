package me.elendrial.hearthclone.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class CardLoader {
	
	//TODO: Ensure all cards load in correctly (Priority: Medium, probably needs to be done AFTER making the game playable).
	public static ArrayList<HearthstoneCard> cards = new ArrayList<HearthstoneCard>();
	
	public static void loadAllCards() throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader json;
		
		HearthstoneCard[] cardsList;
		for(File f : (new File("resources/json/cards")).listFiles()){
			json = new JsonReader(new FileReader(f.getAbsolutePath()));
			cardsList = gson.fromJson(json, HearthstoneCard[].class);
			for(HearthstoneCard c : cardsList){ c.identifiers.put("fileSet", f.getName().replace(".json", "")); cards.add(c);}
		}
	}
	
	public static void printCard(HearthstoneCard c, boolean prettyPrinting) {
		Gson gson;
		if(prettyPrinting) gson = new GsonBuilder().setPrettyPrinting().create();
		else gson = new Gson();
		System.out.println(gson.toJson(c));
	}

}
