import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
// 게임의 메인 화면을 보여줄 패널
public class StartPanel extends JPanel
{
	ImageIcon img;
	
	public StartPanel() 
	{
		this.setLayout(null);
		img = new ImageIcon("src/images/메인 배경화면.png");
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 437, 530);
		
		g.drawImage(img.getImage(), 0, 0, 420, 280, null);
	}
}
