//package teamwork;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;

import javax.swing.JButton;

public class ImageHistEqualization
{
	private int width;
	private int height;
	private BufferedImage src;

	public Image imageHistEqual(BufferedImage srcImage)
	{
	    this.src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();
        int [] Data = new int[width * height];
        int [] newData = new int[width * height];
        int [] hist = new int[256];
        int [] histR = new int[256];
        int [] histG = new int[256];
        for (int i = 0; i < 256; i++)
        	hist[i] = histR[i] = histG[i] = 0;
        src.getRGB(0, 0, width, height, Data, 0, width);
        int blue, red, green;
        for (int i = 0; i < width * height; i++)
		{
			blue = (Data[i]) & 0xff;
			red = (((Data[i]) & 0xff0000) >> 16) & 0xff;
			green = (((Data[i]) & 0x00ff00) >> 8) & 0xff;
			hist[blue]++;
			histR[red]++;
			histG[green]++;
		}
        double[] p = new double[256];    //p == Sk    //blue
        for (int i = 0; i < 256; i++)
            p[i] = (double) (hist[i] * 255) / (width * height);  
        hist[0] = (int) (p[0] + 0.5);    //reuse the array "hist" to store the new value of each intensity value
        for (int i = 0; i < 255; i++)
        {
            p[i + 1] += p[i];
            hist[i + 1] = (int) (p[i + 1] + 0.5);
        }
        
        p = new double[256];    //red
        for (int i = 0; i < 256; i++)
            p[i] = (double) (histR[i] * 255) / (width * height);  
        histR[0] = (int) (p[0] + 0.5);    //reuse the array "hist" to store the new value of each intensity value
        for (int i = 0; i < 255; i++)
        {
            p[i + 1] += p[i];
            histR[i + 1] = (int) (p[i + 1] + 0.5);
        }
        
        p = new double[256];    //green
        for (int i = 0; i < 256; i++)
            p[i] = (double) (histG[i] * 255) / (width * height);  
        histG[0] = (int) (p[0] + 0.5);    //reuse the array "hist" to store the new value of each intensity value
        for (int i = 0; i < 255; i++)
        {
            p[i + 1] += p[i];
            histG[i + 1] = (int) (p[i + 1] + 0.5);
        }  
        int r, g, b;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
            {
                b = Data[i * width + j] & 0x0000ff;
                r = ((Data[i * width + j] & 0xff0000) >> 16) & 0xff;
                g = ((Data[i * width + j] & 0x00ff00) >> 8) & 0xff;
                newData[i * width + j] = 255 << 24 | histR[r] << 16 | histG[g] << 8 | hist[b];
            }
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	
	public Image imageHistEqualAverage(BufferedImage srcImage)
	{
	    this.src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();
        int [] Data = new int[width * height];
        int [] newData = new int[width * height];
        int [] histB = new int[256];
        int [] histR = new int[256];
        int [] histG = new int[256];
        int [] hist = new int[256];
        for (int i = 0; i < 256; i++)
        	histB[i] = histR[i] = histG[i] = hist[i] = 0;
        src.getRGB(0, 0, width, height, Data, 0, width);
        int blue, red, green;
        for (int i = 0; i < width * height; i++)
		{
			blue = Data[i] & 0xff;
			red = ((Data[i] & 0xff0000) >> 16) & 0xff;
			green = ((Data[i] & 0x00ff00) >> 8) & 0xff;
			histB[blue]++;
			histR[red]++;
			histG[green]++;
		}
        for (int i = 0; i < 256; i++)
        {
        	hist[i] = (int)((histR[i] + histG[i] + histB[i]) / 3);
        }
        double[] p = new double[256];    //p == Sk    //blue
        for (int i = 0; i < 256; i++)
            p[i] = (double) (hist[i] * 255) / (width * height);  
        hist[0] = (int) (p[0] + 0.5);    //reuse the array "hist" to store the new value of each intensity value
        for (int i = 0; i < 255; i++)
        {
            p[i + 1] += p[i];
            hist[i + 1] = (int) (p[i + 1] + 0.5);
        }
        for (int i = 0; i < 256; i++)
        {
        	histR[i] = hist[i];
        	histG[i] = hist[i];
        	histB[i] = hist[i];
        }
        int r, g, b;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
            {
                b = Data[i * width + j] & 0x0000ff;
                r = ((Data[i * width + j] & 0xff0000) >> 16) & 0xff;
                g = ((Data[i * width + j] & 0x00ff00) >> 8) & 0xff;
                newData[i * width + j] = 255 << 24 | histR[r] << 16 | histG[g] << 8 | histB[b];
            }
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	public void showHistEqual(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(4, 3, "Histogram Equalization", 320, 200, 5);

        CheckboxGroup cb = new CheckboxGroup();
        final Checkbox S1 = new Checkbox("Grey Image", true, cb);
        final Checkbox S2 = new Checkbox("RGB Image: separate histogram as the basis", false, cb);
        Checkbox S3 = new Checkbox("RGB Image: average histogram as the basis", false, cb);
        main.commonFrame.add(S1);
        main.commonFrame.add(S2);
        main.commonFrame.add(S3);
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int state = 0;
                if (S1.getState() || S2.getState())
                    state = 1;
                else
                    state = 2;
                main.myHistEqual = new ImageHistEqualization();
                BufferedImage buf = main.getBufferedImage();
                if (state == 1)
                	main.pi2 = main.myHistEqual.imageHistEqual(buf);
                else
                	main.pi2 = main.myHistEqual.imageHistEqualAverage(buf);
                main.commonFrame.dispose();
                main.Draw();                   
            }
        });
        main.commonFrame.setVisible(true);
	}
}
