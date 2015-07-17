//package teamwork;

import java.awt.Font;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class random {
	public int score;
	Font f1=new Font("宋体",Font.BOLD,30);
  
    public int myRandom() {
    	Random r = new Random();	
    	int score = r.nextInt(10);
    	score = Math.abs(r.nextInt() % 100);
    	score++;
    	System.out.println(score);
    	return score;
    }
  
    public String InttoString(int a)
    {
    	String str = "";
    	str = a + "";
    	return str;
    }
    public void Frame()
    {
    	JFrame RFrame = new JFrame("测测你的颜值");
    	RFrame.setSize(400,400);
    	RFrame.setLocation(200,200);
    	RFrame.setVisible(true);
    	JLabel l1;
    	l1 = new JLabel("你的颜值为");
    	l1.setFont(f1);
    	l1.setBounds(100, 10, 200, 50);
    	JLabel l2;
    	l2 = new JLabel(InttoString(myRandom()) + "/100");
    	l2.setHorizontalAlignment(SwingConstants.CENTER);
    	l2.setFont(f1);
    	//l2.setBounds(100, 80, 200, 100);
    	RFrame.add(l1);
    	RFrame.add(l2);  
    }
}
