//package teamwork;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import javax.swing.JButton;

public class ImageSharpen
{
	private int width;
	private int height;
	private BufferedImage src;
	
	public Image imageLaplace(BufferedImage srcImage)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();	
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        int [] Laplace = {0, 1, 0, 1, -4, 1, 0, 1, 0};    //3 * 3 Laplacian filter
        
        for (int h = 0; h < height; h++)
        {
            for (int w = 0; w < width; w++)
            {
                int alpha = oldData[h * width + w] >> 24 & 0xff;
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;
                int index = 0;
				for (int i = -1; i <= 1; i++)
                {
                    int newi = h + i;
                    if (newi < 0 || newi >= height)
                        newi = h;
                    for (int j = -1; j <= 1; j++)
                    {
                        int newj = w + j;
                        if (newj < 0 || newj >= width)
                            newj = w;
                        int red = oldData[newi * width + newj] >> 16 & 0xff;
                        int green = oldData[newi * width + newj] >> 8 & 0xff;
                        int blue = oldData[newi * width + newj] & 0xff;
                        sumR += red * Laplace[index];
                        sumG += green * Laplace[index];
                        sumB += blue * Laplace[index];
                        index++;
                    }
                }
                sumR = (oldData[h * width + w] >> 16 & 0xff) - sumR;
                sumG = (oldData[h * width + w] >> 8 & 0xff) - sumG;
                sumB = (oldData[h * width + w] & 0xff) - sumB;
                newData[h * width + w] = check(alpha) << 24 | check(sumR) << 16 | check(sumG) << 8 | check(sumB);
            }
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	public int check(int num)
	{
        return num < 0 ? 0 : ((num > 255 ? 255 : num));
    }
	
	public Image imageSoble(BufferedImage srcImage, int state, int add)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();	
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        
        int [] Soble1 = {-1, -2, -1, 0, 0, 0, 1, 2, 1};
        int [] Soble2 = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
        int [] Soble = new int[8];
        
        if (state == 1)
            Soble = Soble1;
        else
        	Soble = Soble2;
        for (int h = 0; h < height; h++)
        {
            for (int w = 0; w < width; w++)
            {
                int alpha = oldData[h * width + w] >> 24 & 0xff;
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;
                int index = 0;
				for (int i = -1; i <= 1; i++)
                {
                    int newi = h + i;
                    if (newi < 0 || newi >= height)
                        newi = h;
                    for (int j = -1; j <= 1; j++)
                    {
                        int newj = w + j;
                        if (newj < 0 || newj >= width)
                            newj = w;
                        int red = oldData[newi * width + newj] >> 16 & 0xff;
                        int green = oldData[newi * width + newj] >> 8 & 0xff;
                        int blue = oldData[newi * width + newj] & 0xff;
                        sumR += red * Soble[index];
                        sumG += green * Soble[index];
                        sumB += blue * Soble[index];
                        index++;
                    }
                }
				if (add == 1)
				{
                    sumR = (oldData[h * width + w] >> 16 & 0xff) + sumR;
                    sumG = (oldData[h * width + w] >> 8 & 0xff) + sumG;
                    sumB = (oldData[h * width + w] & 0xff) + sumB;
				}
                newData[h * width + w] = check(alpha) << 24 | check(sumR) << 16 | check(sumG) << 8 | check(sumB);
            }
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	
	public void showLaplace(mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
    	ImageSharpen mySharpen = new ImageSharpen();
        BufferedImage buf = main.getBufferedImage();
        main.pi2 = mySharpen.imageLaplace(buf);
        main.Draw();
	}
	
	public void showSobel(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(4, 3, "Sobel", 320, 200, 5);

        CheckboxGroup cb = new CheckboxGroup();
        final Checkbox S1 = new Checkbox("Sobel = {-1, -2, -1, 0, 0, 0, 1, 2, 1}", true, cb);
        Checkbox S2 = new Checkbox("Sobel = {-1, 0, 1, -2, 0, 2, -1, 0, 1}", false, cb);
        main.commonFrame.add(S1);
        main.commonFrame.add(S2);
        final Checkbox S = new Checkbox("Add original picture into the Sobel filtered result", false);
        main.commonFrame.add(S);
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int state = 0, add = 0;
                if (S1.getState())
                    state = 1;
                else
                    state = 2;
                if (S.getState())
                    add = 1;
                else
                    add = 0;
                ImageSharpen mySharpen = new ImageSharpen();
                BufferedImage buf = main.getBufferedImage();
                main.pi2 = mySharpen.imageSoble(buf, state, add);
                main.commonFrame.dispose();
                main.Draw();                   
            }
        });
        main.commonFrame.setVisible(true);
	}

}
