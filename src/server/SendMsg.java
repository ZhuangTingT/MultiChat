package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

class SendMsg extends Thread
{
	Socket client;
	DataOutputStream toClient;
	ArrayList<DataOutputStream> toClients;
	String text = null;

	public SendMsg(Socket client, DataOutputStream toClient, ArrayList<DataOutputStream> toClients) 
	{
		this.client = client;
		this.toClient = toClient;
		this.toClients = toClients;
	}

	public void run() 
	{
		try 
		{
			DataInputStream fromClient = new DataInputStream(client.getInputStream());
			while((text=fromClient.readUTF()) != null)
			{
				text = client.getInetAddress()+ ": " + text;
				for(DataOutputStream writer : toClients)
				{
					writer.writeUTF(text);
					writer.flush();
				}
			}
		}
		catch(SocketException se)
		{
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			toClients.remove(toClient);
			try
			{
				for(DataOutputStream writer : toClients)
				{
					System.out.println(client.getInetAddress()+"退出聊天室");
					writer.writeUTF(client.getInetAddress()+"退出聊天室");
					writer.flush();
				}
				client.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
}
