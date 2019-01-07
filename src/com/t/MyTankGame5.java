/**
 * 功能：坦克游戏4.0
 * 1.画出坦克
 * 2.我的坦克可以上下左右移动
 * 3.可以发射子弹，子弹连发(最多连发5颗)
 * 4.敌人坦克可以连发子弹，并且可以互相攻击
 * 5.可以记录我的坦克击毁敌人坦克的数量，并且可以恢复到某一时刻的游戏状态
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
	//定义组件
	//定义菜单栏
	JMenuBar jMenuBar=null;
	//定义菜单
	JMenu jMenu=null;
	//定义菜单子选项
	JMenuItem jMenuItem=null;
	JMenuItem jMenuItem2=null;
	
	//定义面板
	MyPanel mp=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame5 my=new MyTankGame5();
	}
	
	public MyTankGame5() {
		// TODO Auto-generated constructor stub
		mp=new MyPanel();
		
		//创建菜单栏
		jMenuBar=new JMenuBar();
		//创建菜单
		jMenu=new JMenu("文件");
		//创建菜单子选项
		jMenuItem=new JMenuItem("读档");
		jMenuItem2=new JMenuItem("存档");
		
		jMenuBar.add(jMenu);
		jMenu.add(jMenuItem);
		jMenu.add(jMenuItem2);
		
		//启动mp线程 
		Thread t2=new Thread(mp);
		t2.start();
		
		this.add(mp);
		
		//注册监听
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
			
			//读档
			
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
			
			//存档
			
			FileWriter fileWriter=null;
			BufferedWriter bufferedWriter=null;
			
			try {
				fileWriter=new FileWriter("res/record.txt");
				bufferedWriter=new BufferedWriter(fileWriter);
				//保存我方坦克的位置坐标--0
				bufferedWriter.write("0 "+Integer.toString(mp.hero.getX())+" "+Integer.toString(mp.hero.getY())+"\r\n");
				//保存敌人坦克的位置坐标
				for (int i = 0; i < mp.ets.size(); i++) {
					//从敌人坦克组中取出一个敌人坦克--1
					EnemyTank enemyTank=mp.ets.get(i);
					bufferedWriter.write("1 "+Integer.toString(enemyTank.getX())+" "+Integer.toString(enemyTank.getY())+"\r\n");					
				}
				
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}finally {
				try {
					//关闭文件流
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
	
	//定义一个我的坦克
	Hero hero=null;
	
	//定义敌人的坦克组
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//定义炸弹集合
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	int enSize=3;
	
	int destroyEnemyTankNum=0;
	
	//定义三张图片，三张图片实际上是一颗炸弹
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//构造函数
	public MyPanel() {
		hero=new Hero(100, 100);
		
		//初始化敌人的坦克
		for (int i = 0; i < enSize; i++) {
			//创建一个敌人的坦克对象
			EnemyTank et=new EnemyTank((i+1)*50, 0);
			Thread thread=new Thread(et);
			thread.start();
			
			et.setColor(0);
			et.setDirect(2);
			//加入到坦克组
			ets.add(et);
		}
		
		//加载图片
		try {
			image1=ImageIO.read(new File("res/bomb_1.gif"));
			image2=ImageIO.read(new File("res/bomb_2.gif"));
			image3=ImageIO.read(new File("res/bomb_3.gif"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//加载图片,下面这种方法加载图片会出现问题，而且也不能用这样的路径：res/bomb_1.gif来加载图片
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

	}
	
	//重写paint
	public void paint(Graphics g) {
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		//画出自己的坦克
		if (hero.isLive) {
			
			this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		}
		
		for (int i = 0; i < hero.bullets.size(); i++) {
			Bullet myBullet=hero.bullets.get(i);
			//画出子弹,画出一颗子弹
			if (myBullet!=null && myBullet.isLive==true) {
				g.draw3DRect(myBullet.getX(), myBullet.getY(), 1, 1, false);
			}
			if (myBullet.isLive==false) {
				//从向量bullets中删除掉该子弹
				hero.bullets.remove(myBullet);
			}
			
		}
		
		g.setColor(Color.red);
		g.drawString("击毁数："+Integer.toString(destroyEnemyTankNum), 320, 15);
		
		//画出炸弹
		for (int i = 0; i < bombs.size(); i++) {
			//取出炸弹
			Bomb b=bombs.get(i);
			if (b.life>6) {
				g.drawImage(image1, b.getX(), b.getY(), 30, 30, this);
			}else if (b.life>3) {
				g.drawImage(image2, b.getX(), b.getY(), 30, 30, this);
			}else {
				g.drawImage(image3, b.getX(), b.getY(), 30, 30, this);
			}
			
			//让炸弹的生命值减小
			b.lifeDown();
			//如果炸弹生命值为0，则从向量中移除
			if (b.life==0) {
				bombs.remove(b);
			}
			
		}
		
		//画出敌人的坦克
		for (int i = 0; i < ets.size(); i++) {
			EnemyTank et=ets.get(i);
			if (et.isLive) {
				
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
				for (int j = 0; j < et.bullets.size(); j++) {
					Bullet etBullet=et.bullets.get(j);
					//画出敌人坦克子弹
					if (etBullet!=null && etBullet.isLive==true) {
						g.draw3DRect(etBullet.getX(), etBullet.getY(), 1, 1, false);
					}
					if (etBullet.isLive==false) {
						//从向量bullets中删除掉该子弹
						et.bullets.remove(etBullet);
					}
					
				}
			}
			
		}
	}
	
	//记录我方坦克击毁敌人坦克的数量
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
	
	//判断我的坦克是否被击中
	public void hitMe() {
		//取出每一个敌人的坦克
		for (int i = 0; i < ets.size(); i++) {
			//取出坦克
			EnemyTank enemyTank=ets.get(i);
			if (enemyTank.isLive) {
				for (int j = 0; j < enemyTank.bullets.size(); j++) {
					//取出子弹
					Bullet bullet=enemyTank.bullets.get(j);
					if (bullet.isLive) {
						this.hitTank(bullet, hero);
					}
				}				
			}
		}
	}
	
	//判断我的子弹是否击中敌人的坦克
	public void hitEnemyTank() {
		//判断是否击中敌人的坦克
		for (int i = 0; i < hero.bullets.size(); i++) {
			//取出子弹
			Bullet bullet=hero.bullets.get(i);
			if (bullet.isLive) {
				//取出每个坦克，与它判断
				for (int j = 0; j < enSize; j++) {
					//取出坦克
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
	
	//写一个函数专门判断子弹是否击中敌人坦克
	public void hitTank(Bullet bullet,Tank tank) {
		//判断该坦克的方向
		switch (tank.direct) {
		//如果坦克的方向是上或者是下
		case 0:
		case 2:
			if (bullet.getX()>tank.getX() && bullet.getX()<tank.getX()+20 && bullet.getY()>tank.getY() && bullet.getY()<tank.getY()+30) {
				//击中
				//子弹死亡
				bullet.isLive=false;
				//坦克死亡
				tank.isLive=false;
				//创建一个炸弹，加入vector
				Bomb b=new Bomb(tank.getX(), tank.getY());
				bombs.add(b);
				
			}
			break;
		case 1:
		case 3:
			if (bullet.getX()>tank.getX() && bullet.getX()<tank.getX()+30 && bullet.getY()>tank.getY() && bullet.getY()<tank.getY()+20) {
				//击中
				//子弹死亡
				bullet.isLive=false;
				//坦克死亡
				tank.isLive=false;
				//创建一个炸弹，加入vector
				Bomb b=new Bomb(tank.getX(), tank.getY());
				bombs.add(b);
			}
			break;
		default:
			break;
		}
	}
	
	//画出坦克的函数
	public void drawTank(int x,int y,Graphics g,int direct,int type) {
		//判断是什么类型的坦克
		switch (type) {
		case 0:
			//改变画笔的颜色
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		
		//判断方向
		switch (direct) {
		//向上
		case 0:
			//画出我的坦克（到时再封装成一个函数）
			//1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30,false);
			//2.画出右边矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.画出圆形
			g.fillOval(x+5, y+10, 10, 10);
			//5.画出线
			g.drawLine(x+10, y+15, x+10, y);
			break;
		//向右	
		case 1:
			//画出上边的矩形
			g.fill3DRect(x, y, 30, 5, false);
			//画出下边的矩形
			g.fill3DRect(x, y+15, 30, 5, false);
			//画出中间的矩形
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//画出圆形
			g.fillOval(x+10, y+5, 10, 10);
			//画出线
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		//向下
		case 2:
			//画出我的坦克（到时再封装成一个函数）
			//1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30,false);
			//2.画出右边矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.画出圆形
			g.fillOval(x+5, y+10, 10, 10);
			//5.画出线
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		//向左
		case 3:
			//画出上边的矩形
			g.fill3DRect(x, y, 30, 5, false);
			//画出下边的矩形
			g.fill3DRect(x, y+15, 30, 5, false);
			//画出中间的矩形
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//画出圆形
			g.fillOval(x+10, y+5, 10, 10);
			//画出线
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
	//键按下处理 a表示向左，d表示向右，w表示向上，s表示向下
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_W) {
			//设置我的坦克的方向
			this.hero.setDirect(0);
			this.hero.moveUp();
		}else if (e.getKeyCode()==KeyEvent.VK_D) {
			//向右
			this.hero.setDirect(1);
			this.hero.moveRight();
		}else if (e.getKeyCode()==KeyEvent.VK_S) {
			//向下
			this.hero.setDirect(2);
			this.hero.moveDown();
		}else if (e.getKeyCode()==KeyEvent.VK_A) {
			//向左
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}
		
		if (e.getKeyCode()==KeyEvent.VK_J) {
			//判断玩家是否按下j键
			
			//开火
			if (this.hero.bullets.size()<5) {
				
				this.hero.shot();
			}
			
		}
		
		//必须重新绘制Panel
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//每隔100毫秒去重绘
		while (true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			//函数，判断我的子弹是否击中敌人
			this.hitEnemyTank();
			//函数，判断敌人的子弹是否击中我
			this.hitMe();
			
			//recordNum(destroyEnemyTankNum,hero,ets);
			
			//重绘
			this.repaint();
		}
	}
}

