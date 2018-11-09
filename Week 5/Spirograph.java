package spirograph;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Spirograph 
{
	private double outerRadius;
	private double innerRadius;
	private double offsetRadius;
	private int loops;
	private double res;
	private Color penColor;
	private ArrayList<Point2D> graph = new ArrayList<Point2D>();
	
	public Spirograph(double outer, double inner, int loopc, double resolution)
	{
		outerRadius = outer;
		innerRadius = inner;
		offsetRadius = 0;
		loops = loopc;
		res=resolution;
	}	
	public ArrayList<Point2D> createGraph(double offset)
	{
		offsetRadius = offset;
		for(double t = 0; t<=loops*2*Math.PI; t+=res)
		{
			double x = ((outerRadius-innerRadius)*Math.cos(t) + offsetRadius*Math.cos(((outerRadius-innerRadius)/innerRadius)*t));
			double y = ((outerRadius-innerRadius)*Math.sin(t) - offsetRadius*Math.sin(((outerRadius-innerRadius)/innerRadius)*t));
			Point2D point = new Point2D.Double(x,y);
			graph.add(point);
		}
		return(graph);
	}
	
	public Color getColor()
	{
		return(penColor);
	}
	public int getSize()
	{
		return((int) outerRadius*2);
	}
}
