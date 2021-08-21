package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain 
{		
	public static void main(String[] args) throws IOException
	{
		ServerSocket server = new ServerSocket(8080);
		System.out.println("服务器启动成功");
		ArrayList<DataOutputStream> clients = new ArrayList<DataOutputStream>();
		
		while(true)
		{
			Socket socket = server.accept();
			System.out.println(socket.getRemoteSocketAddress() + ":已成功连接");
			DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
			clients.add(toClient);
			
			SendMsg thread = new SendMsg(socket, toClient, clients);
			thread.start();
		}
	}
}