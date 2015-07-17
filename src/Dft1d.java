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
		
		double s = 1 / Math.sqrt(M);   //�����任ǰ�ĳ�����ʾΪ1/��MN,�������������任ǰ���ܵõ�һ����Ϊ�ԳƵı任��
		for (int i = 0; i < M; i++)
		{
			double sumRe = 0;
			double sumIm = 0;
			for (int j = 0; j < M; j++)
			{  
				double real = Arr[j].re;
				double imag = Arr[j].im;
				int k = (i * j) % M;              //sin(2��x/M) == sin(2��(x % M)/M)
				double cos_ = cosTable[k];
				double sin_ = sinTable[k];
				if (isDFT)                          //isDFT���������жϵ�ǰ���е���DFT����IDFT
					sin_ = -sin_;                    //e^x = cosx + i * sinx		                                      
				sumRe += real * cos_ - imag * sin_;    //complex multiplication: (real + i gIm) * (cos_ + i*sin_)
				sumIm += real * sin_ + imag * cos_;
			}
			G[i].re = s * sumRe;            //F(u) = Complex(G[u].re, G[u].im)
			G[i].im = s * sumIm;
		}
		return G;                    //����G���ǲ�������g��Ӧ��DFT���ֵ
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
				int k = (i * j) % M;              //sin(2��x/M) == sin(2��(x % M)/M)
				double cos_ = cosTable[k];
				double sin_ = sinTable[k];
				if (isDFT)                          //isDFT���������жϵ�ǰ���е���DFT����IDFT
					sin_ = -sin_;                    //e^x = cosx + i * sinx		                                      
				sumRe += real * cos_ - imag * sin_;    //complex multiplication: (real + i gIm) * (cos_ + i*sin_)
				sumIm += real * sin_ + imag * cos_;
			}
			G[i].re = s * sumRe;            //F(u) = Complex(G[u].re, G[u].im)
			G[i].im = s * sumIm;
		}
		return G;                    //����G���ǲ�������g��Ӧ��DFT���ֵ
	}
	
	
}