package com.txd;

//子弹类
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
				//向上
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
			//子弹何时死亡
			
			//判断该子弹是否碰到边缘
			if (x<0||x>400||y<0||y>300) {
				this.isLive=false;
				break;
			}
		}
	}
	
}

//坦克类
class Tank{
	//表示坦克的横坐标
	int x=0;
	//表示坦克的纵坐标
	int y=0;
	
	//坦克方向
	//0表示上  1表示右  2表示下  3表示左
	int direct=0;	
	int color;
	
	//坦克的速度
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

//敌人的坦克
class EnemyTank extends Tank{
	public EnemyTank(int x,int y) {
		super(x, y);
	}
}

//我的坦克
class Hero extends Tank{
	
	//子弹
	Bullet bullet=null;
	
	public Hero(int x,int y) {
		super(x,y);
	}
	
	//开火
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
		
		//启动子弹
		Thread t1=new Thread(bullet);
		t1.start();
	}
	
	//坦克向上移动
	public void moveUp() {
		y-=speed;
	}
	//坦克向右移动
	public void moveRight() {
		x+=speed;
	}
	//坦克向右移动
	public void moveDown() {
		y+=speed;
	}
	//坦克向右移动
	public void moveLeft() {
		x-=speed;
		}
}
