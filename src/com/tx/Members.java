package com.tx;

import java.util.Vector;

//ը����
class Bomb{
	//����ը��������
	int x;
	int y;
	
	//����ը��������
	int life=9;
	boolean isLive=true;
	public Bomb(int x,int y) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
	}
	
	//��������ֵ
	public void lifeDown() {
		if (life>0) {
			life--;
		}else {
			this.isLive=false;
		}
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

//�ӵ���
class Bullet implements Runnable{
	int x;
	int y;
	int direct;
	int speed=1;
	boolean isLive=true;
	public Bullet(int x,int y,int direct) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		this.direct=direct;
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			switch (direct) {
			case 0:
				//����
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
			default:
				break;
			}
			//�ӵ���ʱ����
			
			//�жϸ��ӵ��Ƿ�������Ե
			if (x<0||x>400||y<0||y>300) {
				this.isLive=false;
				break;
			}
		}
	}
	
}

//̹����
class Tank{
	//��ʾ̹�˵ĺ�����
	int x=0;
	//��ʾ̹�˵�������
	int y=0;
	
	//̹�˷���
	//0��ʾ��  1��ʾ��  2��ʾ��  3��ʾ��
	int direct=0;	
	int color;
	
	//̹�˵��ٶ�
	int speed=1;
	
	//�ӵ�
	Bullet bullet=null;
	Vector<Bullet> bullets=new Vector<Bullet>();
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

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

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	//����
	public void shot() {
		switch (this.direct) {
		case 0:
			//����һ���ӵ�
			bullet=new Bullet(x+10, y,0);
			//���ӵ���������
			bullets.add(bullet);
			break;
		case 1:
			bullet=new Bullet(x+30, y+10,1);
			bullets.add(bullet);
			break;
		case 2:
			bullet=new Bullet(x+10, y+30,2);
			bullets.add(bullet);
			break;
		case 3:
			bullet=new Bullet(x, y+10,3);
			bullets.add(bullet);
			break;
		default:
			break;
		}
		
		//�����ӵ�
		Thread t1=new Thread(bullet);
		t1.start();
	}
	

}

//���˵�̹�ˣ����������ƶ�
class EnemyTank extends Tank implements Runnable{
	
	boolean isLive=true;
	public EnemyTank(int x,int y) {
		super(x, y);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			switch (this.direct) {
			case 0:
				//forѭ������̹����һ���������ƶ�һ�ξ��루30�����أ����޸ģ�������Ҫ�ı��̫Ƶ��
				for (int i = 0; i < 30; i++) {
					//���Ƶ���̹���ڹ̶������ڻ������������
					if (y>0) {
						y-=speed;						
					}
					try {
						//����50���룬��̹���ƶ�����һ�㣨��Ȼһ�㣩
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}		
				}
				break;
			case 1:
				for (int i = 0; i < 30; i++) {
					if (x<400) {
						x+=speed;						
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;
			case 2:
				for (int i = 0; i < 30; i++) {
					if (y<300) {
						y+=speed;						
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}					
				}
				break;
			case 3:
				for (int i = 0; i < 30; i++) {
					if (x>0) {
						x-=speed;						
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}					
				}
				break;
			default:
				break;
			}
			
			//�õ���̹������ı��ƶ�����
			this.direct=(int)(Math.random()*4);
			
			//�жϵ���̹���Ƿ�����
			if (isLive==false) {
				break;
			}
		}
	}
}

//�ҵ�̹��
class Hero extends Tank{
	
	public Hero(int x,int y) {
		super(x,y);
	}	
	
	//̹�������ƶ�
	public void moveUp() {
		y-=speed;
	}
	//̹�������ƶ�
	public void moveRight() {
		x+=speed;
	}
	//̹�������ƶ�
	public void moveDown() {
		y+=speed;
	}
	//̹�������ƶ�
	public void moveLeft() {
		x-=speed;
		}
}
