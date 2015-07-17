//package teamwork;

public class Complex              //developing basic notation for complex structure of DFT
{
	public double re;
	public double im;

	public Complex()
	{
		this.re = 0;
		this.im = 0;
	}
	
	public Complex(double re, double im)
	{
		this.re = re;                //object is invoked in constructor
		this.im = im;
	}
	
	public static Complex[] makeComplexVector(int M)
	{
		Complex[] g = new Complex[M];
		for (int i = 0; i < M; i++)
			g[i] = new Complex(0,0);      //    g gets assigned to origin
		return g;
	}
	
	public static Complex[] makeComplexVector(double[] signal)   //complex vector for 1-D DFT
	{
		int M = signal.length;
		Complex[] g = new Complex[M];
		for (int i = 0; i < M; i++)
			g[i] = new Complex(signal[i], 0);
		return g;
	}
	
	static Complex[] makeComplexVector(double[] real, double[] imag)  //complex vector for 2-D DFT
	{
		int M = real.length;
		Complex[] g = new Complex[M];
		for (int i = 0; i < M; i++)
			g[i] = new Complex(real[i], imag[i]);
		return g;
	}
	
	public Complex add(Complex c)
	{
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	public Complex minus(Complex c)
	{
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	public Complex mul(Complex c)
	{
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
	}

	public Complex mul(double c)
	{
		return new Complex(this.re * c, this.im * c);
	}

}
