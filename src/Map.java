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
	//�s��map�Ʀr���G�L�}�C
	static int[][] MapArr = new int[10][10];
    //Panel
	private JPanel Main;
	//�Ϥ�
	Image Wallimage=new ImageIcon("src/brickwall.png").getImage();
	Image Diamondimage=new ImageIcon("src/diamond.png").getImage();
	Image Ghostimage=new ImageIcon("src/ghost.png").getImage();
	Image Heartimage=new ImageIcon("src/heart.png").getImage();
	
	//�Ϥ�����
	int wallcount;
	int diamondcount;
	int ghostcount;
	int heartcount;
	int roadcount;
	//HP��
	private JPanel HP;
	private String info;
	private int hp=100; 
		
	public Map() {
		openFile();
		readMap();
		
		//�إߥDPanel
		Main = new JPanel();
		Main.setLayout(new GridLayout(10,10));
		
		//����Ϯw
		ArrayList<DrawImage> wall = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			wall.add(new DrawImage(Wallimage));
		}
		//�p�۹Ϯw
		ArrayList<DrawImage> diamond = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			diamond.add(new DrawImage(Diamondimage));
		}
		//���Ϯw
		ArrayList<DrawImage> ghost = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			ghost.add(new DrawImage(Ghostimage));
		}
		//�R�߹Ϯw
		ArrayList<DrawImage> heart = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			heart.add(new DrawImage(Heartimage));
		}
		//�D���Ϯw
		ArrayList<DrawImage> road = new ArrayList<DrawImage>();
		for(int i=0;i < 100;i++) {
			road.add(new DrawImage());
		}
		
		//ø�s���
		JPanel HP = new JPanel();
		HP.setLayout(new BoxLayout(HP, BoxLayout.X_AXIS));
		HP.setOpaque(true);
		ArrayList<JPanel> hpPanel = new ArrayList<JPanel>();
		for(int i=0;i < 20;i++) {
			hpPanel.add(new JPanel());
			hpPanel.get(i).setBackground(Color.BLUE);
			HP.add(hpPanel.get(i));
		}
		
		//ø�s�a��
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				switch (MapArr[i][j]) {
				   
				   //�I��D���Ҳ��ͪ��ƥ�
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
							 //�p�G����k0   ��������
								if (hp == 0) {
									int lose=JOptionPane.showConfirmDialog(null, "����k0","����", JOptionPane.DEFAULT_OPTION);
									System.exit(0);
								}
						   }
					   );
					   roadcount++;
					   break;
				   case 1 :
					   Random ran = new Random();
					   
					   //�I��R�ߩҲ��ͪ��ƥ�
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
					   
					   //�I�찭�Ҳ��ͪ��ƥ�
					   else if (ran.nextInt(7) == 2) {
						   Main.add(ghost.get(ghostcount));
						   ghost.get(ghostcount).addMouseListener((MovedListener)(e) ->{
								   hp = 0;
								   for(int w=0;w < 20;w++) {
										hpPanel.get(w).setBackground(Color.WHITE);
									}
								 //�p�G����k0   ��������
									if (hp == 0) {
										int lose=JOptionPane.showConfirmDialog(null, "����k0","����", JOptionPane.DEFAULT_OPTION);
										System.exit(0);
									}
							   });
					   }
						   
						   
					   //�I������Ҳ��ͪ��ƥ�	   
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
							 //�p�G����k0   ��������
								if (hp == 0) {
									int lose=JOptionPane.showConfirmDialog(null, "����k0","����", JOptionPane.DEFAULT_OPTION);
									System.exit(0);
								}
					   });
					   }
					   ghostcount++;
					   heartcount++;
					   wallcount++;
					   break;
				   //�I���p�۩Ҳ��ͪ��ƥ�
				   case 2 :
					   Main.add(diamond.get(diamondcount));
					   diamond.get(diamondcount).addMouseListener(new MouseAdapter() {
						   public void mouseEntered(MouseEvent e) {
							   int sucess=JOptionPane.showConfirmDialog(null, "���߳q�L","���\", JOptionPane.DEFAULT_OPTION); 
							   System.exit(0);
						   }
					   });
					   diamondcount++;
					   break;
				}
			}
		}
		
		//�N���,�a�ϥ[�JFrame
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
