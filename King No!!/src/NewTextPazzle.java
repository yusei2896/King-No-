import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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

/**
 * @author King no !!
 *
 */
public class NewTextPazzle extends JFrame implements KeyListener {
	JPanel cardPanel;
	CardLayout layout;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane,card1,card2,card3,ButtonPanel,nanidopanel,EndPanel,panel;
	private JTextField textField;
	private JLabel LeftLabel,UpLabel,CenterLabel,DownLabel,RightLabel,HideLabel,ResultLabel,ScoreLabel,DispLabel,DifficultyLabel,txtLabel;
	public JButton MenuButton,NextdifficultyButton,ExitButton,answerButton,EasyButton,NormalButton,HardButton,EndButton,GoButton;

	int difficulty = 0; // 難易度選択0:easy 1:normal 2:hard
	JLabel[] Labels = { CenterLabel, LeftLabel, UpLabel, DownLabel, RightLabel };
	String[] fiveans = new String[5]; // 5つの解を入れる配列

	int anscnt = 0;
	int correct = 0;
	int miss = 0;

	String C, L = "左", U = "上", D = "下", R = "右";
	String Left, Up, Down, Right;

	URL HideimageURL = this.getClass().getResource("resources/84089164_480x480.png");
	private ImageIcon Hide = new ImageIcon(HideimageURL); // 問題を隠している画像

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewTextPazzle frame = new NewTextPazzle();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(0,0,960,540);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewTextPazzle() {
		setBounds(new Rectangle(0,0,960,540));
		anscnt = 0;
		correct = 0;
		miss = 0;
		//Fiveanswer();

		//for (String a : fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		//System.out.println("");

		//Questions(fiveans[anscnt]);
		// タイトルの後ろに難易度を表示
		if (difficulty == 0) {
			setTitle("Textvirsion:easy");
		} else if (difficulty == 1) {
			setTitle("Textvirsion:normal");
		} else if (difficulty == 2) {
			setTitle("Textvirsion:hard");
		}
		/*タイトルカード*/
		card3 = new JPanel();
		setTitle("タイトル");
		card3.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		GoButton = new JButton("click to start");
		GoButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 24));
		GoButton.setBounds(371, 368, 207, 57);
		GoButton.addKeyListener(this);
		GoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cardPanel,"Menu" );
			}
		});
		card3.setLayout(null);
		card3.add(GoButton);
		
		JLabel TitleLabel = new JLabel("虫食い!!漢字クロス");
		TitleLabel.setBounds(267, 87, 437, 94);
		TitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 42));
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card3.add(TitleLabel);
		
		
		/*メニューカード*/
		card2 = new JPanel();
		setTitle("メニュー");
		card2.setBorder(new EmptyBorder(5, 5, 5, 5));
		card2.setLayout(new BorderLayout(20, 20));
		
		nanidopanel = new JPanel();
		FlowLayout fl_nanidopanel = (FlowLayout) nanidopanel.getLayout();
		fl_nanidopanel.setVgap(70);
		fl_nanidopanel.setHgap(30);
		card2.add(nanidopanel, BorderLayout.CENTER);
		
		EasyButton = new JButton("\u304B\u3093\u305F\u3093");
		EasyButton.addKeyListener(this);
		EasyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				easy();
			}
		});
		EasyButton.setBackground(Color.WHITE);
		EasyButton.setFont(new Font("ＭＳ 明朝", Font.BOLD, 25));
		nanidopanel.add(EasyButton);
		
		NormalButton = new JButton("\u3075\u3064\u3046");
		NormalButton.addKeyListener(this);
		NormalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				normal();
			}
		});
		NormalButton.setBackground(Color.WHITE);
		NormalButton.setFont(new Font("ＭＳ 明朝", Font.BOLD, 25));
		nanidopanel.add(NormalButton);
		
		HardButton = new JButton("\u3080\u305A\u304B\u3057\u3044");
		HardButton.addKeyListener(this);
		HardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hard();
			}
		});
		HardButton.setBackground(Color.WHITE);
		HardButton.setFont(new Font("ＭＳ 明朝", Font.BOLD, 25));
		HardButton.setHorizontalAlignment(SwingConstants.RIGHT);
		nanidopanel.add(HardButton);
		
		EndPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) EndPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		card2.add(EndPanel, BorderLayout.SOUTH);
		
		EndButton = new JButton("\u7D42\u4E86");
		EndButton.addKeyListener(this);
		EndButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		EndButton.setBackground(Color.BLACK);
		EndButton.setForeground(Color.WHITE);
		EndButton.setFont(new Font("Dialog", Font.BOLD, 20));
		EndButton.setHorizontalAlignment(SwingConstants.LEFT);
		EndPanel.add(EndButton);
		
		panel = new JPanel();
		card2.add(panel, BorderLayout.NORTH);
		
		DispLabel = new JLabel("");
		panel.add(DispLabel);
		
		
		/* 問題カード */
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 960, 540));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		LeftLabel = new JLabel(L);
		LeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LeftLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		LeftLabel.setBounds(75, 170, 160, 160);
		contentPane.add(LeftLabel);

		UpLabel = new JLabel(U);
		UpLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		UpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UpLabel.setBounds(248, 0, 160, 160);
		contentPane.add(UpLabel);

		CenterLabel = new JLabel(C);
		CenterLabel.setForeground(Color.BLACK);
		CenterLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		CenterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CenterLabel.setBounds(248, 170, 160, 160);
		contentPane.add(CenterLabel);

		DownLabel = new JLabel(D);
		DownLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		DownLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DownLabel.setBounds(248, 343, 160, 160);
		contentPane.add(DownLabel);

		RightLabel = new JLabel(R);
		RightLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		RightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RightLabel.setBounds(420, 170, 160, 160);
		contentPane.add(RightLabel);

		/* 黒い画像 */
		HideLabel = new JLabel(Hide);
		HideLabel.setBounds(248, 170, 160, 160);
		contentPane.add(HideLabel);
		HideLabel.setVisible(true); // これで画像が見える（答えが見えなくなる）

		answerButton = new JButton("\u89E3\u7B54");
		answerButton.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		answerButton.addKeyListener(this);
		answerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer();
			}
		});
		answerButton.setBounds(752, 429, 130, 45);
		contentPane.add(answerButton);

		textField = new JTextField();
		textField.addKeyListener(this);
		textField.setFont(new Font("MS UI Gothic", Font.BOLD, 25));
		textField.setBounds(750, 325, 130, 45);
		contentPane.add(textField);
		textField.setColumns(10);

		txtLabel = new JLabel(anscnt+1+"/5");
		txtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		txtLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		txtLabel.setBounds(661, 21, 275, 196);
		contentPane.add(txtLabel);

		/* 結果カード */
		card1 = new JPanel();
		card1.setLayout(new BorderLayout(0, 0));

		ButtonPanel = new JPanel();
		card1.add(ButtonPanel, BorderLayout.SOUTH);

		MenuButton = new JButton("メニューに戻る");
		MenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cardPanel, "Menu");
			}
		});
		MenuButton.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		MenuButton.addKeyListener(this);
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		ButtonPanel.add(MenuButton);

		NextdifficultyButton = new JButton("次の難易度へ");
		NextdifficultyButton.addKeyListener(this);
		NextdifficultyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextdifficulty();
			}
		});
		NextdifficultyButton.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		NextdifficultyButton.addKeyListener(this);
		ButtonPanel.add(NextdifficultyButton);

		ExitButton = new JButton("終了");
		ExitButton.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		ExitButton.addKeyListener(this);
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		ButtonPanel.add(ExitButton);
		ResultLabel = new JLabel("5問中〇問正解");
		ResultLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 80));
		ResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card1.add(ResultLabel, BorderLayout.NORTH);
		
		ScoreLabel = new JLabel("スコア：");
		ScoreLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		ScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card1.add(ScoreLabel, BorderLayout.EAST);
		
		DifficultyLabel = new JLabel("難易度：");
		DifficultyLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		DifficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card1.add(DifficultyLabel, BorderLayout.WEST);

		/* 親（cardPanel自身）の定義 */
		cardPanel = new JPanel();
		layout = new CardLayout();
		cardPanel.setLayout(layout);
		
		cardPanel.add(card3,"Title");
		cardPanel.add(card2, "Menu");
		cardPanel.add(contentPane, "TextPazzle");
		cardPanel.add(card1, "result");
		
		
		getContentPane().add(cardPanel, BorderLayout.CENTER); // 最初に表示させるカードの指定（今回なら問題カード）

	}

	public void Fiveanswer()  {
		
		try {
			InputStream easyansurl = this.getClass().getClassLoader().getResourceAsStream("resources/easy.txt");
			InputStream normalansurl = this.getClass().getClassLoader().getResourceAsStream("resources/normal.txt");
			InputStream hardansurl = this.getClass().getClassLoader().getResourceAsStream("resources/hard.txt");
			InputStream[] ansurllist = { easyansurl, normalansurl, hardansurl };
			Random rnd = new Random();
			String answork; 
			InputStreamReader ansisr = new InputStreamReader(ansurllist[difficulty],"UTF-8");
			BufferedReader ansbr = new BufferedReader(ansisr);
			ArrayList<String> anslist = new ArrayList<String>();// 可変配列
			// 答えが入ってるの中身を全部配列に入れる
			String s = ansbr.readLine();
			while (s != null) {
				anslist.add(s);
				s = ansbr.readLine();
			}
			ansbr.close();
			// 5つ解を選択
			answork = anslist.get(rnd.nextInt(anslist.size())); // 解をランダム抽選
			fiveans[0] = answork;
			for (int i = 1; i < 5; i++) {
				answork = anslist.get(rnd.nextInt(anslist.size()));
				for (int j = 0; j < i; j++) {
					if (!(fiveans[j].equals(answork))) { // 被ってなかったらループを抜ける
					} else {
						answork = anslist.get(rnd.nextInt(anslist.size()));
						continue;
					}
				}
				fiveans[i] = answork;
			}
			
		} catch (IOException e) {
			System.out.println(e);

		}
		
	}

	public void Questions(String answer) {
		C = answer;
		// System.out.print(anscnt+1+"問目");
		// ファイル操作
		try {
			InputStream easyTxturl = this.getClass().getResourceAsStream("resources/J-easy.txt");
			InputStream normalTxturl = this.getClass().getResourceAsStream("resources/J-normal.txt");
			InputStream hardTxturl = this.getClass().getResourceAsStream("resources/J-hard.txt");
			InputStream[] difflist = { easyTxturl, normalTxturl, hardTxturl };
			InputStreamReader jisr = new InputStreamReader(difflist[difficulty],"UTF-8");
			BufferedReader jbr = new BufferedReader(jisr);
			Random rnd = new Random();
			ArrayList<String> wordlist = new ArrayList<String>();// 可変配列
			String work1, work2;

			// テキストファイルの中身を全部配列に入れる
			String str = jbr.readLine();
			while (str != null) {
				wordlist.add(str);
				str = jbr.readLine();
			}
			jbr.close();
			
			ArrayList<String> firstlist = new ArrayList<String>(); // 1文字目が解と同じ単語の配列
			ArrayList<String> secondlist = new ArrayList<String>(); // 2文字目が解と同じ単語の配列
			for (String word : wordlist) {
				work1 = word.substring(0, 1); // 1文字目を取り出す
				work2 = word.substring(1, 2); // 2文字目を取り出す
				if (C.equals(work1)) { // 1文字目と解が同じなら
					firstlist.add(word); // 配列の要素を足す
				} else if (C.equals(work2)) { // 2文字目と解が同じなら
					secondlist.add(word); // 配列の要素を足す
				}
			}
			// 保存した配列からランダムに文字を取ってくる操作
			Left = secondlist.get(rnd.nextInt(secondlist.size())); // 配列の要素からランダムに取ってくる
			while (true) {
				Up = secondlist.get(rnd.nextInt(secondlist.size()));// 配列の要素からランダムに取ってくる
				if (!(Left.substring(0, 1).equals(Up.substring(0, 1)))) { // 被ってなかったらループを抜ける
					break;
				}
			}
			while (true) {
				Down = firstlist.get(rnd.nextInt(firstlist.size())); // 配列の要素からランダムに取ってくる
				if (!(Left.substring(0, 1).equals(Down.substring(1, 2)))
						&& !(Up.substring(0, 1).equals(Down.substring(1, 2)))) {
					break;
				}
			}
			while (true) {
				Right = firstlist.get(rnd.nextInt(firstlist.size())); // 配列の要素からランダムに取ってくる
				if (!(Down.substring(1, 2).equals(Right.substring(1, 2)))
						&& !(Left.substring(0, 1).equals(Right.substring(1, 2)))
						&& !(Up.substring(0, 1).equals(Right.substring(1, 2)))) { // 被ってなかったらループを抜ける
					break;
				}
			}
			// ラベルに表示する変数に文字を入れる
			L = Left.substring(0, 1);
			U = Up.substring(0, 1);
			D = Down.substring(1, 2);
			R = Right.substring(1, 2);
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	public void easy() {	//かんたん
		difficulty = 0;
		anscnt = 0;
		correct = 0;
		Fiveanswer();
		/*for (String a : fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		System.out.println("");*/
		Questions(fiveans[anscnt]); // 問題の再設定
		txtLabel.setText(anscnt+1+"/5");
		CenterLabel.setText(C);
		LeftLabel.setText(L);
		UpLabel.setText(U);
		RightLabel.setText(R);
		DownLabel.setText(D);
		setTitle("Textvirsion:easy");
		layout.show(cardPanel, "TextPazzle");
	}
	
	public void normal() {	//ふつう
		difficulty = 1;
		anscnt = 0;
		correct = 0;
		Fiveanswer();
		/*for (String a : fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		System.out.println("");*/
		Questions(fiveans[anscnt]); // 問題の再設定
		txtLabel.setText(anscnt+1+"/5");
		CenterLabel.setText(C);
		LeftLabel.setText(L);
		UpLabel.setText(U);
		RightLabel.setText(R);
		DownLabel.setText(D);
		setTitle("Textvirsion:normal");
		layout.show(cardPanel, "TextPazzle");
	}
	
	public void hard() {	//むずかしい
		difficulty = 2;
		anscnt = 0;
		correct = 0;
		Fiveanswer();
		/*for (String a : fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		System.out.println("");*/
		Questions(fiveans[anscnt]); // 問題の再設定
		txtLabel.setText(anscnt+1+"/5");
		CenterLabel.setText(C);
		LeftLabel.setText(L);
		UpLabel.setText(U);
		RightLabel.setText(R);
		DownLabel.setText(D);
		setTitle("Textvirsion:hard");
		layout.show(cardPanel, "TextPazzle");
	}
	
	public void nextdifficulty() {
		/*次の難易度への処理*/
		difficulty++;
		if(difficulty == 3) {
			String[] buttons = { "タイトルへ戻る", "メニューへ戻る", };
			int button = JOptionPane.showOptionDialog(null, "あなたは最高難易度をクリアしました", "おめでとうございます！！！", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			
			if(button == 0) {
				layout.show(cardPanel, "Title");
				
			}else if (button == 1) {
				layout.show(cardPanel, "Menu");
			}	
		}else if(difficulty == 1){
			anscnt = 0;
			correct = 0;
			Fiveanswer();
			/*for (String a : fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
			System.out.println("");*/
			Questions(fiveans[anscnt]); // 問題の再設定
			txtLabel.setText(anscnt+1+"/5");
			CenterLabel.setText(C);
			LeftLabel.setText(L);
			UpLabel.setText(U);
			RightLabel.setText(R);
			DownLabel.setText(D);
			setTitle("Textvirsion:normal");
			layout.show(cardPanel, "TextPazzle");
		}else if(difficulty == 2) {
			anscnt = 0;
			correct = 0;
			Fiveanswer();
			/*for (String a : fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
			System.out.println("");*/
			Questions(fiveans[anscnt]); // 問題の再設定
			txtLabel.setText(anscnt+1+"/5");
			CenterLabel.setText(C);
			LeftLabel.setText(L);
			UpLabel.setText(U);
			RightLabel.setText(R);
			DownLabel.setText(D);
			setTitle("Textvirsion:hard");
			layout.show(cardPanel, "TextPazzle");
		}
				
	}
	
	public void answer() {
		/* 解答の判定 */
		String ans = textField.getText();
		// テキストフィールドを空にする
		textField.setText("");
		if (ans.equals(C)) {
			anscnt++;
			correct++;
			miss = 0;
			
			HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
			String[] buttons = { "次の問題へ", "メニューへ戻る", };
			int button = JOptionPane.showOptionDialog(null, "正解です", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if (anscnt == 5) {
				String[] resultbuttons = { "結果へ" };
				int resultbutton = JOptionPane.showOptionDialog(null, "全問解き終わりました", "結果画面へ", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, resultbuttons, resultbuttons[0]);
				if(resultbutton == 0 || resultbutton == -1) {
					ResultLabel.setText(anscnt + "問中" + correct + "問正解");
					if(difficulty == 0) {
						DifficultyLabel.setText("難易度：かんたん");
					}else if (difficulty == 1) {
						DifficultyLabel.setText("難易度：ふつう");
					}else if (difficulty == 2) {
						DifficultyLabel.setText("難易度：むずかしい");
					}
					layout.show(cardPanel, "result");
					setTitle("Result");
					anscnt = 0;
				}
			}
			if (button == 0 || button == -1) /* 次の問題ボタン */ {

				/* ここから問題を再描画 */
				txtLabel.setText(anscnt+1+"/5");
				Questions(fiveans[anscnt]); // 問題の再設定
				CenterLabel.setText(C);
				LeftLabel.setText(L);
				UpLabel.setText(U);
				RightLabel.setText(R);
				DownLabel.setText(D);
				
			} else if (button == 1) {
				layout.show(cardPanel, "Menu");
			}

		} else {
			miss++;
			if (miss == 3) {
				HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
			}
			String[] buttons = { "解答しなおす", "メニューへ戻る" };
			int button = JOptionPane.showOptionDialog(null, "不正解です ", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE, null, buttons, buttons[0]);

			if (button == 0 || button == -1) {

				if (miss == 3 && anscnt == 4) {
					String[] missresultbuttons = { "結果へ" };
					int missresultbutton = JOptionPane.showOptionDialog(null, "3回間違えました", "警告", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, missresultbuttons, missresultbuttons[0]);
					if(missresultbutton == 0 || missresultbutton == -1) {
						anscnt++;
							ResultLabel.setText(anscnt + "問中" + correct + "問正解");
						if(difficulty == 0) {
							DifficultyLabel.setText("難易度：かんたん");
						}else if (difficulty == 1) {
							DifficultyLabel.setText("難易度：ふつう");
						}else if (difficulty == 2) {
							DifficultyLabel.setText("難易度：むずかしい");
						}
						layout.show(cardPanel, "result");
						setTitle("Result");
						anscnt = 0;
						miss = 0;
					}
				}
				if (miss == 3 && anscnt < 4) {
					String[] missbuttons = { "閉じる" };
					int missbutton = JOptionPane.showOptionDialog(null, "3回間違えました", "警告", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, missbuttons, missbuttons[0]);
					if(missbutton == 0 || missbutton == -1) {
						anscnt++;
						miss = 0;
						// System.out.println("miss3回");
						Questions(fiveans[anscnt]); // 問題の再設定
						CenterLabel.setText(C);
						LeftLabel.setText(L);
						UpLabel.setText(U);
						RightLabel.setText(R);
						DownLabel.setText(D);
						txtLabel.setText(anscnt+1+"/5");
					}
				}

			} else if (button == 1) {
				anscnt = 0;
				miss = 0;
				layout.show(cardPanel, "Menu");
				setTitle("メニュー");
			}
		}
		HideLabel.setVisible(true); // これで画像が見える（答えが見えなくなる）
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
			KeyEvent fKey = new KeyEvent(
				e.getComponent(),
				e.getID(),
				e.getWhen(),
				InputEvent.SHIFT_DOWN_MASK,
				e.getKeyCode(),
				e.getKeyChar());
			event.dispatchEvent(fKey);
			break;
		//デバッグ
		case KeyEvent.VK_F1:
			HideLabel.setVisible(false);
			break;
		// enter
		case KeyEvent.VK_ENTER:
			// easy
			if(EasyButton.equals(event)) {
				easy();
			}
			// normal
			if(NormalButton.equals(event)) {
				normal();
			}
			// hard
			if(HardButton.equals(event)) {
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
			if(MenuButton.equals(event) || GoButton.equals(event)) {
				layout.show(cardPanel, "Menu");
			}
			// Result画面の次の難易度ボタン上でエンター
			if(NextdifficultyButton.equals(event)) {
				nextdifficulty();
			}
			// 終了ボタン上でエンター
			if(ExitButton.equals(event) || EndButton.equals(event)) {
				System.exit(0);
			}	
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
				HideLabel.setVisible(true);
				break;
		}
	}

}
