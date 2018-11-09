package spirograph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Graph extends JPanel
{
	private ArrayList<Point2D> points;
	private Color color;
	private int size;
	
	public Graph(ArrayList<Point2D> points, Color color, int size)
	{
		this.points = points;
		this.color = color;
		this.size = size;
		setSize(size,size);
		
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		for(Point2D point :points)
		{
			Ellipse2D.Double dot = new Ellipse2D.Double(point.getX()+500, point.getY()+450,1,1);
			g2.setColor(color);
			g2.fill(dot);
		}
	}
	
}
