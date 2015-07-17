//package teamwork;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Dft2d
{
	int width;
	int height;
	double[] Real;
	double[] Imag;
	double[] RealIdft;
	double[] ImagIdft;
	double[] Power;
	double PowerMax;
	private BufferedImage ioImg;
	int [] oldData;
	
	public Dft2d() {}
	
	public Dft2d(BufferedImage io_)
	{
		ioImg = io_;
		width = ioImg.getWidth();     //gets the width and height of image
		height = ioImg.getHeight();
		Real = new double [width * height];
		RealIdft = new double [width * height];
		oldData = new int[width * height];
        io_.getRGB(0, 0, width, height, oldData, 0, width);

		for(int j = 0; j < height; j++)
            for(int i = 0; i < width; i++)
            {  
	            int rgb = ioImg.getRGB(i, j);
                Real[j * width + i] = rgb & 0x000000ff;    //if doesn't &0xff, the spectrum will be whiter
            }
		Imag = new double[width * height];
		ImagIdft = new double[width * height];
	}

	public void doDft2d (double[] oriReal, double[] oriImag, double[] finReal, double[] finImag, int choose)
	{
		Complex[] row = Complex.makeComplexVector(width);
		Dft1d dftR = new Dft1d(width);                            
		for (int v = 0; v < height; v++)
		{
			for (int u = 0; u < width; u++)
			{
				row[u].re = oriReal[v * width + u];
				row[u].im = oriImag[v * width + u];
			}
			Complex[] rowDft;
			if (choose == 1)
			    rowDft = dftR.DFT(row, true);
			else
				rowDft = dftR.DFT2(row, true);
			for (int u = 0; u < width; u++)
			{
				finReal[v * width + u] = (double)rowDft[u].re;
				finImag[v * width + u] = (double)rowDft[u].im;
			}
		}			
		Complex[] col = Complex.makeComplexVector(height);     
		Dft1d dftC = new Dft1d(height);
		for (int u = 0; u < width; u++)
		{
			for (int v = 0; v < height; v++)
			{
				col[v].re = finReal[v * width + u];
				col[v].im = finImag[v * width + u];
			}
			Complex[] colDft;
			if (choose == 1)
				colDft = dftC.DFT(col, true);
			else
				colDft = dftC.DFT2(col, true);
			for (int v = 0; v < height; v++)
			{
				finReal[v * width + u] = (double) colDft[v].re;
				finImag[v * width + u] = (double) colDft[v].im;
			}
		}
	}

	public void doIDft(double[] oriReal, double[] oriImag, double[] finReal, double[] finImag, int choose)
	{
		Complex[] row = Complex.makeComplexVector(width);
		Dft1d dftR = new Dft1d(width);                            
		for (int v = 0; v < height; v++)
		{
			for (int u = 0; u < width; u++)
			{
				row[u].re = oriReal[v * width + u];
				row[u].im = oriImag[v * width + u];
			}
			Complex[] rowDft;
			if (choose == 1)
			    rowDft = dftR.DFT(row, false);
			else
				rowDft = dftR.DFT2(row, false);
			for (int u = 0; u < width; u++)
			{
				finReal[v * width + u] = (double)rowDft[u].re;
				finImag[v * width + u] = (double)rowDft[u].im;
			}
		}
		Complex[] col = Complex.makeComplexVector(height);
		Dft1d dftC = new Dft1d(height);
		for (int u = 0; u < width; u++)
		{
			for (int v = 0; v < height; v++)
			{
				col[v].re = finReal[v * width + u];
				col[v].im = finImag[v * width + u];
			}
			Complex[] colDft;
			if (choose == 1)
				colDft = dftC.DFT(col, false);
			else
				colDft = dftC.DFT2(col, false);
			for (int v = 0; v < height; v++)
			{
				finReal[v * width + u] = (double) colDft[v].re;
				finImag[v * width + u] = (double) colDft[v].im;
			}
		}
	}
	void makeFourierSpectrum()               //computes the Fourier spectrum
	{
		Power = new double[Real.length];
		PowerMax = 0.0f;
		for (int i = 0; i < Real.length; i++)
		{
			double a = Real[i];
			double b = Imag[i];
			double p = (double) Math.sqrt(a * a + b * b);  //computes the magnitude of spectrum |F(u,v)|=sqrt(R*R + I*I)
			Power[i] = p;
			if (p > PowerMax)
				PowerMax = p;
		}
	}
	
	public Image showDFTspectrum()
	{
		int [] pixels = new int [width * height];
		int [] pixels2 = new int [width * height];
		for (int i = 0; i < width * height; i++)
			pixels[i] = pixels2[i] = 0;
	                                                               
		double max = Math.log(PowerMax + 1.0);               //taking log function of spectrum
		double scale = 255 / max;
		
		for (int i = 0; i < pixels.length; i++)
		{
			double p = Power[i];                      
			double plog = Math.log(p + 1.0);            //log(1 + |F(u,v)|)
			int pixValue = (int)(plog * scale);           //value of pixValue is in double 
			
			if (pixValue < 0)
				pixValue = 0;
			if (pixValue > 255)
				pixValue = 255;
			pixels[i] = 0xff000000;
			pixels[i] |= pixValue << 16;
			pixels[i] |= pixValue << 8;
			pixels[i] |= pixValue;
		}

		int w = width / 2;                       //dft shift
		int h = height / 2;
		
		int [][] leftTop = new int [h][w];
	    int [][] rightTop = new int [h][w + 1];
	    int [][] leftDown = new int [h][w];
	    int [][] rightDown = new int[h][w + 1];

		for (int i = 0; i < h; i++)
			for (int j = 0; j < w; j++)
			{
				leftTop[i][j] = pixels[(h + i) * width + w + j];
				leftDown[i][j] = pixels[i * width + w + j];
				rightTop[i][j] = pixels[(h + i) * width + j];
				rightDown[i][j] = pixels[i * width + j];
			}
		
		if (width % 2 != 0 && height % 2 == 0)
		{
			for (int i = 0; i < h; i++)
				for (int j = 0; j < w; j++)
				{
					pixels2[i * width + j] = leftTop[i][j];
					pixels2[i * width + w + j + 1] = rightTop[i][j];
					pixels2[(h + i) * width + j] = leftDown[i][j];
					pixels2[(h + i) * width + w + j + 1] = rightDown[i][j];
				}
			for (int k = 0; k < h; k++)
				pixels2[k * width + w] = pixels[(h + 1 + k) * width - 1];
			for (int k = h; k < height; k++)
				pixels2[k * width + w] = pixels[(k - h + 1) * width - 1];
		}
		if (width % 2 == 0 && height % 2 != 0)
		{
			for (int i = 0; i < h; i++)
				for (int j = 0; j < w; j++)
				{
					pixels2[i * width + j] = leftTop[i][j];
					pixels2[i * width + w + j] = rightTop[i][j];
					pixels2[(h + i + 1) * width + j] = leftDown[i][j];
					pixels2[(h + i + 1) * width + w + j] = rightDown[i][j];
				}
			for (int k = 0; k < w; k++)
				pixels2[h * width + k] = pixels[(height - 1) * width + w + k];
			for (int k = w; k < width; k++)
				pixels2[h * width + k] = pixels[(height - 1) * width + k - w];
		}
		if (width % 2 != 0 && height % 2 != 0)
		{
			for (int i = 0; i < h; i++)
				for (int j = 0; j < w; j++)
				{
					pixels2[i * width + j] = leftTop[i][j];
					pixels2[i * width + w + j + 1] = rightTop[i][j];
					pixels2[(h + i + 1) * width + j] = leftDown[i][j];
					pixels2[(h + i + 1) * width + w + j + 1] = rightDown[i][j];
				}
			for (int k = 0; k < h; k++)
				pixels2[k * width + w] = pixels[(h + 1 + k) * width - 1];
			for (int k = h; k < height; k++)
				pixels2[k * width + w] = pixels[(k - h + 1) * width - 1];
			for (int k = 0; k < w; k++)
				pixels2[h * width + k] = pixels[(height - 1) * width + w + k];
			for (int k = w; k < width; k++)
				pixels2[h * width + k] = pixels[(height - 1) * width + k - w];
		}
		else
		{
		    for (int i = 0; i < h; i++)
			    for (int j = 0; j < w; j++)
			    {
				    pixels2[i * width + j] = leftTop[i][j];
				    pixels2[i * width + w + j] = rightTop[i][j];
				    pixels2[(h + i) * width + j] = leftDown[i][j];
				    pixels2[(h + i) * width + w + j] = rightDown[i][j];
			    }
		}
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pixels2, 0, width));
	}
	
	public Image makeIdftImage()
	{
		int [] pixels = new int [width * height];
		for (int i = 0; i < width * height; i++)
			pixels[i] = 0;
		
		double [] iPower = new double[Real.length];
		for (int i = 0; i < Real.length; i++)
		{
			double a = RealIdft[i];
			double b = ImagIdft[i];
			float p = (float) Math.sqrt(a * a + b * b);
			iPower[i] = p;
		}
		for (int i = 0; i < width * height; i++)
        {
			pixels[i] = 0xff000000;
			pixels[i] |= (int)iPower[i] << 16;
			pixels[i] |= (int)iPower[i] << 8;
			pixels[i] |= (int)iPower[i];
        }
		return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pixels, 0, width));
	}
	
	public void showIdft(mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
    	BufferedImage buf = main.getBufferedImage();
    	Dft2d myIdft = new Dft2d(buf);
    	myIdft.doDft2d(myIdft.Real, myIdft.Imag, myIdft.Real, myIdft.Imag, 1);
    	myIdft.doIDft(myIdft.Real, myIdft.Imag, myIdft.RealIdft, myIdft.ImagIdft, 1);
    	main.setCommonFrame(1, 1, "IDFT", 500, 500, 0);
    	Image spec = myIdft.makeIdftImage();
    	if (main.spectrumLabel!= null)
    		main.commonFrame.remove(main.spectrumLabel);
    	main.spectrumLabel = new JLabel(new ImageIcon(spec));
    	main.commonFrame.add(main.spectrumLabel);
    	main.commonFrame.setVisible(true);
	}
	public void showDft(mainFrame main)
	{
		if (!main.thisImageExistence())
    		return;
    	BufferedImage buf = main.getBufferedImage();
    	Dft2d myDFT2d = new Dft2d(buf);
    	myDFT2d.doDft2d(myDFT2d.Real, myDFT2d.Imag, myDFT2d.Real, myDFT2d.Imag, 1);
    	main.setCommonFrame(1, 1, "Fourier Spectrum (DFT)", 500, 400, 0);
    	myDFT2d.makeFourierSpectrum();
    	Image spec = myDFT2d.showDFTspectrum();
    	if (main.spectrumLabel!= null)
    		main.commonFrame.remove(main.spectrumLabel);
    	main.spectrumLabel = new JLabel(new ImageIcon(spec));
    	main.commonFrame.add(main.spectrumLabel);
    	main.commonFrame.setVisible(true);
	}
}                                                     
		