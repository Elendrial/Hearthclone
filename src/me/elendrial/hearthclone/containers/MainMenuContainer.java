package me.elendrial.hearthclone.containers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.decks.StandardDeck;
import me.elendrial.cardGameBase.helpers.TextureHelper;
import me.elendrial.hearthclone.HearthController;

public class MainMenuContainer extends BaseContainer{
	
	public MainMenuContainer(){
		this.decks = new StandardDeck[]{};
		this.players = 0;
		this.identifier = "mainMenu";
		this.background = TextureHelper.loadTexture("textures/background/", "MainMenu.png", this);
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
		Color c = g.getColor();
		g.setColor(Color.red);
		g.drawString("Users connected to Host:", 7, 45);
		g.drawString("You", 10, 60);
		for(String s : HearthController.usersOnHost) g.drawString(s, 10, 60 + 15 * (HearthController.usersOnHost.indexOf(s)+1));
		g.setColor(c);
	}
	
	@Override
	public void mousePressed(MouseEvent arg){
		
	}
	
}
