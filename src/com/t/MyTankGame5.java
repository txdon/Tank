/**
 * ���ܣ�̹����Ϸ4.0
 * 1.����̹��
 * 2.�ҵ�̹�˿������������ƶ�
 * 3.���Է����ӵ����ӵ�����(�������5��)
 * 4.����̹�˿��������ӵ������ҿ��Ի��๥��
 * 5.���Լ�¼�ҵ�̹�˻��ٵ���̹�˵����������ҿ��Իָ���ĳһʱ�̵���Ϸ״̬
 */

package com.t;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.regex.Pattern;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyTankGame5 extends JFrame implements ActionListener{
	//�������
	//����˵���
	JMenuBar jMenuBar=null;
	//����˵�
	JMenu jMenu=null;
	//����˵���ѡ��
	JMenuItem jMenuItem=null;
	JMenuItem jMenuItem2=null;
	
	//�������
	MyPanel mp=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame5 my=new MyTankGame5();
	}
	
	public MyTankGame5() {
		// TODO Auto-generated constructor stub
		mp=new MyPanel();
		
		//�����˵���
		jMenuBar=new JMenuBar();
		//�����˵�
		jMenu=new JMenu("�ļ�");
		//�����˵���ѡ��
		jMenuItem=new JMenuItem("����");
		jMenuItem2=new JMenuItem("�浵");
		
		jMenuBar.add(jMenu);
		jMenu.add(jMenuItem);
		jMenu.add(jMenuItem2);
		
		//����mp�߳� 
		Thread t2=new Thread(mp);
		t2.start();
		
		this.add(mp);
		
		//ע�����
		this.addKeyListener(mp);
		jMenuItem.addActionListener(this);
		jMenuItem.setActionCommand("open");
		jMenuItem2.addActionListener(this);
		jMenuItem2.setActionCommand("save");
		
		this.setJMenuBar(jMenuBar);
		
		this.setSize(400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("open")) {
			
			//����
			
			FileReader fileReader=null;
			BufferedReader bufferedReader=null;
			
			try {
				fileReader=new FileReader("res/record.txt");
				bufferedReader=new BufferedReader(fileReader);
				
				String s="";
				while ((s=bufferedReader.readLine())!=null) {
					System.out.println(Pattern.compile(":").split(s));
				}
				
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}finally {
				
			}
			
		}else if (e.getActionCommand().equals("save")) {
			
			//�浵
			
			FileWriter fileWriter=null;
			BufferedWriter bufferedWriter=null;
			
			try {
				fileWriter=new FileWriter("res/record.txt");
				bufferedWriter=new BufferedWriter(fileWriter);
				//�����ҷ�̹�˵�λ������--0
				bufferedWriter.write("0 "+Integer.toString(mp.hero.getX())+" "+Integer.toString(mp.hero.getY())+"\r\n");
				//�������̹�˵�λ������
				for (int i = 0; i < mp.ets.size(); i++) {
					//�ӵ���̹������ȡ��һ������̹��--1
					EnemyTank enemyTank=mp.ets.get(i);
					bufferedWriter.write("1 "+Integer.toString(enemyTank.getX())+" "+Integer.toString(enemyTank.getY())+"\r\n");					
				}
				
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}finally {
				try {
					//�ر��ļ���
					bufferedWriter.close();
					fileWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}

class MyPanel extends JPanel implements KeyListener,Runnable{
	
	//����һ���ҵ�̹��
	Hero hero=null;
	
	//������˵�̹����
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//����ը������
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	int enSize=3;
	
	int destroyEnemyTankNum=0;
	
	//��������ͼƬ������ͼƬʵ������һ��ը��
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//���캯��
	public MyPanel() {
		hero=new Hero(100, 100);
		
		//��ʼ�����˵�̹��
		for (int i = 0; i < enSize; i++) {
			//����һ�����˵�̹�˶���
			EnemyTank et=new EnemyTank((i+1)*50, 0);
			Thread thread=new Thread(et);
			thread.start();
			
			et.setColor(0);
			et.setDirect(2);
			//���뵽̹����
			ets.add(et);
		}
		
		//����ͼƬ
		try {
			image1=ImageIO.read(new File("res/bomb_1.gif"));
			image2=ImageIO.read(new File("res/bomb_2.gif"));
			image3=ImageIO.read(new File("res/bomb_3.gif"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//����ͼƬ,�������ַ�������ͼƬ��������⣬����Ҳ������������·����res/bomb_1.gif������ͼƬ
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

	}
	
	//��дpaint
	public void paint(Graphics g) {
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		//�����Լ���̹��
		if (hero.isLive) {
			
			this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		}
		
		for (int i = 0; i < hero.bullets.size(); i++) {
			Bullet myBullet=hero.bullets.get(i);
			//�����ӵ�,����һ���ӵ�
			if (myBullet!=null && myBullet.isLive==true) {
				g.draw3DRect(myBullet.getX(), myBullet.getY(), 1, 1, false);
			}
			if (myBullet.isLive==false) {
				//������bullets��ɾ�������ӵ�
				hero.bullets.remove(myBullet);
			}
			
		}
		
		g.setColor(Color.red);
		g.drawString("��������"+Integer.toString(destroyEnemyTankNum), 320, 15);
		
		//����ը��
		for (int i = 0; i < bombs.size(); i++) {
			//ȡ��ը��
			Bomb b=bombs.get(i);
			if (b.life>6) {
				g.drawImage(image1, b.getX(), b.getY(), 30, 30, this);
			}else if (b.life>3) {
				g.drawImage(image2, b.getX(), b.getY(), 30, 30, this);
			}else {
				g.drawImage(image3, b.getX(), b.getY(), 30, 30, this);
			}
			
			//��ը��������ֵ��С
			b.lifeDown();
			//���ը������ֵΪ0������������Ƴ�
			if (b.life==0) {
				bombs.remove(b);
			}
			
		}
		
		//�������˵�̹��
		for (int i = 0; i < ets.size(); i++) {
			EnemyTank et=ets.get(i);
			if (et.isLive) {
				
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
				for (int j = 0; j < et.bullets.size(); j++) {
					Bullet etBullet=et.bullets.get(j);
					//��������̹���ӵ�
					if (etBullet!=null && etBullet.isLive==true) {
						g.draw3DRect(etBullet.getX(), etBullet.getY(), 1, 1, false);
					}
					if (etBullet.isLive==false) {
						//������bullets��ɾ�������ӵ�
						et.bullets.remove(etBullet);
					}
					
				}
			}
			
		}
	}
	
	//��¼�ҷ�̹�˻��ٵ���̹�˵�����
	public void recordNum(int num, Hero hero, Vector<EnemyTank> enemyTanks) {
		
		FileWriter fileWriter=null;
		BufferedWriter bufferedWriter=null;
		
		try {
			fileWriter=new FileWriter("res/record.txt");
			bufferedWriter=new BufferedWriter(fileWriter);
			
			bufferedWriter.write(Integer.toString(num)+"\r\n");
			
			bufferedWriter.write(Integer.toString(hero.getX())+" ");
			bufferedWriter.write(Integer.toString(hero.getY())+"\r\n");
			
			for (int i = 0; i < enemyTanks.size(); i++) {
				EnemyTank enemyTank=enemyTanks.get(i);
				bufferedWriter.write(Integer.toString(enemyTank.getX())+" ");
				bufferedWriter.write(Integer.toString(enemyTank.getY())+"\r\n");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//�ж��ҵ�̹���Ƿ񱻻���
	public void hitMe() {
		//ȡ��ÿһ�����˵�̹��
		for (int i = 0; i < ets.size(); i++) {
			//ȡ��̹��
			EnemyTank enemyTank=ets.get(i);
			if (enemyTank.isLive) {
				for (int j = 0; j < enemyTank.bullets.size(); j++) {
					//ȡ���ӵ�
					Bullet bullet=enemyTank.bullets.get(j);
					if (bullet.isLive) {
						this.hitTank(bullet, hero);
					}
				}				
			}
		}
	}
	
	//�ж��ҵ��ӵ��Ƿ���е��˵�̹��
	public void hitEnemyTank() {
		//�ж��Ƿ���е��˵�̹��
		for (int i = 0; i < hero.bullets.size(); i++) {
			//ȡ���ӵ�
			Bullet bullet=hero.bullets.get(i);
			if (bullet.isLive) {
				//ȡ��ÿ��̹�ˣ������ж�
				for (int j = 0; j < enSize; j++) {
					//ȡ��̹��
					EnemyTank et=ets.get(j);
					if (et.isLive) {
						
						this.hitTank(bullet, et);
						if (!et.isLive) {
							destroyEnemyTankNum++;
							//recordNum(destroyEnemyTankNum,hero,ets);
						}
					}
				}
			}
		}
	}
	
	//дһ������ר���ж��ӵ��Ƿ���е���̹��
	public void hitTank(Bullet bullet,Tank tank) {
		//�жϸ�̹�˵ķ���
		switch (tank.direct) {
		//���̹�˵ķ������ϻ�������
		case 0:
		case 2:
			if (bullet.getX()>tank.getX() && bullet.getX()<tank.getX()+20 && bullet.getY()>tank.getY() && bullet.getY()<tank.getY()+30) {
				//����
				//�ӵ�����
				bullet.isLive=false;
				//̹������
				tank.isLive=false;
				//����һ��ը��������vector
				Bomb b=new Bomb(tank.getX(), tank.getY());
				bombs.add(b);
				
			}
			break;
		case 1:
		case 3:
			if (bullet.getX()>tank.getX() && bullet.getX()<tank.getX()+30 && bullet.getY()>tank.getY() && bullet.getY()<tank.getY()+20) {
				//����
				//�ӵ�����
				bullet.isLive=false;
				//̹������
				tank.isLive=false;
				//����һ��ը��������vector
				Bomb b=new Bomb(tank.getX(), tank.getY());
				bombs.add(b);
			}
			break;
		default:
			break;
		}
	}
	
	//����̹�˵ĺ���
	public void drawTank(int x,int y,Graphics g,int direct,int type) {
		//�ж���ʲô���͵�̹��
		switch (type) {
		case 0:
			//�ı仭�ʵ���ɫ
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		
		//�жϷ���
		switch (direct) {
		//����
		case 0:
			//�����ҵ�̹�ˣ���ʱ�ٷ�װ��һ��������
			//1.������ߵľ���
			g.fill3DRect(x, y, 5, 30,false);
			//2.�����ұ߾���
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.����Բ��
			g.fillOval(x+5, y+10, 10, 10);
			//5.������
			g.drawLine(x+10, y+15, x+10, y);
			break;
		//����	
		case 1:
			//�����ϱߵľ���
			g.fill3DRect(x, y, 30, 5, false);
			//�����±ߵľ���
			g.fill3DRect(x, y+15, 30, 5, false);
			//�����м�ľ���
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//����Բ��
			g.fillOval(x+10, y+5, 10, 10);
			//������
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		//����
		case 2:
			//�����ҵ�̹�ˣ���ʱ�ٷ�װ��һ��������
			//1.������ߵľ���
			g.fill3DRect(x, y, 5, 30,false);
			//2.�����ұ߾���
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.����Բ��
			g.fillOval(x+5, y+10, 10, 10);
			//5.������
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		//����
		case 3:
			//�����ϱߵľ���
			g.fill3DRect(x, y, 30, 5, false);
			//�����±ߵľ���
			g.fill3DRect(x, y+15, 30, 5, false);
			//�����м�ľ���
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//����Բ��
			g.fillOval(x+10, y+5, 10, 10);
			//������
			g.drawLine(x+15, y+10, x, y+10);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//�����´��� a��ʾ����d��ʾ���ң�w��ʾ���ϣ�s��ʾ����
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_W) {
			//�����ҵ�̹�˵ķ���
			this.hero.setDirect(0);
			this.hero.moveUp();
		}else if (e.getKeyCode()==KeyEvent.VK_D) {
			//����
			this.hero.setDirect(1);
			this.hero.moveRight();
		}else if (e.getKeyCode()==KeyEvent.VK_S) {
			//����
			this.hero.setDirect(2);
			this.hero.moveDown();
		}else if (e.getKeyCode()==KeyEvent.VK_A) {
			//����
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}
		
		if (e.getKeyCode()==KeyEvent.VK_J) {
			//�ж�����Ƿ���j��
			
			//����
			if (this.hero.bullets.size()<5) {
				
				this.hero.shot();
			}
			
		}
		
		//�������»���Panel
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//ÿ��100����ȥ�ػ�
		while (true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			//�������ж��ҵ��ӵ��Ƿ���е���
			this.hitEnemyTank();
			//�������жϵ��˵��ӵ��Ƿ������
			this.hitMe();
			
			//recordNum(destroyEnemyTankNum,hero,ets);
			
			//�ػ�
			this.repaint();
		}
	}
}

