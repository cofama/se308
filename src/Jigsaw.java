//package teamwork;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.util.Calendar;
import java.util.Date;
import java.awt.event.*;

public class Jigsaw extends JFrame implements ActionListener {
	JButton restart = new JButton("重新开始");
    JButton gameImg = new JButton("更换图片");
    int endh,endm,ends;
    boolean isInitial;
    String filename = "e.jpg";
	ImageIcon preimage=null;
    JButton prebtn=null;
    int m=3,n=3;
    int w=120,h=120;
    int step=0;
    int sed = 0;
    Thread thread;
    JLabel lstep=new JLabel("步数：0步");
    JLabel pict = new JLabel("原图");
    JLabel time=new JLabel("时间：00:00");
    ImageIcon[] pic=null;
	JButton btn[][]=null;
	
    public Jigsaw() {
    	this.setTitle("拼图游戏");
        this.setLayout(null);
        this.setBounds(250,30,700,680);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        this.repaint();
    }
    
    public void init() {
    	lstep.setBounds(500,300,100,30);
    	pict.setBounds(400,10,100,30);
        time.setBounds(500,400,200,30);
        restart.setBounds(500,500,100,30);
        gameImg.setBounds(500,550,100,30);
        this.add(lstep);
        this.add(pict);
        this.add(time);
        this.add(restart);
        this.add(gameImg);
        restart.addActionListener(this);
        gameImg.addActionListener(this);
        this.setVisible(true);
        this.setResizable(true);
        
        isInitial = true;
        setpic();
    }

    public void setpic() {
	    step=0;
        lstep.setText("步数："+step+"步");
        Calendar aa = Calendar.getInstance();
        int starth = aa.get(Calendar.HOUR_OF_DAY);
        int startm = aa.get(Calendar.MINUTE);
        int starts = aa.get(Calendar.SECOND);
        thread = new Thread() {
    	    @Override
            public void run() {
    		    while(true) {
    			    Calendar bb = Calendar.getInstance();
    			    endh = bb.get(Calendar.HOUR_OF_DAY);
    			    endm = bb.get(Calendar.MINUTE);
    			    ends = bb.get(Calendar.SECOND);
    			    Date y = bb.getTime();
    			    String endt = y.toLocaleString();
    			    sed = (endm - startm)*60+(ends-starts);
    			    time.setText("时间："+sed+"秒");
                }
    	    }
        };
        thread.start();
        w=400/m;
        h=400/n;
        this.repaint();
        Image[] l=splitImage(filename,1,1,200,200);
        preimage=new ImageIcon(l[0]);
        if(prebtn==null)
        	prebtn = new JButton(preimage);
        else
        	prebtn.setIcon(preimage);
        prebtn.setBounds(450,05,200,200);
        this.add(prebtn);
        
        pic=new ImageIcon[9];
        if(btn==null)
        	btn=new JButton[4][3];
        Image[] t=splitImage(filename,3,3,w,h);
        
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 3;j++)
            {
                if(btn[i][j]==null) {
            	    btn[i][j]=new JButton();
                    btn[i][j].setBounds(j*w+5,i*h+50,w,h);
                }
                if(!((i==0)&&(j==1 || j==2)))
                {
                    btn[i][j].addActionListener(this);
                    btn[i][j].addKeyListener(new MyKeyListener());
                }
                else
                    btn[i][j].setVisible(false);
                this.add(btn[i][j]);
            }
        int temp[]={0,8,7,4,5,3,6,2,1};
        for(int i=0;i<9;i++)
        {
            pic[i]=new ImageIcon(t[i]);
        }
        for (int i=0;i<9;i++)
        {
        	btn[(i+3)/3][(i+3)%3].setIcon(pic[temp[i]]);
        }
        btn[0][0].setIcon(null);
        btn[0][1].setIcon(null);
        btn[0][2].setIcon(null);
        this.repaint();
    }
