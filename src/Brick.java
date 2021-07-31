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
	
	// �Ϲ� ����
	public Brick(int column, int number, int isNotBall) {	
		this.row = 2;
		this.column = column;
		this.number = number;
		this.x = column*width;
		this.y = row*height;
		this.isBall = isNotBall;
		this.isBarGame = false;
	}
	
	// ���� �ε����� ���ο� ���� ��.
	public Brick(int column, int isBall) {					
		this.row = 2;
		this.column = column;
		this.number=1;
		this.x = column*width+10;
		this.y = row*height+10;
		this.isBall = isBall;
		this.isBarGame = false;
	}

	// �ٸ� �������� �������� ���ӿ� ���� ����
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
		String blockNumber = "src/images/����" + (number%45+1) + ".png"; 
		
		// ���� ������ ��� �ִ� ���̶�� ���� ���·� �׸���
		if(this.isBall==1) { 
			// �� �����̸� ������ ������ �Բ� �׸���
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
		// �Ϲ� ������ ���
		else if(this.isBall==0) {
			g.drawImage(new ImageIcon(blockNumber).getImage(),this.x, this.y, width, height,null);
			g.setColor(Color.black);
			g.drawRect(this.x, this.y, width, height);

			if(!this.isBarGame) {
				g.setColor(Color.white);
				g.setFont(new Font("������", Font.BOLD, 20));
				g.drawString(Integer.toString(number), this.x+25, this.y+25);
			}
		}
	}

	public void crash(Ball ball) {
		// ������ �Ʒ���� �浹
		if((x-8) <= (ball.x+ball.radius/2) && (x+width+8) >= (ball.x+ball.radius/2) && ball.y >= (y+height-5) && ball.y <= (y+height+5)) {
			if(this.isBall==0) {	// �Ϲݺ����� ���
				if(ball.moveY<0) {
					ball.moveY = -(ball.moveY);
				}
				this.number--;		// ���� ������ �� ����
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
		// ������ ����� �浹
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
		// ������ ���ʸ�� �浹
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
		// ������ �����ʸ�� �浹
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