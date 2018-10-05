package emailExtraction;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main 
{

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		String userID = "";
		userID = in.nextLine();
		String webAdress = "https://www.ecs.soton.ac.uk/people/"+userID;
		try 
		{
			URL adress = new URL(webAdress);
		} catch (MalformedURLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try 
		{
			String source = getURLSource(webAdress);
			int startIndex = source.indexOf("property=\"name\">", 0);
			int endIndex = source.indexOf("<", startIndex);
			String name = source.substring(startIndex+16, endIndex);
			System.out.print(name);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	public static String getURLSource(String url) throws IOException
    {
        URL urlObject = new URL(url);
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        return toString(urlConnection.getInputStream());
    }

    private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }

}
