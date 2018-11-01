package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Server 
{
	private static List<Socket> clients = new ArrayList<>();
	private static List<BufferedReader> scanners = new ArrayList<>();
	private static ServerSocket socket;
	private static Thread thread = new Thread() 
	{
		@Override
		public void run()
		{
			while(!socket.isClosed())
			{
				try
				{
					System.out.println("Connecting on 3333");
					Socket client = socket.accept();
					clients.add(client);
					scanners.add(new BufferedReader(new InputStreamReader(client.getInputStream())));

				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			
		}
	};

	public static void main(String[] args) 
	{
		try 
		{
			socket = new ServerSocket(3333);
			thread.start();
			while(!socket.isClosed())
			{
				ArrayList<Socket> tempClients = new ArrayList<>(clients);
				ArrayList<BufferedReader> tempScanners = new ArrayList<>(scanners);
				int i = 0;
				for(BufferedReader scanner : tempScanners)
				{
					if(scanner.ready())
					{
						String clientMessage = scanner.readLine();
						for(Socket receiver : tempClients)
						{
							if(!tempClients.get(i).equals(receiver))
							{
								receiver.getOutputStream().write(clientMessage.getBytes());
							}
						}
						System.out.println(clientMessage);
					}
					i++;
				}
				try 
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}	
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			
	}

}
