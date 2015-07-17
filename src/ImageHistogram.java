//package teamwork;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageHistogram
{
	private BufferedImage src;
	private int width;
	private int height;
	private BufferedImage histImage;
	
	public ImageHistogram(BufferedImage srcImage)
	{
		histImage = new BufferedImage(560, 300, BufferedImage.TYPE_4BYTE_ABGR);
		this.src = srcImage;
	}
	
	public BufferedImage getHistogramBlue()
	{
		width = src.getWidth();
        height = src.getHeight();
        int[] Data = new int[width * height];
        int[] hist = new int[256];
        for (int i = 0; i < hist.length; i++)
        	hist[i] = 0;
        src.getRGB(0, 0, width, height, Data, 0, src.getWidth());
        for (int i = 0; i < width * height; i++)
		{
			int blue = (Data[i]) & 0xff;
			hist[blue]++;
		}
        Graphics2D g2d = histImage.createGraphics();
        g2d.setPaint(Color.WHITE);        //set the background color
        g2d.fillRect(0, 0, 560, 300);
        g2d.setPaint(Color.BLACK);
        g2d.drawLine(20, 270, 540, 270);    //draw x line
        g2d.drawLine(20, 270, 20, 20);    //draw y line
        
        g2d.setPaint(Color.BLACK);
        int max = 0;        // find the max value in the hist array
	    for (int i = 0; i < 256; i++)
	        if (hist[i] > max)
	        	max = hist[i];
        float rate = 200.0f/((float)max);
        for (int i = 0; i < hist.length * 2; i += 2)
        {
        	int frequency = (int)(hist[i / 2] * rate);
        	g2d.drawLine(22 + i, 269, 22 + i, 269 - frequency);        //draw the content of the histogram
        }
        g2d.setPaint(Color.RED);
        g2d.drawString("0", 18, 290);
        g2d.drawString("255", 525, 290);
		return histImage;
	}
	
	public BufferedImage getHistogramRed()
	{
		width = src.getWidth();
        height = src.getHeight();
        int[] Data = new int[width * height];
        int[] hist = new int[256];
        for (int i = 0; i < hist.length; i++)
        	hist[i] = 0;
        src.getRGB(0, 0, width, height, Data, 0, src.getWidth());
        for (int i = 0; i < width * height; i++)
		{
			int red = ((Data[i] & 0xff0000) >> 16) & 0xff;
			hist[red]++;
		}
        Graphics2D g2d = histImage.createGraphics();
        g2d.setPaint(Color.WHITE);        //set the background color
        g2d.fillRect(0, 0, 560, 300);
        g2d.setPaint(Color.BLACK);
        g2d.drawLine(20, 270, 540, 270);    //draw x line
        g2d.drawLine(20, 270, 20, 20);    //draw y line
        
        g2d.setPaint(Color.BLACK);
        int max = 0;        // find the max value in the hist array
	    for (int i = 0; i < 256; i++)
	        if (hist[i] > max)
	        	max = hist[i];
        float rate = 200.0f/((float)max);
        for (int i = 0; i < hist.length * 2; i += 2)
        {
        	int frequency = (int)(hist[i / 2] * rate);
        	g2d.drawLine(22 + i, 269, 22 + i, 269 - frequency);        //draw the content of the histogram
        }
        g2d.setPaint(Color.RED);
        g2d.drawString("0", 18, 290);
        g2d.drawString("255", 525, 290);
		return histImage;
	}
	
	public BufferedImage getHistogramGreen()
	{
		width = src.getWidth();
        height = src.getHeight();
        int[] Data = new int[width * height];
        int[] hist = new int[256];
        for (int i = 0; i < hist.length; i++)
        	hist[i] = 0;
        src.getRGB(0, 0, width, height, Data, 0, src.getWidth());
        for (int i = 0; i < width * height; i++)
		{
        	int green = ((Data[i] & 0x00ff00) >> 8) & 0xff;
			hist[green]++;
		}
        Graphics2D g2d = histImage.createGraphics();
        g2d.setPaint(Color.WHITE);        //set the background color
        g2d.fillRect(0, 0, 560, 300);
        g2d.setPaint(Color.BLACK);
        g2d.drawLine(20, 270, 540, 270);    //draw x line
        g2d.drawLine(20, 270, 20, 20);    //draw y line
        
        g2d.setPaint(Color.BLACK);
        int max = 0;        // find the max value in the hist array
	    for (int i = 0; i < 256; i++)
	        if (hist[i] > max)
	        	max = hist[i];
        float rate = 200.0f/((float)max);
        for (int i = 0; i < hist.length * 2; i += 2)
        {
        	int frequency = (int)(hist[i / 2] * rate);
        	g2d.drawLine(22 + i, 269, 22 + i, 269 - frequency);        //draw the content of the histogram
        }
        g2d.setPaint(Color.RED);
        g2d.drawString("0", 18, 290);
        g2d.drawString("255", 525, 290);
		return histImage;
	}
}
