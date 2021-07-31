import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BreakingWithBar extends JPanel implements MouseListener, KeyListener{
	ArrayList<Brick> brickList = new ArrayList<Brick>();
	ArrayList<Ball> list = new ArrayList<Ball>();
	ArrayList<Ball> newBallList = new ArrayList<Ball>();
	ImageIcon fullHeart = new ImageIcon("src/images/fullHeart.png");
	ImageIcon emptyHeart = new ImageIcon("src/images/emptyHeart.png");
	ImageIcon bar = new ImageIcon("src/images/bar.png");
	ImageIcon record = new ImageIcon("src/images/������.png");
	ImageIcon youLose = new ImageIcon("src/images/youLose...png");
	ImageIcon youWin = new ImageIcon("src/images/youWin!.png");
	ImageIcon starImg = new ImageIcon("src/images/��.gif");
	Timer timer;												// ���� ���� Ÿ�̸�
	Timer timer2;												// 1�ʸ��� ī��Ʈ Ÿ�̸�
	int img_x=0; int img_y=0;
	Point myP = new Point();
	int time = 0;
	int heart = 5;
	int newBallCount=0;
	Boolean right = false, left = false;
	MyImageIcon img = new MyImageIcon(150, 470, 135, 15,Color.red);
	MyImageIcon star;
	public BreakingWithBar() {
		list.add(new Ball(200,300,20, true, false)); 			//row, column, number, isBarGame, isBall
		for(int i = 2; i<7; i++) {
			for(int j = 0; j<7; j++) {
				int n = (int) (Math.random()*4);
				if(n==1) {
					brickList.add(new Brick(i, j, 1, true, 1)); // row, column, number, isBarGame, isBall (�Ϲݺ��� = 0, �������� ���� ���� = 1)
				}
				else
					brickList.add(new Brick(i, j, 1, true, 0));
			}
		}

		timer = new Timer(5, new TimerListener());
		timer2 = new Timer(1000, new SecondListener());

		addMouseListener(this);
		addKeyListener(this);

	}

	public void starCreate() {
		star = new MyImageIcon(190,20,true);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillRect(0,0,this.getWidth(),this.getHeight());

		// ��Ʈ�� ��� ����� ���
		if(heart==0) {
			g2d.drawImage(youLose.getImage(),this.getWidth()/2-120, this.getHeight()/2-80,250,50,null);
			g2d.setColor(Color.black);
			g2d.setFont(new Font("������", Font.BOLD, 30));
			g2d.drawString("����ð�  "+time, 130, 300);
		}
		// ���� ���� ��� ���
		else if(star == null) {
			g2d.drawImage(youWin.getImage(),this.getWidth()/2-120, this.getHeight()/2-80,250,50,null);
			g2d.setColor(Color.black);
			g2d.setFont(new Font("������", Font.BOLD, 30));
			g2d.drawString("����ð�  "+time, 130, 300);
		}

		//���� ���� ���� ���
		else if(!list.isEmpty() && heart!=0) {
			img.draw(g2d);
			if(star != null)
				star.draw(g2d);
			for(Ball i : list) {
				i.draw(g2d);
			}
			for(Brick brick : brickList) {
				brick.draw(g2d);
			}
			for(Ball newBall : newBallList) {
				newBall.draw(g2d);
			}
			// ��� �ð��� ���� �ڸ�, ���� �ڸ��� ������ ���ڿ��� ����
			String units = "src/images/"+time%10+".png";
			String tens = "src/images/"+ time/10+".png";

			g2d.drawImage(record.getImage(), 125,5, 80,20,null);

			// �ð��� 100�� �̻��� ���
			if(time >=100) {
				String hund = "src/images/"+ time/100+".png";
				tens= "src/images/"+ time%100/10+".png";
				units  = "src/images/"+ time%100%10%10+".png";
				g2d.drawImage(new ImageIcon(hund).getImage(),210,5,15,15,null);
			}
			g2d.drawImage(new ImageIcon(tens).getImage(),225,5,15,15,null);
			g2d.drawImage(new ImageIcon(units).getImage(),240,5,15,15,null);
			// ä���� ��Ʈ �׸���
			for(int i=1; i<=heart; i++) {
				g2d.drawImage(fullHeart.getImage(),250+i*30, 5, 15, 15,null);
				// �� ��Ʈ �׸���
			}for(int j = 5; j > heart; j--) {
				g2d.drawImage(emptyHeart.getImage(),250+j*30, 5, 15, 15,null);
			}
		}
	}
	class SecondListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {		
			time++;
			if(time % 10 == 0) {									// 10�ʸ��� ���ο� �� ����
				list.add(new Ball(200,300,20, true, false));
			}
			if(time % 20 ==0) {										// 20�ʸ��� ���ο� ���� ����
				for(Brick brick : brickList) {
					brick.row++;
				}
				int n;
				for(int j = 0; j<7; j++) {
					n = (int)(Math.random()*3);
					if(n==0) {										// �������� 0�̸� �������� ���� ������ ����
						brickList.add(new Brick(2, j, 1, true, 1));
					}
					else
						brickList.add(new Brick(2, j, 1, true, 0));
				}
			}
		}
	}
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			requestFocus();
			setFocusable(true);

			if(star == null) {										// ��� ���� ������� ���� �ߴ�
				repaint();
				timer.stop();
				timer2.stop();
			}

			if(list.isEmpty() && heart!=0) {						// ���� ��� ������� ���ο� �� ����
				list.add(new Ball(200,300,20, true, false));
			}
			if(heart==0) {											// ��Ʈ�� ��� ������� ���� �ߴ�
				timer2.stop();
				timer.stop();
			}

			for(Ball pi : list) {
				img.crash(pi);										// �ٿ� ���� �浹�ϴ� �޼ҵ�
				for(Ball newBall : newBallList) {					// ���Ӱ� ���� ������
					img.crash(newBall);
					if(newBall.barLand || newBall.y>=img.pY) {		// �ٿ� ������ ����Ʈ���� ����, ���� �ʾƵ� ����
						newBallList.remove(newBall);
						break;
					}
					newBall.move(newBall.moveX, newBall.moveY);
				}

				for(Brick brick : brickList) {
					brick.crash(pi);
					if(brick.isBall ==2) {							// ���� ���� ���� ������ ���� �浹�ߴٸ�
						newBallList.add(new Ball(brick.x+25, brick.y+10, 15,true, true));	// �������� ���ο� �� ����Ʈ�� ����
					}

					Point ballP = new Point();						// ���� ��ǥ�� ���� Point ��ü ����
					ballP.x = (int)pi.x + pi.radius;	ballP.y = (int)pi.y + pi.radius;
					myP.x = 215;	myP.y = 45;						// ��� ���� x,y �·�
					if(ballP.distance(myP) <= 30) {					// ��� ���� ���� �����Ÿ� ���϶��
						star = null;								// ��� �� ��ü ���
					}

					if(brick.number <= 0 || brick.isBall == 2) { 	// ������ ������ ���ڰ� 0, ���� ���� ��� ���̶�� ����Ʈ���� ����
						brickList.remove(brick);
						break;
					}
				}

				if(pi.y>=img.pY){									// ���� �ٿ� ���� ���� ���
					list.remove(pi);
					heart--;
					break;
				}

				pi.move(pi.moveX,pi.moveY);

				if (pi.x <= 0 || pi.x >= 407) {					// ��ǥ�� ���� �����ϰų� �׸��� ������ ��ǥ�� ��ü ������ ũ��� �׸� ũ���� ���̺��� ũ��
					pi.moveX = -(pi.moveX);                     // �����̴� ������ �ٲپ� �̵���Ŵ.
				}
				if (pi.y <= 0) {
					pi.moveY = -(pi.moveY);
				}
			}
			if(right) {											// ������ Ű���尡 ���� ���
				if(img.pX <= 280)								// ȭ�� ũ�� ������ ������ �� �ֵ��� ����
					img.pX += 10;	
			}
			if(left) {											// ���� Ű���尡 ���� ���
				if(img.pX >= 5 )
					img.pX -= 10;
			}
			repaint();
		}
	}

	// ��(��ƽ)�� ��� �� ��ü�� ����� ���� Ŭ����
	class MyImageIcon{
		int pX;
		int pY;
		int width;
		int height;
		int xMove = 2;
		int yMove = 2;
		Color color;
		Boolean star;

		// �� ������
		public MyImageIcon(int x, int y, int width, int height, Color color) {
			pX = x;
			pY = y;
			this.width = width;
			this.height = height;
			this.color = color;
			star = false;
		}
		// ��� �� ������
		public MyImageIcon(int x,int y, Boolean star) {
			this.pX = x;
			this.pY = y;
			this.star = star;
			this.width = 50;
			this.height = 50;
		}

		public void move(int x, int y) {
			pX += x;
			pY += y;
		}
		public void draw(Graphics g) {
			if(this.star) {
				g.drawImage(starImg.getImage(),pX, pY, width, height, null);
			}
			else
				g.drawImage(bar.getImage(), pX, pY, width, height, null);
		}
		public void crash(Ball ball) {
			if(13 + ball.y>=this.pY&& this.pX<=ball.x+10 && this.pX+this.width>=ball.x+10) {

				// ������ ���� ���
				if(!ball.isNewBall) {
					// �ٿ� ��� ��ġ�� ���� ���� �ӵ��� ��ȭ��Ű�� ����.
					if(this.pX+10>=ball.x|| this.pX+115 <= ball.x)
						if(ball.moveX>0)
							ball.moveX = ball.moveY = 1.8;
						else
							ball.moveX = -1.8;
					else if(this.pX+40>=ball.x|| this.pX+80 <= ball.x)
						if(ball.moveX>0)
							ball.moveX = ball.moveY =1.5;
						else
							ball.moveX = -1.5;

					else
						if(ball.moveX>0)
							ball.moveX = ball.moveY =1.2;
						else
							ball.moveX = -1.2;

					ball.moveY = -(ball.moveY);
				}
				// �������� ���� ���� ���
				else if(ball.isNewBall){
					ball.barLand = true;
					if(heart<5)
						heart++;
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_LEFT:	
			left = true;
			break;

		case KeyEvent.VK_RIGHT:	
			right = true;
			break;
		}
		repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {			
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_LEFT:	
			left = false;
			break;

		case KeyEvent.VK_RIGHT:	
			right = false;
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {		}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!list.isEmpty()) {
			timer.start();
			timer2.start();
		}
		if(list.isEmpty()) {
			if((arg0.getX()<this.getWidth()&&arg0.getX()>0) || (arg0.getY()<this.getHeight()&&arg0.getY()>0))
				System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg01) {
	}
}
