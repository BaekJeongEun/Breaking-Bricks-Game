import java.applet.AudioClip;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;

public class BreakingBricks {
	private JFrame frame;
	public static int WIDTH = 437, HEIGHT = 530;
	CardLayout panels = new CardLayout();				  // �������� �г��� �̿��Ͽ� ȭ�� ��ȯ�� ����
	StartPanel startPanel = new StartPanel();			  // ���� ���� ȭ��
	ManualPanel dragManualPanel = new ManualPanel();	  // ���Ӽ��� ȭ��
	BarManualPanel barManualPanel = new BarManualPanel(); // ���Ӽ��� ȭ��
	BreakingWithBar barGame;							  // �ٸ� ����� �������� Ŭ����
	BreakingWithDrag dpDrag;							  // �巡�׸� ����� �������� Ŭ����
	JButton nextButton,nextButton2;						  // ��뼳�� ���������� ��ư
	JButton backButton,backButton2;					 	  // ��뼳�� �������� ��ư
	JButton dragManualButton, barManualButton;			  // �巡�װ���, �� ���� ��뼳�� ��ư
	JButton dragStartButton, barStartButton;			  // �巡�� �������� ȭ�� ��ȯ ��ư, �� �������� ȭ�� ��ȯ ��ư
	JButton dragToStart, barToStart, backToStart,backToStart2;	// ���� ���� ȭ�� ��ȯ ��ư
	
	public static int clickCount = 0; 					  // ���α׷� ������ ��𼭳� ������ �� �ִ� ���� ����, ���� �гο��� ������������ �Ѿ�� ���� ī��Ʈ ����

	private final String PAGE_TURN_SOUND = "/res/Pageturnsound.wav";	
	private final String BACK_GROUND_SOUND = "/res/����-�������.wav";	
	private AudioClip pageTurnSound;
	private AudioClip backgroundSound;

	public static ArrayList<Ball> ballList = new ArrayList<Ball>();
	public static ArrayList<Brick> brickList = new ArrayList<Brick>();

	public static void main(String[] args) {
		BreakingBricks bricks = new BreakingBricks();
		bricks.go();
	}

