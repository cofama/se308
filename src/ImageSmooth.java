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

public class ImageSmooth
{
	private int width;
	private int height;
	private BufferedImage src;
	
	public Image imageSmooth(BufferedImage srcImage, int size)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();	
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        int sum = 0;
        int num = 0, tmp = 0;
        size = (size - 1) / 2;
        for (int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
        		sum = num = 0;
        		for (int m = -size; m <= size; m++)
        		{
        			if (i + m < 0 || i + m >= height)
        				continue;
        			for (int n = -size; n <= size; n++)
        			{
        				if (j + n < 0 || j + n >= width)
        					continue;
        				sum += (oldData[(i + m) * width + j + n] & 0xff);
        				num++;
        			}
        		}
        		tmp = (int)(sum / num) & 0xff;
        		newData[i * width + j] = 255 << 24 | tmp << 16 | tmp << 8 | tmp;
        	}
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	public Image imageSmoothC(BufferedImage srcImage, int size, double Q)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();	
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        double sum1r = 0, sum2r = 0, sum1g = 0, sum2g = 0, sum1b = 0, sum2b = 0;
        int newB = 0, newR = 0, newG = 0;
        size = (size - 1) / 2;
        for (int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
        		sum1r = sum2r = sum1g = sum2g = sum1b = sum2b = 0;
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
        				if (newR == 0)
        					newR = 1;
        				if (newG == 0)
        					newG = 1;
        				if (newB == 0)
        					newB = 1;
        				sum1r += Math.pow(newR, 1 + Q);
        				sum2r += Math.pow(newR, Q);
        				sum1g += Math.pow(newG, 1 + Q);
        				sum2g += Math.pow(newG, Q);
        				sum1b += Math.pow(newB, 1 + Q);
        				sum2b += Math.pow(newB, Q);        				
        			}
        		}
        		newR = ((int)(sum1r / sum2r)) & 0xff;
        		newG = ((int)(sum1g / sum2g)) & 0xff;
        		newB = ((int)(sum1b / sum2b)) & 0xff;
        		newData[i * width + j] = 0xff000000;
        		newData[i * width + j] = newData[i * width + j] | newR << 16 | newG << 8 | newB;
        	}
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	
	public Image imageSmoothGeo(BufferedImage srcImage, int size)
	{
		src = srcImage;
	    width = src.getWidth();
        height = src.getHeight();	
        int [] newData = new int[width * height];
        int [] oldData = new int[width * height];
        src.getRGB(0, 0, width, height, oldData, 0, width);
        double mulr, mulg, mulb;
        int newB = 0, newR = 0, newG = 0;
        size = (size - 1) / 2;
        int count = 0;
        for (int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
        		mulr = mulg = mulb = 1;
        		count = 0;
        		for (int m = -size; m <= size; m++)
        		{
        			if (i + m < 0 || i + m >= height)
        				continue;
        			for (int n = -size; n <= size; n++)
        			{
        				if (j + n < 0 || j + n >= width)
        					continue;
        				count++;
        				newR = ((oldData[(i + m) * width + j + n] & 0xff0000) >> 16) & 0xff;
        				newG = ((oldData[(i + m) * width + j + n] & 0xff00) >> 8) & 0xff;
        				newB = oldData[(i + m) * width + j + n] & 0xff;
        				mulr *= newR;
        				mulg *= newG;
        				mulb *= newB;
        			}
        		}
        		double mi = 1.0 / count;
        		newR = ((int)(Math.pow(mulr, mi)) & 0xff);
        		newG = ((int)(Math.pow(mulg, mi)) & 0xff);
        		newB = ((int)(Math.pow(mulb, mi)) & 0xff);
        		if (newR > 255)  newR = 255;
        		if (newR < 0)   newR = 0;
        		if (newG > 255)  newG = 255;
        		if (newG < 0)   newG = 0;
        		if (newB > 255)  newB = 255;
        		if (newB < 0)   newB = 0;
        		newData[i * width + j] = 0xff000000;
        		newData[i * width + j] = newData[i * width + j] | newR << 16 | newG << 8 | newB;
        	}
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, newData, 0, width));
	}
	

	public void smoothA(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(4, 3, "Smooth (Arithmetic mean)", 300, 200, 5);
        
        JLabel sizeL = new JLabel("Size of the Averaging filters:(pixel)");
        main.commonFrame.add(sizeL);
        final JTextField sizeTxt = new JTextField();
        main.commonFrame.add(sizeTxt);
            
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ActionListener listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int size = Integer.parseInt(sizeTxt.getText());
                if (size >= main.pi2.getWidth(null) - 10 || size >= main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The value of side is too large!");
                else
                {
                	ImageSmooth mySmooth = new ImageSmooth();
                    BufferedImage buf = main.getBufferedImage();
                    main.pi2 = mySmooth.imageSmoothC(buf, size, 0);
                    sizeTxt.setText("");
                    main.commonFrame.dispose();
                    main.Draw();
                }
            }
        };
        ok.addActionListener(listener);
        main.commonFrame.setVisible(true);
	}
	
	public void smoothHar(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(4, 3, "Harmonic mean", 300, 200, 5);
            
        JLabel sizeL = new JLabel("Size of the Harmonic mean filters:(pixel)");
        main.commonFrame.add(sizeL);
        final JTextField sizetxt = new JTextField();
        main.commonFrame.add(sizetxt);
            
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ActionListener listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int size = Integer.parseInt(sizetxt.getText());
                if (size >= main.pi2.getWidth(null) - 10 || size >= main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The value of side is too large!");
                else
                {
                    ImageSmooth mySmooth = new ImageSmooth();
                    BufferedImage buf = main.getBufferedImage();
                    main.pi2 = mySmooth.imageSmoothC(buf, size, -1);
                    sizetxt.setText("");
                    main.commonFrame.dispose();
                    main.Draw();
                }
            }
        };
        ok.addActionListener(listener);
        main.commonFrame.setVisible(true);
	}
	
	public void smoothContraHar(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(5, 2, "ContraHarmonic mean", 320, 230, 5);
            
        JLabel sizeL = new JLabel("Size of the Contraharmonic mean filters:(pixel)");
        main.commonFrame.add(sizeL);
        final JTextField sizetxt = new JTextField("3");
        main.commonFrame.add(sizetxt);
        JLabel theQ = new JLabel("The Q parameter for contraharmonic mean filters:");
        main.commonFrame.add(theQ);
        final JTextField numQ = new JTextField("-1.5");
        main.commonFrame.add(numQ);
            
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ActionListener listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int size = Integer.parseInt(sizetxt.getText());
                double Q = Double.parseDouble(numQ.getText());
                if (size >= main.pi2.getWidth(null) - 10 || size >= main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The value of side is too large!");
                else
                {
                	ImageSmooth mySmooth = new ImageSmooth();
                    BufferedImage buf = main.getBufferedImage();
                    main.pi2 = mySmooth.imageSmoothC(buf, size, Q);
                    sizetxt.setText("");
                    main.commonFrame.dispose();
                    main.Draw();
                }
            }
        };
        ok.addActionListener(listener);
        main.commonFrame.setVisible(true);
	}
	
	public void smoothG(final mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
		main.setCommonFrame(4, 2, "Geometric mean", 320, 200, 5);
            
        JLabel sizeL = new JLabel("Size of the Geometric mean filters:(pixel)");
        main.commonFrame.add(sizeL);
        final JTextField sizetxt = new JTextField("3");
        main.commonFrame.add(sizetxt);
            
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ActionListener listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int size = Integer.parseInt(sizetxt.getText());
                if (size >= main.pi2.getWidth(null) - 10 || size >= main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The value of side is too large!");
                else
                {
                    ImageSmooth mySmooth = new ImageSmooth();
                    BufferedImage buf = main.getBufferedImage();
                    main.pi2 = mySmooth.imageSmoothGeo(buf, size);
                    sizetxt.setText("");
                    main.Draw();
                }
            }
        };
        ok.addActionListener(listener);
        main.commonFrame.setVisible(true);
	}
}
