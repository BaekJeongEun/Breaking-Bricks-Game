import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public class Brick extends Point{

	ImageIcon greenCircle = new ImageIcon("src/images/greenCircle.gif");
	ImageIcon redCircle = new ImageIcon("src/images/redCircle.gif");
	int row;
	int column;
	int number;
	int width = 70;
	int height = 40;
	int isBall;
	Boolean isBarGame;
	Image img;
	
	// 일반 벽돌
	public Brick(int column, int number, int isNotBall) {	
		this.row = 2;
		this.column = column;
		this.number = number;
		this.x = column*width;
		this.y = row*height;
		this.isBall = isNotBall;
		this.isBarGame = false;
	}
	
	// 공과 부딪히면 새로운 공이 됨.
	public Brick(int column, int isBall) {					
		this.row = 2;
		this.column = column;
		this.number=1;
		this.x = column*width+10;
		this.y = row*height+10;
		this.isBall = isBall;
		this.isBarGame = false;
	}

	// 바를 움직여서 벽돌깨기 게임에 쓰일 벽돌
	public Brick(int row, int column, int number, Boolean isBarGame, int isBall) {
		this.number = number;
		this.row = row;
		this.column = column;
		this.isBarGame = isBarGame;
		this.x = column*width;
		this.y = row*height;
		this.isBarGame = isBarGame;
		this.isBall = isBall;
	}

	public void draw(Graphics g) {
		this.x = column*width;
		this.y = row*height;
		String blockNumber = "src/images/벽돌" + (number%45+1) + ".png"; 
		
		// 공의 정보를 담고 있는 블럭이라면 공의 형태로 그리기
		if(this.isBall==1) { 
			// 바 게임이면 벽돌과 빨간공 함께 그리기
			if(this.isBarGame) {
				g.drawImage(new ImageIcon(blockNumber).getImage(),this.x, this.y, width, height,null);
				g.setColor(Color.black);
				g.drawRect(this.x, this.y, width, height);
				g.drawImage(redCircle.getImage(),this.x+25, this.y+5, 15, 15, null);
			}
			else {
				g.drawImage(greenCircle.getImage(),this.x+15, this.y, 30, 30, null);
			}
		}
		// 일반 벽돌일 경우
		else if(this.isBall==0) {
			g.drawImage(new ImageIcon(blockNumber).getImage(),this.x, this.y, width, height,null);
			g.setColor(Color.black);
			g.drawRect(this.x, this.y, width, height);

			if(!this.isBarGame) {
				g.setColor(Color.white);
				g.setFont(new Font("새굴림", Font.BOLD, 20));
				g.drawString(Integer.toString(number), this.x+25, this.y+25);
			}
		}
	}

	public void crash(Ball ball) {
		// 벽돌의 아랫면과 충돌
		if((x-8) <= (ball.x+ball.radius/2) && (x+width+8) >= (ball.x+ball.radius/2) && ball.y >= (y+height-5) && ball.y <= (y+height+5)) {
			if(this.isBall==0) {	// 일반벽돌일 경우
				if(ball.moveY<0) {
					ball.moveY = -(ball.moveY);
				}
				this.number--;		// 공의 내구성 수 감소
			}
			else{
				this.isBall=2;
			}
			ball.shooting = true;

			if(this.isBarGame) {
				if(ball.moveY<0) {
					ball.moveY = -(ball.moveY);
				}
				if(this.isBall == 1) {
					this.isBall = 2;
				}
			}
		}
		// 벽돌의 윗면과 충돌
		else if((x-8) <= (ball.x+ball.radius/2) && (x+width+8) >= (ball.x+ball.radius/2) && (ball.y + ball.radius) <= (y+5) && (ball.y+ ball.radius) >= (y-5)) {
			if(this.isBall==0) {
				if(ball.moveY>0) {
					ball.moveY = -(ball.moveY);
				}
				this.number--;
			}else{
				this.isBall=2;
			}
			ball.shooting = true;

			if(this.isBarGame) {
				if(ball.moveY<0) {
					ball.moveY = -(ball.moveY);
				}
				if(this.isBall == 1) {
					this.isBall = 2;
				}
			}
		}
		// 벽돌의 왼쪽면과 충돌
		else if((ball.y+ball.radius/2) >= (y-8) && (ball.y+ball.radius/2) <= (y+height+8) && (ball.x+ball.radius) >= (x-5) && (ball.x+ball.radius) <= (x+5)) {
			if(this.isBall==0) {
				if(ball.moveX>0) {
					ball.moveX = -(ball.moveX);
				}
				this.number--;
			}
			else{
				this.isBall=2;
			}
			ball.shooting = true;

			if(this.isBarGame) {
				if(ball.moveX>0) {
					ball.moveX = -(ball.moveX);
				}
				if(this.isBall == 1) {
					this.isBall = 2;
				}
			}
		}
		// 벽돌의 오른쪽면과 충돌
		else if((ball.y+ball.radius/2) >= (y-8) && (ball.y+ball.radius) <= (y+height+8) && ball.x <= (x+width+5) && ball.x >= (x+width-5)) {
			if(this.isBall==0) {
				if(ball.moveX<0) {
					ball.moveX = -(ball.moveX);
				}
				this.number--;
			}else{
				this.isBall=2;
			}
			ball.shooting = true;

			if(this.isBarGame) {
				if(ball.moveX<0) {
					ball.moveX = -(ball.moveX);
				}
				if(this.isBall == 1) {
					this.isBall = 2;
				}
			}
		}
	}
}