	public void go() {
		frame = new JFrame ("BreakingBricks_20185093 ������");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setLayout(panels);

		dpDrag = new BreakingWithDrag();
		barGame = new BreakingWithBar();

		frame.getContentPane().add("startP",startPanel);			// ����ȭ�� �г� �߰�
		frame.getContentPane().add("dragGameP",dpDrag.dp);			// �巡���Ͽ� �������� ���� �г� �߰�
		frame.getContentPane().add("barGameP",barGame);				// �ٸ� �̿��ϴ� �������� ���� �г� �߰�
		frame.getContentPane().add("dragMenualP",dragManualPanel);  // �巡�� ���� �г� �߰�
		frame.getContentPane().add("barManualP",barManualPanel);	// �� ���� �г� �߰�
		panels.show(frame.getContentPane(), "startP");				// ���� ȭ���� �����.

		ActionButtonListener buttonListener = new ActionButtonListener();
		nextButton = new JButton(new ImageIcon("src/images/forward.png"));
		backButton = new JButton(new ImageIcon("src/images/backward.png"));
		nextButton2 = new JButton(new ImageIcon("src/images/forward.png"));
		backButton2 = new JButton(new ImageIcon("src/images/backward.png"));
		backToStart = new JButton(new ImageIcon("src/images/backToStart.png"));
		backToStart2 = new JButton(new ImageIcon("src/images/backToStart.png"));
		dragToStart = new JButton(new ImageIcon("src/images/backToStart.png"));
		barToStart = new JButton(new ImageIcon("src/images/backToStart.png"));

		// ����ȭ�鿡 �ʿ��� ��ư��
		dragStartButton = new JButton(new ImageIcon("src/images/���콺 ���� ��ư.png"));
		dragStartButton.setContentAreaFilled(false);
		dragStartButton.setBounds(90, 310, 100, 50);
		dragStartButton.setBorderPainted(false);
		dragStartButton.addActionListener(buttonListener);

		barStartButton = new JButton(new ImageIcon("src/images/Ű���� ���� ��ư.png"));
		barStartButton.setContentAreaFilled(false);
		barStartButton.setBounds(240, 310, 100, 50);
		barStartButton.setBorderPainted(false);
		barStartButton.addActionListener(buttonListener);

		dragManualButton = new JButton(new ImageIcon("src/images/���Ӽ���1.png"));
		dragManualButton.setContentAreaFilled(false);
		dragManualButton.setBounds(90, 400, 100, 50);
		dragManualButton.setBorderPainted(false);
		dragManualButton.addActionListener(buttonListener);
		
		barManualButton = new JButton(new ImageIcon("src/images/���Ӽ���2.png"));
		barManualButton.setContentAreaFilled(false);
		barManualButton.setBounds(240, 400, 100, 50);
		barManualButton.setBorderPainted(false);
		barManualButton.addActionListener(buttonListener);

		startPanel.add(dragStartButton);
		startPanel.add(dragManualButton);
		startPanel.add(barStartButton);
		startPanel.add(barManualButton);
	
		// ����ȭ������ ���ư��� ���� ��ư
		backToStart.setBounds(10, 0, 30, 30);
		backToStart.setBorderPainted(false); // ��ư �׵θ� ���ֱ�
		backToStart.setContentAreaFilled(false); // ��ư�� �̹��� ����
		backToStart2.setBounds(10, 0, 30, 30);
		backToStart2.setBorderPainted(false); // ��ư �׵θ� ���ֱ�
		backToStart2.setContentAreaFilled(false); // ��ư�� �̹��� ����
		backToStart.addActionListener(buttonListener);
		backToStart2.addActionListener(buttonListener);
		dragManualPanel.add(backToStart);
		barManualPanel.add(backToStart2);
		barGame.setLayout(null);
		barGame.add(barToStart);
		barToStart.setBounds(10, 0, 30, 30);
		barToStart.setBorderPainted(false); // ��ư �׵θ� ���ֱ�
		barToStart.setContentAreaFilled(false);		
		barToStart.addActionListener(buttonListener);
		dpDrag.dp.setLayout(null);	
		dpDrag.dp.add(dragToStart);
		dragToStart.setBounds(10, 0, 30, 30);
		dragToStart.setBorderPainted(false); // ��ư �׵θ� ���ֱ�
		dragToStart.setContentAreaFilled(false);	
		dragToStart.addActionListener(buttonListener);

		// ������ ������������ �̵��ϱ� ���� ��ư
		nextButton.setBounds(380, 455, 30, 30);
		nextButton.setBorderPainted(false); // ��ư �׵θ� ���ֱ�
		nextButton.setContentAreaFilled(false);	
		nextButton.addActionListener(buttonListener);
		dragManualPanel.add(nextButton);
		nextButton2.setBounds(380, 455, 30, 30);
		nextButton2.setBorderPainted(false); // ��ư �׵θ� ���ֱ�
		nextButton2.setContentAreaFilled(false);	
		nextButton2.addActionListener(buttonListener);
		barManualPanel.add(nextButton2);
		backButton.setBounds(10, 455, 30, 30);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);	
		backButton.addActionListener(buttonListener);
		dragManualPanel.add(backButton);
		backButton2.setBounds(10, 455, 30, 30);
		backButton2.setBorderPainted(false);
		backButton2.setContentAreaFilled(false);	
		backButton2.addActionListener(buttonListener);
		barManualPanel.add(backButton2);

		frame.setSize(437,530);
		frame.setVisible(true);
		frame.setPreferredSize (new Dimension(WIDTH, HEIGHT));
		
