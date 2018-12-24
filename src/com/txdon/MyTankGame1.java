/**
 * ���ܣ�̹����Ϸ1.0
 * 1.����̹��
 */

package com.txdon;

import java.awt.*;
import javax.swing.*;

public class MyTankGame1 extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame1 my=new MyTankGame1();
	}
	
	public MyTankGame1() {
		// TODO Auto-generated constructor stub
		MyPanel mp=new MyPanel();
		
		this.add(mp);
		
		this.setSize(400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class MyPanel extends JPanel{
	
	//����һ���ҵ�̹��
	Hero hero=null;
	//���캯��
	public MyPanel() {
		hero=new Hero(100, 100);
	}
	
	//��дpaint
	public void paint(Graphics g) {
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		this.drawTank(hero.getX(), hero.getY(), g, 0, 1);

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

		default:
			break;
		}
	}
}

//̹����
class Tank{
	//��ʾ̹�˵ĺ�����
	int x=0;
	//��ʾ̹�˵�������
	int y=0;
	
	public Tank(int x,int y) {
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	

}
//�ҵ�̹��
class Hero extends Tank{
	public Hero(int x,int y) {
		super(x,y);
	}
}