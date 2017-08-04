package me.elendrial.hearthclone.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import me.elendrial.cardGameBase.server.GameProtocol;
import me.elendrial.hearthclone.HearthController;
import me.elendrial.hearthclone.containers.ClientMatchContainer;
import me.elendrial.hearthclone.decks.HearthstoneDeck;
import me.elendrial.hearthclone.display.HearthWindow;
import me.elendrial.hearthclone.general.JsonHandler;

public class ClientProtocol extends GameProtocol{

	private HearthstoneDeck deck;
	private String ruleSet;
	private boolean inMatch = false;
	
	@Override
	public void run() {
		while(!socket.isClosed()) recieveData();
	}
	
	@Override
	public void sendData(String s){
		System.out.println("[Client]: Sending >>>>>>>> " + s);
		super.sendData(s);
	}
	
	@Override
	public void recieveData() {
		String data;
		try {
		//	System.out.println("[Client]: Waiting to recieve data");
			data = in.readLine();
			System.out.println("[Client]: Recieved <<<<<<<< " + data);
			
			if(data.contains("disconnect:")) 	disconnect();
			else if(data.startsWith("info:"))	infoHandler(data);
			else if(data.startsWith("chat:")) 	chatHandler(data);
			else if(data.equals("init")) 		generalSetup();
			else if(data.startsWith("challenge:")) challengeHandler(data);
			else if(data.startsWith("match:")) 	matchHandler(data);
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
		}
	}

	@Override
	public void generalSetup() {
		sendData("info:-userJoin " + ClientSettings.username);
		recieveData();
		sendData("info:-sendUserList");
		recieveData();
		
		HearthController.mainContainer = HearthController.containers.get("mainMenu");
	}


	public void disconnect(){
		out.close();
		try {
			in.close();
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void disconnect(String message) {
		out.println("disconnect:" + message);
	}
	
	public void sendChat(String message){
		System.out.println("[Client] Sending chat: " + message);
		sendData("chat:[" + ClientSettings.username +"]"+ message);
	}
	
	private static ArrayList<String> chatLogs = new ArrayList<String>();
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getChatLogs(){return (ArrayList<String>) chatLogs.clone();}
	
	public void chatHandler(String data){
		// Special Cases
		if(data.contains("-logsinc")){
			String data2;
			chatLogs = new ArrayList<String>();
			try {
				while(!(data2 = in.readLine()).contains("-logsfin")){
					chatLogs.add(data2);
				}
			} catch (IOException e) {e.printStackTrace();}
			return;
		}
		
		// General Chat
		chatLogs.add(data.substring(5));
		((HearthWindow) HearthController.mainWindow).updateChat();
	}
	
	public void infoHandler(String data){
		if(data.contains("-userListInc")){
			String data2;
			HearthController.usersOnHost = new ArrayList<String>();
			try {
				while(!(data2 = in.readLine()).contains("-userListFin")){
					HearthController.usersOnHost.add(data2);
				}
			} catch (IOException e) {e.printStackTrace();}
			HearthController.usersOnHost.remove(ClientSettings.username);
			return;
		}
		
		if(data.contains("-userNameChange")){
			if(!data.contains(ClientSettings.username)){
				HearthController.usersOnHost.remove(data.split("->")[0].replace("info:-userNameChange ", ""));
				HearthController.usersOnHost.add(data.split("->")[1]);
			}
			chatLogs.add("   -- " + data.split("->")[0].replace("info:-userNameChange ", "") + " -> " + data.split("->")[1] + " --");
			((HearthWindow) HearthController.mainWindow).updateChat();
		}
		
		if(data.contains("-userJoin")){
			String username = data.replace("info:-userJoin ", "");
			chatLogs.add("   -- " + username + " Has Joined Chat. --");
			((HearthWindow) HearthController.mainWindow).updateChat();
			
			if(!username.equals(ClientSettings.username))HearthController.usersOnHost.add(username);
		}
	}
	
	public void challengeHandler(String data) throws IOException{
		if(data.contains("-init ")){
			if(inMatch) {
				sendData("challenge:-deny");
				return;
			}
			
			// TODO: Implement this into an in-window menu system, rather than an additional popup. (Priority: Low)
			String opponentUsername = data.split("-")[1].replace("init ", "");
			ruleSet = data.split("-")[2].replace("ruleSet ", "");
			
			int accept = JOptionPane.showOptionDialog(null, "You have recieved a challenge from " + opponentUsername + " using the " + ruleSet + "rule set.", "Challenge", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Accept", "Deny"}, "Accept");
			
			if(accept != 0){
				sendData("challenge:-deny");
				inMatch = false;
			}
			else{
				sendData("challenge:-accept");
				HearthController.containers.put("match", new ClientMatchContainer());
				inMatch = true;
				
			}
			return;
		}
		
		if(data.contains("-reject")){
			JOptionPane.showMessageDialog(null, "Challenge Rejected.");
			return;
		}
		
		if(data.contains("-chooseDeck")){
			String deckName = JOptionPane.showInputDialog(null, "Which deck would you like to use?");
			List<File> a = Arrays.asList((new File("resources/json/decks")).listFiles());
			
			while(!a.contains(deckName)){
				JOptionPane.showMessageDialog(null, "That is not a deck in your resources/json/decks folder.");
				deckName = JOptionPane.showInputDialog(null, "Which deck would you like to use?");
			}
			
			deck = JsonHandler.loadDeck(deckName);
			sendData("challenge:-deckChosen " + deckName + "-version " + deck.version);
			
			String data2 = in.readLine();
			if(data2.equals("load:successful")) return;
			
			sendData(deck.name);
			sendData(deck.version);
			sendData(deck.ruleSetName);
			sendData(deck.deckClass.name());
			
			sendData("-idListInc");
			for(String s : deck.cardIDs) sendData(s);
			sendData("-idListFin");
			
		}
	}
	
	public void matchHandler(String data) throws NumberFormatException, IOException{
		if(data.contains("-contInitInc")){
			
			boolean first = Boolean.parseBoolean(in.readLine());
			int oppDeckSize = Integer.parseInt(in.readLine());
			
			((ClientMatchContainer) HearthController.containers.get("match")).setupMatch(first, ruleSet, deck, oppDeckSize);
			return;
		}
	}

	public void endGame() {
		inMatch = false;
	}
	
}
