import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * @author King no !!
 * 
 */
public class NewTextPazzle extends JFrame implements KeyListener, ActionListener {
	JPanel cardPanel;
	CardLayout layout;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane,resultCard,menuCard,titleCard,buttonPanel,endPanel;
	private JTextField textField;
	private JLabel leftLabel,upLabel,centerLabel,downLabel,rightLabel,hideLabel,resultLabel,
					scoreLabel,difficultyLabel,numberOfQuestionsLabel,heartLabel,timerLabel,BestScoreLabel,
					TitleLabel,QLabel,ResultLabel,ResultannounceLabel;
	public JButton menuButton,nextDifficultyButton,exitButton,answerButton,endButton,goButton,
					easyButton,normalButton,hardButton;
	Timer timer = new Timer(1000, this);
	int sec, min, secLap, minLap, easyBest, normalBest, hardBest;
	int[] bestScore = {easyBest, normalBest, hardBest};
	private String[] numitems = {"5","10","15","20"};
	DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(numitems);
	int tnoq = 5;	//問題数を入れる変数
	int difficulty = 0; // 難易度選択0:easy 1:normal 2:hard
	JLabel[] labels = { centerLabel, leftLabel, upLabel, downLabel, rightLabel };
	String[] tnoqAnswers = new String[tnoq]; // 5つの解を入れる配列
	
	int ansCnt = 0;
	int correct = 0;
	int miss = 0;
	int score = 10000;
	int Diffch = 0;
	
	double quota = 0.6;

	String center, left = "左", up = "上", down = "下", right = "右";
	String jLeft, jUp, jDown, jRight;

	URL hide_imageURL = this.getClass().getResource("resources/84089164_480x480.png");
	private ImageIcon Hide = new ImageIcon(hide_imageURL); // 問題を隠している画像
	URL Title_imageURL = this.getClass().getResource("resources/Title.jpg");
	private ImageIcon Title = new ImageIcon(Title_imageURL);
	URL Menu_imageURL = this.getClass().getResource("resources/Menu.png");
	private ImageIcon Menu = new ImageIcon(Menu_imageURL);
	URL Q_URL = this.getClass().getResource("resources/Que.png");
	private ImageIcon Que = new ImageIcon(Q_URL);
	URL Result_URL = this.getClass().getResource("resources/Result.png");
	private ImageIcon Result = new ImageIcon(Result_URL);

	URL TitleBGM = this.getClass().getResource("resources/MusMus-STLP-006.wav");
	URL MenuBGM = this.getClass().getResource("resources/MusMus-BGM-004.wav");
	URL QBGM = this.getClass().getResource("resources/MusMus-BGM-003.wav");
	URL ResultBGM = this.getClass().getResource("resources/MusMus-STLP-008.wav");
	static Clip Titleclip = null;
	Clip Menuclip = null;
	Clip Qclip = null;
	Clip Resultclip = null;
	private JComboBox<String> QnumComboBox;
	private JLabel QnumLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewTextPazzle frame = new NewTextPazzle();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(100,100,960,540);
					frame.setVisible(true);
					TiBGMStart();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Clip createClip(URL bGM2) {
		//指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(bGM2)){
			
			//ファイルの形式取得
			AudioFormat af = ais.getFormat();
			
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip)AudioSystem.getLine(dataLine);
			
