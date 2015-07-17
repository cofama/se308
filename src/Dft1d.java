//package teamwork;

public class Dft1d
{
	double[] cosTable;
	double[] sinTable;   
	Dft1d(int M)
	{ 
		sinTable = new double[M];
		cosTable = new double[M];
		for (int i = 0; i < M; i++)
		{
			sinTable[i]= Math.sin(2 * Math.PI * i / M);
			cosTable[i]= Math.cos(2 * Math.PI * i / M);
		}
	}
	
	public Complex[] DFT(Complex[] Arr, boolean isDFT)
	{
		int M = Arr.length;
		Complex [] G = new Complex[M];
		for (int i = 0; i < M; i++)
			G[i] = new Complex(0, 0);
		
		double s = 1 / Math.sqrt(M);   //将反变换前的常数表示为1/√MN,并包含在正反变换前，能得到一个更为对称的变换对
		for (int i = 0; i < M; i++)
		{
			double sumRe = 0;
			double sumIm = 0;
			for (int j = 0; j < M; j++)
			{  
				double real = Arr[j].re;
				double imag = Arr[j].im;
				int k = (i * j) % M;              //sin(2πx/M) == sin(2π(x % M)/M)
				double cos_ = cosTable[k];
				double sin_ = sinTable[k];
				if (isDFT)                          //isDFT变量用于判断当前进行的是DFT还是IDFT
					sin_ = -sin_;                    //e^x = cosx + i * sinx		                                      
				sumRe += real * cos_ - imag * sin_;    //complex multiplication: (real + i gIm) * (cos_ + i*sin_)
				sumIm += real * sin_ + imag * cos_;
			}
			G[i].re = s * sumRe;            //F(u) = Complex(G[u].re, G[u].im)
			G[i].im = s * sumIm;
		}
		return G;                    //数组G就是参数数组g对应的DFT后的值
	}
	
	public Complex[] DFT2(Complex[] Arr, boolean isDFT)
	{
		int M = Arr.length;
		Complex [] G = new Complex[M];
		for (int i = 0; i < M; i++)
			G[i] = new Complex(0, 0);
		
		double s;
		if (isDFT)
		    s = 1;
		else
		{
			s = 1 /Math.sqrt(M);
		}
		for (int i = 0; i < M; i++)
		{
			double sumRe = 0;
			double sumIm = 0;
			for (int j = 0; j < M; j++)
			{  
				double real = Arr[j].re;
				double imag = Arr[j].im;
				int k = (i * j) % M;              //sin(2πx/M) == sin(2π(x % M)/M)
				double cos_ = cosTable[k];
				double sin_ = sinTable[k];
				if (isDFT)                          //isDFT变量用于判断当前进行的是DFT还是IDFT
					sin_ = -sin_;                    //e^x = cosx + i * sinx		                                      
				sumRe += real * cos_ - imag * sin_;    //complex multiplication: (real + i gIm) * (cos_ + i*sin_)
				sumIm += real * sin_ + imag * cos_;
			}
			G[i].re = s * sumRe;            //F(u) = Complex(G[u].re, G[u].im)
			G[i].im = s * sumIm;
		}
		return G;                    //数组G就是参数数组g对应的DFT后的值
	}
	
	
}