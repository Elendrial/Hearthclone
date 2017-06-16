package me.elendrial.hearthclone.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import me.elendrial.cardGameBase.server.GameClient;
import me.elendrial.hearthclone.HearthController;
import me.elendrial.hearthclone.server.ClientProtocol;
import me.elendrial.hearthclone.server.ClientSettings;
import me.elendrial.hearthclone.server.ServerProtocol;

public class MenuListener implements ActionListener{
	
	
	public static MenuListener self = new MenuListener();

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof JMenuItem){
			int port;
			String hostname;
			switch(((JMenuItem)(arg0.getSource())).getText()){
			case "Host":
				port = Integer.parseInt(JOptionPane.showInputDialog(null, "Server port:"));
				HearthController.host(port, ServerProtocol.class);
				break;
			case "Connect":
				hostname = JOptionPane.showInputDialog(null, "Host:");
				port = Integer.parseInt(JOptionPane.showInputDialog(null, "Server port:"));
				HearthController.connect(hostname, port, new ClientProtocol());
				break;
			case "Chat":
				((ClientProtocol)GameClient.getProtocol()).sendChat("Testing testing 1212");
				break;
			case "Exit":
				HearthController.disconnect();
				HearthController.stopGame();
				System.exit(0);
				break;
			case "Temp":
				ClientSettings.username = JOptionPane.showInputDialog(null, "Chat Name:").replace("-", "_").trim();
				if(GameClient.isRunning()) GameClient.getProtocol().sendData("info:-nameChange " + ClientSettings.username);
				break;
			}
		}
	}

}
