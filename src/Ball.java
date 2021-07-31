import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Ball{
	Boolean land = true; // 공 생성시 지면에 위치해 있다는 의미
	int radius = 16;
	double moveX, moveY;
	double x,y;
	int number;			// 공의 내구성 숫자
	Boolean shooting;	// 충돌경험 유무
	ImageIcon img = new ImageIcon("src/images/공 이미지.png");
	ImageIcon xImg = new ImageIcon("src/images/x.png");
	Boolean isBarGame;	// 바를 이용한 게임에서 쓰이는 공인지의 유무
	Boolean isNewBall;	// 새로 생성된 빨간 공인지의 여부
	Boolean barLand;	// 바에 닿았는지의 여부

	public Ball() {
		this.x = BreakingBricks.WIDTH/2-radius/2; 		// 프레임의 중앙에 맞춤
		this.y = BreakingBricks.HEIGHT -40 - radius;    // 프레임의 하단에 맞춤 
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

		if(this == BreakingWithDrag.ballList.get(0) && this.land) { // 초기 공이 지면에 닿아있을 때 초기 공의 상단에만 갯수 표시, 따라서 공이 움직이고 있을 경우엔 해당 안됨.
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