package com.zhj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

//��¼��
class Recorder{
	//��¼ÿ���ж��ٵ���
	private static int enNum=20;
	//�������ж��ٿ����õ���
	private static int myLife=3;
	//��¼�ܹ������˶��ٵ���
	private static int allEnNum=0;
	
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<EnemyTank>enemyTanks=new Vector<EnemyTank>();
	
	//�ָ��浵
	public static void recGame() {
		try {
			fr=new FileReader("res/myRecord.txt");
			br=new BufferedReader(fr);
			String s=br.readLine();
			String[] strings= {};
			allEnNum=Integer.parseInt(s);
			while ((s=br.readLine())!=null) {
				strings=s.split(" ");
				EnemyTank eTank=new EnemyTank(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
				eTank.setDirect(Integer.parseInt(strings[2]));
				enemyTanks.add(eTank);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//������ٵ��˵������͵���̹�����ꡢ����
	public static void keepRecAndEnemyTank() {
		try {
			fw=new FileWriter("res/myRecord.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnNum+"\r\n");
			
			for (int i = 0; i < enemyTanks.size(); i++) {
				EnemyTank eTank=enemyTanks.get(i);
				if (eTank.isLive) {
					
					String s=eTank.getX()+" "+eTank.getY()+" "+eTank.getDirect();
					bw.write(s+"\r\n");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//���ļ��ж�ȡ����¼
	public static void getRecording() {
		try {
			fr=new FileReader("res/record.txt");
			br=new BufferedReader(fr);
			String s=br.readLine();
			allEnNum=Integer.parseInt(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//����¼д���ļ�
	public static void keepRecording() {
		try {
			fw=new FileWriter("res/record.txt");
			bw=new BufferedWriter(fw);
			bw.write(Integer.toString(allEnNum));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
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
	public static Vector<EnemyTank> getEnemyTanks() {
		return enemyTanks;
	}

	public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
		Recorder.enemyTanks = enemyTanks;
	}

	//���ٵ���̹�˵�����
	public static void reduceEnNum() {
		enNum--;
		allEnNum++;
	}
	
	//�����ҷ�̹�˵�����
	public static void reduceHeroNum() {
		myLife--;
	}
}

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
	
	boolean isLive=true;
	
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
	//̹�������ƶ�7
	public void moveDown() {
		y+=speed;
	}
	//̹�������ƶ�
	public void moveLeft() {
		x-=speed;
		}
}
