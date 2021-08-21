package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain
{
	GUI gui = null;
	PrintWriter writer = null;
	Socket socket = null;
	String serverName = "127.0.0.1";
	int port = 8080;
	
	ClientMain() throws UnknownHostException, IOException
	{
		// ���������������
		socket = new Socket(serverName, port);
		if(socket != null)
		System.out.println("�ͻ��������ɹ�");

        gui = new GUI(socket);
        Thread thread = new Thread(gui);
        thread.start();
	}
	
	public static void main(String[] args) throws IOException
	{
		new ClientMain();
	}
}
