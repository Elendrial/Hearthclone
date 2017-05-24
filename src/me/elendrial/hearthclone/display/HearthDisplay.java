package me.elendrial.hearthclone.display;

import java.awt.Graphics;

import me.elendrial.cardGameBase.display.Display;
import me.elendrial.cardGameBase.display.Window;
import me.elendrial.hearthclone.HearthController;
import me.elendrial.hearthclone.server.ClientProtocol;

public class HearthDisplay extends Display{

	private static final long serialVersionUID = 3783433136653351527L;

	public HearthDisplay(Window window) {
		super(window);
	}
	
	public void render(Graphics g){
		super.render(g);
		HearthOverlay.render(g); // Yes, this entire class is technically here for just this one line... at the moment
	}
	
	// This might not be the right place to keep this, but it's not entirely wrong and I'm not sure where else to put it.
	public void updateChat(){
		String text = "";
		
		for(String s : ClientProtocol.getChatLogs()){
			text += "\n" + s;
		}
		
		((HearthWindow)HearthController.mainWindow).chatBox.setText(text);
	}
	
}
