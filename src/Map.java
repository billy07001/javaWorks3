import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.*;


public class Map extends JFrame{
	//Scanner
	private static Scanner input;	 
	//存放map數字的二微陣列
	static int[][] MapArr = new int[10][10];
    //Panel
	private JPanel Main;
	//圖片
	Image Wallimage=new ImageIcon("src/brickwall.png").getImage();
	Image Diamondimage=new ImageIcon("src/diamond.png").getImage();
	Image Ghostimage=new ImageIcon("src/ghost.png").getImage();
	Image Heartimage=new ImageIcon("src/heart.png").getImage();
	
	//圖片索引
	int wallcount;
	int diamondcount;
	int ghostcount;
	int heartcount;
	int roadcount;
	//HP條
	private JPanel HP;
	private String info;
	private int hp=100; 
		
	public Map() {
		openFile();
		readMap();
		
		//建立主Panel
		Main = new JPanel();
		Main.setLayout(new GridLayout(10,10));
		
		//牆壁圖庫
		ArrayList<DrawImage> wall = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			wall.add(new DrawImage(Wallimage));
		}
		//鑽石圖庫
		ArrayList<DrawImage> diamond = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			diamond.add(new DrawImage(Diamondimage));
		}
		//鬼圖庫
		ArrayList<DrawImage> ghost = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			ghost.add(new DrawImage(Ghostimage));
		}
		//愛心圖庫
		ArrayList<DrawImage> heart = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			heart.add(new DrawImage(Heartimage));
		}
		//道路圖庫
		ArrayList<DrawImage> road = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			road.add(new DrawImage());
		}
		
		//繪製血條
		JPanel HP = new JPanel();
		HP.setLayout(new BoxLayout(HP, BoxLayout.X_AXIS));
		HP.setOpaque(true);
		ArrayList<JPanel> hpPanel = new ArrayList<JPanel>();
		for(int i=0;i < 20;i++) {
			hpPanel.add(new JPanel());
			hpPanel.get(i).setBackground(Color.BLUE);
			HP.add(hpPanel.get(i));
		}
		
		//繪製地圖
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				switch (MapArr[i][j]) {
				   
				   //碰到道路所產生的事件
				   case 0 :					
					   Main.add(road.get(roadcount));
					   road.get(roadcount).setBackground(Color.WHITE);
					   road.get(roadcount).addMouseListener((MovedListener)(e) ->{
							   hp=hp-5;
							   if(hp >= 0)
							   {hpPanel.get(hp/5).setBackground(Color.WHITE);}
							   
							   if (hp < 0) {
								   hp = 0;
							   }
							 //如果血條歸0   關閉視窗
								if (hp == 0) {
									int lose=JOptionPane.showConfirmDialog(null, "血條歸0","失敗", JOptionPane.DEFAULT_OPTION);
									System.exit(0);
								}
						   }
					   );
					   roadcount++;
					   break;
				   case 1 :
					   Random ran = new Random();
					   
					   //碰到愛心所產生的事件
					   if (ran.nextInt(7) == 0) {
						   Main.add(heart.get(heartcount));
						   heart.get(heartcount).addMouseListener((MovedListener)(e) ->{							   
								   if (hp > 70) {
										hp = 100;
									   for(int q=0;q < 20;q++)  
											hpPanel.get(q).setBackground(Color.BLUE);
						         }
								   
								   
								   if (hp <= 70) {
								   for(int d = 0 ; d < 6 ; d++) {
									   if (hp <= 100)
									   hpPanel.get((hp+(d*5))/5).setBackground(Color.BLUE);
									   }								   
								   hp = hp+30;
								   }
								   });
					   }
					   
					   //碰到鬼所產生的事件
					   else if (ran.nextInt(7) == 2) {
						   Main.add(ghost.get(ghostcount));
						   ghost.get(ghostcount).addMouseListener((MovedListener)(e) ->{
								   hp = 0;
								   for(int w=0;w < 20;w++) {
										hpPanel.get(w).setBackground(Color.WHITE);
									}
								 //如果血條歸0   關閉視窗
									if (hp == 0) {
										int lose=JOptionPane.showConfirmDialog(null, "血條歸0","失敗", JOptionPane.DEFAULT_OPTION);
										System.exit(0);
									}
							   });
					   }
						   
						   
					   //碰到牆壁所產生的事件	   
					   else {
					   Main.add(wall.get(wallcount));
					   wall.get(wallcount).addMouseListener((MovedListener)(e) ->{
							   int hp1=hp-5;
							   int hp2=hp-10;
							   int hp3=hp-15;
							   int hp4=hp-20;
							   hp=hp-20;
							   if(hp >= 0)
							   {hpPanel.get(hp1/5).setBackground(Color.WHITE);
							    hpPanel.get(hp2/5).setBackground(Color.WHITE);
							    hpPanel.get(hp3/5).setBackground(Color.WHITE);
							    hpPanel.get(hp4/5).setBackground(Color.WHITE);
							   }
							   
							   if (hp < 20) {
								for(int r=0;r < 4;r++)  
								hpPanel.get(r).setBackground(Color.WHITE);  
							   }
							   
							   if (hp < 0) {
								   hp = 0;
							   }
							 //如果血條歸0   關閉視窗
								if (hp == 0) {
									int lose=JOptionPane.showConfirmDialog(null, "血條歸0","失敗", JOptionPane.DEFAULT_OPTION);
									System.exit(0);
								}
					   });
					   }
					   ghostcount++;
					   heartcount++;
					   wallcount++;
					   break;
				   //碰到鑽石所產生的事件
				   case 2 :
					   Main.add(diamond.get(diamondcount));
					   diamond.get(diamondcount).addMouseListener(new MouseAdapter() {
						   public void mouseEntered(MouseEvent e) {
							   int sucess=JOptionPane.showConfirmDialog(null, "恭喜通過","成功", JOptionPane.DEFAULT_OPTION); 
							   System.exit(0);
						   }
					   });
					   diamondcount++;
					   break;
				}
			}
		}
		
		//將血條,地圖加入Frame
		add(HP, BorderLayout.SOUTH);
		add(Main, BorderLayout.CENTER);
		closeFile();
	}

	
	
//To open file	
public static void openFile() {
	try 
	{
	    input = new Scanner(Paths.get("src/map.txt"));	
	}
	
	catch (IOException ioException)
	{
		System.err.println("Error opening file. Terminating");
		System.exit(1);
	}
}

//To read map.txt
public static void readMap() {
	try
	{
		while (input.hasNext()) {
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					MapArr[i][j]=input.nextInt();
				}
			}
		}
	}
	catch (NoSuchElementException elementException)
	{
		System.err.println("File improperty formed. Terminating");
	}
	catch (IllegalStateException stateException)
	{
		System.err.println("Error reading from file. Terminating");
	}
}

//To close file
public static void closeFile() {
	if (input != null)
		input.close();
}


}


interface MovedListener extends MouseListener
{
    @Override
    public default void mouseExited(MouseEvent e) {}

    @Override
    public default void mousePressed(MouseEvent e) {}

    @Override
    public default void mouseReleased(MouseEvent e) {}
    
    @Override
    public default void mouseClicked(MouseEvent e) {}
}
