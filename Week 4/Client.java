package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client 
{
	public static void main(String[] args) 
	{
		BufferedReader clientScanner = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter you name: ");
		String name ="";
		try
		{
			name = clientScanner.readLine();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		try 
		{
			Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 3333);
			BufferedReader serverScanner = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(!socket.isClosed())
			{
				if(clientScanner.ready())
				{
					socket.getOutputStream().write(("["+name+"]:"+clientScanner.readLine()+"\n").getBytes());
					System.out.print("sent");
				}
				if(serverScanner.ready())
				{
					System.out.println(serverScanner.readLine());
				}
			}
			
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
}
