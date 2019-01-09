package com.zhj;

import java.util.Vector;

//记录类
class Recorder{
	//记录每关有多少敌人
	private static int enNum=20;
	//设置我有多少可以用的人
	private static int myLife=3;
	//记录总共消灭了多少敌人
	private static int allEnNum=0;
	
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recorder.allEnNum = allEnNum;
	}
	//减少敌人坦克的数量
	public static void reduceEnNum() {
		enNum--;
		allEnNum++;
	}
	
	//减少我方坦克的数量
	public static void reduceHeroNum() {
		myLife--;
	}
}

//炸弹类
class Bomb{
	//定义炸弹的坐标
	int x;
	int y;
	
	//定义炸弹的生命
	int life=9;
	boolean isLive=true;
	public Bomb(int x,int y) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
	}
	
	//减少生命值
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
	
	boolean isLive=true;
	
	//子弹
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
	
	//开火
	public void shot() {
		switch (this.direct) {
		case 0:
			//创建一颗子弹
			bullet=new Bullet(x+10, y,0);
			//把子弹加入向量
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
		
		//启动子弹
		Thread t1=new Thread(bullet);
		t1.start();
	}
	

}

//敌人的坦克，让其自由移动
class EnemyTank extends Tank implements Runnable{
	
	public EnemyTank(int x,int y) {
		super(x, y);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			switch (this.direct) {
			case 0:
				//for循环控制坦克在一个方向上移动一段距离（30个像素，可修改），方向不要改变的太频繁
				for (int i = 0; i < 30; i++) {
					//控制敌人坦克在固定区域内活动，即窗口里面
					if (y>0) {
						y-=speed;						
					}
					try {
						//休眠50毫秒，让坦克移动的慢一点（自然一点）
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}	
					if (y==50||y==100) {
						this.shot();
					}
				}
				break;
			case 1:
				for (int i = 0;  i < 30; i++) {
					if (x<400) {
						x+=speed;						
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					if (x==267||x==334) {
						
						this.shot();
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
					if (y==200||y==250) {
						
						this.shot();
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
					if (x==67||x==134) {
						
						this.shot();
					}
				}
				break;
			default:
				break;
			}
			
			//让敌人坦克随机改变移动方向
			this.direct=(int)(Math.random()*4);
			
			//判断敌人坦克是否死亡
			if (isLive==false) {
				break;
			}
		}
	}
}

//我的坦克
class Hero extends Tank{
	
	public Hero(int x,int y) {
		super(x,y);
	}	
	
	//坦克向上移动
	public void moveUp() {
		y-=speed;
	}
	//坦克向右移动
	public void moveRight() {
		x+=speed;
	}
	//坦克向右移动7
	public void moveDown() {
		y+=speed;
	}
	//坦克向右移动
	public void moveLeft() {
		x-=speed;
		}
}
