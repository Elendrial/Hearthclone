package me.elendrial.hearthclone.display;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.cardGameBase.server.GameClient;
import me.elendrial.hearthclone.HearthController;

public class HearthOverlay {
	
	public static void render(Graphics g){
		connection(g);
	}
	
	public static void connection(Graphics g){
		if(HearthController.connected) {
			Color c = g.getColor();
			g.setColor(Color.red);
			g.drawString("Connected to: " + GameClient.getHost() + ":" + GameClient.getPort(), 5, 15);
			g.setColor(c);
		}
		else if(HearthController.host){
			Color c = g.getColor();
			g.setColor(Color.red);
			g.drawString("HOST CLIENT, THIS SHOULD NOT BE USED, I'LL FIND A BETTER WAY TO DO THIS AT SOMEPOINT I SWEAR", 5, 15);
			g.setColor(c);
		}
	}
	
}
