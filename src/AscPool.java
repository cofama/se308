//package teamwork;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.HashMap;

public class AscPool implements Iterable<Entry<Integer, int[]>>
{
	private Map<Integer, int[]> asciiPool;
	private Square mySquare;
	
	public AscPool(Font font, Color charColor, Color backColor)
	{		
		BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		Graphics2D graphics = (Graphics2D) g;
		graphics.setFont(font);
		FontMetrics fm = graphics.getFontMetrics();
		//The bound of "M" character
		Rectangle rect = new TextLayout(Character.toString((char)65), fm.getFont(), fm.getFontRenderContext()).getOutline(null).getBounds();
		this.mySquare = new Square();
		this.mySquare.width = (int)rect.getWidth();
		this.mySquare.height = (int)rect.getHeight();
		
		img = new BufferedImage(this.mySquare.width, this.mySquare.height, BufferedImage.TYPE_INT_RGB);
		g = img.getGraphics();
		Map<Integer, int[]> pool = new HashMap<>();

		for (int i = 65; i <= 126; i++)    //65 & 126 are ascII of characters
		{
			g.setColor(backColor);    //background color
			g.fillRect(0, 0, this.mySquare.width, this.mySquare.height);
			
			g.setColor(charColor);    //character's color
			String c = Character.toString((char) i);
			rect = new TextLayout(c, fm.getFont(), fm.getFontRenderContext()).getOutline(null).getBounds();
			g.drawString(c, 0, (int)(rect.getHeight()));
			pool.put(i, img.getRGB(0, 0, this.mySquare.width, this.mySquare.height, null, 0, this.mySquare.width));
		}
		this.asciiPool = pool;
	}
	public Square getSquareSize()
	{
		return mySquare;
	}
	public Iterator<Entry<Integer, int[]>> iterator()
	{
		return asciiPool.entrySet().iterator();
	}
}


