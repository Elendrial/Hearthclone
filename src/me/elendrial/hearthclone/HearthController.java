package me.elendrial.hearthclone;

import java.util.ArrayList;
import java.util.HashMap;

import me.elendrial.cardGameBase.Controller;
import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.server.GameClient;
import me.elendrial.cardGameBase.server.GameProtocol;
import me.elendrial.cardGameBase.server.GameServer;
import me.elendrial.hearthclone.display.HearthWindow;

public class HearthController extends Controller{
	
	public static boolean host = false;
	public static boolean connected = false;
	public static ArrayList<String> usersOnHost = new ArrayList<String>();
	public static HashMap<String, BaseContainer> containers = new HashMap<String, BaseContainer>();
	
	public static void startGame(String windowTitle, int windowWidth, int windowHeight){
		startGame(new HearthWindow(windowTitle, windowWidth, windowHeight));
	}
	
	public static void startGameAsHost(String windowTitle, int windowWidth, int windowHeight, int port, Class<? extends GameProtocol> protocol){
		host = true;
		Controller.startGameAsHost(windowTitle, windowWidth, windowHeight, port, protocol);
	}
	
	public static void host(int port, Class<? extends GameProtocol>  protocol){
		host = true;
		GameServer.hostServer(port, protocol);
	}
	
	public static void connect(String computer, int port, GameProtocol protocol){
		GameClient.connectToServer(computer, port, protocol);
		connected = true;
	}

	public static void disconnect() {
		if(host)
			GameServer.closeServer();
		else if(connected)
			GameClient.disconnectFromServer("Client Leaving.");
		host = false;
		connected = false;
	}
	
}
