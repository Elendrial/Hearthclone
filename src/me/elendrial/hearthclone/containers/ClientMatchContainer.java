package me.elendrial.hearthclone.containers;

import java.awt.event.MouseEvent;

import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.helpers.TextureHelper;
import me.elendrial.cardGameBase.server.GameClient;
import me.elendrial.hearthclone.game.Action;

public class ClientMatchContainer extends BaseContainer{
	
	public ClientMatchContainer(){
		this.players = 2;
		this.identifier = "match";
		this.background = TextureHelper.loadTexture("textures/background/", "Match.png", this);
	}
	
	@Override
	public void mousePressed(MouseEvent arg){
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg){
		
	}
	
	@Override
	public void mouseReleased(MouseEvent arg){
		
	}
	
	public void tryAction(Action a){
		GameClient.getProtocol().sendData("match:-action " + a.getStringRepresentation());
	}
	
	public void confirmAction(Action a){
		
	}
	
	public void endTurn(){
		
	}
	
}
