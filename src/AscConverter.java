//package teamwork;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AscConverter
{
	  
	private AscPool charPool;
	public AscConverter()
	{
		this.charPool = null;
	}
	public AscConverter(AscPool pool)
	{
		this.charPool = pool;
	}
	public BufferedImage convertImgToAsc(BufferedImage source)
	{
		Square grid = this.charPool.getSquareSize();
		int outWidth = (source.getWidth() / grid.width) * grid.width;
		int outHeight = (source.getHeight() / grid.height) * grid.height;
		int[] imagePixels = source.getRGB(0, 0, outWidth, outHeight, null, 0, outWidth);
		BufferedImage outImg = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < outHeight / grid.height; i++)
			for (int j = 0; j < outWidth / grid.width; j++)
				convertGridToAsc(outWidth, imagePixels, j, i);
		outImg.setRGB(0, 0, outWidth, outHeight, imagePixels, 0, outWidth);
		return outImg;
	}

	public void convertGridToAsc(int originImgWidth, int[] pixel, int gridX, int gridY)
	{
		double minError = Double.MAX_VALUE;
		double sumError = 0;
		Entry<Integer, int[]> bestChar = null;
		for (Entry<Integer, int[]> poolEntry : this.charPool)    // find the best character
		{
			sumError = 0;
			int charR, charG, charB, originR, originG, originB, position;
			int [] charPixels = poolEntry.getValue();
			for (int i = 0; i < charPixels.length; i++)
			{
				charR = (charPixels[i] >> 16) & 0xff;
				charG = (charPixels[i] >> 8) & 0xff;
				charB = charPixels[i] & 0xFF;

				int xOffset = i % this.charPool.getSquareSize().width;
				int yOffset = i / this.charPool.getSquareSize().width;
				int startX = gridX * this.charPool.getSquareSize().width;
				int startY = gridY * this.charPool.getSquareSize().height;
				position = (startY + yOffset) * originImgWidth + startX + xOffset;
				int originPixel = pixel[position];
				originR = (originPixel >> 16) & 0xff;
				originG = (originPixel >> 8) & 0xff;
				originB = originPixel & 0xff;

				int Ave = (originR + originG + originB) / 3;
				sumError += (charR-Ave) * (charR-Ave) + (charG-Ave) * (charG-Ave) + (charB-Ave) * (charB-Ave);
			}
			if (sumError < minError)
			{
				minError = sumError;
				bestChar = poolEntry;
			}
		}
		int[] charPixels = bestChar.getValue();
		for (int i = 0; i < charPixels.length; i++)    //draw the character
		{
			int xOffset = i % this.charPool.getSquareSize().width;
			int yOffset = i / this.charPool.getSquareSize().width;
			int startX = gridX * this.charPool.getSquareSize().width;
			int startY = gridY * this.charPool.getSquareSize().height;
			int position = (startY + yOffset) * originImgWidth + startX + xOffset;
			pixel[position] = charPixels[i];
		}
	}
	public void X(final mainFrame Main)
	{
		if (!Main.thisImageExistence())
    		return;
        Main.setCommonFrame(4, 2, "AscII image", 350, 200, 5);
        
        JLabel charSize = new JLabel("character size(pixels):");
        Main.commonFrame.add(charSize);
        final JTextField size = new JTextField("10");
        Main.commonFrame.add(size);
        JLabel characterColor = new JLabel("Character color:");
        Main.commonFrame.add(characterColor);
        final JButton chooseCharColor = new JButton();
        chooseCharColor.setBackground(new Color(0, 0, 0));
        Main.commonFrame.add(chooseCharColor);
        
        JLabel backgroundColor = new JLabel("Background color:");
        Main.commonFrame.add(backgroundColor);
        final JButton chooseBackColor = new JButton();
        chooseBackColor.setBackground(new Color(255, 255, 255));
        Main.commonFrame.add(chooseBackColor);
        JLabel blank = new JLabel();
        Main.commonFrame.add(blank);
        JButton ok = new JButton("OK");
        Main.commonFrame.add(ok);

        final JFrame f = new JFrame();  
        Main.backColor = new Color(255, 255, 255);
        Main.charColor = new Color(0, 0, 0);
        chooseCharColor.addActionListener(new ActionListener()  
        {
            public void actionPerformed(ActionEvent ae)  
            {  
                final JColorChooser colorPane = new JColorChooser(Main.charColor);  
                JDialog jd = JColorChooser.createDialog(f ,"character color",false, colorPane, new ActionListener()  
                {  
                     public void actionPerformed(ActionEvent ae)  
                     {
                    	  Main.charColor = colorPane.getColor();
                          chooseCharColor.setBackground(Main.charColor);
                     }  
                }, null);
                jd.setVisible(true);  
            }  
        });
        chooseBackColor.addActionListener(new ActionListener()  
        {
            public void actionPerformed(ActionEvent ae)  
            {
                final JColorChooser colorPane = new JColorChooser(Main.charColor);  
                JDialog jd = JColorChooser.createDialog(f, "background color", false, colorPane, new ActionListener()  
                {
                     public void actionPerformed(ActionEvent ae)  
                     {
                    	 Main.backColor = colorPane.getColor();
                    	 chooseBackColor.setBackground(Main.backColor);
                     }  
                }, null);
                jd.setVisible(true);  
            }  
        });
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	int sizeValue = Integer.parseInt(size.getText());
            	if (sizeValue >= Main.pi2.getWidth(null) - 10 || sizeValue >= Main.pi2.getHeight(null) - 10)
                    JOptionPane.showMessageDialog(null, "The size of character is too large!");
            	else
            	{
                    BufferedImage buf = Main.getBufferedImage();
                    Font font = new Font("Arial", Font.PLAIN, sizeValue);
                    AscPool pool = new AscPool(font, Main.charColor, Main.backColor);
            		AscConverter myConverter = new AscConverter(pool);
            		
            		Main.pi2 = myConverter.convertImgToAsc(buf);
            		
            		Main.commonFrame.dispose();
            		Main.Draw();
            	}
            }
        });
        Main.commonFrame.setVisible(true);
	}
}

