package me.elendrial.hearthclone.containers;

import java.awt.event.MouseEvent;

import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.helpers.TextureHelper;

public class MatchContainer extends BaseContainer{
	public MatchContainer(){
		this.players = 2;
		this.identifier = "match";
		this.background = TextureHelper.loadTexture("textures/background/", "Match.png", this);
	}
	
	@Override
	public void mousePressed(MouseEvent arg){
		
	}
	
	public void endTurn(){
		
	}
}
