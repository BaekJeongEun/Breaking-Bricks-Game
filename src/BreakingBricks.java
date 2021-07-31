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
	CardLayout panels = new CardLayout();				  // 여러개의 패널을 이용하여 화면 전환을 위함
	StartPanel startPanel = new StartPanel();			  // 게임 메인 화면
	ManualPanel dragManualPanel = new ManualPanel();	  // 게임설명 화면
	BarManualPanel barManualPanel = new BarManualPanel(); // 게임설명 화면
	BreakingWithBar barGame;							  // 바를 사용한 벽돌깨기 클래스
	BreakingWithDrag dpDrag;							  // 드래그를 사용한 벽돌깨기 클래스
	JButton nextButton,nextButton2;						  // 사용설명서 다음페이지 버튼
	JButton backButton,backButton2;					 	  // 사용설명서 전페이지 버튼
	JButton dragManualButton, barManualButton;			  // 드래그게임, 바 게임 사용설명서 버튼
	JButton dragStartButton, barStartButton;			  // 드래그 벽돌깨기 화면 전환 버튼, 바 벽돌깨기 화면 전환 버튼
	JButton dragToStart, barToStart, backToStart,backToStart2;	// 게임 메인 화면 전환 버튼
	
	public static int clickCount = 0; 					  // 프로그램 내에서 어디서나 접근할 수 있는 전역 변수, 설명서 패널에서 다음페이지로 넘어가기 위한 카운트 변수

	private final String PAGE_TURN_SOUND = "/res/Pageturnsound.wav";	
	private final String BACK_GROUND_SOUND = "/res/게임-배경음악.wav";	
	private AudioClip pageTurnSound;
	private AudioClip backgroundSound;

	public static ArrayList<Ball> ballList = new ArrayList<Ball>();
	public static ArrayList<Brick> brickList = new ArrayList<Brick>();

	public static void main(String[] args) {
		BreakingBricks bricks = new BreakingBricks();
		bricks.go();
	}

	public void go() {
		frame = new JFrame ("BreakingBricks_20185093 백정은");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setLayout(panels);

		dpDrag = new BreakingWithDrag();
		barGame = new BreakingWithBar();

		frame.getContentPane().add("startP",startPanel);			// 메인화면 패널 추가
		frame.getContentPane().add("dragGameP",dpDrag.dp);			// 드래그하여 벽돌깨기 게임 패널 추가
		frame.getContentPane().add("barGameP",barGame);				// 바를 이용하는 벽돌깨기 게임 패널 추가
		frame.getContentPane().add("dragMenualP",dragManualPanel);  // 드래그 설명서 패널 추가
		frame.getContentPane().add("barManualP",barManualPanel);	// 바 설명서 패널 추가
		panels.show(frame.getContentPane(), "startP");				// 메인 화면을 띄워줌.

		ActionButtonListener buttonListener = new ActionButtonListener();
		nextButton = new JButton(new ImageIcon("src/images/forward.png"));
		backButton = new JButton(new ImageIcon("src/images/backward.png"));
		nextButton2 = new JButton(new ImageIcon("src/images/forward.png"));
		backButton2 = new JButton(new ImageIcon("src/images/backward.png"));
		backToStart = new JButton(new ImageIcon("src/images/backToStart.png"));
		backToStart2 = new JButton(new ImageIcon("src/images/backToStart.png"));
		dragToStart = new JButton(new ImageIcon("src/images/backToStart.png"));
		barToStart = new JButton(new ImageIcon("src/images/backToStart.png"));

		// 시작화면에 필요한 버튼들
		dragStartButton = new JButton(new ImageIcon("src/images/마우스 게임 버튼.png"));
		dragStartButton.setContentAreaFilled(false);
		dragStartButton.setBounds(90, 310, 100, 50);
		dragStartButton.setBorderPainted(false);
		dragStartButton.addActionListener(buttonListener);

		barStartButton = new JButton(new ImageIcon("src/images/키보드 게임 버튼.png"));
		barStartButton.setContentAreaFilled(false);
		barStartButton.setBounds(240, 310, 100, 50);
		barStartButton.setBorderPainted(false);
		barStartButton.addActionListener(buttonListener);

		dragManualButton = new JButton(new ImageIcon("src/images/게임설명1.png"));
		dragManualButton.setContentAreaFilled(false);
		dragManualButton.setBounds(90, 400, 100, 50);
		dragManualButton.setBorderPainted(false);
		dragManualButton.addActionListener(buttonListener);
		
		barManualButton = new JButton(new ImageIcon("src/images/게임설명2.png"));
		barManualButton.setContentAreaFilled(false);
		barManualButton.setBounds(240, 400, 100, 50);
		barManualButton.setBorderPainted(false);
		barManualButton.addActionListener(buttonListener);

		startPanel.add(dragStartButton);
		startPanel.add(dragManualButton);
		startPanel.add(barStartButton);
		startPanel.add(barManualButton);
	
		// 시작화면으로 돌아가기 위한 버튼
		backToStart.setBounds(10, 0, 30, 30);
		backToStart.setBorderPainted(false); // 버튼 테두리 없애기
		backToStart.setContentAreaFilled(false); // 버튼에 이미지 맞춤
		backToStart2.setBounds(10, 0, 30, 30);
		backToStart2.setBorderPainted(false); // 버튼 테두리 없애기
		backToStart2.setContentAreaFilled(false); // 버튼에 이미지 맞춤
		backToStart.addActionListener(buttonListener);
		backToStart2.addActionListener(buttonListener);
		dragManualPanel.add(backToStart);
		barManualPanel.add(backToStart2);
		barGame.setLayout(null);
		barGame.add(barToStart);
		barToStart.setBounds(10, 0, 30, 30);
		barToStart.setBorderPainted(false); // 버튼 테두리 없애기
		barToStart.setContentAreaFilled(false);		
		barToStart.addActionListener(buttonListener);
		dpDrag.dp.setLayout(null);	
		dpDrag.dp.add(dragToStart);
		dragToStart.setBounds(10, 0, 30, 30);
		dragToStart.setBorderPainted(false); // 버튼 테두리 없애기
		dragToStart.setContentAreaFilled(false);	
		dragToStart.addActionListener(buttonListener);

		// 설명서의 다음페이지로 이동하기 위한 버튼
		nextButton.setBounds(380, 455, 30, 30);
		nextButton.setBorderPainted(false); // 버튼 테두리 없애기
		nextButton.setContentAreaFilled(false);	
		nextButton.addActionListener(buttonListener);
		dragManualPanel.add(nextButton);
		nextButton2.setBounds(380, 455, 30, 30);
		nextButton2.setBorderPainted(false); // 버튼 테두리 없애기
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
		
		// 음향파일 실행
		try {
			pageTurnSound = JApplet.newAudioClip(getClass().getResource(PAGE_TURN_SOUND));
			backgroundSound = JApplet.newAudioClip(getClass().getResource(BACK_GROUND_SOUND));			
		}
		catch(Exception e){
			System.out.println("음향 파일 로딩 실패");
		}
	}

	public class ActionButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == dragStartButton)
			{
				panels.show(frame.getContentPane(), "dragGameP"); // 드래그 게임 화면 띄워줌.
				dpDrag.draw = true;
				dpDrag.newBall = 0;
				dpDrag.landCount = 0;
				dpDrag.ballTimer = 0;
				dpDrag.brickNumber = 1;
				dpDrag.game = true;
				dpDrag.timer.start();
				backgroundSound.loop(); // 배경음악 연속재생
			}
			// 바 게임 화면 보여짐
			if(e.getSource() == barStartButton)
			{
				panels.show(frame.getContentPane(), "barGameP");	// 바 게임 화면 띄워줌.
				backgroundSound.loop(); // 배경음악 연속재생
				barGame.heart=5;
				barGame.time = 0;
				barGame.img.pX = 150;
				barGame.img.pY = 470;
				barGame.starCreate();
			} 
			//  설명서 화면 보여짐
			if(e.getSource() == dragManualButton)
			{
				panels.show(frame.getContentPane(), "dragMenualP");	// 드래그 설명서 화면 띄워즘.
				clickCount=0;
			}
			if(e.getSource() == barManualButton) {
				panels.show(frame.getContentPane(), "barManualP");	// 바 설명서 화면 띄워줌.
				clickCount=0;
			}
			// 설명화면의 다음 페이지 넘김 버튼
			if(e.getSource() == nextButton ||e.getSource() == nextButton2) 
			{				
				clickCount++;
				System.out.println(clickCount);
				if(e.getSource()== nextButton && clickCount > 4) {	// 드래그 게임 설명서가 5페이지가 마지막이기 때문에 4로 제한
					clickCount = 0;
				}
				
				if(e.getSource()== nextButton2 && clickCount > 5) {	// 바 게임 설명서가 6페이지가 마지막이기 때문에 5로 제한
					clickCount = 0;
				}
				pageTurnSound.play();								// 페이지 넘어가는 음향 재생
			}
			// 설명화면의 이전 페이지 넘김 버튼
			if(e.getSource() == backButton || e.getSource() == backButton2) {
				
				clickCount--;
				System.out.println(clickCount);
				if(clickCount < 0) {
					clickCount = 0;
				}
				pageTurnSound.play();
			}
			// 시작화면으로 돌아감
			if(e.getSource() == backToStart || e.getSource() == backToStart2) 
			{
				// 첫 시작화면으로
				panels.show(frame.getContentPane(), "startP");		// 게임 시작 화면 띄워줌.
			}
			// 시작화면으로 돌아감
			if(e.getSource() == dragToStart || e.getSource() == barToStart)
			{
				//드래그 벽돌깨기 게임 내용 초기화
				dpDrag.timer.stop();
				if(dpDrag.ballList.size()>0 || dpDrag.brickList.size()>0) {
					dpDrag.ballList.clear();
					dpDrag.brickList.clear();
					int j=(int)(Math.random()*3); 					   // 벽돌의 열을 결정하는 j, 0부터 2까지
					for(int i = 0; i < (int)(Math.random()*5+1); i++){ // 랜덤 갯수로 생성 1개부터 5개까지 화면에 보이는 총 7개의 열(0부터 시작하면 6까지의 열)을 활용함.
						dpDrag.brickList.add(new Brick(j, 1, 0)); 
						j++; 										   // 다음 열에 새로운 벽돌 생성

					}
				}
				dpDrag.ballList.add(new Ball()); // 공 1개 생성

				//키보드 벽돌깨기 게임 내용 초기화
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

				// 첫 시작화면 보여줌
				backgroundSound.stop();
				panels.show(frame.getContentPane(), "startP");
			}
			// 설명서의 이미지를 변환하기 위함
			dragManualPanel.repaint();
			barManualPanel.repaint();
		}
	}
}
