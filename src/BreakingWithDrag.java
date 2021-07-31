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
	int pointDistance = 0;					// 조준선이 벽면에 닿았을 때의 위치에서부터 지면까지의 거리
	Point focus = new Point(1,1);			// 공의 중점 좌표를 담을 Point 객체 생성
	Point wallDistance = new Point(1,1);	// 조준점이 벽에 닿을 때의 좌표를 담을 Point 객체 생성
	int brickNumber = 1; 					// 벽돌의 내구성 수
	Boolean game = true; 					// 게임시작과 게임 종료 화면을 분리하여 나타내기 위함.
	Boolean draw = true;					// 조준선 그리기를 제한하기 위함.
	int newBall = 0; 						// 기존의 공과 녹색 공과 닿으면 카운트
	int landCount = 0; 						// 공이 지면에 착지하는 갯수를 카운트
	int ballTimer = 0; 						// 첫번째 공이 움직이기 시작하면 카운트, 시간차를 두고 공이 움직이기 시작해야 함.
	DrawPanel dp = new DrawPanel();			
	ImageIcon record = new ImageIcon("src/images/현재기록.png");
	ImageIcon youLose = new ImageIcon("src/images/youLose...png");
	public static ArrayList<Ball> ballList = new ArrayList<Ball>();	// 공을 담는 리스트
	public ArrayList<Brick> brickList = new ArrayList<Brick>();		// 벽돌을 담는 리스트

	public BreakingWithDrag() {
		int j=(int)(Math.random()*3); 								// 벽돌의 열을 결정하는 j, 0부터 2까지
		for(int i = 0; i < (int)(Math.random()*5+1); i++){  		// 랜덤 갯수로 생성 1개부터 5개까지 화면에 보이는 총 7개의 열(0부터 시작하면 6까지의 열)을 활용함.
			brickList.add(new Brick(j, brickNumber, 0)); 			// column, number, isNotBall
			j++; 													// 다음 열에 새로운 벽돌 생성
		}
		ballList.add(new Ball()); 									// 공 1개 생성
		timer = new Timer(10, new TimerListener());
	}

	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!draw) 												// 공이 움직이기 시작하면 조준점을 그릴 수 없을 경우에만 카운트 시작
				ballTimer++;
			for(Ball ball : ballList) {
				for(Brick brick : brickList) {
					brick.crash(ball); 								// 공과 벽돌의 충돌 메소드 호출

					if(brick.row==14) { 							// 벽돌이 지면에 닿으면 게임 종료
						dp.repaint();
						game = false;
						timer.stop();				
					}

					if(brick.isBall==2) { 							// 공의 정보를 담은 벽돌객체(녹색 공)와 부딪혔다면 (일반 벽돌은 0, 녹색 공 1, 기존의 공과 닿은 녹색 공 2)
						newBall++; 
						brickList.remove(brick);					// 벽돌 리스트에서 제거(공과 부딪히면 더이상 벽돌이 아니기 때문)
						break;
					}
					if(brick.number <= 0) { 						// 벽돌의 내구성 숫자가 0이 되면 리스트에서 제거
						brickList.remove(brick);
						break;
					}

				}
				if(!ball.land) { 									// 공이 지면에서 떨어져 있을 경우에만 움직일 수 있도록 제한
					if(ballList.indexOf(ball)*3 <= ballTimer) {
						ball.move(ball.moveX, ball.moveY);
					}

					if (ball.x <= 0 || ball.x >= dp.getWidth()-20) {	// 좌표가 끝에 도달하거나 그림의 꼭지점 좌표가 전체 프레임 크기와 그림 크기의 차이보다 크면
						ball.moveX= -(ball.moveX);                      // 움직이는 방향을 바꾸어 이동시킴.
						ball.shooting = true;
					}
					if (ball.y <= 30 || ball.y >= dp.getHeight()-10) {
						ball.moveY = -(ball.moveY);
						ball.shooting = true;
					}

					if(ball.y +ball.radius >= dp.getHeight()-2 && ball.shooting) { // 공이 움직이다가 지면에 닿을 경우, 한 번이라도 충돌 경험이 있어야 착지 인정
						ball.shooting = false;
						ball.land = true;
						landCount++; 											   // 공이 몇 개나 착지했는지
					}
					if(ballList.get(0).land && landCount == ballList.size()) { 	   // 모든 공이 착지해야만 실행
						ballTimer = 0;
						draw = true;
						for(Brick brick : brickList) {
							brick.row++; 										   // 벽돌이 새로 생성될 때마다 기존의 벽돌들의 위치를 바꾸어 주기 위함.
						}
						brickNumber++;											   // 공이 지면에 닿을 때마다 벽돌의 내구성 숫자를 늘려서 생성하기 위함.

						int j=(int)(Math.random()*4);
						for(int i = 0; i < (int)(Math.random()*5+1); i++){		   // 벽돌 생성
							brickList.add(new Brick(j, brickNumber, 0));
							j++;												   // 새로 생길 벽돌의 열(column) 위치를 지정해주기 위한 증가식 
							if(j>6) {											   // 마지막 열(column)의 위치가 6이기 때문에 초과할 경우 첫 번째 열 위치로 지정
								j=0;
							}
						}

						int ballCreate = (int)(Math.random()*1);					// 녹색 공을 생성할 것인가를 판단하는 문장
						if(ballCreate == 0) {
							brickList.add(new Brick(j, 1));							// 초기 생성되는 공은 벽돌의 위치정보를 가지고 있어야 하기 때문에 벽돌 객체로 생성
						}
					}
				}
			}

			if(ballList.get(0).land && landCount == ballList.size()) {				// 공의 반복 루프를 빠져나온 뒤, 모든 공이 지면에 착지해 있을 경우에만 새로운 공 생성
				landCount = 0;
				if(newBall>0) {														// 공의 정보를 담은 벽돌객체와 부딪힌 수만큼 공 생성
					for(int i=0; i<newBall; i++) {
						ballList.add(new Ball());
					}newBall = 0;													// 부딪힌 개수 초기화
				}
			}
			dp.repaint();
		}
	}


	class DrawPanel extends JPanel implements MouseListener, MouseMotionListener{
		public DrawPanel() {
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
			background = new ImageIcon("src/images/게임 배경.jpg");
		}

		public void paintComponent (Graphics page) {
			page.setColor(Color.white);
			page.drawImage(background.getImage(),0,0,this.getWidth(),this.getHeight(),null);

			// 게임 종료일 경우 그려지는 내용물
			if(!game) {	
				page.drawImage(youLose.getImage(),dp.getWidth()/2-120, dp.getHeight()/2-80,250,50,null);
				page.setColor(Color.black);
				page.setFont(new Font("새굴림", Font.BOLD, 30));
				page.drawString("기록  "+ (brickNumber-1), dp.getWidth()/2-50, dp.getHeight()/2+10);
			}
			// 게임 진행 중에 그려지는 내용물
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

				for(Brick brick : brickList) { // 벽돌 그리기
					brick.draw(page);
				}
				for(Ball ball : ballList) { // 공 그리기
					ball.draw(page);
				}

				if(draw) { // 공이 지면에 있을 경우에만 조준점 그려주기
					page.setColor(Color.magenta);
					if (focus != null && wallDistance != null && wallDistance.y<=30)
						page.drawLine (focus.x, focus.y, wallDistance.x, wallDistance.y);
					else
						page.drawLine (focus.x, focus.y, wallDistance.x, wallDistance.y);

				}
				else { // 공이 지면에서 떨어지면 기존의 좌표값들 초기화 (공이 움직일 때 조준점이 그려질 가능성 고려)
					focus.x=focus.y=0;
					wallDistance.x=wallDistance.y = 0;
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// 공이 지면에 있을 경우에만 클릭 가능하게 제한
			if(draw) {
				distance(e);
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// 공이 지면에 있을 경우에만 클릭 가능하게 제한
			if(draw) {
				distance(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// 공이 지면에 있을 경우에만 클릭 가능하게 제한
			if(draw) {
				for(Ball ball : ballList) {
					ball.shooting = false;
					// 첫 번째 공의 x,y 보폭
					// x보폭 = 공에서부터 벽까지의 거리 / (공부터 벽까지의 거리 + 지면에서부터 조준선까지의 거리), y보폭 = 지면에서부터 조준선까지의 거리 / (공부터 벽까지의 거리 + 지면에서부터 조준선까지의 거리)
					ballList.get(0).moveX = ((double)((Math.sqrt(Math.pow(wallDistance.x-ballList.get(0).x, 2)) / ((Math.sqrt(Math.pow(wallDistance.x-ballList.get(0).x, 2)) + pointDistance)))))*5;
					ballList.get(0).moveY = - ((double)((pointDistance / ((Math.sqrt(Math.pow(wallDistance.x-ballList.get(0).x, 2)) + pointDistance)))))*5;
					// 공의 x좌표보다 클릭지점의 x좌표가 작다면 공이 왼쪽으로 움직여야하기 때문에 부호를 바꿔줌.
					if(e.getX()<ballList.get(0).x) { 
						ballList.get(0).moveX = -(ballList.get(0).moveX);
					}

					ball.moveX = ballList.get(0).moveX;
					ball.moveY = ballList.get(0).moveY;
					ball.x = ballList.get(0).x;
					ball.y = ballList.get(0).y;

					// 마우스를 떼는 순간부터 조준점 그리기 제한
					draw = false;
					ball.land = false;				// 마우스를 떼면 공이 움직이기 시작함. 공이 지면에서 떨어져 있음을 의미
				}
			}
		}

		public void distance(MouseEvent e) {			// 공의 중점에서부터 마우스클릭 지점, 벽면까지 연결하였을 때 도달하는 벽면 점의 위치를 구하기 위한 메소드
			// 공의 중점 좌표
			focus.x = (int) (ballList.get(0).x+ballList.get(0).radius/2);
			focus.y = (int) (ballList.get(0).y+ballList.get(0).radius/2);

			// 마우스클릭 지점에서 지면(공의 중점으로 가정)까지 수직으로 내린 점의 위치
			Point mouseDistance = new Point(1,1);
			mouseDistance.x = e.getX();
			mouseDistance.y = focus.y;

			// 공의 중점과 마우스클릭 지점, 벽면까지 연결하였을 때 도달하는 벽면 점에서부터 수직으로 내린 지면 까지의 거리 (삼각형의 닮음비 공식)
			if(e.getX() > focus.x) { // 클릭지점 x좌표가 공의 x좌표보다 크다면
				pointDistance = (int)mouseDistance.distance(e.getPoint()) * (dp.getWidth()-focus.x) / (int)focus.distance(mouseDistance);
			}
			else if(e.getX() == focus.x) {				// 클릭지점 x좌표가 공의 x좌표와 같다면
				pointDistance = dp.getHeight() - 10;	// 패널의 높이에서 공의 반지름 크기의 차
			}
			else {
				pointDistance = (int)mouseDistance.distance(e.getPoint()) * focus.x / (int)focus.distance(mouseDistance);
			}

			// 공의 중점에서부터 마우스클릭 지점, 벽면까지 연결하였을 때 도달하는 벽면 점의 위치
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
