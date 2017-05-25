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
	public void sendData() {
		
	}

	@Override
	public void recieveData() {
		String data;
		try {
			System.out.println("[Client]: Waiting to recieve data");
			data = in.readLine();
			if(data.contains("disconnect:")) disconnect();
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
		sendChat("-- Has Joined Chat --");
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

}
