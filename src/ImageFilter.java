//package teamwork;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ImageFilter
{
	private int width;
	private int height;
	private BufferedImage src;
	
	public Image imageMinFilter(BufferedImage srcImage, int size)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();	
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        int minR = 255, minG = 255, minB = 255;
        int newB = 0, newR = 0, newG = 0;
        size = (size - 1) / 2;
        for (int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
        		minR = minG = minB = 255;
        		for (int m = -size; m <= size; m++)
        		{
        			if (i + m < 0 || i + m >= height)
        				continue;
        			for (int n = -size; n <= size; n++)
        			{
        				if (j + n < 0 || j + n >= width)
        					continue;
        				newR = ((oldData[(i + m) * width + j + n] & 0xff0000) >> 16) & 0xff;
        				newG = ((oldData[(i + m) * width + j + n] & 0xff00) >> 8) & 0xff;
        				newB = oldData[(i + m) * width + j + n] & 0xff;
        				if (newR < minR)
        					minR = newR;
        				if (newG < minG)
        					minG = newG;
        				if (newB < minB)
        					minB = newB;		
        			}
        		}
        		newData[i * width + j] = 0xff000000;
        		newData[i * width + j] = newData[i * width + j] | minR << 16 | minG << 8 | minB;
        	}
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	public Image imageMaxFilter(BufferedImage srcImage, int size)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        int maxR = 0, maxG = 0, maxB = 0;
        int newB = 0, newR = 0, newG = 0;
        size = (size - 1) / 2;
        for (int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
        		maxR = maxG = maxB = 0;
        		for (int m = -size; m <= size; m++)
        		{
        			if (i + m < 0 || i + m >= height)
        				continue;
        			for (int n = -size; n <= size; n++)
        			{
        				if (j + n < 0 || j + n >= width)
        					continue;
        				newR = ((oldData[(i + m) * width + j + n] & 0xff0000) >> 16) & 0xff;
        				newG = ((oldData[(i + m) * width + j + n] & 0xff00) >> 8) & 0xff;
        				newB = oldData[(i + m) * width + j + n] & 0xff;
        				if (newR > maxR)
        					maxR = newR;
        				if (newG > maxG)
        					maxG = newG;
        				if (newB > maxB)
        					maxB = newB;		
        			}
        		}
        		newData[i * width + j] = 0xff000000;
        		newData[i * width + j] = newData[i * width + j] | maxR << 16 | maxG << 8 | maxB;
        	}
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	public Image imageMedianFilter(BufferedImage srcImage, int size)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        int newB = 0, newR = 0, newG = 0, tmp = 0;
        int [] arrR;
        int [] arrG;
        int [] arrB;
        int oriSize = size;
        size = (size - 1) / 2;
        for (int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
        		arrR = new int[oriSize * oriSize];
        		arrG = new int[oriSize * oriSize];
        		arrB = new int[oriSize * oriSize];
        		int index = 0;
        		for (int m = -size; m <= size; m++)
        		{
        			if (i + m < 0 || i + m >= height)
        				continue;
        			for (int n = -size; n <= size; n++)
        			{
        				if (j + n < 0 || j + n >= width)
        					continue;
        				arrR[index] = ((oldData[(i + m) * width + j + n] & 0xff0000) >> 16) & 0xff;
        				arrG[index] = ((oldData[(i + m) * width + j + n] & 0xff00) >> 8) & 0xff;
        				arrB[index] = oldData[(i + m) * width + j + n] & 0xff;
        				index++;
        			}
        		}
        		for (int x = 0; x < index; x++)
        			for (int y = 0; y < index - 1; y++)
        			{
        				if (arrR[y] > arrR[y + 1])
        				{
        					tmp = arrR[y];
        					arrR[y] = arrR[y + 1];
        					arrR[y + 1] = tmp;
        				}
        				if (arrG[y] > arrG[y + 1])
        				{
        					tmp = arrG[y];
        					arrG[y] = arrG[y + 1];
        					arrG[y + 1] = tmp;
        				}
        				if (arrB[y] > arrB[y + 1])
        				{
        					tmp = arrB[y];
        					arrB[y] = arrB[y + 1];
        					arrB[y + 1] = tmp;
        				}
        			}
        		newR = arrR[index / 2];
        		newG = arrG[index / 2];
        		newB = arrB[index / 2];
        		newData[i * width + j] = 0xff000000;
        		newData[i * width + j] = newData[i * width + j] | newR << 16 | newG << 8 | newB;
        	}
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}

	public void showMining(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
    	main.setCommonFrame(4, 3, "Min Filter", 300, 200, 5);
            
        JLabel sizeL = new JLabel("Size of the Min filters:(pixel)");
        main.commonFrame.add(sizeL);
        final JTextField sizetxt = new JTextField("3");
        main.commonFrame.add(sizetxt);
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int size = Integer.parseInt(sizetxt.getText());
                if (size >= main.pi2.getWidth(null) - 10 || size >= main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The value of side is too large!");
                else
                {
                    ImageFilter myFilt = new ImageFilter();
                    BufferedImage buf = main.getBufferedImage();
                    main.pi2 = myFilt.imageMinFilter(buf, size);
                    sizetxt.setText("");
                    main.commonFrame.dispose();
                    main.Draw();
                }
            }
        });
        main.commonFrame.setVisible(true);
	}
	
	public void showMaxing(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(4, 3, "Max Filter", 300, 200, 5);
            
        JLabel sizeL = new JLabel("Size of the Max filters:(pixel)");
        main.commonFrame.add(sizeL);
        final JTextField sizetxt = new JTextField("3");
        main.commonFrame.add(sizetxt);
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int size = Integer.parseInt(sizetxt.getText());
                if (size >= main.pi2.getWidth(null) - 10 || size >= main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The value of side is too large!");
                else
                {
                    ImageFilter myFilt = new ImageFilter();
                    BufferedImage buf = main.getBufferedImage();
                    main.pi2 = myFilt.imageMaxFilter(buf, size);
                    sizetxt.setText("");
                    main.commonFrame.dispose();
                    main.Draw();
                }
            }
        });
        main.commonFrame.setVisible(true);
	}
	
	public void showMedian(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(4, 3, "Median Filter", 300, 200, 5);
            
        JLabel sizeL = new JLabel("Size of the Median filters:(pixel)");
        main.commonFrame.add(sizeL);
        final JTextField sizetxt = new JTextField("3");
        main.commonFrame.add(sizetxt);
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int size = Integer.parseInt(sizetxt.getText());
                if (size >= main.pi2.getWidth(null) - 10 || size >= main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The value of side is too large!");
                else
                {
                    ImageFilter myFilt = new ImageFilter();
                    BufferedImage buf = main.getBufferedImage();
                    main.pi2 = myFilt.imageMedianFilter(buf, size);
                    sizetxt.setText("");
                    main.commonFrame.dispose();
                    main.Draw();
                }
            }
        });
        main.commonFrame.setVisible(true);
	}
}
