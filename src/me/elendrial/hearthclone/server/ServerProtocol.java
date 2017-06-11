package me.elendrial.hearthclone.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.time.Instant;
import java.util.ArrayList;

import me.elendrial.cardGameBase.server.GameProtocol;
import me.elendrial.cardGameBase.server.GameServer;
import me.elendrial.hearthclone.HearthController;

public class ServerProtocol extends GameProtocol{
	
	private static ArrayList<String> chatLogs = new ArrayList<String>();
	private String username;
	
	public ServerProtocol() {super();}

	public ServerProtocol(Socket socket, int id) {super(socket, id);}


	@Override
	public void run() {
		while(!socket.isClosed()) recieveData();
	}
	
	@Override
	public void sendData(String s){
		System.out.println("[Server-" + id + "]: Sending >>>>>>>> " + s);
		super.sendData(s);
	}
	
	@Override
	public void recieveData() {
		String data;
		try {
		//	System.out.println("[Server-" + id + "]: Waiting to recieve data");
			data = in.readLine();
			if(data.startsWith("disconnect:")) disconnect();
			else if(data.startsWith("info:")) infoHandler(data);
			else if(data.startsWith("chat:")) chatHandler(data);
			else if(data.equals("init")) generalSetup();

			System.out.println("[Server-" + id + "][From Client]: " + data);
		} catch (IOException e) {
			if(e instanceof SocketException) disconnect();
			else e.printStackTrace();
		}
	}

	@Override
	public void generalSetup() {
		sendData("init");
	}
	
	public void disconnect(){
		chatHandler("chat:-- Has Left Chat --");
		out.close();
		try {
			in.close();
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
		HearthController.usersOnHost.remove(username);
		GameServer.getConnections().remove(this);
	}
	
	@Override
	public void disconnect(String message) {
		out.println("disconnect:" + message);
		disconnect();
	}
	
	public void chatHandler(String data){
		// Special Cases
		if(data.contains("-sendall")){
			this.sendData("chat:-logsinc");
			for(String s : chatLogs) this.sendData(s);
			this.sendData("chat:-logsfin");
			return;
		}
		
		// General Chat
		data = "[" + Instant.now().toString().substring(11, 19) + "]" + data.substring(5);
		chatLogs.add(data);
		
		for(GameProtocol connection : GameServer.getConnections()) connection.sendData("chat:" + data);
		
	}
	
	public void infoHandler(String data){
		if(data.contains("-sendUserList")){
			this.sendData("info:-userListInc");
			for(String s : HearthController.usersOnHost) this.sendData(s);
			this.sendData("info:-userListFin");
			return;
		}
		
		if(data.contains("-nameChange")){
			HearthController.usersOnHost.remove(username);
			String newUsername = data.replace("info:-nameChange ", "");
			HearthController.usersOnHost.add(newUsername);
			for(GameProtocol connection : GameServer.getConnections()) connection.sendData("info:-userNameChange " + username + "->" + newUsername);
			username = newUsername;
		}
		
		if(data.contains("-userJoin")){
			System.out.println("DATA :: " + data);
			username = data.replace("info:-userJoin ", "");
			HearthController.usersOnHost.add(username);
			for(GameProtocol connection : GameServer.getConnections()) connection.sendData("info:-userJoin " + username);
			
		}
	}
}