		// �������� ����
		try {
			pageTurnSound = JApplet.newAudioClip(getClass().getResource(PAGE_TURN_SOUND));
			backgroundSound = JApplet.newAudioClip(getClass().getResource(BACK_GROUND_SOUND));			
		}
		catch(Exception e){
			System.out.println("���� ���� �ε� ����");
		}
	}

	public class ActionButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == dragStartButton)
			{
				panels.show(frame.getContentPane(), "dragGameP"); // �巡�� ���� ȭ�� �����.
				dpDrag.draw = true;
				dpDrag.newBall = 0;
				dpDrag.landCount = 0;
				dpDrag.ballTimer = 0;
				dpDrag.brickNumber = 1;
				dpDrag.game = true;
				dpDrag.timer.start();
				backgroundSound.loop(); // ������� �������
			}
			// �� ���� ȭ�� ������
			if(e.getSource() == barStartButton)
			{
				panels.show(frame.getContentPane(), "barGameP");	// �� ���� ȭ�� �����.
				backgroundSound.loop(); // ������� �������
				barGame.heart=5;
				barGame.time = 0;
				barGame.img.pX = 150;
				barGame.img.pY = 470;
				barGame.starCreate();
			} 
			//  ���� ȭ�� ������
			if(e.getSource() == dragManualButton)
			{
				panels.show(frame.getContentPane(), "dragMenualP");	// �巡�� ���� ȭ�� �����.
				clickCount=0;
			}
			if(e.getSource() == barManualButton) {
				panels.show(frame.getContentPane(), "barManualP");	// �� ���� ȭ�� �����.
				clickCount=0;
			}
			// ����ȭ���� ���� ������ �ѱ� ��ư
			if(e.getSource() == nextButton ||e.getSource() == nextButton2) 
			{				
				clickCount++;
				System.out.println(clickCount);
				if(e.getSource()== nextButton && clickCount > 4) {	// �巡�� ���� ������ 5�������� �������̱� ������ 4�� ����
					clickCount = 0;
				}
				
				if(e.getSource()== nextButton2 && clickCount > 5) {	// �� ���� ������ 6�������� �������̱� ������ 5�� ����
					clickCount = 0;
				}
				pageTurnSound.play();								// ������ �Ѿ�� ���� ���
			}
			// ����ȭ���� ���� ������ �ѱ� ��ư
			if(e.getSource() == backButton || e.getSource() == backButton2) {
				
				clickCount--;
				System.out.println(clickCount);
				if(clickCount < 0) {
					clickCount = 0;
				}
				pageTurnSound.play();
			}
			// ����ȭ������ ���ư�
			if(e.getSource() == backToStart || e.getSource() == backToStart2) 
			{
				// ù ����ȭ������
				panels.show(frame.getContentPane(), "startP");		// ���� ���� ȭ�� �����.
			}
			// ����ȭ������ ���ư�
			if(e.getSource() == dragToStart || e.getSource() == barToStart)
			{
				//�巡�� �������� ���� ���� �ʱ�ȭ
				dpDrag.timer.stop();
				if(dpDrag.ballList.size()>0 || dpDrag.brickList.size()>0) {
					dpDrag.ballList.clear();
					dpDrag.brickList.clear();
					int j=(int)(Math.random()*3); 					   // ������ ���� �����ϴ� j, 0���� 2����
					for(int i = 0; i < (int)(Math.random()*5+1); i++){ // ���� ������ ���� 1������ 5������ ȭ�鿡 ���̴� �� 7���� ��(0���� �����ϸ� 6������ ��)�� Ȱ����.
						dpDrag.brickList.add(new Brick(j, 1, 0)); 
						j++; 										   // ���� ���� ���ο� ���� ����

					}
				}
				dpDrag.ballList.add(new Ball()); // �� 1�� ����

				//Ű���� �������� ���� ���� �ʱ�ȭ
				barGame.timer.stop();
				barGame.timer2.stop();
				barGame.list.clear();
				barGame.brickList.clear();
				barGame.newBallList.clear();

				barGame.list.add(new Ball(200,300,20, true, false));
				for(int i = 2; i<7; i++) {
					for(int j = 0; j<7; j++) {
						int n = (int) (Math.random()*5);
						if(n==0) {
							barGame.brickList.add(new Brick(i, j, 1, true, 1));
						}
						else
							barGame.brickList.add(new Brick(i, j, 1, true, 0));
					}
				}

				// ù ����ȭ�� ������
				backgroundSound.stop();
				panels.show(frame.getContentPane(), "startP");
			}
			// ������ �̹����� ��ȯ�ϱ� ����
			dragManualPanel.repaint();
			barManualPanel.repaint();
		}
	}
}
