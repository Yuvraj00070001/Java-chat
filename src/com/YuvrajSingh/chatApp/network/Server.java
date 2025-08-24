package com.YuvrajSingh.chatApp.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.YuvrajSingh.chatApp.utils.ConfigReader;

public class Server {
	ServerSocket serverSocket;
	ArrayList<ServerWorker> workers=new ArrayList<>();
	public Server() throws IOException {
		int PORT=Integer.parseInt(ConfigReader.getValue("PORTNO"));
		serverSocket=new ServerSocket(PORT);
		handleClientRequest();
	}
	
	public void handleClientRequest() throws IOException {
		while(true) {
			Socket clientSocket=serverSocket.accept();
			//per client per thread
			ServerWorker serverWorker=new ServerWorker(clientSocket, this);
			System.out.println("Server starts and waiting for clients to join...");
			workers.add(serverWorker);
			serverWorker.start();
			
		}
			
	}
	/*SINGLE CLIENT 
	 * public Server() throws IOException {
		int PORT=Integer.parseInt(ConfigReader.getValue("PORTNO"));
		serverSocket=new ServerSocket(PORT);
		System.out.println("Srever Started and waiting for the Client Connection....");
		Socket socket=serverSocket.accept();//handshaking
		System.out.println("Client Joins the Srever");
		InputStream in=socket.getInputStream();
		byte arr[]=in.readAllBytes();
		String str=new String(arr);
		System.out.println("Message rec From the Client "+str);
		in.close();
		socket.close();
	}*/
	public static void main(String[] args) throws IOException {
		Server server=new Server();
	}

}