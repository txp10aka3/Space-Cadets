package interpreter;

public class Variable
{
	private String variableName;
	private int value;
	
	public Variable(String name, int value)
	{
		variableName = name;
		this.value = value;
	}
	
	public String getName()
	{
		return(variableName);
	}
	public int getValue()
	{
		return(value);
	} 
	public void incr()
	{
		value++;
	}
	public void decr()
	{
		value--;
		if(value<0)
		{
			value = 0;
		}
	}
}
