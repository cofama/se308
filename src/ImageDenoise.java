//package teamwork;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ImageDenoise
{
	public Image addSaltAndPepper(BufferedImage src, double salt, double pepper)
	{
		int width = src.getWidth();
        int height = src.getHeight();
        int[] Data = new int[width * height];
        src.getRGB(0, 0, width, height, Data, 0, src.getWidth());
        
        int index = 0;
        int saltSize = (int)(Data.length * salt);
        int pepperSize = (int)(Data.length * pepper);

        for (int i = 0; i < saltSize; i++)
        {
        	int row = (int)(Math.random() * height);
        	int col = (int)(Math.random() * width);
        	index = row * width + col;
        	Data[index] = 0xffffffff;
        }
        for (int i = 0; i < pepperSize; i++)
        {
        	int row = (int)(Math.random() * height);
        	int col = (int)(Math.random() * width);
        	index = row * width + col;
        	Data[index] = 0xff000000;
        }
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, Data, 0, width));
	}

	public Image addGaussianNoise(BufferedImage src, double sigma, double mean)
	{
		int width = src.getWidth();
        int height = src.getHeight();
        int[] Data = new int[width * height];
        src.getRGB(0, 0, width, height, Data, 0, src.getWidth());
        int[] newData = new int[width * height];
        double gauss = 0;
        for(int i = 0; i < height; i++)
        {
        	int red = 0, green = 0, blue = 0;
        	for (int j = 0; j < width; j++)
        	{
        		red = (Data[i * width + j] >> 16) & 0xff;
        		green = (Data[i * width + j] >> 8) & 0xff;
                blue = Data[i * width + j] & 0xff;
                gauss = generateGaussian(sigma, mean);
                red = (int)(red + gauss);
                green = (int)(green + gauss);
                blue = (int)(blue + gauss);
                if (red > 255) red = 255;
                if (red < 0) red = 0;
                if (green > 255) green = 255;
                if (green < 0) green = 0;
                if (blue > 255) blue = 255;
                if (blue < 0) blue = 0;
                newData[i * width + j] = 0xff000000 | (red << 16) | (green << 8) | blue;
        	}
        }
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	double generateGaussian(double sigma, double mean)
	{
		double x1 = Math.random();
		if (x1 < 1e-100) x1 = 1e-100;
		x1 = -2 * Math.log(x1);
		double x2 = Math.random() * 2 * Math.PI;
		return (Math.sqrt(x1) * Math.cos(x2)) * sigma + mean;
	}
	
	public void Gauss(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(5, 2, "Add Gaussion Noise", 320, 230, 5);
        
        JLabel M = new JLabel("Input the mean:");
        main.commonFrame.add(M);
        final JTextField mean = new JTextField("0");
        main.commonFrame.add(mean);
        JLabel S = new JLabel("Input the sigma:");
        main.commonFrame.add(S);
        final JTextField sigma = new JTextField("40");
        main.commonFrame.add(sigma);
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	double average = Double.parseDouble(mean.getText());
            	double sig = Double.parseDouble(sigma.getText());
            	ImageDenoise myDenoise = new ImageDenoise();
                BufferedImage buf = main.getBufferedImage();
                main.pi2 = myDenoise.addGaussianNoise(buf, sig, average);
                main.commonFrame.dispose();
                main.Draw();                    
            }
        });
        main.commonFrame.setVisible(true);
	}
	
	public void pepp(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(5, 2, "Add Salt & Pepper", 320, 230, 5);
        
        JLabel salt = new JLabel("Input the probability of Salt noise (0 ~ 1):");
        main.commonFrame.add(salt);
        final JTextField saltProbability = new JTextField("0.2");
        main.commonFrame.add(saltProbability);
        JLabel pepper = new JLabel("Input the probability of Pepper noise (0 ~ 1):");
        main.commonFrame.add(pepper);
        final JTextField pepperProbability = new JTextField("0.2");
        main.commonFrame.add(pepperProbability);
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	double saltPro = Double.parseDouble(saltProbability.getText());
            	double pepperPro = Double.parseDouble(pepperProbability.getText());
            	ImageDenoise myDenoise = new ImageDenoise();
                BufferedImage buf = main.getBufferedImage();
                main.pi2 = myDenoise.addSaltAndPepper(buf, saltPro, pepperPro);
                main.commonFrame.dispose();
                main.Draw();                   
            }
        });
        main.commonFrame.setVisible(true);
	}
}
