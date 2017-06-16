package me.elendrial.hearthclone.containers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.decks.StandardDeck;
import me.elendrial.cardGameBase.helpers.TextureHelper;
import me.elendrial.cardGameBase.server.GameClient;
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
	
	@Override // TODO: Turn this into a selection, eg: clicking on usernames shown above selects them, then you press challenge etc etc... (Priority: Low)
	public void mousePressed(MouseEvent arg){
		if(!(arg.getX() < 100 && arg.getY() < 100)) return;
		
		String user = JOptionPane.showInputDialog(null, "Which player would you like to challenge?");
		if(GameClient.isRunning()) GameClient.getProtocol().sendData("challenge:-init " + user);
	}
	
}
