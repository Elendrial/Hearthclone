package me.elendrial.hearthclone.containers;

import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.helpers.TextureHelper;
import me.elendrial.hearthclone.HearthController;
import me.elendrial.hearthclone.action.Action;
import me.elendrial.hearthclone.server.ServerProtocol;

public class ServerMatchContainer extends BaseContainer{
	public ServerProtocol player1, player2;
	public boolean p1Ready = false, p2Ready = false;
	public boolean p1HeroPower, p2HeroPower;
	
	public ServerMatchContainer(){
		this.players = 2;
		this.identifier = "match";
		this.background = TextureHelper.loadTexture("textures/background/", "Match.png", this);
	}
	
	public void takeAction(Action a){
		if(isValid(a)){
			player1.sendData("match:-actionSuccess " + a.getStringRepresentation());
			player2.sendData("match:-actionSuccess " + a.getStringRepresentation());
		}
		else{
			if(turn%2 == 0) player1.sendData("match:-actionFail");
			else player2.sendData("match:-actionFail");
		}
	}
	
	public boolean isValid(Action a){
		return false;
	}
	
	public void endTurn(){
		
	}

	public void selectFirst(ServerProtocol a, ServerProtocol b) {
		if(HearthController.rand.nextBoolean()){
			player1 = a;
			player2 = b;
		}
		else{
			player1 = b;
			player2 = a;
		}
	}
	
	public void setReady(ServerProtocol a){
		if(a.equals(player1)) p1Ready = true;
		else p2Ready = true;
		
		if(p1Ready && p2Ready){
			setupClients();
		}
	}
	
	public void setupClients(){
		player1.sendData("match:-contInitInc");
		
		player1.sendData("match:-contInitFin");
		
		player2.sendData("match:-init oppDeckSpecs");
	}
}
