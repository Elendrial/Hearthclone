package me.elendrial.hearthclone.server;

import java.io.IOException;
import java.util.ArrayList;

import me.elendrial.cardGameBase.server.GameProtocol;
import me.elendrial.hearthclone.HearthController;
import me.elendrial.hearthclone.display.HearthDisplay;

public class ClientProtocol extends GameProtocol{

	@Override
	public void run() {
		while(!socket.isClosed()) recieveData();
	}
	
	@Override
	public void sendData(String s){
		System.out.println("[Client]: Sending >>>>>>>> " + s);
		super.sendData(s);
	}
	
	@Override
	public void recieveData() {
		String data;
		try {
		//	System.out.println("[Client]: Waiting to recieve data");
			data = in.readLine();
			if(data.contains("disconnect:")) disconnect();
			else if(data.startsWith("info:")) infoHandler(data);
			else if(data.startsWith("chat:")) chatHandler(data);
			else if(data.equals("init")) generalSetup();

			System.out.println("[Client]: From Server: " + data);
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
		}
	}

	@Override
	public void generalSetup() {
		sendData("info:-userJoin " + ClientSettings.username);
		recieveData();
		sendData("info:-sendUserList");
		recieveData();
		
		HearthController.mainContainer = HearthController.containers.get("mainMenu");
	}


	public void disconnect(){
		out.close();
		try {
			in.close();
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void disconnect(String message) {
		out.println("disconnect:" + message);
	}
	
	public void sendChat(String message){
		System.out.println("[Client] Sending chat: " + message);
		sendData("chat:[" + ClientSettings.username +"]"+ message);
	}
	
	private static ArrayList<String> chatLogs = new ArrayList<String>();
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getChatLogs(){return (ArrayList<String>) chatLogs.clone();}
	
	
	public void chatHandler(String data){
		// Special Cases
		if(data.contains("-logsinc")){
			String data2;
			chatLogs = new ArrayList<String>();
			try {
				while(!(data2 = in.readLine()).contains("-logsfin")){
					chatLogs.add(data2);
				}
			} catch (IOException e) {e.printStackTrace();}
			return;
		}
		
		// General Chat
		chatLogs.add(data.substring(5));
		((HearthDisplay) HearthController.mainWindow.display).updateChat();
	}
	

	public void infoHandler(String data){
		if(data.contains("-userListInc")){
			String data2;
			HearthController.usersOnHost = new ArrayList<String>();
			try {
				while(!(data2 = in.readLine()).contains("-userListFin")){
					HearthController.usersOnHost.add(data2);
				}
			} catch (IOException e) {e.printStackTrace();}
			HearthController.usersOnHost.remove(ClientSettings.username);
			return;
		}
		
		if(data.contains("-userNameChange")){
			if(!data.contains(ClientSettings.username)){
				HearthController.usersOnHost.remove(data.split("->")[0].replace("info:-userNameChange ", ""));
				HearthController.usersOnHost.add(data.split("->")[1]);
			}
			chatLogs.add("   -- " + data.split("->")[0].replace("info:-userNameChange ", "") + " -> " + data.split("->")[1] + " --");
			((HearthDisplay) HearthController.mainWindow.display).updateChat();
		}
		
		if(data.contains("-userJoin")){
			String username = data.replace("info:-userJoin ", "");
			chatLogs.add("   -- " + username + " Has Joined Chat. --");
			((HearthDisplay) HearthController.mainWindow.display).updateChat();
			
			if(!username.equals(ClientSettings.username))HearthController.usersOnHost.add(username);
		}
	}
	
}
