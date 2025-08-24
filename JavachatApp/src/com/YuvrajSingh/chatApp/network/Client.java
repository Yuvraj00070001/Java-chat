package com.YuvrajSingh.chatApp.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JTextArea;

import com.YuvrajSingh.chatApp.utils.ConfigReader;

public class Client {
	OutputStream out;
	InputStream in;
	Socket socket;
	ClientWorker worker;
	JTextArea textArea;
	public Client(JTextArea textArea) throws UnknownHostException, IOException {
		int PORT=Integer.parseInt(ConfigReader.getValue("PORTNO"));
		socket=new Socket(ConfigReader.getValue("SERVER_IP"),PORT);
		out=socket.getOutputStream();
		in=socket.getInputStream();
		this.textArea=textArea;
		readMessage();
		/*System.out.println("Client comes.....");
		System.out.println("Enter the Message send to the server...");
		Scanner scanner=new Scanner(System.in);
		String message=scanner.nextLine();
		OutputStream out=socket.getOutputStream();
		out.write(message.getBytes());
		System.out.println("Message send to server..");
		scanner.close();
		out.close();
		socket.close();
		
		
	}*/}
	public void sendMessage(String message) throws IOException {
		message=message+"\n";
		out.write(message.getBytes());
		
	}
	public void readMessage() {
		worker=new ClientWorker(in,textArea);
		worker.start();
	}
	

}