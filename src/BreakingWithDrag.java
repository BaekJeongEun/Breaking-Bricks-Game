import java.applet.AudioClip;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BreakingWithDrag {
	ImageIcon background;
	Timer timer;
	int pointDistance = 0;					// ���ؼ��� ���鿡 ����� ���� ��ġ�������� ��������� �Ÿ�
	Point focus = new Point(1,1);			// ���� ���� ��ǥ�� ���� Point ��ü ����
	Point wallDistance = new Point(1,1);	// �������� ���� ���� ���� ��ǥ�� ���� Point ��ü ����
	int brickNumber = 1; 					// ������ ������ ��
	Boolean game = true; 					// ���ӽ��۰� ���� ���� ȭ���� �и��Ͽ� ��Ÿ���� ����.
	Boolean draw = true;					// ���ؼ� �׸��⸦ �����ϱ� ����.
	int newBall = 0; 						// ������ ���� ��� ���� ������ ī��Ʈ
	int landCount = 0; 						// ���� ���鿡 �����ϴ� ������ ī��Ʈ
	int ballTimer = 0; 						// ù��° ���� �����̱� �����ϸ� ī��Ʈ, �ð����� �ΰ� ���� �����̱� �����ؾ� ��.
	DrawPanel dp = new DrawPanel();			
	ImageIcon record = new ImageIcon("src/images/������.png");
	ImageIcon youLose = new ImageIcon("src/images/youLose...png");
	public static ArrayList<Ball> ballList = new ArrayList<Ball>();	// ���� ��� ����Ʈ
	public ArrayList<Brick> brickList = new ArrayList<Brick>();		// ������ ��� ����Ʈ

	public BreakingWithDrag() {
		int j=(int)(Math.random()*3); 								// ������ ���� �����ϴ� j, 0���� 2����
		for(int i = 0; i < (int)(Math.random()*5+1); i++){  		// ���� ������ ���� 1������ 5������ ȭ�鿡 ���̴� �� 7���� ��(0���� �����ϸ� 6������ ��)�� Ȱ����.
			brickList.add(new Brick(j, brickNumber, 0)); 			// column, number, isNotBall
			j++; 													// ���� ���� ���ο� ���� ����
		}
		ballList.add(new Ball()); 									// �� 1�� ����
		timer = new Timer(10, new TimerListener());
	}

	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!draw) 												// ���� �����̱� �����ϸ� �������� �׸� �� ���� ��쿡�� ī��Ʈ ����
				ballTimer++;
			for(Ball ball : ballList) {
				for(Brick brick : brickList) {
					brick.crash(ball); 								// ���� ������ �浹 �޼ҵ� ȣ��

					if(brick.row==14) { 							// ������ ���鿡 ������ ���� ����
						dp.repaint();
						game = false;
						timer.stop();				
					}

					if(brick.isBall==2) { 							// ���� ������ ���� ������ü(��� ��)�� �ε����ٸ� (�Ϲ� ������ 0, ��� �� 1, ������ ���� ���� ��� �� 2)
						newBall++; 
						brickList.remove(brick);					// ���� ����Ʈ���� ����(���� �ε����� ���̻� ������ �ƴϱ� ����)
						break;
					}
					if(brick.number <= 0) { 						// ������ ������ ���ڰ� 0�� �Ǹ� ����Ʈ���� ����
						brickList.remove(brick);
						break;
					}

				}
				if(!ball.land) { 									// ���� ���鿡�� ������ ���� ��쿡�� ������ �� �ֵ��� ����
					if(ballList.indexOf(ball)*3 <= ballTimer) {
						ball.move(ball.moveX, ball.moveY);
					}

					if (ball.x <= 0 || ball.x >= dp.getWidth()-20) {	// ��ǥ�� ���� �����ϰų� �׸��� ������ ��ǥ�� ��ü ������ ũ��� �׸� ũ���� ���̺��� ũ��
						ball.moveX= -(ball.moveX);                      // �����̴� ������ �ٲپ� �̵���Ŵ.
						ball.shooting = true;
					}
					if (ball.y <= 30 || ball.y >= dp.getHeight()-10) {
						ball.moveY = -(ball.moveY);
						ball.shooting = true;
					}

					if(ball.y +ball.radius >= dp.getHeight()-2 && ball.shooting) { // ���� �����̴ٰ� ���鿡 ���� ���, �� ���̶� �浹 ������ �־�� ���� ����
						ball.shooting = false;
						ball.land = true;
						landCount++; 											   // ���� �� ���� �����ߴ���
					}
					if(ballList.get(0).land && landCount == ballList.size()) { 	   // ��� ���� �����ؾ߸� ����
						ballTimer = 0;
						draw = true;
						for(Brick brick : brickList) {
							brick.row++; 										   // ������ ���� ������ ������ ������ �������� ��ġ�� �ٲپ� �ֱ� ����.
						}
						brickNumber++;											   // ���� ���鿡 ���� ������ ������ ������ ���ڸ� �÷��� �����ϱ� ����.

						int j=(int)(Math.random()*4);
						for(int i = 0; i < (int)(Math.random()*5+1); i++){		   // ���� ����
							brickList.add(new Brick(j, brickNumber, 0));
							j++;												   // ���� ���� ������ ��(column) ��ġ�� �������ֱ� ���� ������ 
							if(j>6) {											   // ������ ��(column)�� ��ġ�� 6�̱� ������ �ʰ��� ��� ù ��° �� ��ġ�� ����
								j=0;
							}
						}

						int ballCreate = (int)(Math.random()*1);					// ��� ���� ������ ���ΰ��� �Ǵ��ϴ� ����
						if(ballCreate == 0) {
							brickList.add(new Brick(j, 1));							// �ʱ� �����Ǵ� ���� ������ ��ġ������ ������ �־�� �ϱ� ������ ���� ��ü�� ����
						}
					}
				}
			}

			if(ballList.get(0).land && landCount == ballList.size()) {				// ���� �ݺ� ������ �������� ��, ��� ���� ���鿡 ������ ���� ��쿡�� ���ο� �� ����
				landCount = 0;
				if(newBall>0) {														// ���� ������ ���� ������ü�� �ε��� ����ŭ �� ����
					for(int i=0; i<newBall; i++) {
						ballList.add(new Ball());
					}newBall = 0;													// �ε��� ���� �ʱ�ȭ
				}
			}
			dp.repaint();
		}
	}


	class DrawPanel extends JPanel implements MouseListener, MouseMotionListener{
		public DrawPanel() {
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
			background = new ImageIcon("src/images/���� ���.jpg");
		}

		public void paintComponent (Graphics page) {
			page.setColor(Color.white);
			page.drawImage(background.getImage(),0,0,this.getWidth(),this.getHeight(),null);

			// ���� ������ ��� �׷����� ���빰
			if(!game) {	
				page.drawImage(youLose.getImage(),dp.getWidth()/2-120, dp.getHeight()/2-80,250,50,null);
				page.setColor(Color.black);
				page.setFont(new Font("������", Font.BOLD, 30));
				page.drawString("���  "+ (brickNumber-1), dp.getWidth()/2-50, dp.getHeight()/2+10);
			}
			// ���� ���� �߿� �׷����� ���빰
			else { 
				page.setColor(Color.black);
				page.drawLine(0,30,dp.getWidth(),30);
				page.drawLine(0, 15*30, dp.getWidth(),15*30);
				page.drawImage(record.getImage(), 150,5, 80,20,null);
				String units = "src/images/"+(brickNumber)%10+".png";
				String tens = "src/images/"+(brickNumber)/10+".png";
				if(brickNumber >=100) {
					String hund = "src/images/"+brickNumber/100+".png";
					tens= "src/images/"+brickNumber%100/10+".png";
					units  = "src/images/"+brickNumber%100%10%10+".png";
					page.drawImage(new ImageIcon(hund).getImage(),230,5,15,15,null);
				}
				page.drawImage(new ImageIcon(tens).getImage(),245,5,15,15,null);
				page.drawImage(new ImageIcon(units).getImage(),260,5,15,15,null);

				for(Brick brick : brickList) { // ���� �׸���
					brick.draw(page);
				}
				for(Ball ball : ballList) { // �� �׸���
					ball.draw(page);
				}

				if(draw) { // ���� ���鿡 ���� ��쿡�� ������ �׷��ֱ�
					page.setColor(Color.magenta);
					if (focus != null && wallDistance != null && wallDistance.y<=30)
						page.drawLine (focus.x, focus.y, wallDistance.x, wallDistance.y);
					else
						page.drawLine (focus.x, focus.y, wallDistance.x, wallDistance.y);

				}
				else { // ���� ���鿡�� �������� ������ ��ǥ���� �ʱ�ȭ (���� ������ �� �������� �׷��� ���ɼ� ���)
					focus.x=focus.y=0;
					wallDistance.x=wallDistance.y = 0;
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// ���� ���鿡 ���� ��쿡�� Ŭ�� �����ϰ� ����
			if(draw) {
				distance(e);
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// ���� ���鿡 ���� ��쿡�� Ŭ�� �����ϰ� ����
			if(draw) {
				distance(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// ���� ���鿡 ���� ��쿡�� Ŭ�� �����ϰ� ����
			if(draw) {
				for(Ball ball : ballList) {
					ball.shooting = false;
					// ù ��° ���� x,y ����
					// x���� = ���������� �������� �Ÿ� / (������ �������� �Ÿ� + ���鿡������ ���ؼ������� �Ÿ�), y���� = ���鿡������ ���ؼ������� �Ÿ� / (������ �������� �Ÿ� + ���鿡������ ���ؼ������� �Ÿ�)
					ballList.get(0).moveX = ((double)((Math.sqrt(Math.pow(wallDistance.x-ballList.get(0).x, 2)) / ((Math.sqrt(Math.pow(wallDistance.x-ballList.get(0).x, 2)) + pointDistance)))))*5;
					ballList.get(0).moveY = - ((double)((pointDistance / ((Math.sqrt(Math.pow(wallDistance.x-ballList.get(0).x, 2)) + pointDistance)))))*5;
					// ���� x��ǥ���� Ŭ�������� x��ǥ�� �۴ٸ� ���� �������� ���������ϱ� ������ ��ȣ�� �ٲ���.
					if(e.getX()<ballList.get(0).x) { 
						ballList.get(0).moveX = -(ballList.get(0).moveX);
					}

					ball.moveX = ballList.get(0).moveX;
					ball.moveY = ballList.get(0).moveY;
					ball.x = ballList.get(0).x;
					ball.y = ballList.get(0).y;

					// ���콺�� ���� �������� ������ �׸��� ����
					draw = false;
					ball.land = false;				// ���콺�� ���� ���� �����̱� ������. ���� ���鿡�� ������ ������ �ǹ�
				}
			}
		}

		public void distance(MouseEvent e) {			// ���� ������������ ���콺Ŭ�� ����, ������� �����Ͽ��� �� �����ϴ� ���� ���� ��ġ�� ���ϱ� ���� �޼ҵ�
			// ���� ���� ��ǥ
			focus.x = (int) (ballList.get(0).x+ballList.get(0).radius/2);
			focus.y = (int) (ballList.get(0).y+ballList.get(0).radius/2);

			// ���콺Ŭ�� �������� ����(���� �������� ����)���� �������� ���� ���� ��ġ
			Point mouseDistance = new Point(1,1);
			mouseDistance.x = e.getX();
			mouseDistance.y = focus.y;

			// ���� ������ ���콺Ŭ�� ����, ������� �����Ͽ��� �� �����ϴ� ���� ���������� �������� ���� ���� ������ �Ÿ� (�ﰢ���� ������ ����)
			if(e.getX() > focus.x) { // Ŭ������ x��ǥ�� ���� x��ǥ���� ũ�ٸ�
				pointDistance = (int)mouseDistance.distance(e.getPoint()) * (dp.getWidth()-focus.x) / (int)focus.distance(mouseDistance);
			}
			else if(e.getX() == focus.x) {				// Ŭ������ x��ǥ�� ���� x��ǥ�� ���ٸ�
				pointDistance = dp.getHeight() - 10;	// �г��� ���̿��� ���� ������ ũ���� ��
			}
			else {
				pointDistance = (int)mouseDistance.distance(e.getPoint()) * focus.x / (int)focus.distance(mouseDistance);
			}

			// ���� ������������ ���콺Ŭ�� ����, ������� �����Ͽ��� �� �����ϴ� ���� ���� ��ġ
			if(e.getX()>focus.x) {
				wallDistance.x = dp.getWidth();
			}else wallDistance.x = 0;
			if(e.getY() <= dp.getHeight()-10)
				wallDistance.y = focus.y - pointDistance;

			dp.repaint();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
		}
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
