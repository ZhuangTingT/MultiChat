package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame implements Runnable
{
	static JTextArea textarea;
	static JTextField textfield;
	String textToServer;
	String textFromServer;
	Socket socket;
	DataOutputStream toServer;
	DataInputStream fromServer;
	JButton button = null;
	
	GUI(Socket socket) throws UnknownHostException, IOException
	{
		this.socket = socket;
		this.toServer = new DataOutputStream(socket.getOutputStream());
		this.fromServer = new DataInputStream(socket.getInputStream());
		
        setTitle("Chat");
		setSize(500, 500);
		textarea = new JTextArea(25, 30);
		textarea.setEditable(false);
		textfield = new JTextField(25);
		
		button = new JButton("send");		
		sendListener send_lis = new sendListener();
		button.addActionListener(send_lis);
		
		JScrollPane sp = new JScrollPane(textarea);
		JPanel panel = new JPanel();
		this.add(sp, BorderLayout.CENTER);
		panel.add(textfield);
		panel.add(button);
		this.add(panel, BorderLayout.SOUTH);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	class sendListener implements ActionListener
	{
		/**
		 * actionPerformed: 按下send键后的响应
		 */
		public void actionPerformed(ActionEvent e)
		{
			String buttonName = e.getActionCommand(); // 获得按键名称
			if("send".equals(buttonName))
			{
				textToServer = textfield.getText();
				try 
				{
					toServer.writeUTF(textToServer);
					toServer.flush();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				
				textfield.setText("");
			}
	    }
	}

	public void run() 
	{
		try 
		{
			while((textFromServer=fromServer.readUTF()) != null)
			{
				textarea.append(textFromServer + "\n");
			}
		}
		catch(SocketException se)
		{
			System.out.println(socket.getInetAddress()+"退出聊天室。");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				socket.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
