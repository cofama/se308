//package teamwork
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;
//定义画图的基本图形单元
public class ImageDrawing extends JFrame implements ActionListener //主类，扩展了JFrame类，用来生成主界面
{
	
    private String names[] = {
        "Draw at will",
        "Draw a straight line",
        "Draw a rectangle",
        "Draw an oval",
        "Draw a circle",    
        "Draw a round rectangle",    
        "Erase at will",     
    };
    private JLabel statusBar;           
    private DrawPanel drawPanel;       
    private int width = 800,  height = 550;   
    primitive[] primitiveList = new primitive[5000];
    private int currentChoice = 0;            
    int index = 0;                        
    private Color color = Color.black;    
    int R, G, B;                                      
    private float stroke = 1.0f;  
    BufferedImage image;
    boolean isImage = false;
    String name;
    JComboBox styles;
    JMenuItem newItem,saveItem,loadItem,colorItem,strokeItem,exitItem;
    JMenuItem[] menuItem;
    public ImageDrawing() //构造函数
    {
        super("Drawing Pad");
        JMenuBar bar = new JMenuBar();      //定义菜单条
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        newItem = new JMenuItem("New");
        newItem.setMnemonic('N');
        newItem.addActionListener(this);
        fileMenu.add(newItem);

        saveItem = new JMenuItem("Save");
        saveItem.setMnemonic('S');
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);

        loadItem = new JMenuItem("Load");
        loadItem.setMnemonic('L');
        loadItem.addActionListener(this);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();

        exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);
        bar.add(fileMenu);
        
        JMenu brushType = new JMenu("BrushType");
        menuItem = new JMenuItem[8];
        for(int i = 0;i < 7;i++){
        	menuItem[i] = new JMenuItem(names[i]);
        	brushType.add(menuItem[i]);
        	menuItem[i].addActionListener(this);
        }
        bar.add(brushType);

        JMenu colorMenu = new JMenu("Color");
        colorMenu.setMnemonic('C');

        colorItem = new JMenuItem("Choose Color");
        colorItem.setMnemonic('O');
        colorItem.addActionListener(this);
        colorMenu.add(colorItem);
        bar.add(colorMenu);

        JMenu strokeMenu = new JMenu("Stroke");
        strokeMenu.setMnemonic('S');

        strokeItem = new JMenuItem("Set Stroke");
        strokeItem.setMnemonic('K');
        strokeItem.addActionListener(this);
        strokeMenu.add(strokeItem);
        bar.add(strokeMenu);
        drawPanel = new DrawPanel();

        Container c = getContentPane();
        super.setJMenuBar(bar);

        c.add(drawPanel, BorderLayout.CENTER);
        statusBar = new JLabel();
        c.add(statusBar, BorderLayout.SOUTH);
        statusBar.setText("     Welcome To The Little Drawing Pad!!!  :)");
        createPrimitive();
        setSize(width, height);
        show();
    }
    class mouseA extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            statusBar.setText("     Mouse Pressed @:[" + e.getX() +
                    ", " + e.getY() + "]");//设置状态提示
            primitiveList[index].x1 = primitiveList[index].x2 = e.getX();
            primitiveList[index].y1 = primitiveList[index].y2 = e.getY();
            index++;
            createPrimitive();
            
            repaint();
        }
        public void mouseReleased(MouseEvent e) {
            statusBar.setText("     Mouse Released @:[" + e.getX() +
                    ", " + e.getY() + "]");
            primitiveList[index].x2 = e.getX();
            primitiveList[index].y2 = e.getY();
            if (currentChoice == 0 || currentChoice == 6) {
                primitiveList[index].x1 = e.getX();
                primitiveList[index].y1 = e.getY();
            }
            index++;
            createPrimitive();
            repaint();
        }
        public void mouseEntered(MouseEvent e) {
            statusBar.setText("     Mouse Entered @:[" + e.getX() +
                    ", " + e.getY() + "]");
        }
        public void mouseExited(MouseEvent e) {
            statusBar.setText("     Mouse Exited @:[" + e.getX() +
                    ", " + e.getY() + "]");
        }
    }

    class mouseB extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            statusBar.setText("     Mouse Dragged @:[" + e.getX() +
                    ", " + e.getY() + "]");
            if (currentChoice == 0 || currentChoice == 6) {
                primitiveList[index - 1].x1 = primitiveList[index].x2 = primitiveList[index].x1 = e.getX();
                primitiveList[index - 1].y1 = primitiveList[index].y2 = primitiveList[index].y1 = e.getY();
                index++;
                createPrimitive();
            } else {
                primitiveList[index].x2 = e.getX();
                primitiveList[index].y2 = e.getY();
            }
            repaint();
        }
        public void mouseMoved(MouseEvent e) {
            statusBar.setText("     Mouse Moved @:[" + e.getX() +
                    ", " + e.getY() + "]");
        }
    }

    class DrawPanel extends JPanel {
        public DrawPanel() {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            setBackground(Color.white);
            addMouseListener(new mouseA());
            addMouseMotionListener(new mouseB());
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;    //定义画笔
            int j = 0;
            if(isImage){
            	try {
            		image = ImageIO.read(new File(name));
            		g2d.drawImage(image, 0, 0, this);
            	} catch (IOException e) {
				// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
            
            }
           
        
           	while (j <= index) {
          		draw(g2d, primitiveList[j]);
           		j++;
          	}
          }
        

        void draw(Graphics2D g2d, primitive i) {
            i.draw(g2d);//将画笔传入到各个子类中，用来完成各自的绘图
        }
    }
//新建一个画图基本单元对象的程序段
    void createPrimitive() {
        
        drawPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));    
        switch (currentChoice) {
            case 0:
                primitiveList[index] = new Pencil();
                break;
            case 1:
                primitiveList[index] = new Line();
                break;
            case 2:
                primitiveList[index] = new Rect();
                break;
            case 3:
                primitiveList[index] = new Oval();
                break;
            case 4:
                primitiveList[index] = new Circle();
                break;
            case 5:
                primitiveList[index] = new RoundRect();
                break;
            case 6:
                primitiveList[index] = new Rubber();
                break;
        }
        primitiveList[index].type = currentChoice;
        primitiveList[index].R = R;
        primitiveList[index].G = G;
        primitiveList[index].B = B;
        primitiveList[index].stroke = stroke;
    }
    public void actionPerformed(ActionEvent e){
    	if(e.getSource()  == newItem){
    		newFile();
    	}
    	if(e.getSource()  == loadItem){
    		try {
				loadFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	if(e.getSource()  == saveItem){
    		saveFile();
    	}
    	for(int i = 0;i < 8;i++){
    		if(e.getSource()==menuItem[i]){
    			currentChoice = i;
    			createPrimitive();
    			repaint();
    		}
    	}
    	if(e.getSource()  == strokeItem){
    		setStroke();
    	}
    	if(e.getSource()  == colorItem){
    		setColor();
    	}
    	if(e.getSource()  == exitItem){
    		System.exit(0);
    	}
    }
//选择当前颜色程序段
    public void setColor() {
        color = JColorChooser.showDialog(ImageDrawing.this,
                "Choose a color", color);
        R = color.getRed();
        G = color.getGreen();
        B = color.getBlue();
        primitiveList[index].R = R;
        primitiveList[index].G = G;
        primitiveList[index].B = B;
    }
//选择当前线条粗细程序段
    public void setStroke() {
        String input;
        input = JOptionPane.showInputDialog(
                "Please input a float stroke value! ( >0 )");
        stroke = Float.parseFloat(input);
        primitiveList[index].stroke = stroke;
    }
//保存图形文件程序段
    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(this);
        File file = fileChooser.getSelectedFile();
        Dimension d = drawPanel.getSize();
        BufferedImage saveImage = (BufferedImage)drawPanel.createImage(d.width,d.height);
        Graphics g = saveImage.getGraphics();
        drawPanel.paintComponent(g);
        try {
        	ImageIO.write(saveImage,"JPG",file);
        } catch (IOException e) {
		// TODO Auto-generated catch block
        	e.printStackTrace();
        }
      
    }
    public void loadFile() throws IOException {
    	JFileChooser chooser;
         chooser = new  JFileChooser();
    	 int result = chooser.showOpenDialog(null);
    	 if(result == JFileChooser.APPROVE_OPTION){
        	 isImage = true;
        	 name = chooser.getSelectedFile().getPath();
         }
         repaint();
    }
    public void newFile() {
        index = 0;
        currentChoice = 0;
        createPrimitive();
        repaint();//将有关值设置为初始状态，并且重画
    }
//主函数段
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }//将界面设置为当前windows风格
        ImageDrawing newPad = new ImageDrawing();
        newPad.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
    }
}
class primitive implements Serializable//父类，基本图形单元，用到串行化接口，保存时所用
{
    int x1, y1, x2, y2; 
    int R, G, B;       
    float stroke;      
    int type;       
    void draw(Graphics2D g2d) {
    }
    ;//定义绘图函数
}
class Line extends primitive //直线类
{
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
}
class Rect extends primitive//矩形类
{
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2),
                Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
}
class Oval extends primitive//椭圆类
{
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2),
                Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
}
class Circle extends primitive//圆类
{
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }
}

class RoundRect extends primitive//圆角矩形类
{
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2),
                Math.abs(x1 - x2), Math.abs(y1 - y2),
                50, 35);
    }
}

class Pencil extends primitive//随笔画类
{
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
}
class Rubber extends primitive//橡皮擦类
{
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(255, 255, 255));
        g2d.setStroke(new BasicStroke(stroke + 4,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
}
