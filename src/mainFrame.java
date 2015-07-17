////package teamwork;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class mainFrame extends JFrame
{
	mainFrame thisFrame = this;
	public static JFrame commonFrame = null;
	
	public Image pi2;    //point
    public Image originalPic;
	public ImageIcon smallPicIcon;
	
	public Color charColor, backColor;
	public JLabel bigPicLabel;    //p2
	public JLabel spectrumLabel;
	public Container container;
	public int width, height;
	public ImageHistEqualization myHistEqual;
	public ImageIcon temp;
	
	public void savepic(Image ima, String path)
	{
    	 int w = ima.getWidth((ImageObserver) this); 
         int h = ima.getHeight((ImageObserver) this);	
         BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);  
         Graphics g = bi.getGraphics();    
         try
         {
             g.drawImage(ima, 0, 0, null);
             ImageIO.write(bi,"jpg",new File(path));
         }
         catch (IOException e)
         {
             e.printStackTrace(); 
         }
    }
	
	public mainFrame()
	{
       //Font f = new Font("����",Font.BOLD,16);
       final JLabel whole;
       whole = new JLabel();
       ImageIcon backgroundPic = new ImageIcon(mainFrame.class.getResource("f.jpg")); 
       whole.setIcon(backgroundPic);
       whole.setBounds(0, 0, 1000, 1000);
       this.add(whole);
       
       //area for pic before
       final JLabel smallPicLabel;
       smallPicLabel = new JLabel();
       smallPicIcon = new ImageIcon(mainFrame.class.getResource("welcome.png"));
       Image small = smallPicIcon.getImage();
       
       int sw = small.getWidth(null);
       int sh = small.getHeight(null);
       int newH = sh, newW = sw;
       if (sw >= sh && sw > 150)
       {
           double ratio = sh * 1.0 / sw;
           newH = (int)(150 * ratio);
           newW = 150;
       }
       else if (sh > sw && sh > 150)
       {
    	   double ratio = sw * 1.0 / sh;
    	   newW = (int)(150 * ratio);
    	   newH = 150;
       }
       smallPicIcon.setImage(smallPicIcon.getImage().getScaledInstance(newW,newH,Image.SCALE_DEFAULT));
       smallPicLabel.setIcon(smallPicIcon);
       smallPicLabel.setBounds(30, 50, newW + 3, newH + 3);
       whole.add(smallPicLabel);
       
       //area for pic after
       pi2 = new ImageIcon("E:/java/teamwork/src/welcome.png").getImage();
       originalPic = pi2;
       ImageIcon tmp = new ImageIcon(pi2);
       int w = pi2.getWidth(null); 
       int h = pi2.getHeight(null);
       newH = h;
       newW = w;
       if (w >= h && w > 500)
       {
           double ratio = h * 1.0 / w;
           newH = (int)(500 * ratio);
           newW = 500;
       }
       else if (h > w && h > 500)
       {
    	   double ratio = w * 1.0 / h;
    	   newW = (int)(500 * ratio);
    	   newH = 500;
       }
       tmp.setImage(tmp.getImage().getScaledInstance(newW, newH, Image.SCALE_DEFAULT));
       bigPicLabel = new JLabel();
       bigPicLabel.setBounds(250, 50, newW + 3, newH + 3);
       bigPicLabel.setIcon(tmp);
       whole.add(bigPicLabel);
       
       //menu
       this.setBounds(0, 0, 300, 200);
       JMenuBar TestJMenuBar=new JMenuBar();
       this.setJMenuBar(TestJMenuBar);
       
       //File
       JMenu FileMenu = new JMenu("�ļ�");
       TestJMenuBar.add(FileMenu);
       JMenuItem File1 = new JMenuItem("��ͼƬ");
       FileMenu.add(File1);
       JMenuItem File2 = new JMenuItem("ˢ��ͼƬ");
       FileMenu.add(File2);
       JMenuItem File3 = new JMenuItem("����ͼƬ");
       FileMenu.add(File3);
       
       //listen of file
       File1.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		   JFileChooser jChooser = new JFileChooser();
               int index = jChooser.showDialog(null, "��ͼƬ");
               if(index == JFileChooser.APPROVE_OPTION) {
                   final File selected = jChooser.getSelectedFile();
                   ImageIcon pi1 = new ImageIcon(selected.getPath());
                   pi2 = pi1.getImage();
                   originalPic = pi2;
                   temp = pi1;
                   ImageIcon tmp = new ImageIcon(pi2);
                   
                   int w = pi2.getWidth(null); 
                   int h = pi2.getHeight(null);
                   int newH = h, newW = w;
                   if (w >= h && w > 500)
                   {
                       double ratio = h * 1.0 / w;
                       newH = (int)(500 * ratio);
                       newW = 500;
                   }
                   else if (h > w && h > 500)
                   {
                	   double ratio = w * 1.0 / h;
                	   newW = (int)(500 * ratio);
                	   newH = 500;
                   }
                   tmp.setImage(tmp.getImage().getScaledInstance(newW, newH, Image.SCALE_DEFAULT));
                   
                   pi1.setImage(pi1.getImage().getScaledInstance((int)(newW * 0.3), (int)(newH * 0.3), Image.SCALE_DEFAULT));
                   bigPicLabel.setIcon(tmp);
                   bigPicLabel.setBounds(250, 50, newW + 3, newH + 3);
                   smallPicLabel.setIcon(pi1);
                   smallPicLabel.setBounds(30, 50, (int)(newW * 0.3) + 3, (int)(newH * 0.3) + 3);
                   whole.add(smallPicLabel);
                   //JOptionPane.showMessageDialog(null,"ͼƬ�ϴ��ɹ�");
               }
    	   }
       });
       
       //Refresh
       File2.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
               pi2 = originalPic;
               ImageIcon tmp = new ImageIcon(pi2);
               int w = pi2.getWidth(null); 
               int h = pi2.getHeight(null);
               int newH = h, newW = w;
               if (w >= h && w > 500)
               {
                   double ratio = h * 1.0 / w;
                   newH = (int)(500 * ratio);
                   newW = 500;
               }
               else if (h > w && h > 500)
               {
            	   double ratio = w * 1.0 / h;
            	   newW = (int)(500 * ratio);
            	   newH = 500;
               }
               tmp.setImage(tmp.getImage().getScaledInstance(newW,newH,Image.SCALE_DEFAULT));
               //pi2 = tmp.getImage();
               bigPicLabel.setIcon(tmp);
               bigPicLabel.setBounds(250,50,newW + 3, newH + 3);
    	   }
       });
       
       File3.addActionListener(new ActionListener() {    //save
    	   public void actionPerformed(ActionEvent e) {
    		   JFileChooser jChooser = new JFileChooser();
    		   jChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    		   int index = jChooser.showDialog(null, "����ͼƬ");
               if(index == JFileChooser.APPROVE_OPTION) {
    		     final File selected = jChooser.getSelectedFile();
    		     String path1 = selected.getPath() + "/gray.jpg";
    		     String path = path1.replace("\\", "/");
    		     
    		     savepic(pi2,path);
    		     JOptionPane.showMessageDialog(null,"ͼƬ����ɹ�");
               }
    	   }
       });
        
       //Function
       JMenu FuncMenu1=new JMenu("Ȥζitems");
       TestJMenuBar.add(FuncMenu1);
       
       JMenuItem Func3 = new JMenuItem("ƴͼ��Ϸ");
       FuncMenu1.add(Func3);
       
       JMenuItem Func8 = new JMenuItem("��������ֵ");
       FuncMenu1.add(Func8);
       JMenuItem Func20 = new JMenuItem("��ͼ����");
       FuncMenu1.add(Func20);
       
       JMenu FuncMenu2=new JMenu("ͼƬ����Ĺ���");
       TestJMenuBar.add(FuncMenu2);
       JMenuItem Func4 = new JMenuItem("ͼƬ��Ƶ��ͼ");
       FuncMenu2.add(Func4);
       JMenuItem Func6 = new JMenuItem("ͼƬ��ֱ��ͼ");
       FuncMenu2.add(Func6);
       
       JMenu FuncMenu3=new JMenu("�����˾�");
       TestJMenuBar.add(FuncMenu3);
       JMenuItem Func1 = new JMenuItem("�ϵ���");//��˹
       FuncMenu3.add(Func1);
       JMenuItem Func2 = new JMenuItem("����");
       FuncMenu3.add(Func2);
       JMenuItem Func5 = new JMenuItem("���첻��ҹ�ĺ�");    //����Ҷ��任
       FuncMenu3.add(Func5);
       JMenuItem Func7 = new JMenuItem("��������");    //ֱ��ͼ����
       FuncMenu3.add(Func7);    
       JMenuItem Func9 = new JMenuItem("��ͷ����Сͷ�ְ�");    //����
       FuncMenu3.add(Func9);
       JMenuItem Func0 = new JMenuItem("�ַ�֮��");    //�ַ���
       FuncMenu3.add(Func0);
       JMenuItem Func15 = new JMenuItem("С�ɲ�¶����");    //��
       FuncMenu3.add(Func15);
       JMenuItem Func16 = new JMenuItem("����");    //sobel��
       FuncMenu3.add(Func16);
       
       JMenu FuncMenu4=new JMenu("�����˾�");
       TestJMenuBar.add(FuncMenu4);
       JMenuItem Func11 = new JMenuItem("���л�");    //����ƽ��
       FuncMenu4.add(Func11);
       JMenuItem Func12 = new JMenuItem("��ͬ��ģ��");    //г��ƽ��
       FuncMenu4.add(Func12);
       JMenuItem Func13 = new JMenuItem("˫��ģ��");    //��г��ƽ��
       FuncMenu4.add(Func13);
       JMenuItem Func14 = new JMenuItem("�����۾�");    //����ƽ��
       FuncMenu4.add(Func14);
       JMenuItem Func17 = new JMenuItem("��è��");    //��Сֵ�˲�
       FuncMenu4.add(Func17);
       JMenuItem Func18 = new JMenuItem("��ʹ�ĳ��");    //���ֵ�˲�
       FuncMenu4.add(Func18);
       JMenuItem Func19 = new JMenuItem("��ʹ����è��");    //��ֵ�˲�
       FuncMenu4.add(Func19);
     
       //FuncListener
       Func1.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		   ImageDenoise MyDenoise = new ImageDenoise();
    	       MyDenoise.Gauss(thisFrame);
    	   }
       });
       
       Func2.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    	       ImageDenoise MyDenoise = new ImageDenoise();
    	       MyDenoise.pepp(thisFrame);
    	       
    	   }
       });
       
       Func3.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		    new Jigsaw();
    	   }
       });
       
       Func4.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    	        Dft2d myDft = new Dft2d();
    	        myDft.showDft(thisFrame);
    	   }
       });
       Func5.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		   Dft2d myDft = new Dft2d();
   	           myDft.showIdft(thisFrame);
    	   }
       });
       Func6.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    	       if(!thisImageExistence())
    	    	  return;
    	       BufferedImage buf = getBufferedImage();
    	       new ImageHistUI(buf,"blue");
    	   }
       });
       Func7.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    	       myHistEqual = new ImageHistEqualization();
    	       myHistEqual.showHistEqual(thisFrame);
    	   }
       });
       Func8.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    	      new random().Frame();
    	   }
       });
       Func9.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    	        ImageScale myScale = new ImageScale();
    	        myScale.showScale(thisFrame);
    	   }
       });
       Func0.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  AscConverter myC = new AscConverter();
    		  myC.X(thisFrame);
    	   }
       });
       Func11.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageSmooth smooth = new ImageSmooth();
    		  smooth.smoothA(thisFrame);
    	   }
       });
       Func12.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageSmooth smooth = new ImageSmooth();
    		  smooth.smoothHar(thisFrame);
    	   }
       });
       Func13.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageSmooth smooth = new ImageSmooth();
    		  smooth.smoothContraHar(thisFrame);
    	   }
       });
       Func14.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageSmooth smooth = new ImageSmooth();
    		  smooth.smoothG(thisFrame);
    	   }
       });
       Func15.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageSharpen mySharpen = new ImageSharpen();
    		  mySharpen.showLaplace(thisFrame);
    	   }
       });
       Func16.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageSharpen mySharpen = new ImageSharpen();
     		  mySharpen.showSobel(thisFrame);
    	   }
       });
       Func17.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageFilter myFilter = new ImageFilter();
     		  myFilter.showMining(thisFrame);
    	   }
       });
       Func18.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageFilter myFilter = new ImageFilter();
     		  myFilter.showMaxing(thisFrame);
    	   }
       });
       Func19.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		  ImageFilter myFilter = new ImageFilter();
     		  myFilter.showMedian(thisFrame);
    	   }
       });
       Func20.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		   new ImageDrawing();
    	   }
       });
       
       //about us
       JMenu AboutMenu=new JMenu("��������");
       TestJMenuBar.add(AboutMenu);
       JMenuItem About1 = new JMenuItem("�Ŷӽ���");
       AboutMenu.add(About1);
       JMenuItem About2 = new JMenuItem("��ϵ����");
       AboutMenu.add(About2);
       
       About1.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		    JOptionPane.showMessageDialog(null,"��������ɽ��ѧ���ѧԺ��ý+������12�챾������"
    		    	    + "\n"+"С���Ա�У�"
    		    		+ "\n"+"12330090    �ؽ�"
    		    		+ "\n"+"12330091   ������"
    		    		+ "\n12330092   ��ΰ��"
    		    		+ "\n12330094   ������"
    		    		+ "\n12330027   ������"
    		    		+ "\n12330428   ��Ӿդ");
    	   }
       });
       
       About2.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		    JOptionPane.showMessageDialog(null,"�����κ����⣬����ϵ��guanyuhan@qq.com");
    	   }
       });
       this.setTitle("Ȥζͼ����");
       this.setSize(900,650);
       this.setLocation(300,100);
       this.setVisible(true);
       this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	
	//copy pic
	public void Draw()
    {
		ImageIcon tmp = new ImageIcon(pi2);
		
		int w = pi2.getWidth(null); 
	    int h = pi2.getHeight(null);
	    int newH = h, newW = w;
	    if (w >= h && w > 500)
	    {
	        double ratio = h * 1.0 / w;
	        newH = (int)(500 * ratio);
	        newW = 500;
	    }
	    else if (h > w && h > 500)
	    {
	    	double ratio = w * 1.0 / h;
	    	newW = (int)(500 * ratio);
	    	newH = 500;
	    }
	    tmp.setImage(tmp.getImage().getScaledInstance(newW, newH, Image.SCALE_DEFAULT));
		
		bigPicLabel.setIcon(tmp);
		bigPicLabel.setBounds(250, 50, newW + 3, newH + 3);
    }
	
	//this func is from another member 
    public void setCommonFrame(int row, int col, String title, int boundWidth, int boundHeight, int vGap)
	{
        if (commonFrame != null)
        {
        	commonFrame.dispose();
        	commonFrame = null;
        }
        commonFrame = new JFrame();
        GridLayout tmp = new GridLayout(row, col);
        tmp.setVgap(vGap);
        commonFrame.setLayout(tmp);
        commonFrame.setTitle(title);
        commonFrame.setBounds(200, 200, boundWidth, boundHeight);
        commonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
    //this func is from another member 
	public BufferedImage getBufferedImage()    //convert toolkitImage into BufferedImage
	{
		BufferedImage buf = new BufferedImage(pi2.getWidth(null), pi2.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics g = buf.createGraphics();
		g.drawImage(pi2, 0, 0, null);   
	    g.dispose();
	    return buf;
	}
	
	//this func is from another member
	public boolean thisImageExistence()
	{
        if (pi2 == null)
        {
            JOptionPane.showMessageDialog(null, "Please open an image!");
            return false;
        }
        return true;
   }
	  	    
    public static void main(String args[])
    {
    	  mainFrame test = new mainFrame();
    }
}