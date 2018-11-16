package circles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CircleDetector 
{
	private static int[][] gx = {{1,0,-1},{2,0,-2},{1,0,-1}};
	private static int[][] gy = {{1,2,1},{0,0,0},{-1,-2,-1}};
	private static final int THRESHOLD = 120;
	private static final int CIRCLE_ERROR = 200;
	
	public static void main(String[] args) 
	{
		try 
		{
			BufferedImage bufferedImage = ImageIO.read(new File("D:\\Desktop\\tennis.jpg"));
			int[][] image = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
			int[][] colorImage = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
			for(int i = 0; i<bufferedImage.getWidth(); i++)
			{
				for(int j = 1; j<bufferedImage.getHeight(); j++)
				{
					int pxl = bufferedImage.getRGB(i,j);
					colorImage[i][j] = pxl;
					int a = (pxl>>24)&0xff;
					int r = (pxl>>16)&0xff;
					int g = (pxl>>8)&0xff;
					int b = pxl&0xff;
					image[i][j] = (r+g+b)/3;
				}
			}
			int[][] edges = sobel(image);
			int[][] detected = hough(edges, colorImage);
			new Frame(edges);
			new Frame(detected);
			
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
	public static int[][] sobel(int[][] image)
	{
		double[][] magnitude = new double[image.length][image[0].length];
		int[][] temp = new int[3][3];
		
		for(int i = 1; i<image.length-1; i++)
		{
			for(int j = 1; j<image[0].length-1; j++)
			{
				temp[0][0] = image[i-1][j-1];
				temp[1][0] = image[i][j-1];
				temp[2][0] = image[i+1][j-1];
				temp[0][1] = image[i-1][j];
				temp[1][1] = image[i][j];
				temp[2][1] = image[i+1][j];
				temp[0][2] = image[i-1][j+1];
				temp[1][2] = image[i][j+1];
				temp[2][2] = image[i+1][j+1];
				int sumx = sum(gx, temp);
				int sumy = sum(gy, temp);
				magnitude[i][j] = Math.sqrt(sumx*sumx + sumy*sumy);
			}
		}
		
		int threshold = THRESHOLD; 
		int[][] detected = new int[image.length][image[0].length];
		for(int i = 1; i<image.length-1; i++)
		{
			for(int j = 1; j<image[0].length-1; j++)
			{
				if(Math.abs(magnitude[i][j])>=threshold)
				{
					detected[i][j] = 254;
				}
				else
				{
					detected[i][j] = 0;
				}
			}
		}
		return(detected);
	}
	
	public static int[][] hough(int[][] edgeImage, int[][] ogImage)
	{
		int[][][] acc = new int[edgeImage.length][edgeImage[0].length][(int)Math.sqrt(edgeImage[0].length*edgeImage[0].length+edgeImage.length*edgeImage.length)/2+1];
		for(int i = 0; i<edgeImage.length; i++)
		{
			for(int j = 0; j<edgeImage[0].length; j++)
			{
				if(edgeImage[i][j] != 0)
				{
					for(int k = 10; k<acc[0][0].length;k++)
					{
						for(double t = 0; t<2*Math.PI; t+=0.1)
						{
							int x = (int)( j-Math.cos(t)*k);
							int y = (int)( i- Math.sin(t)*k);
							if(y<edgeImage.length && x<edgeImage[0].length && x>=0 && y>=0)
							{
								acc[y][x][k]++;
							}
						}
						
					}
				}
			}
		}
		int max = 0;
		int indexi = 0;
		int indexj = 0;
		int indexk = 0;
		
		for(int i = 0; i<edgeImage.length; i++)
		{
			for(int j = 0; j<edgeImage[0].length; j++)
			{
				for(int k = 0; k<acc[0][0].length;k++)
				{
					if(acc[i][j][k]>max )
					{
						max=acc[i][j][k];
						indexi = i;
						indexj = j;
						indexk = k;
					}
				}
			}
		}
//		System.out.println(max);
//		System.out.println("i"+indexi+"j"+indexj+"k"+indexk);
		for(int i = 0; i<ogImage.length; i++)
		{
			for(int j = 0; j<ogImage[0].length; j++)
			{
				if((i-indexi)*(i-indexi)+(j-indexj)*(j-indexj) >= (indexk*indexk)-CIRCLE_ERROR && (i-indexi)*(i-indexi)+(j-indexj)*(j-indexj) <= indexk*indexk+CIRCLE_ERROR)
				{
					ogImage[i][j] = 0;
					
					//Making line thicker
					ogImage[i+1][j+1] = 0;
					ogImage[i+1][j] = 0;
					ogImage[i+1][j-1] = 0;	
					ogImage[i][j+1] = 0;
					
					ogImage[i][j-1] = 0;
					ogImage[i-1][j-1] = 0;
					ogImage[i-1][j] = 0;
					ogImage[i-1][j+1] = 0;
					
				}
			}
		}
		System.out.println("Circle Detected");
		return(ogImage);
		
	}
	
	public static int sum(int[][] a, int[][] b)
	{
		int sum = 0;
		for(int i = 0; i<a.length; i++)
		{
			for(int j = 0; j<a[0].length; j++)
			{
				sum += a[i][j]*b[i][j];
			}
		}
		return(sum);
	}
}
