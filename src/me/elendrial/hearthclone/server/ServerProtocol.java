package me.elendrial.hearthclone.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.time.Instant;
import java.util.ArrayList;

import me.elendrial.cardGameBase.server.GameProtocol;
import me.elendrial.cardGameBase.server.GameServer;
import me.elendrial.hearthclone.HearthController;
import me.elendrial.hearthclone.cardVars.ClassEnum;
import me.elendrial.hearthclone.containers.ServerMatchContainer;
import me.elendrial.hearthclone.decks.HearthstoneDeck;
import me.elendrial.hearthclone.general.JsonHandler;
import me.elendrial.hearthclone.ruleSets.RuleSet;

public class ServerProtocol extends GameProtocol{
	
	private static ArrayList<String> chatLogs = new ArrayList<String>();
	private String username;
	protected ServerProtocol opponent;
	public HearthstoneDeck deck;
	protected RuleSet ruleSet;
	protected ServerMatchContainer match;
	
	public ServerProtocol() {super();}

	public ServerProtocol(Socket socket, int id) {super(socket, id);}


	@Override
	public void run() {
		while(!socket.isClosed()) recieveData();
	}
	
	@Override
	public void sendData(String s){
		System.out.println("[Server-" + id + "]: Sending >>>>>>>> " + s);
		super.sendData(s);
	}
	
	//TODO: Rewrite this code to be less messy and higher quality. (Priority: Low)
	@Override
	public void recieveData() {
		String data;
		try {
		//	System.out.println("[Server-" + id + "]: Waiting to recieve data");
			data = in.readLine();
			System.out.println("[Server-" + id + "]: Recieved <<<<<<<< " + data);
			
			if(data.startsWith("disconnect:")) 	disconnect();
			else if(data.startsWith("info:")) 	infoHandler(data);
			else if(data.startsWith("chat:")) 	chatHandler(data);
			else if(data.startsWith("challenge:")) challengeHandler(data);
			else if(data.equals("init")) 		generalSetup();

		} catch (IOException e) {
			if(e instanceof SocketException) disconnect();
			else e.printStackTrace();
		}
	}

	@Override
	public void generalSetup() {
		sendData("init");
	}
	
	public void disconnect(){
		chatHandler("chat:-- Has Left Chat --");
		out.close();
		try {
			in.close();
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
		HearthController.usersOnHost.remove(username);
		GameServer.getConnections().remove(this);
	}
	
	@Override
	public void disconnect(String message) {
		out.println("disconnect:" + message);
		disconnect();
	}
	
	public void chatHandler(String data){
		// Special Cases
		if(data.contains("-sendall")){
			this.sendData("chat:-logsinc");
			for(String s : chatLogs) this.sendData(s);
			this.sendData("chat:-logsfin");
			return;
		}
		
		// General Chat
		data = "[" + Instant.now().toString().substring(11, 19) + "]" + data.substring(5);
		chatLogs.add(data);
		
		for(GameProtocol connection : GameServer.getConnections()) connection.sendData("chat:" + data);
		
	}
	
	public void infoHandler(String data){
		if(data.contains("-sendUserList")){
			this.sendData("info:-userListInc");
			for(String s : HearthController.usersOnHost) this.sendData(s);
			this.sendData("info:-userListFin");
			return;
		}
		
		if(data.contains("-nameChange")){
			HearthController.usersOnHost.remove(username);
			String newUsername = data.replace("info:-nameChange ", "");
			HearthController.usersOnHost.add(newUsername);
			for(GameProtocol connection : GameServer.getConnections()) connection.sendData("info:-userNameChange " + username + "->" + newUsername);
			username = newUsername;
		}
		
		if(data.contains("-userJoin")){
			if(!HearthController.usersOnHost.contains(data.replace("info:-userJoin ", ""))){
				username = data.replace("info:-userJoin ", "");
				HearthController.usersOnHost.add(username);
				for(GameProtocol connection : GameServer.getConnections()) connection.sendData("info:-userJoin " + username);
			}
			else{
				this.sendData("info:-userJoinFail"); //TODO: Implement this on the client and check it works (Priority: Low-Medium)
			}
		}
	}

	//TODO: Finish challenge handler (Priority: High)(Mostly done I think)
	public void challengeHandler(String data) throws IOException{
		if(data.contains("-init ")){
			// --> Before: check other client agrees to match with selected rule set.
			String opponentUsername = data.split("-")[1].replace("ini	t ", "");
			
			if(!HearthController.usersOnHost.contains(opponentUsername) || opponentUsername == username){
				sendData("challenge:-reject -reason:noUserWithName");
				return;
			}
			// TODO: Check both players & server have RuleSet.
			for(GameProtocol connection : GameServer.getConnections()) 
				if(((ServerProtocol) connection).username.equals(opponentUsername)) connection.sendData("challenge:-chalBy " + username + " -ruleSet " + data.split("-")[2].replace("ruleSet ", ""));
			
			String data2 = in.readLine();
			if(data2.contains("-deny")){
				sendData("challenge:-reject -reason:deniedBy" + opponentUsername); 
				return;
			}
			
			for(GameProtocol connection : GameServer.getConnections()) if(((ServerProtocol) connection).username.equals(opponentUsername)){ 
				((ServerProtocol) connection).opponent = this;
				this.opponent = (ServerProtocol) connection;
			}
			
			
			// First : Ask which deck each player would like to use
			sendData("challenge:-chooseDeck");
			opponent.sendData("challenge:-chooseDeck");
		}
		
		if(data.contains("-deckChosen ")){
			// Second: Check card collections are compatible & decks agree with ruleset.
			// TODO: Rewrite this in a better way (Priority: Low)
			hasSetupContainer = false;
			
			try{
				this.deck = JsonHandler.loadDeck(data.split("-")[1].replace("deckChosen ", "") + "-" + username + ".json");
				if(deck.version.equals(data.split("-")[2].replace("version ", ""))){
					sendData("load:successful");
					loadCards();
					
					hasSetupContainer = true;
					setupGameContainer();
					match.setReady(this);
					
					return;
				}
			}
			catch(IOException e){}
			sendData("load:fail");
			
			deck = new HearthstoneDeck();
			deck.name = in.readLine();
			deck.version = in.readLine();
			deck.ruleSetName = in.readLine();
			deck.deckClass = ClassEnum.valueOf(in.readLine());
			
			String data2;
			int count = 0;
			deck.cardIDs = new String[ruleSet.maxTotalDeckSize];
			
			in.readLine();
			while(!(data2 = in.readLine()).contains("-idListFin")){
				deck.cardIDs[count] = data2;
				count++;
			}
			
			loadCards();
			
			hasSetupContainer = true;
			setupGameContainer();
			match.setReady(this);
			
			//TODO: Somehow check that the other player has all the cards WITHOUT sending the cards to them (Priority: Medium-High)
			// Maybe ask them to send all the sets they have + hashcodes for them? If hashcodes match then go ahead, if not check individual cards? Might take a while :/
			// For the moment we assume that they are compatible though
			
			return;
		}
	}
	
	protected void loadCards(){
		try {
			JsonHandler.loadAllCards();
			
			
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	protected boolean hasSetupContainer = false;
	protected void setupGameContainer(){
		// Third : Setup a MatchContainer with these two decks
		if(this.opponent.hasSetupContainer) return;
		
		match = new ServerMatchContainer();
		match.decks = new HearthstoneDeck[]{this.deck, opponent.deck};
		match.selectFirst(this, opponent);
		match.ruleSet = ruleSet;
		
		opponent.match = this.match;
	}
	
}
