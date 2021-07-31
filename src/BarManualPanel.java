import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
// 바를 이용한 벽돌깨기 게임의 설명서 패널
public class BarManualPanel extends JPanel
{	
	ImageIcon img1 = new ImageIcon("src/images/bar_설명1.png");
	ImageIcon img2 = new ImageIcon("src/images/bar_설명2.png");
	ImageIcon img3 = new ImageIcon("src/images/bar_설명3.png");
	ImageIcon img4 = new ImageIcon("src/images/bar_설명6.png");
	ImageIcon img5 = new ImageIcon("src/images/bar_설명4.png");
	ImageIcon img6 = new ImageIcon("src/images/bar_설명5.png");
	
	public BarManualPanel() 
	{
		this.setLayout(null);
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, 437, 530);

		if(BreakingBricks.clickCount == 0) {
			g.drawImage(img1.getImage(), 0,0, this.getWidth(),this.getHeight(),null); 
		}
		if(BreakingBricks.clickCount == 1) {
			g.drawImage(img2.getImage(), 0,0, this.getWidth(),this.getHeight(), null);
		}
		if(BreakingBricks.clickCount == 2)
			g.drawImage(img3.getImage(), 0,0, this.getWidth(),this.getHeight(), null);
		if(BreakingBricks.clickCount == 3)
			g.drawImage(img4.getImage(), 0,0, this.getWidth(),this.getHeight(), null);
		if(BreakingBricks.clickCount == 4)
			g.drawImage(img5.getImage(), 0,0, this.getWidth(),this.getHeight(), null);
		if(BreakingBricks.clickCount == 5)
			g.drawImage(img6.getImage(), 0,0, this.getWidth(),this.getHeight(), null);
		
	}
	
}