package me.elendrial.hearthclone.containers;

import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.helpers.TextureHelper;
import me.elendrial.hearthclone.game.Action;
import me.elendrial.hearthclone.server.ServerProtocol;

public class ServerMatchContainer extends BaseContainer{
	public ServerProtocol player1, player2;
	
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
}
