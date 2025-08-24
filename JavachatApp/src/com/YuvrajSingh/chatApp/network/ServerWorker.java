package com.YuvrajSingh.chatApp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

//Thread=worker


//public class ServerWorker implements Runnable{
public class ServerWorker extends Thread{
	private Socket clientSocket;
	private InputStream in;
	private OutputStream out;
	private Server server;
	public ServerWorker(Socket clientSocket,Server server) throws IOException{
		this.server=server;
		this.clientSocket=clientSocket;
		in=clientSocket.getInputStream();
		out=clientSocket.getOutputStream();
		System.out.println("new Client comes...");
	}
	public void run() {
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String line;
		try {
		while(true) {
			 
				line=br.readLine();//
				System.out.println("line read....");
				if(line.equalsIgnoreCase("quit")) {
					break;
				}
				//out.write(line.getBytes());
				for(ServerWorker serverWorker: server.workers)
				{
					line=line+"\n";
					serverWorker.out.write(line.getBytes());
				}
				}
		}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		finally {
			try {
			if(br!=null) {
				br.close();
			}
			if(in!=null) {
				in.close();
			}
			if(out!=null) {
				out.close();
			}
			if(clientSocket!=null) {
				clientSocket.close();
			}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		
	}
}