			//再生準備完了
			c.open(ais);
			
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Create the frame.
	 */
	public NewTextPazzle() {
		setBounds(new Rectangle(0,0,960,540));
		/*タイトルBGM*/
		Titleclip = createClip(TitleBGM);
		FloatControl Titlectrl = (FloatControl)Titleclip.getControl(FloatControl.Type.MASTER_GAIN);
		Titlectrl.setValue((float)Math.log10((float)40/100)*20);
		
		/*メニューBGM*/
		Menuclip = createClip(MenuBGM);
		FloatControl Menuctrl = (FloatControl)Menuclip.getControl(FloatControl.Type.MASTER_GAIN);
		Menuctrl.setValue((float)Math.log10((float)40/100)*20);
		
		/*問題画面BGM*/
		Qclip = createClip(QBGM);
		FloatControl Qctrl = (FloatControl)Qclip.getControl(FloatControl.Type.MASTER_GAIN);
		Qctrl.setValue((float)Math.log10((float)40/100)*20);
		
		/*結果画面BGM*/
		Resultclip = createClip(ResultBGM);
		FloatControl Resultctrl = (FloatControl)Resultclip.getControl(FloatControl.Type.MASTER_GAIN);
		Resultctrl.setValue((float)Math.log10((float)40/100)*20);

		ansCnt = 0;
		correct = 0;
		miss = 0;
		
		/*タイトルカード*/
		titleCard = new JPanel();
		setTitle("Title");
		titleCard.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// スタートボタン
		goButton = new JButton("Click to Start");
		goButton.setHorizontalAlignment(SwingConstants.LEFT);
		goButton.setForeground(SystemColor.textHighlight);
		goButton.setBackground(Color.WHITE);
		goButton.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD | Font.ITALIC, 31));
		goButton.setBounds(356, 343, 229, 56);
		goButton.addKeyListener(this);
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// メニュー画面へ移る
				TiBGMStop();
				MeBGMStart();
				setTitle("Menu");
				layout.show(cardPanel,"Menu" );
			}
		});
		titleCard.add(goButton);
		TitleLabel = new JLabel(Title);
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitleLabel.setBounds(-10, 0, 960, 500);
		titleCard.add(TitleLabel);
		TitleLabel.setVisible(true);
		titleCard.setLayout(null);
		
		
		/*メニューカード*/
		menuCard = new JPanel();
		menuCard.setBorder(new EmptyBorder(5, 5, 5, 5));
		menuCard.setLayout(null);
		
		QnumLabel = new JLabel("問題数");
		QnumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		QnumLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 30));
		QnumLabel.setBounds(343, 377, 93, 68);
		menuCard.add(QnumLabel);
		
		QnumComboBox = new JComboBox<String>(comboModel);
		QnumComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int NumNo = QnumComboBox.getSelectedIndex();
				if(e.getStateChange() == ItemEvent.SELECTED) {
					tnoq = Integer.parseInt(numitems[NumNo]);
					tnoqAnswers = new String[tnoq]; // 選択した問題数分の解を入れる配列
				}
				
			}
		});
		QnumComboBox.setFont(new Font("ＭＳ 明朝", Font.BOLD, 30));
		QnumComboBox.setBounds(459, 393, 107, 37);
		menuCard.add(QnumComboBox);
		
		endPanel = new JPanel();
		endPanel.setBackground(Color.BLACK);
		endPanel.setBounds(5, 453, 936, 45);
		FlowLayout flowLayout = (FlowLayout) endPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		menuCard.add(endPanel);
		
		endButton = new JButton("\u7D42\u4E86");
		endButton.addKeyListener(this);
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		endButton.setBackground(Color.BLACK);
		endButton.setForeground(Color.WHITE);
		endButton.setFont(new Font("Dialog", Font.BOLD, 20));
		endButton.setHorizontalAlignment(SwingConstants.LEFT);
		endPanel.add(endButton);
		
		easyButton = new JButton("かんたん");
		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MeBGMStop();
				QBGMStart();
				easy();
			}
		});
		easyButton.setForeground(Color.BLUE);
		easyButton.setFont(new Font("ＭＳ 明朝", Font.BOLD, 40));
		easyButton.setBackground(Color.WHITE);
		easyButton.setBounds(40, 65, 239, 59);
		menuCard.add(easyButton);
		
		normalButton = new JButton("ふつう");
		normalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MeBGMStop();
				QBGMStart();
				normal();
			}
		});
		normalButton.setForeground(Color.GREEN);
		normalButton.setFont(new Font("ＭＳ 明朝", Font.BOLD, 40));
		normalButton.setBackground(Color.WHITE);
		normalButton.setBounds(375, 65, 187, 59);
		menuCard.add(normalButton);
		
		hardButton = new JButton("むずかしい");
		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MeBGMStop();
				QBGMStart();
				hard();
			}
		});
		hardButton.setForeground(Color.RED);
		hardButton.setFont(new Font("ＭＳ 明朝", Font.BOLD, 40));
		hardButton.setBackground(Color.WHITE);
		hardButton.setBounds(634, 65, 291, 59);
		menuCard.add(hardButton);
		
		JLabel MenuLabel = new JLabel(Menu);
		MenuLabel.setBounds(-10, 0, 960, 500);
		menuCard.add(MenuLabel);

		
		
		/* 問題カード */
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 960, 540));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		hideLabel = new JLabel(Hide);
		hideLabel.setBounds(248, 170, 160, 160);
		contentPane.add(hideLabel);
		hideLabel.setVisible(true); // これで画像が見える（答えが見えなくなる）

		leftLabel = new JLabel(left);
		leftLabel.setForeground(Color.WHITE);
		leftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		leftLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		leftLabel.setBounds(75, 170, 160, 160);
		contentPane.add(leftLabel);

		upLabel = new JLabel(up);
		upLabel.setForeground(Color.WHITE);
		upLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		upLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upLabel.setBounds(248, 0, 160, 160);
		contentPane.add(upLabel);

		centerLabel = new JLabel(center);
		centerLabel.setForeground(Color.WHITE);
		centerLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		centerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		centerLabel.setBounds(248, 170, 160, 160);
		contentPane.add(centerLabel);

		downLabel = new JLabel(down);
		downLabel.setForeground(Color.WHITE);
		downLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		downLabel.setHorizontalAlignment(SwingConstants.CENTER);
		downLabel.setBounds(248, 343, 160, 160);
		contentPane.add(downLabel);

		rightLabel = new JLabel(right);
		rightLabel.setForeground(Color.WHITE);
		rightLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		rightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rightLabel.setBounds(420, 170, 160, 160);
		contentPane.add(rightLabel);

		/* 黒い画像 */

		answerButton = new JButton("\u89E3\u7B54");
		answerButton.setForeground(Color.BLACK);
		answerButton.setBackground(SystemColor.activeCaption);
		answerButton.setFont(new Font("ＭＳ 明朝", Font.BOLD, 30));
		answerButton.addKeyListener(this);
		answerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer();
			}
		});
		answerButton.setBounds(700, 354, 130, 45);
		contentPane.add(answerButton);

		textField = new JTextField();
		textField.addKeyListener(this);
		textField.setFont(new Font("MS UI Gothic", Font.BOLD, 25));
		textField.setBounds(700, 248, 130, 45);
		contentPane.add(textField);
		textField.setColumns(10);

		numberOfQuestionsLabel = new JLabel("1問目/"+tnoq+"問中");
		numberOfQuestionsLabel.setForeground(Color.WHITE);
		numberOfQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfQuestionsLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 55));
		numberOfQuestionsLabel.setBounds(548, 120, 400, 91);
		contentPane.add(numberOfQuestionsLabel);
		
		heartLabel = new JLabel("♥♥♥");
		heartLabel.setForeground(new Color(255, 0, 0));
		heartLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 80));
		heartLabel.setBounds(10, 10, 210, 74);
		contentPane.add(heartLabel);
		
		timerLabel = new JLabel();
		timerLabel.setForeground(Color.WHITE);
		timerLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 40));
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setBounds(662, 10, 266, 100);
		contentPane.add(timerLabel);
		timerLabel.setText(min + "分" + sec + "秒");
		
		QLabel = new JLabel(Que);
		QLabel.setBounds(-5,0,960,500);
		contentPane.add(QLabel);
		QLabel.setVisible(true);
		

		/* 結果カード */
		resultCard = new JPanel();
		resultCard.setLayout(null);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(222, 184, 135));
		buttonPanel.setBounds(0, 464, 946, 39);
		resultCard.add(buttonPanel);

		menuButton = new JButton("メニューへ戻る");
		menuButton.setBackground(SystemColor.activeCaption);
		menuButton.setForeground(new Color(0, 128, 0));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReBGMStop();
				MeBGMStart();
				setTitle("Menu");
				layout.show(cardPanel, "Menu");
			}
		});
		menuButton.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		menuButton.addKeyListener(this);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(menuButton);

		nextDifficultyButton = new JButton("次の難易度へ");
		nextDifficultyButton.setBackground(SystemColor.activeCaption);
		nextDifficultyButton.setForeground(new Color(165, 42, 42));
		nextDifficultyButton.addKeyListener(this);
		nextDifficultyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextDifficulty();
			}
		});
		nextDifficultyButton.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		buttonPanel.add(nextDifficultyButton);

		exitButton = new JButton("終了");
		exitButton.setBackground(SystemColor.activeCaption);
		exitButton.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		exitButton.addKeyListener(this);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonPanel.add(exitButton);
		resultLabel = new JLabel(tnoq+"問中〇問正解");
		resultLabel.setForeground(Color.GREEN);
		resultLabel.setBounds(183, 80, 563, 75);
		resultLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
		resultCard.add(resultLabel);
		
		scoreLabel = new JLabel("今回のスコア：");
		scoreLabel.setForeground(Color.BLACK);
		scoreLabel.setBounds(37, 265, 800, 70);
		scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		scoreLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		resultCard.add(scoreLabel);
		
		difficultyLabel = new JLabel("難易度：");
		difficultyLabel.setForeground(Color.BLACK);
		difficultyLabel.setBounds(37, 160, 800, 70);
		difficultyLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		difficultyLabel.setHorizontalAlignment(SwingConstants.LEFT);
		resultCard.add(difficultyLabel);
		
		BestScoreLabel = new JLabel("ベストスコア：");
		BestScoreLabel.setForeground(Color.BLACK);
		BestScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		BestScoreLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		BestScoreLabel.setBounds(37, 380, 800, 70);
		resultCard.add(BestScoreLabel);
		
		ResultannounceLabel = new JLabel("結果発表");
		ResultannounceLabel.setForeground(Color.BLUE);
		ResultannounceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ResultannounceLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		ResultannounceLabel.setBounds(280, 0, 350, 80);
		resultCard.add(ResultannounceLabel);
		
		ResultLabel = new JLabel(Result);
		ResultLabel.setBounds(-20, -20, 960, 500);
		resultCard.add(ResultLabel);
		ResultLabel.setVisible(true);

		/* 親（cardPanel自身）の定義 */
		cardPanel = new JPanel();
		layout = new CardLayout();
		cardPanel.setLayout(layout);
		
		cardPanel.add(titleCard,"Title");
		cardPanel.add(menuCard, "Menu");
		cardPanel.add(contentPane, "TextPazzle");
		cardPanel.add(resultCard, "result");

		getContentPane().add(cardPanel, BorderLayout.CENTER); // 最初に表示させるカードの指定（今回なら問題カード）

	}

	public void pickAnswer()  {
		
		try {
			InputStream easyAnswerURL = this.getClass().getResourceAsStream("resources/easy.txt");
			InputStream normalAnswerURL = this.getClass().getResourceAsStream("resources/normal.txt");
			InputStream hardAnswerURL = this.getClass().getResourceAsStream("resources/hard.txt");
			InputStream[] answerURLList = { easyAnswerURL, normalAnswerURL, hardAnswerURL };
			Random rnd = new Random();
			String answork; 
			InputStreamReader ansisr = new InputStreamReader(answerURLList[difficulty],"UTF-8");
			BufferedReader ansbr = new BufferedReader(ansisr);
			ArrayList<String> anslist = new ArrayList<String>();// 可変配列
			// 答えが入ってるの中身を全部配列に入れる
			String s = ansbr.readLine();
			while (s != null) {
				anslist.add(s);
				s = ansbr.readLine();
			}
			ansbr.close();
			// tnoq個解を選択
			answork = anslist.get(rnd.nextInt(anslist.size())); // 解をランダム抽選
			tnoqAnswers[0] = answork;
			for (int i = 1; i < tnoq; i++) {
				answork = anslist.get(rnd.nextInt(anslist.size()));
				for (int j = 0; j < i; j++) {
					if (!(tnoqAnswers[j].equals(answork))) { // 被ってなかったらループを抜ける
					} else {
						answork = anslist.get(rnd.nextInt(anslist.size()));
						continue;
					}
				}
				tnoqAnswers[i] = answork;
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	// 出題する熟語を取り出す
	public void questions(String answer) {
		center = answer;
		// ファイル操作
		try {
			InputStream easy_txturl = this.getClass().getResourceAsStream("resources/J-easy.txt");
			InputStream normal_txturl = this.getClass().getResourceAsStream("resources/J-normal.txt");
			InputStream hard_txturl = this.getClass().getResourceAsStream("resources/J-hard.txt");
			InputStream[] diff_list = { easy_txturl, normal_txturl, hard_txturl };
			InputStreamReader jisr = new InputStreamReader(diff_list[difficulty],"UTF-8");
			BufferedReader jbr = new BufferedReader(jisr);
			Random rnd = new Random();	// ランダム変数
			ArrayList<String> word_list = new ArrayList<String>();// 可変配列
			String work1, work2;

			// 熟語が入っているテキストファイルの中身を全部配列に入れる
			String str = jbr.readLine();
			while (str != null) {
				word_list.add(str);
				str = jbr.readLine();
			}
			jbr.close();
			
			ArrayList<String> first_list = new ArrayList<String>(); // 1文字目が解と同じ単語の配列
			ArrayList<String> second_list = new ArrayList<String>(); // 2文字目が解と同じ単語の配列
			for (String word : word_list) {
				work1 = word.substring(0, 1); // 1文字目を取り出す
				work2 = word.substring(1, 2); // 2文字目を取り出す
				if (center.equals(work1)) { // 1文字目と解が同じなら
					first_list.add(word); // 配列の要素を足す
				} else if (center.equals(work2)) { // 2文字目と解が同じなら
					second_list.add(word); // 配列の要素を足す
				}
			}
			// 保存した配列からランダムに文字を取ってくる操作
			jLeft = second_list.get(rnd.nextInt(second_list.size())); // 配列の要素からランダムに取ってくる
			while (true) {
				jUp = second_list.get(rnd.nextInt(second_list.size()));// 配列の要素からランダムに取ってくる
				if (!(jLeft.substring(0, 1).equals(jUp.substring(0, 1)))) { 	// 左の字と被ってないか
					break;														// 被ってなかったらループを抜ける
				}
			}
			while (true) {
				jDown = first_list.get(rnd.nextInt(first_list.size())); // 配列の要素からランダムに取ってくる
				if (!(jLeft.substring(0, 1).equals(jDown.substring(1, 2))) 		// 左の字と被ってないか
						&& !(jUp.substring(0, 1).equals(jDown.substring(1, 2)))) {// 上の字と被ってないか
					break;															// 被ってなかったらループを抜ける
				}
			}
			while (true) {
				jRight = first_list.get(rnd.nextInt(first_list.size())); // 配列の要素からランダムに取ってくる
				if (!(jDown.substring(1, 2).equals(jRight.substring(1, 2)))			// 下の字と被ってないか
						&& !(jLeft.substring(0, 1).equals(jRight.substring(1, 2)))	// 左の字と被ってないか
						&& !(jUp.substring(0, 1).equals(jRight.substring(1, 2)))) { 	// 上の字と被ってないか
					break;																// 被ってなかったらループを抜ける
				}
			}
			// ラベルに表示する変数に文字を入れる
			left = jLeft.substring(0, 1);
			up = jUp.substring(0, 1);
			down = jDown.substring(1, 2);
			right = jRight.substring(1, 2);
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	public void easy() {	//　かんたん
		difficulty = 0;
		ansCnt = 0;
		correct = 0;
		score = 10000;
		pickAnswer();
		questions(tnoqAnswers[ansCnt]); // 問題の再設定
		numberOfQuestionsLabel.setText(ansCnt+1+"問目/"+tnoq+"問中");
		centerLabel.setText(center);
		leftLabel.setText(left);
		upLabel.setText(up);
		rightLabel.setText(right);
		downLabel.setText(down);
		setTitle("Textvirsion:easy");
		layout.show(cardPanel, "TextPazzle");
		timer.start();
	}
	
	public void normal() {	//　ふつう
		difficulty = 1;
		ansCnt = 0;
		correct = 0;
		score = 10000;
		pickAnswer();
		questions(tnoqAnswers[ansCnt]); // 問題の再設定
		numberOfQuestionsLabel.setText(ansCnt+1+"問目/"+tnoq+"問中");
		centerLabel.setText(center);
		leftLabel.setText(left);
		upLabel.setText(up);
		rightLabel.setText(right);
		downLabel.setText(down);
		setTitle("Textvirsion:normal");
		layout.show(cardPanel, "TextPazzle");
		timer.start();
	}
	
	public void hard() {	//　むずかしい
		difficulty = 2;
		ansCnt = 0;
		correct = 0;
		score = 10000;
		pickAnswer();
		questions(tnoqAnswers[ansCnt]); // 問題の再設定
		numberOfQuestionsLabel.setText(ansCnt+1+"問目/"+tnoq+"問中");
		centerLabel.setText(center);
		leftLabel.setText(left);
		upLabel.setText(up);
		rightLabel.setText(right);
		downLabel.setText(down);
		setTitle("Textvirsion:hard");
		layout.show(cardPanel, "TextPazzle");
		timer.start();
	}
	
	public void nextDifficulty() {
		/*次の難易度への処理*/
		sec = 0;
		min = 0;
		timerLabel.setText(min + "分" + sec + "秒");
		heartLabel.setText("♥♥♥");
		score = 10000;
		if(difficulty == 2) {
			String[] buttons = { "タイトルへ戻る", "メニューへ戻る", };
			int button = JOptionPane.showOptionDialog(null, "あなたは最高難易度をクリアしました", "おめでとうございます！！！", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			
			if(button == 0) {
				ReBGMStop();
				TiBGMStart();
				setTitle("Title");
				layout.show(cardPanel, "Title");
				
			}else if (button == 1) {
				ReBGMStop();
				MeBGMStart();
				setTitle("Menu");
				layout.show(cardPanel, "Menu");
			}	
		}else if(difficulty == 0){
			ReBGMStop();
			QBGMStart();
			normal();
		}else if(difficulty == 1) {
			ReBGMStop();
			QBGMStart();
			hard();
		}
	}
	
	public void answer() {
		/* 解答の判定 */
		String ans = textField.getText();
		// テキストフィールドを空にする
		textField.setText("");
		if (ans.equals(center)) {
			timer.stop();
			score += scoreCalculation(secLap, minLap, sec, min);
			secLap = sec;
			minLap = min;
			correct++;
			miss = 0;
			ansCnt++;
			hideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
			String[] buttons = { "次の問題へ", "メニューへ戻る", };
			int button = JOptionPane.showOptionDialog(null, "正解です", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if (ansCnt == tnoq && correct >= Math.floor(tnoq*quota)+1) {
				bestScore[difficulty] = bestScore(score,difficulty); // 選択中の難易度のベストスコアを取得
				String[] resultbuttons = { "結果へ" };
				int resultbutton = JOptionPane.showOptionDialog(null, "全問解き終わりました", "結果画面へ", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, resultbuttons, resultbuttons[0]);
				if(resultbutton == 0 || resultbutton == -1) {
					heartLabel.setText("♥♥♥");
					resultLabel.setText(ansCnt + "問中" + correct + "問正解");
					if(difficulty == 0) {
						difficultyLabel.setText("難易度：かんたん");
					}else if (difficulty == 1) {
						difficultyLabel.setText("難易度：ふつう");
					}else if (difficulty == 2) {
						difficultyLabel.setText("難易度：むずかしい");
					}
					QBGMStop();
					ReBGMStart();
					scoreLabel.setText("今回のスコア：" + score);
					BestScoreLabel.setText("ベストスコア："+bestScore[difficulty]);
					layout.show(cardPanel, "result");
					setTitle("Result");
					sec = 0;
					min = 0;
					secLap = 0;
					minLap = 0;
					timerLabel.setText(min + "分" + sec + "秒");
					ansCnt = 0;
				}
			}else if(ansCnt == tnoq && correct < Math.floor(tnoq*quota)+1) {
				String[] stageFailedButtons = {"メニューへ戻る"};
				int stageFailedButton = JOptionPane.showOptionDialog(null, (int)Math.floor(tnoq*quota)+1+"問以上正解しよう！","クリア失敗",JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE, null, stageFailedButtons, stageFailedButtons[0]);
				if(stageFailedButton == 0 || stageFailedButton == -1) {
					QBGMStop();
					MeBGMStart();
					layout.show(cardPanel, "Menu");
					layout.show(cardPanel, "Menu");
					setTitle("Menu");
					ansCnt = 0;
					miss = 0;
					correct = 0;
					timer.stop();
					sec = 0;
					min = 0;
					secLap = 0;
					minLap = 0;
					timerLabel.setText(min + "分" + sec + "秒");
					heartLabel.setText("♥♥♥");
				}
			}
			if (button == 0 || button == -1) /* 次の問題ボタン */ {
				heartLabel.setText("♥♥♥");
				/* ここから問題を再描画 */
				numberOfQuestionsLabel.setText(ansCnt+1+"問目/"+tnoq+"問中");
				questions(tnoqAnswers[ansCnt]); // 問題の再設定
				centerLabel.setText(center);
				leftLabel.setText(left);
				upLabel.setText(up);
				rightLabel.setText(right);
				downLabel.setText(down);
				timer.restart();
				
			} else if (button == 1) {
				QBGMStop();
				MeBGMStart();
				
				layout.show(cardPanel, "Menu");
				timer.stop();
				sec = 0;
				min = 0;
				timerLabel.setText(min + "分" + sec + "秒");
				heartLabel.setText("♥♥♥");
			}

		} else {
			miss++;
			if (miss == 1) {
				score -= 250;
				heartLabel.setText("♥♥♡");
			} else if (miss == 2) {
				score -= 750;
				heartLabel.setText("♥♡♡");
			} else if (miss == 3) {
				timer.stop();
				score -= 1000;
				heartLabel.setText("♡♡♡");
				hideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
			}
			String[] buttons = { "解答しなおす", "メニューへ戻る" };
			int button = JOptionPane.showOptionDialog(null, "不正解です ", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE, null, buttons, buttons[0]);

			if (button == 0 || button == -1) {

				if (miss == 3 && ansCnt == tnoq-1 && correct >= Math.floor(tnoq*quota)+1) {
					sec = 0;
					min = 0;
					secLap = 0;
					minLap = 0;
					timerLabel.setText(min + "分" + sec + "秒");
					bestScore[difficulty] = bestScore(score, difficulty);// 選択中の難易度のベストスコアを取得
					String[] missresultbuttons = { "結果へ" };
					int missresultbutton = JOptionPane.showOptionDialog(null, "3回間違えました", "警告", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, missresultbuttons, missresultbuttons[0]);
					if(missresultbutton == 0 || missresultbutton == -1) {
						ansCnt++;
						resultLabel.setText(ansCnt + "問中" + correct + "問正解");
						heartLabel.setText("♥♥♥");
						if(difficulty == 0) {
							difficultyLabel.setText("難易度：かんたん");
						}else if (difficulty == 1) {
							difficultyLabel.setText("難易度：ふつう");
						}else if (difficulty == 2) {
							difficultyLabel.setText("難易度：むずかしい");
						}
						QBGMStop();
						ReBGMStart();
						scoreLabel.setText("今回のスコア：" + score);
						BestScoreLabel.setText("ベストスコア："+bestScore[difficulty]);
						layout.show(cardPanel, "result");
						setTitle("Result");
						ansCnt = 0;
						miss = 0;
					}
				}else if(miss == 3 && ansCnt == tnoq-1 && correct < Math.floor(tnoq*quota)+1) {
					String[] stageFailedButtons = {"メニューへ戻る"};
					int stageFailedButton = JOptionPane.showOptionDialog(null, (int)Math.floor(tnoq*quota)+1+"問以上正解しよう！","クリア失敗",JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, stageFailedButtons, stageFailedButtons[0]);
					if(stageFailedButton == 0 || stageFailedButton == -1) {
						QBGMStop();
						MeBGMStart();
						layout.show(cardPanel, "Menu");
						layout.show(cardPanel, "Menu");
						setTitle("Menu");
						ansCnt = 0;
						miss = 0;
						correct = 0;
						timer.stop();
						sec = 0;
						min = 0;
						secLap = 0;
						minLap = 0;
						timerLabel.setText(min + "分" + sec + "秒");
						heartLabel.setText("♥♥♥");
					}
				}
				if (miss == 3 && ansCnt < tnoq-1) {
					String[] missbuttons = { "閉じる" };
					int missbutton = JOptionPane.showOptionDialog(null, "3回間違えました", "警告", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, missbuttons, missbuttons[0]);
					if(missbutton == 0 || missbutton == -1) {
						secLap = sec;
						minLap = min;
						ansCnt++;
						miss = 0;
						heartLabel.setText("♥♥♥");
						questions(tnoqAnswers[ansCnt]); // 問題の再設定
						centerLabel.setText(center);
						leftLabel.setText(left);
						upLabel.setText(up);
						rightLabel.setText(right);
						downLabel.setText(down);
						numberOfQuestionsLabel.setText(ansCnt+1+"問目/"+tnoq+"問中");
						timer.restart();
					}
				}
			} else if (button == 1) {
				ansCnt = 0;
				miss = 0;
				QBGMStop();
				MeBGMStart();
				layout.show(cardPanel, "Menu");
				setTitle("Menu");
				timer.stop();
				sec = 0;
				min = 0;
				timerLabel.setText(min + "分" + sec + "秒");
				heartLabel.setText("♥♥♥");
			}
		}
		hideLabel.setVisible(true); // これで画像が見える（答えが見えなくなる）
	}

	// タイムによるスコア計算
	public int scoreCalculation(int secLap, int minLap,int sec, int min) {
		int time, timeScore = 10000;
		secLap += (minLap * 60);
		sec += (min * 60);
		time = sec - secLap;
		timeScore -= time * 50;	
		
		return timeScore;
	}

	/*各画面でのBGMスタート処理*/
	/*start()でBGMのスタート。loop(繰り返し回数)で繰り返し回数再生。
	 * Clip.LOOP_CONTINUOUSLYは無限ループ再生*/
	public static void TiBGMStart() {				//タイトルBGMのスタート
		Titleclip.start();
		Titleclip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void MeBGMStart() {						//メニューBGMのスタート
		Menuclip.start();
		Menuclip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void QBGMStart() {						//問題画面BGMのスタート
		Qclip.start();
		Qclip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void ReBGMStart() {						//リザルトBGMのスタート
		Resultclip.start();
		Resultclip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/*各画面でのBGMストップ処理*/
	/*stop()でBGMのストップ。flush()で音声再生の内部バッファを削除。
	 * setFramePosition(指定秒数のミリ単位)で再生位置の指定。
	 * setFramePosition(0)で再生位置を最初に戻す。*
	 * flush()についてはstopメソッドの直前に既に読み込まれた内部バッファが残ってしまうと
	 * 次回再生時にその残りを再生してしまうため、呼び出している。*/
	public void TiBGMStop() {						//タイトルBGMのストップ
		Titleclip.stop();
		Titleclip.flush();
		Titleclip.setFramePosition(0);
	}
	
	public void MeBGMStop() {						//メニューBGMのストップ
		Menuclip.stop();
		Menuclip.flush();
		Menuclip.setFramePosition(0);
	}
	
	public void QBGMStop() {						//問題画面BGMのストップ
		Qclip.stop();
		Qclip.flush();
		Qclip.setFramePosition(0);
	}
	
	public void ReBGMStop() {						//リザルトBGMのストップ
		Resultclip.stop();
		Resultclip.flush();
		Resultclip.setFramePosition(0);
	}
	
	// 難易度ごとのベストスコアを返す
	public int bestScore(int score,int difficult) {
		if(difficult == 0) {
			if(easyBest < score) {
				easyBest = score;
			}
			return easyBest;
		}else if(difficult == 1) {
			if(normalBest < score) {
				normalBest = score;
			}
			return normalBest;
		}else if(difficult == 2) {
			if(hardBest < score) {
				hardBest = score;
			}
			return hardBest;
		}
		return score;
	}
	// 十字キーとエンターキーをボタンとテキストフィールドで使う
	public void keyPressed(KeyEvent e) {
		if(!(e.getSource() instanceof JButton) && !(e.getSource() instanceof JTextField)) {return;} // ボタンとテキストフィールド以外は何もせず返す 
		// イベント発生もと取得
		Component event = e.getComponent();
		
		switch(e.getKeyCode()) {
		// ↓→はタブ
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_KP_RIGHT:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_KP_DOWN:
			// タブに設定してイベント発生
			e.setKeyCode(KeyEvent.VK_TAB);
			event.dispatchEvent(e);
			break;
		// ↑←はシフトタブ
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_KP_LEFT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_KP_UP:
			// タブに設定
			e.setKeyCode(KeyEvent.VK_TAB);
			
			// シフトのイベントを作成、イベントを発生させる
			KeyEvent s_event = new KeyEvent(
				e.getComponent(),
				e.getID(),
				e.getWhen(),
				InputEvent.SHIFT_DOWN_MASK,
				e.getKeyCode(),
				e.getKeyChar());
			event.dispatchEvent(s_event);
			break;
		//デバッグ
		case KeyEvent.VK_F1:
			hideLabel.setVisible(false);
			break;
		// enter
		case KeyEvent.VK_ENTER:
			// easy
			if(easyButton.equals(event)) {
				easy();
			}
			// normal
			if(normalButton.equals(event)) {
				normal();
			}
			// hard
			if(hardButton.equals(event)) {
				hard();
			}
			// 解答ボタン上でエンター
			if(answerButton.equals(event)) {
				answer();
				// タブに設定してイベント発生
				e.setKeyCode(KeyEvent.VK_TAB);
				event.dispatchEvent(e);
			}
			// テキストフィールド上でエンター
			if(textField.equals(event)) {
				e.setKeyCode(KeyEvent.VK_TAB);
				textField.dispatchEvent(e);
			}
			// Title画面とResult画面でメニューボタン上でエンター
			if(menuButton.equals(event) || goButton.equals(event)) {
				TiBGMStop();
				ReBGMStop();
				MeBGMStart();
				setTitle("Menu");
				layout.show(cardPanel, "Menu");
			}
			// Result画面の次の難易度ボタン上でエンター
			if(nextDifficultyButton.equals(event)) {
				nextDifficulty();
			}
			// 終了ボタン上でエンター
			if(exitButton.equals(event) || endButton.equals(event)) {
				System.exit(0);
			}
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			//デバッグ
			case KeyEvent.VK_F1:
				hideLabel.setVisible(true);
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timerLabel.setText(min + "分" + sec + "秒");
		if(sec==60) {
			  min+=1;
			  sec=0;
		  }
	    if (sec >= 1000){
	      timer.stop();
	    }else{
	      sec++;
	    }
	}
}
