/**
 * 功能：坦克游戏2.0
 * 1.画出坦克
 * 2.我的坦克可以上下左右移动
 */

package com.txdo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.*;

public class MyTankGame2 extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame2 my=new MyTankGame2();
	}
	
	public MyTankGame2() {
		// TODO Auto-generated constructor stub
		MyPanel mp=new MyPanel();
		
		this.add(mp);
		
		//注册监听
		this.addKeyListener(mp);
		
		this.setSize(400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class MyPanel extends JPanel implements KeyListener{
	
	//定义一个我的坦克
	Hero hero=null;
	
	//定义敌人的坦克组
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	int enSize=3;
	
	//构造函数
	public MyPanel() {
		hero=new Hero(100, 100);
		
		//初始化敌人的坦克
		for (int i = 0; i < enSize; i++) {
			//创建一个敌人的坦克对象
			EnemyTank et=new EnemyTank((i+1)*50, 0);
			et.setColor(0);
			et.setDirect(2);
			//加入到坦克组
			ets.add(et);
		}
	}
	
	//重写paint
	public void paint(Graphics g) {
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		//画出自己的坦克
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		//画出敌人的坦克
		for (int i = 0; i < ets.size(); i++) {
			this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getDirect(), 0);
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
		
		//必须重新绘制Panel
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

