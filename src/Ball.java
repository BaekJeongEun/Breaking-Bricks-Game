import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Ball{
	Boolean land = true; // �� ������ ���鿡 ��ġ�� �ִٴ� �ǹ�
	int radius = 16;
	double moveX, moveY;
	double x,y;
	int number;			// ���� ������ ����
	Boolean shooting;	// �浹���� ����
	ImageIcon img = new ImageIcon("src/images/�� �̹���.png");
	ImageIcon xImg = new ImageIcon("src/images/x.png");
	Boolean isBarGame;	// �ٸ� �̿��� ���ӿ��� ���̴� �������� ����
	Boolean isNewBall;	// ���� ������ ���� �������� ����
	Boolean barLand;	// �ٿ� ��Ҵ����� ����

	public Ball() {
		this.x = BreakingBricks.WIDTH/2-radius/2; 		// �������� �߾ӿ� ����
		this.y = BreakingBricks.HEIGHT -40 - radius;    // �������� �ϴܿ� ���� 
		if(BreakingWithDrag.ballList.size()>0) {
			this.x = BreakingWithDrag.ballList.get(0).x;
			this.y = BreakingWithDrag.ballList.get(0).y;
			shooting = false;
		}
		this.isBarGame = false;
		this.isNewBall = false;
	}

	public Ball(int x, int y, int radius, Boolean barGame, Boolean isNewBall) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.isBarGame = barGame;
		this.moveX = 0;
		this.moveY = 1;
		this.shooting = false;
		this.isNewBall = isNewBall;
		this.barLand = false;
	}


	public void draw(Graphics g) {

		g.drawImage(img.getImage(),(int)this.x, (int)this.y, radius, radius, null);

		if(this == BreakingWithDrag.ballList.get(0) && this.land) { // �ʱ� ���� ���鿡 ������� �� �ʱ� ���� ��ܿ��� ���� ǥ��, ���� ���� �����̰� ���� ��쿣 �ش� �ȵ�.
			String tens = "src/images/"+(BreakingWithDrag.ballList.size())/10+".png";
			String units = "src/images/"+(BreakingWithDrag.ballList.size())%10+".png";
			
			if(BreakingWithDrag.ballList.size() >=100) {
				String hund = "src/images/"+BreakingWithDrag.ballList.size()/100+".png";
				tens= "src/images/"+BreakingWithDrag.ballList.size()%100/10+".png";
				units  = "src/images/"+BreakingWithDrag.ballList.size()%100%10%10+".png";
				g.drawImage(new ImageIcon(hund).getImage(),(int)this.x-16, (int)this.y-18,15,15,null);
			}
			
			g.drawImage(xImg.getImage(),(int)this.x-10, (int)this.y-15, 10, 10, null);
			g.drawImage(new ImageIcon(units).getImage(),(int)this.x+20, (int)this.y-18,15,15,null);
			g.drawImage(new ImageIcon(tens).getImage(),(int)this.x+2, (int)this.y-18,15,15,null);
		}
		if(this.isNewBall) {
			g.setColor(Color.red);
			g.fillOval((int)this.x, (int)this.y, this.radius, this.radius);
		}

	}
	public void move(double x, double y) {

		this.x=this.x+x;
		this.y=this.y+y;

	}
}