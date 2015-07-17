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
       //Font f = new Font("宋体",Font.BOLD,16);
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
       JMenu FileMenu = new JMenu("文件");
       TestJMenuBar.add(FileMenu);
       JMenuItem File1 = new JMenuItem("打开图片");
       FileMenu.add(File1);
       JMenuItem File2 = new JMenuItem("刷新图片");
       FileMenu.add(File2);
       JMenuItem File3 = new JMenuItem("保存图片");
       FileMenu.add(File3);
       
       //listen of file
       File1.addActionListener(new ActionListener()
       {
    	   public void actionPerformed(ActionEvent e)
    	   {
    		   JFileChooser jChooser = new JFileChooser();
               int index = jChooser.showDialog(null, "打开图片");
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
                   //JOptionPane.showMessageDialog(null,"图片上传成功");
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
    		   int index = jChooser.showDialog(null, "保存图片");
               if(index == JFileChooser.APPROVE_OPTION) {
    		     final File selected = jChooser.getSelectedFile();
    		     String path1 = selected.getPath() + "/gray.jpg";
    		     String path = path1.replace("\\", "/");
    		     
    		     savepic(pi2,path);
    		     JOptionPane.showMessageDialog(null,"图片保存成功");
               }
    	   }
       });
        
       //Function
       JMenu FuncMenu1=new JMenu("趣味items");
       TestJMenuBar.add(FuncMenu1);
       
       JMenuItem Func3 = new JMenuItem("拼图游戏");
       FuncMenu1.add(Func3);
       
       JMenuItem Func8 = new JMenuItem("测测你的颜值");
       FuncMenu1.add(Func8);
       JMenuItem Func20 = new JMenuItem("画图工具");
       FuncMenu1.add(Func20);
       
       JMenu FuncMenu2=new JMenu("图片背后的故事");
       TestJMenuBar.add(FuncMenu2);
       JMenuItem Func4 = new JMenuItem("图片的频谱图");
       FuncMenu2.add(Func4);
       JMenuItem Func6 = new JMenuItem("图片的直方图");
       FuncMenu2.add(Func6);
       
       JMenu FuncMenu3=new JMenu("各种滤镜");
       TestJMenuBar.add(FuncMenu3);
       JMenuItem Func1 = new JMenuItem("老电视");//高斯
       FuncMenu3.add(Func1);
       JMenuItem Func2 = new JMenuItem("椒盐");
       FuncMenu3.add(Func2);
       JMenuItem Func5 = new JMenuItem("白天不懂夜的黑");    //傅里叶逆变换
       FuncMenu3.add(Func5);
       JMenuItem Func7 = new JMenuItem("岁月如梭");    //直方图均衡
       FuncMenu3.add(Func7);    
       JMenuItem Func9 = new JMenuItem("大头儿子小头爸爸");    //缩放
       FuncMenu3.add(Func9);
       JMenuItem Func0 = new JMenuItem("字符之美");    //字符画
       FuncMenu3.add(Func0);
       JMenuItem Func15 = new JMenuItem("小荷才露尖尖角");    //锐化
       FuncMenu3.add(Func15);
       JMenuItem Func16 = new JMenuItem("浮雕");    //sobel锐化
       FuncMenu3.add(Func16);
       
       JMenu FuncMenu4=new JMenu("还是滤镜");
       TestJMenuBar.add(FuncMenu4);
       JMenuItem Func11 = new JMenuItem("雾中花");    //算术平均
       FuncMenu4.add(Func11);
       JMenuItem Func12 = new JMenuItem("不同的模糊");    //谐波平均
       FuncMenu4.add(Func12);
       JMenuItem Func13 = new JMenuItem("双眼模糊");    //逆谐波平均
       FuncMenu4.add(Func13);
       JMenuItem Func14 = new JMenuItem("忘带眼镜");    //几何平均
       FuncMenu4.add(Func14);
       JMenuItem Func17 = new JMenuItem("熊猫眼");    //最小值滤波
       FuncMenu4.add(Func17);
       JMenuItem Func18 = new JMenuItem("天使的翅膀");    //最大值滤波
       FuncMenu4.add(Func18);
       JMenuItem Func19 = new JMenuItem("天使的熊猫眼");    //中值滤波
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
       JMenu AboutMenu=new JMenu("关于我们");
       TestJMenuBar.add(AboutMenu);
       JMenuItem About1 = new JMenuItem("团队介绍");
       AboutMenu.add(About1);
       JMenuItem About2 = new JMenuItem("联系我们");
       AboutMenu.add(About2);
       
       About1.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		    JOptionPane.showMessageDialog(null,"我们是中山大学软件学院数媒+电政的12届本科生，"
    		    	    + "\n"+"小组成员有："
    		    		+ "\n"+"12330090    关金槐"
    		    		+ "\n"+"12330091   关雨晗"
    		    		+ "\n12330092   管伟键"
    		    		+ "\n12330094   郭海旋"
    		    		+ "\n12330027   陈曼生"
    		    		+ "\n12330428   钟泳栅");
    	   }
       });
       
       About2.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		    JOptionPane.showMessageDialog(null,"若有任何问题，请联系：guanyuhan@qq.com");
    	   }
       });
       this.setTitle("趣味图像处理");
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