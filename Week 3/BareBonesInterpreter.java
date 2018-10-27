package interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BareBonesInterpreter
{
	private static ArrayList<Variable> variables;
	
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		boolean fileNotFound = true;
		ArrayList<String> program = new ArrayList<String>();
		variables = new ArrayList<Variable>();
		while(fileNotFound)
		{
			System.out.println("Please enter the adress of the file you would like to interpret. (eg: D:\\Desktop\\BareBones2.txt)");
			String file = in.nextLine();
			System.out.println(file);
			try
			{
				fileNotFound = false;
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(new File(file));
				
				while (scanner.hasNext())
				{
					program.add(scanner.nextLine());
				}
			}
			catch (FileNotFoundException e)
			{
				fileNotFound = true;
				System.out.println("File not Found");
			}
		}
		int[] loops = new int[program.size()];
		for(int i = 0; i<program.size(); i++)
		{
			System.out.print(i+". ");
			System.out.println(program.get(i));
			String line = program.get(i);
			if(line.contains("clear") && !line.contains("//"))				//Clear
			{
				
				String name = line.substring(line.indexOf("clear")+6,line.indexOf("clear")+7); //assumes that variables can only be used after they have been cleared
				Variable extra = null;
				for(Variable v: variables)
				{
					if(v.getName().equals(name))
					{
						extra = v; 
					}
				}
				variables.remove(extra);
				variables.add(new Variable(name,0));
				printVariables();
			}
			
			if(line.contains("incr") && !line.contains("//"))			//Increment 
			{
				String variable = line.substring(line.indexOf("incr")+5,line.indexOf("incr")+6);
				for(Variable v: variables)
				{
					if(v.getName().equals(variable))
					{
						v.incr();
					}
				}
				printVariables();
			}
			
			if(line.contains("decr") && !line.contains("//"))			//Decrement
			{
				String variable = line.substring(line.indexOf("decr")+5,line.indexOf("decr")+6);
				for(Variable v: variables)
				{
					if(v.getName().equals(variable))
					{
						v.decr();
					}
				}
				printVariables();
			}
			
			if(line.contains("while") && !line.contains("//"))			//While
			{
				String variable = line.substring(line.indexOf("while")+6,line.indexOf("while")+7);
				int target = Integer.parseInt(line.substring(line.indexOf("while")+12,line.indexOf("while")+13));
				for(Variable v: variables)
				{
					if(v.getName().equals(variable))			//While and end work by storing their line numbers (i) in to an array. each pair of one positive and negative number represents the line of the while loop and end statement respectively
					{
						if(v.getValue()>target)
						{
							boolean assigned = false;
							for(int j=0; j<loops.length; j++)
							{
								if(loops[j]==i-1)
								{
									assigned = true;
								}
							}
							if(assigned==false)
							{
								for(int j=0; j<loops.length; j+=2)
								{
									if(loops[j]==0)
									{
										loops[j] = i-1;
										j=loops.length+1;
									}
								}
							}
						}
						else
						{
							for(int j=0; j<loops.length; j+=2)
							{
								if(loops[j]==i-1)
								{
									i = -loops[j+1];
								}
							}
						}
					}
				}
				printVariables();
			}
			
			if(line.contains("end") && !line.contains("//"))			//End
			{
				boolean assigned = false;
				for(int j=0; j<loops.length; j++)
				{
					if(loops[j]==-(i))
					{
						assigned = true;
						i = loops[j-1];
					}
				}
				if(!assigned)
				{
					for(int j=loops.length-1; j>=0; j--)
					{
						if(loops[j]>0 && loops[j+1]==0)
						{
							loops[j+1] =-(i);
							i = loops[j];
							j=-3;
						}
					}
				}
				printVariables();
			}
																						//New Math(+,-,X,%)
			if(line.contains("add") && !line.contains("//"))
			{
				String variable = line.substring(line.indexOf("add")+4,line.indexOf("add")+5);
				String argument = line.substring(line.indexOf("add")+6,line.indexOf("add")+7);
				int value = 0;
				for(Variable v: variables)
				{
					if(v.getName().equals(argument))
					{
						value = v.getValue();
					}
				}
				for(Variable v: variables)
				{
					if(v.getName().equals(variable))
					{
						v.add(value);
					}
				}
				printVariables();
			}
			if(line.contains("sub") && !line.contains("//"))
			{
				String variable = line.substring(line.indexOf("sub")+4,line.indexOf("sub")+5);
				String argument = line.substring(line.indexOf("sub")+6,line.indexOf("sub")+7);
				int value = 0;
				for(Variable v: variables)
				{
					if(v.getName().equals(argument))
					{
						value = v.getValue();
					}
				}
				for(Variable v: variables)
				{
					if(v.getName().equals(variable))
					{
						v.sub(value);
					}
				}
				printVariables();
			}
			if(line.contains("mul") && !line.contains("//"))
			{
				String variable = line.substring(line.indexOf("mul")+4,line.indexOf("mul")+5);
				String argument = line.substring(line.indexOf("mul")+6,line.indexOf("mul")+7);
				int value = 0;
				for(Variable v: variables)
				{
					if(v.getName().equals(argument))
					{
						value = v.getValue();
					}
				}
				for(Variable v: variables)
				{
					if(v.getName().equals(variable))
					{
						v.mul(value);
					}
				}
				printVariables();
			}
			if(line.contains("div") && !line.contains("//"))
			{
				String variable = line.substring(line.indexOf("div")+4,line.indexOf("div")+5);
				String argument = line.substring(line.indexOf("div")+6,line.indexOf("div")+7);
				int value = 0;
				for(Variable v: variables)
				{
					if(v.getName().equals(argument))
					{
						value = v.getValue();
					}
				}
				for(Variable v: variables)
				{
					if(v.getName().equals(variable))
					{
						v.div(value);
					}
				}
				printVariables();
			}
		}
		System.out.println("done");
		printVariables();
		
		
	}
	
	public static void printVariables()
	{
		System.out.print("                          ");
		for(Variable v: variables)
		{
			System.out.print(v.getName()+" = "+v.getValue()+" ");
		}
		System.out.println();
	}
}
