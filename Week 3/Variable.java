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
	public void add(int n)
	{
		value+=n;
	}
	public void sub(int n)
	{
		value-=n;
	}
	public void mul(int n)
	{
		value*=n;
	}
	public void div(int n)
	{
		value/=n;
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
