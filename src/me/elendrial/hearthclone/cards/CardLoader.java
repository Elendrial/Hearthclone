package me.elendrial.hearthclone.cards;

import com.google.gson.Gson;

public class CardLoader {

	public static void loadAllCards() {
		
	}

	public static void loadCards(String[] ids) {

	}

	public static HearthstoneCard loadCard(String s){
		Gson gson = new Gson();
		return gson.fromJson(s, HearthstoneCard.class);
	}
	
	public static void printCard(HearthstoneCard c) {
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Gson gson = new Gson();
		System.out.println(gson.toJson(c));
	}

}
