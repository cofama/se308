//package teamwork;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ImageScale
{
	int newWidth;
	int newHeight;
	private BufferedImage src;
	
	public Image scale_Process(BufferedImage srcImage, int neww, int newh)
	{	
		
		newWidth = neww;
		newHeight = newh;
		src = srcImage;
	    int w = src.getWidth();
        int h = src.getHeight();	
        int [] newData = new int[newWidth * newHeight];
        int [] oldData = new int[w * h];
        int [] newRed = new int[newWidth * newHeight];
		int [] newGreen = new int[newWidth * newHeight];
		int [] newBlue  = new int[newWidth * newHeight];
        src.getRGB(0, 0, w, h, oldData, 0, w);

		for (int i = 0; i < newWidth * newHeight; i++)
			newData[i] = newRed[i] = newGreen[i] = newBlue[i] = 0;
		
		for(int j = 0; j < newHeight; j++)
		{
			for(int i = 0; i < newWidth; i++)
			{
				double r1, g1, b1, r2, g2, b2, r3, g3, b3, r4, g4, b4;    //记录四邻点的rgb信息
				
				double sw = (double) w / newWidth;
				double sh = (double) h / newHeight;
				double dw = sw * i;
				double dh = sh * j;
				
				int x = (int)dw;    //x，y为对应新图(i，j)像素的原图像素的坐标
				int y = (int)dh;
				int x1 = (int)sw;
				int y1 = (int)sh;
				double deltaW = sw - x1;    //bInterpolation的参数
				double deltaH = sh - y1;
				
				r1 = (oldData[y * w + x] >> 16) & 0xff;
				g1 = (oldData[y * w + x] >> 8) & 0xff;
				b1 = (oldData[y * w + x]) & 0xff;
				
				if ((y + 1) * w + x + 1 >= w * h)    //判断Q21点是否越界
				{
					r2 = r3 = r4 = r1;
					g2 = g3 = g4 = g1;
					b2 = b3 = b4 = b1;
					
				} else
				{
					r2 = (oldData[y * w + x + 1] >> 16) & 0xff;
					g2 = (oldData[y * w + x + 1] >> 8) & 0xff;
					b2 = (oldData[y * w + x + 1]) & 0xff;
					
					r3 = (oldData[(y + 1) * w + x] >> 16) & 0xff;
					g3 = (oldData[(y + 1) * w + x] >> 8) & 0xff;
					b3 = (oldData[(y + 1) * w + x]) & 0xff;
					
					r4 = (oldData[(y + 1) * w + x + 1] >> 16) & 0xff;
					g4 = (oldData[(y + 1) * w + x + 1] >> 8) & 0xff;
					b4 = (oldData[(y + 1) * w + x + 1]) & 0xff;
				}			
				newRed[j * newWidth + i] = bInterpolation(r1, r2, r3, r4, deltaW, deltaH);  //得到新的rgb值
				newGreen[j * newWidth + i] = bInterpolation(g1, g2, g3, g4, deltaW, deltaH);
				newBlue[j * newWidth + i] = bInterpolation(b1, b2, b3, b4, deltaW, deltaH);
			}
		}
		for (int i = 0; i < newWidth * newHeight; i++)
		{
			newData[i] = 0xff000000;
			newData[i] |= newRed[i] << 16;
			newData[i] |= newGreen[i] << 8;
			newData[i] |= newBlue[i];
		}
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(newWidth, newHeight, newData, 0, newWidth));
	}
	
    private int bInterpolation(double r1, double r2, double r3, double r4, double deltaW, double deltaH)
    {    //Bilinear Interpolation
		double x1 = r1 * (1 - deltaW) + r2 * deltaW;
		double x2 = r3 * (1 - deltaW) + r4 * deltaW;		
		return (int)(x1 * (1 - deltaH) + x2 * deltaH);
	}
    
    public void showScale(final mainFrame main)
    {
    	if (!main.thisImageExistence())
    		return;
    	main.setCommonFrame(5, 3, "Scale", 300, 230, 5);
            
        JLabel widthLabel = new JLabel("Width:(pixel)");
        main.commonFrame.add(widthLabel);
        final JTextField widthField = new JTextField();
        main.commonFrame.add(widthField);
            
        JLabel heightLabel = new JLabel("Height:(pixel)");
        main.commonFrame.add(heightLabel);
        final JTextField heightField = new JTextField();
        main.commonFrame.add(heightField);
            
        JButton ok = new JButton("OK");
        main.commonFrame.add(ok);
            
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	main.height = Integer.parseInt(heightField.getText());
            	main.width = Integer.parseInt(widthField.getText());
            	main.thisImageExistence();
            	ImageScale processor = new ImageScale();
            	BufferedImage buf = main.getBufferedImage();
            	main.pi2 = processor.scale_Process(buf, main.width, main.height);
                widthField.setText("");
                heightField.setText("");
                main.commonFrame.dispose();
                main.Draw();                    
            }
        });
        main.commonFrame.setVisible(true);
    }
}
