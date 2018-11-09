package spirograph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class Frame extends JFrame
{
	Spirograph spiro = new Spirograph(150,105,10, 0.001);
	Graph graph = new Graph(spiro.createGraph(50),Color.WHITE, spiro.getSize());
	
	public Frame()
	{
		setSize(1000, 1000);
		this.setBackground(Color.BLACK);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBounds(0,0,1000,100);
		panel.setPreferredSize(new Dimension(1000,100));
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setBackground(new Color(150,150,250));
		gbc.gridx = 1;
		gbc.gridy = 0;
		JLabel labelO = new JLabel("Outer Radius: ");
		panel.add(labelO, gbc);
		gbc.gridx = 2;
		JTextField outer = new JTextField(5);
		outer.setText("150");
		panel.add(outer, gbc);
		gbc.gridx = 3;
		JLabel labelI = new JLabel("Inner Radius: ");
		panel.add(labelI, gbc);
		gbc.gridx = 4;
		JTextField inner = new JTextField(5);
		inner.setText("105");
		panel.add(inner, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		panel.add(new JLabel("Advanced: "),gbc);
		gbc.gridy = 1;
		gbc.gridx = 1;
		JLabel lbl = new JLabel("Loops: ");
		panel.add(lbl, gbc);
		gbc.gridy = 1;
		gbc.gridx = 2;
		JTextField loops = new JTextField(5);
		loops.setText("10");
		panel.add(loops, gbc);
		gbc.gridy = 1;
		gbc.gridx = 3;
		JLabel lblr = new JLabel("Resolution: ");
		panel.add(lblr, gbc);
		gbc.gridy = 1;
		gbc.gridx = 4;
		JTextField res = new JTextField(5);
		res.setText("0.01");
		panel.add(res, gbc);
		gbc.gridy = 0;
		gbc.gridx = 5;
		JButton btn = new JButton("Create Spirograph");
		btn.setContentAreaFilled(false);
		panel.add(btn, gbc);
		gbc.gridx = 6;
		panel.add(new JLabel("      "),gbc);
		gbc.gridx = 7;
		panel.add(new JLabel("Pen Offset: "),gbc);	
		gbc.gridx = 8;
		JTextField offset = new JTextField(5);
		offset.setText("0");
		panel.add(offset, gbc);
		gbc.gridx = 9;
		JButton draw = new JButton("Draw");
		draw.setContentAreaFilled(false);
		panel.add(draw,gbc);
		gbc.gridx = 9;
		gbc.gridy = 1;
		JButton ani = new JButton("Generate");
		ani.setContentAreaFilled(false);
		panel.add(ani,gbc);
		add(panel);
		graph.setLocation(500,450);
		add(graph);
		
		
		
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				remove(graph);
				graph.revalidate();
				spiro = new Spirograph(Double.parseDouble(outer.getText()) , Double.parseDouble(inner.getText()), Integer.parseInt(loops.getText()), Double.parseDouble(res.getText()));			
				offset.setText(""+10);
			}
			
		});
		ani.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				double start = Double.parseDouble(offset.getText());
				Color color = new Color((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
				remove(graph);
				for(int i = 0; i<110; i+=10)		
				{
					graph = new Graph(spiro.createGraph(Double.parseDouble(offset.getText())), color, spiro.getSize());
					graph.setPreferredSize(new Dimension(400,400));
					add(graph);
					offset.setText(""+(start+i));
					graph.revalidate();
//					try 
//					{
//						Thread.sleep(1);
//					} catch (InterruptedException e) 
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
			
		});
		draw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				remove(graph);
				graph = new Graph(spiro.createGraph(Double.parseDouble(offset.getText())), new Color((int) (Math.random()*255),(int)(Math.random()*255),(int) (Math.random()*255)), spiro.getSize());
				graph.setPreferredSize(new Dimension(400,400));
				add(graph);
				offset.setText(""+(Double.parseDouble(offset.getText())+10));
				repaint();
				graph.revalidate();
				
			}
			
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void update(int i)
	{
		
	}

	public static void main(String[] args) 
	{
		new Frame();
	}

}
