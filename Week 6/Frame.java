package circles;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame extends JFrame
{
	public Frame(int[][] image)
	{
		setSize(1000, 1000);
		BufferedImage bImage = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i<bImage.getWidth(); i++)
		{
			for(int j = 1; j<bImage.getHeight(); j++)
			{
				int p = (0xff<<24)|(image[i][j]<<16)|(image[i][j]<<8)|(image[i][j]);
				
				bImage.setRGB(i, j, p);
			}
		}
		ImageIcon icon = new ImageIcon(bImage);
		add(new JLabel(icon));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
