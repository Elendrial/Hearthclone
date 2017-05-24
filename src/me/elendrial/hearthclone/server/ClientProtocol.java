package me.elendrial.hearthclone.server;

import java.io.IOException;
import java.net.SocketException;

import me.elendrial.cardGameBase.server.GameProtocol;

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
		//	else out.println(data);

			System.out.println("[Client]: From Server: " + data);
		} catch (IOException e) {
			e.printStackTrace();
			if(e instanceof SocketException) disconnect();
		}
	}

	@Override
	public void sendSetup() {
	}

	@Override
	public void recieveSetup() {
	}

	public void disconnect(){
		out.close();
		try {
			in.close();
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

}