@Override
    public void actionPerformed(ActionEvent e) {
	    if(e.getSource()==restart)
	    {
	    	thread.stop();
	    	setpic();
	    	return;
	    }
	    if(e.getSource()==gameImg) {
	    	FileDialog df=new FileDialog(this,"图片选择",FileDialog.LOAD);
	    	df.setVisible(true);
	    	filename=df.getDirectory()+df.getFile();
	    	for(int j=0;j<(m+1)*n;j++) {
	    		int x2=j/n,y2=j%n;
	    		btn[x2][y2].removeActionListener(this);
	    	}
	    	isInitial = false;
	    	thread.stop();
	    	setpic();
	    	return;
	    }
        
	    for(int i = 0;i<4;i++)
	    	for(int j = 0;j<3;j++) {
	    		int x1=i,y1=j;
	    	    if (e.getSource()==btn[x1][y1])
	    	    {
	    	    	step++;
	    	    	lstep.setText("步数："+step+"步");
	    	    	if((x1==0&&y1==1)||(x1==0&&y1==2))
	    	    		continue;
	    	    	//btn[x1][y1].addKeyListener(new MyKeyListener());
	    	    	if(x1>0&&btn[x1-1][y1].isVisible()&&btn[x1-1][y1].getIcon()==null) {
	    	    		btn[x1-1][y1].setIcon(btn[x1][y1].getIcon());
	    	    		btn[x1][y1].setIcon(null);
	    	    	}
	    	    	if(x1<m&&btn[x1+1][y1].isVisible()&&btn[x1+1][y1].getIcon()==null) {
	    	    		btn[x1+1][y1].setIcon(btn[x1][y1].getIcon());
	    	    		btn[x1][y1].setIcon(null);
	    	    	}
	    	    	if(y1>0&&btn[x1][y1-1].isVisible()&&btn[x1][y1-1].getIcon()==null) {
	    	    		btn[x1][y1-1].setIcon(btn[x1][y1].getIcon());
	    	    		btn[x1][y1].setIcon(null);
	    	    	}
	    	    	if(y1<n-1&&btn[x1][y1+1].isVisible()&&btn[x1][y1+1].getIcon()==null) {
	    	    		btn[x1][y1+1].setIcon(btn[x1][y1].getIcon());
	    	    		btn[x1][y1].setIcon(null);
	    	    	}
	    	    	btn[x1][y1].setBackground(new Color(167, 245, 120));
	    	    	int finish = 0;
	    	    	for(int z = 3; z < 12; z++)
	    	    	{
	    	    		int x=z/3,y=z%3;
	    	    		if(btn[x][y].getIcon()!=pic[z-3])
	    	    		{
	    	    			finish = 1;
	    	    		}
	    	    	}
	    	    	if(finish == 0)
	    	    	{
	    	    		JOptionPane.showMessageDialog(null,"Congratulate,you win the game with "+step+" steps and "+sed+" seconds");
	    	    	}
	    	    }
	    	}
    }
    public Image[] splitImage(String file, int rows, int cols, int w, int h) {
    	Image t;
    	if (isInitial) {
    		t = new ImageIcon(mainFrame.class.getResource("e.jpg")).getImage();
    	}
    	else {
    		t = new ImageIcon(file).getImage();
    	}
    	Image[] result = new Image[rows * cols];
    	for (int i = 0; i < result.length; i++) {
    		result[i] = createImage(w, h);
    		Graphics g = result[i].getGraphics();
    		g.translate((-i%cols) * w,(-i/cols)*h);
    		g.drawImage(t, 0, 0, w*cols,rows*h,0,0,t.getWidth(this),t.getHeight(this),this);
    	}
    	return result;
    }
   
    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int charA=e.getKeyCode();
            for(int i=1;i<(m+1)*n;i++)
            {
                int x1=i/n,y1=i%n;
                if((x1==0&&y1==1)||(x1==0&&y1==2))
                    continue;
                if((btn[x1][y1].getIcon() == null)&&(btn[x1][y1].isVisible()))
                {   step++;
                    lstep.setText("步数："+step+"步");
                    if((charA==KeyEvent.VK_UP)&&(x1>0))
                    {    if(!btn[x1-1][y1].isVisible()) break;
                         btn[x1][y1].setIcon(btn[x1-1][y1].getIcon());
                         btn[x1-1][y1].setIcon(null);
                         charA=0;
                         break;
                     }
                  
                    if((charA==KeyEvent.VK_DOWN)&&(x1<m ))
                    {   if(!btn[x1+1][y1].isVisible()) break;
                        btn[x1][y1].setIcon(btn[x1+1][y1].getIcon());
                        btn[x1+1][y1].setIcon(null);
                        charA=0;
                        break;
                    }
                    
                    if((charA==KeyEvent.VK_LEFT)&&(y1>0))
                    {   if(!btn[x1][y1-1].isVisible()) break;
                        btn[x1][y1].setIcon(btn[x1][y1-1].getIcon());
                        btn[x1][y1-1].setIcon(null);
                        charA=0;
                        break;
                    }
                
                    if((charA==KeyEvent.VK_RIGHT)&&(y1<n-1))
                    {   if(!btn[x1][y1+1].isVisible()) break;
                        btn[x1][y1].setIcon(btn[x1][y1+1].getIcon());
                        btn[x1][y1+1].setIcon(null);
                        charA=0;
                        break;
                    }
                }
            }
        }
    }
    public static void main(String[] args){
    	Jigsaw p= new Jigsaw();
    }
}

