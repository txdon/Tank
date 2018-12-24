package com.txd;

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
	

}

//���˵�̹��
class EnemyTank extends Tank{
	public EnemyTank(int x,int y) {
		super(x, y);
	}
}

//�ҵ�̹��
class Hero extends Tank{
	
	//�ӵ�
	Bullet bullet=null;
	
	public Hero(int x,int y) {
		super(x,y);
	}
	
	//����
	public void shot() {
		switch (this.direct) {
		case 0:
			bullet=new Bullet(x+10, y,0);
			break;
		case 1:
			bullet=new Bullet(x+30, y+10,1);
			break;
		case 2:
			bullet=new Bullet(x+100, y+300,2);
			break;
		case 3:
			bullet=new Bullet(x, y+10,3);
			break;
		default:
			break;
		}
		
		//�����ӵ�
		Thread t1=new Thread(bullet);
		t1.start();
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
