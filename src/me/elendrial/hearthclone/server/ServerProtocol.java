package me.elendrial.hearthclone.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.time.Instant;
import java.util.ArrayList;

import me.elendrial.cardGameBase.server.GameProtocol;
import me.elendrial.cardGameBase.server.GameServer;

public class ServerProtocol extends GameProtocol{
	
	public ServerProtocol() {super();}


	public ServerProtocol(Socket socket, int id) {super(socket, id);}


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
			System.out.println("[Server-" + id + "]: Waiting to recieve data");
			data = in.readLine();
			if(data.contains("disconnect:")) disconnect();
			else if(data.startsWith("chat:"))chatHandler(data);
			else out.println(data);

			System.out.println("[Server-" + id + "][From Client]: " + data);
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
		out.close();
		try {
			in.close();
		} catch (IOException e) {e.printStackTrace();}
	}

	private static ArrayList<String> chatLogs = new ArrayList<String>();
	
	public void chatHandler(String data){
		// Special Cases
		if(data.contains("-sendall")){
			this.sendData("chat:-logsinc-");
			for(String s : chatLogs) this.sendData(s);
			this.sendData("chat:-logsfin-");
			return;
		}
		
		// General Chat
		data = "[" + Instant.now() + "]" + data.substring(5);
		chatLogs.add(data);
		
		for(GameProtocol connection : GameServer.getConnections()) connection.sendData("chat:" + data);
		
	}
	
}
