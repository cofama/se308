//package teamwork;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ImageHistUI  extends JComponent
{
	private static final long serialVersionUID = 1L;
	private BufferedImage ioImg;
	
	public ImageHistUI(BufferedImage io_, String isRGB)
	{
		ImageHistogram filter = new ImageHistogram(io_);
		if (isRGB == "blue")
		{
		    ioImg = filter.getHistogramBlue();
		    JFrame imageFrame = new JFrame("Histogram");
		    imageFrame.getContentPane().add(this);
		    imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    imageFrame.setBounds(200, 200, 600, 370);
		    imageFrame.setVisible(true);
		}
		if (isRGB == "red")
		{
		    ioImg = filter.getHistogramRed();
		    JFrame imageFrame3 = new JFrame("Red Histogram");
		    imageFrame3.getContentPane().add(this);
		    imageFrame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    imageFrame3.setBounds(200, 200, 600, 370);
		    imageFrame3.setVisible(true);
		}
		if (isRGB == "green")
		{
			ioImg = filter.getHistogramGreen();
			JFrame imageFrame3 = new JFrame("Green Histogram");
			imageFrame3.getContentPane().add(this);
			imageFrame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			imageFrame3.setBounds(200, 200, 600, 370);
			imageFrame3.setVisible(true);
		}
	}
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(ioImg, 0, 0, ioImg.getWidth(), ioImg.getHeight(), null);
	}
}