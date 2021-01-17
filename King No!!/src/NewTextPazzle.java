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
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Rectangle;
import java.net.MalformedURLException;
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
 * JAR用
 */
public class NewTextPazzle extends JFrame implements KeyListener {
	JPanel card_panel;
	CardLayout layout;
	private static final long serialVersionUID = 1L;
	private JPanel content_pane,result_card,menu_card,title_card,button_panel,nanido_panel,end_panel,panel;
	private JTextField text_field;
	private JLabel left_label,up_label,center_label,down_label,right_label,hide_label,result_label,score_label,disp_label,difficulty_label,txt_Label;
	public JButton menu_button,next_difficulty_button,exit_button,answer_button,easy_button,normal_button,hard_button,end_button,go_button;

	int difficulty = 0; // 難易度選択0:easy 1:normal 2:hard
	JLabel[] labels = { center_label, left_label, up_label, down_label, right_label };
	String[] five_answers = new String[5]; // 5つの解を入れる配列

	int ans_cnt = 0;
	int correct = 0;
	int miss = 0;
	int score = 10000;

	String center, left = "左", up = "上", down = "下", right = "右";
	String j_left, j_up, j_down, j_right;

	URL hide_imageURL = this.getClass().getResource("resources/84089164_480x480.png");
	private ImageIcon Hide = new ImageIcon(hide_imageURL); // 問題を隠している画像

	URL TitleBGM = this.getClass().getResource("resources/MusMus-STLP-006.wav");
	URL MenuBGM = this.getClass().getResource("resources/MusMus-BGM-004.wav");
	URL QBGM = this.getClass().getResource("resources/MusMus-BGM-003.wav");
	URL ResultBGM = this.getClass().getResource("resources/MusMus-STLP-008.wav");
	Clip Menuclip = null;
	Clip Qclip = null;
	static Clip Titleclip = null;
	Clip Resultclip = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewTextPazzle frame = new NewTextPazzle();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(50,50,960,540);
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
		
		ans_cnt = 0;
		correct = 0;
		miss = 0;
		
		// タイトルの後ろに難易度を表示
		if (difficulty == 0) {
			setTitle("Textvirsion:easy");
		} else if (difficulty == 1) {
			setTitle("Textvirsion:normal");
		} else if (difficulty == 2) {
			setTitle("Textvirsion:hard");
		}
		/*タイトルカード*/
		title_card = new JPanel();
		setTitle("タイトル");
		title_card.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// スタートボタン
		go_button = new JButton("click to start");
		go_button.setFont(new Font("MS UI Gothic", Font.PLAIN, 24));
		go_button.setBounds(371, 368, 207, 57);
		go_button.addKeyListener(this);
		go_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// メニュー画面へ移る
				TiBGMStop();
				MeBGMStart();
				layout.show(card_panel,"Menu" );
			}
		});
		title_card.setLayout(null);
		title_card.add(go_button);
		
		//　タイトル
		JLabel TitleLabel = new JLabel("虫食い!!漢字クロス");
		TitleLabel.setBounds(267, 87, 437, 94);
		TitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 42));
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		title_card.add(TitleLabel);
		
		
		/*メニューカード*/
		menu_card = new JPanel();
		setTitle("メニュー");
		menu_card.setBorder(new EmptyBorder(5, 5, 5, 5));
		menu_card.setLayout(new BorderLayout(20, 20));
		
		nanido_panel = new JPanel();
		FlowLayout fl_nanidopanel = (FlowLayout) nanido_panel.getLayout();
		fl_nanidopanel.setVgap(70);
		fl_nanidopanel.setHgap(30);
		menu_card.add(nanido_panel, BorderLayout.CENTER);
		
		easy_button = new JButton("\u304B\u3093\u305F\u3093");
		easy_button.addKeyListener(this);
		easy_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//難易度かんたんへ
				MeBGMStop();
				QBGMStart();
				easy();
			}
		});
		easy_button.setBackground(Color.WHITE);
		easy_button.setFont(new Font("ＭＳ 明朝", Font.BOLD, 25));
		nanido_panel.add(easy_button);
		
		normal_button = new JButton("\u3075\u3064\u3046");
		normal_button.addKeyListener(this);
		normal_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//難易度ふつうへ
				MeBGMStop();
				QBGMStart();
				normal();
			}
		});
		normal_button.setBackground(Color.WHITE);
		normal_button.setFont(new Font("ＭＳ 明朝", Font.BOLD, 25));
		nanido_panel.add(normal_button);
		
		hard_button = new JButton("\u3080\u305A\u304B\u3057\u3044");
		hard_button.addKeyListener(this);
		hard_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//難易度むずかしいへ
				MeBGMStop();
				QBGMStart();
				hard();
			}
		});
		hard_button.setBackground(Color.WHITE);
		hard_button.setFont(new Font("ＭＳ 明朝", Font.BOLD, 25));
		hard_button.setHorizontalAlignment(SwingConstants.RIGHT);
		nanido_panel.add(hard_button);
		
		end_panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) end_panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		menu_card.add(end_panel, BorderLayout.SOUTH);
		
		end_button = new JButton("\u7D42\u4E86");
		end_button.addKeyListener(this);
		end_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		end_button.setBackground(Color.BLACK);
		end_button.setForeground(Color.WHITE);
		end_button.setFont(new Font("Dialog", Font.BOLD, 20));
		end_button.setHorizontalAlignment(SwingConstants.LEFT);
		end_panel.add(end_button);
		
		panel = new JPanel();
		menu_card.add(panel, BorderLayout.NORTH);
		
		disp_label = new JLabel("");
		panel.add(disp_label);
		
		
		/* 問題カード */
		content_pane = new JPanel();
		content_pane.setBounds(new Rectangle(0, 0, 960, 540));
		content_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		content_pane.setLayout(null);

		left_label = new JLabel(left);
		left_label.setHorizontalAlignment(SwingConstants.CENTER);
		left_label.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		left_label.setBounds(75, 170, 160, 160);
		content_pane.add(left_label);

		up_label = new JLabel(up);
		up_label.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		up_label.setHorizontalAlignment(SwingConstants.CENTER);
		up_label.setBounds(248, 0, 160, 160);
		content_pane.add(up_label);

		center_label = new JLabel(center);
		center_label.setForeground(Color.BLACK);
		center_label.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		center_label.setHorizontalAlignment(SwingConstants.CENTER);
		center_label.setBounds(248, 170, 160, 160);
		content_pane.add(center_label);

		down_label = new JLabel(down);
		down_label.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		down_label.setHorizontalAlignment(SwingConstants.CENTER);
		down_label.setBounds(248, 343, 160, 160);
		content_pane.add(down_label);

		right_label = new JLabel(right);
		right_label.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		right_label.setHorizontalAlignment(SwingConstants.CENTER);
		right_label.setBounds(420, 170, 160, 160);
		content_pane.add(right_label);

		/* 黒い画像 */
		hide_label = new JLabel(Hide);
		hide_label.setBounds(248, 170, 160, 160);
		content_pane.add(hide_label);
		hide_label.setVisible(true); // これで画像が見える（答えが見えなくなる）

		answer_button = new JButton("\u89E3\u7B54");
		answer_button.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		answer_button.addKeyListener(this);
		answer_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer();
			}
		});
		answer_button.setBounds(752, 429, 130, 45);
		content_pane.add(answer_button);

		text_field = new JTextField();
		text_field.addKeyListener(this);
		text_field.setFont(new Font("MS UI Gothic", Font.BOLD, 25));
		text_field.setBounds(750, 325, 130, 45);
		content_pane.add(text_field);
		text_field.setColumns(10);

		txt_Label = new JLabel(ans_cnt+1+"/5");
		txt_Label.setHorizontalAlignment(SwingConstants.CENTER);
		txt_Label.setFont(new Font("ＭＳ 明朝", Font.BOLD, 80));
		txt_Label.setBounds(661, 21, 275, 196);
		content_pane.add(txt_Label);

		/* 結果カード */
		result_card = new JPanel();
		result_card.setLayout(new BorderLayout(0, 0));

		button_panel = new JPanel();
		result_card.add(button_panel, BorderLayout.SOUTH);

		menu_button = new JButton("メニューに戻る");
		menu_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReBGMStop();
				MeBGMStart();
				layout.show(card_panel, "Menu");
			}
		});
		menu_button.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		menu_button.addKeyListener(this);
		button_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		button_panel.add(menu_button);

		next_difficulty_button = new JButton("次の難易度へ");
		next_difficulty_button.addKeyListener(this);
		next_difficulty_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReBGMStop();
				QBGMStart();
				next_difficulty();
			}
		});
		next_difficulty_button.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		next_difficulty_button.addKeyListener(this);
		button_panel.add(next_difficulty_button);

		exit_button = new JButton("終了");
		exit_button.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
		exit_button.addKeyListener(this);
		exit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button_panel.add(exit_button);
		result_label = new JLabel("5問中〇問正解");
		result_label.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 80));
		result_label.setHorizontalAlignment(SwingConstants.CENTER);
		result_card.add(result_label, BorderLayout.NORTH);
		
		score_label = new JLabel("スコア：");
		score_label.setHorizontalAlignment(SwingConstants.CENTER);
		score_label.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		result_card.add(score_label, BorderLayout.CENTER);
		
		difficulty_label = new JLabel("難易度：");
		difficulty_label.setFont(new Font("ＭＳ 明朝", Font.BOLD | Font.ITALIC, 60));
		difficulty_label.setHorizontalAlignment(SwingConstants.CENTER);
		result_card.add(difficulty_label, BorderLayout.WEST);

		/* 親（cardPanel自身）の定義 */
		card_panel = new JPanel();
		layout = new CardLayout();
		card_panel.setLayout(layout);
		
		card_panel.add(title_card,"Title");
		card_panel.add(menu_card, "Menu");
		card_panel.add(content_pane, "TextPazzle");
		card_panel.add(result_card, "result");
		
		
		getContentPane().add(card_panel, BorderLayout.CENTER); // 最初に表示させるカードの指定（今回なら問題カード）
		
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
			five_answers[0] = answork;
			for (int i = 1; i < 5; i++) {
				answork = anslist.get(rnd.nextInt(anslist.size()));
				for (int j = 0; j < i; j++) {
					if (!(five_answers[j].equals(answork))) { // 被ってなかったらループを抜ける
					} else {
						answork = anslist.get(rnd.nextInt(anslist.size()));
						continue;
					}
				}
				five_answers[i] = answork;
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	// 出題する熟語を取り出す
	public void questions(String answer) {
		center = answer;
		// System.out.print(ans_cnt+1+"問目");
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
			j_left = second_list.get(rnd.nextInt(second_list.size())); // 配列の要素からランダムに取ってくる
			while (true) {
				j_up = second_list.get(rnd.nextInt(second_list.size()));// 配列の要素からランダムに取ってくる
				if (!(j_left.substring(0, 1).equals(j_up.substring(0, 1)))) { 	// 左の字と被ってないか
					break;														// 被ってなかったらループを抜ける
				}
			}
			while (true) {
				j_down = first_list.get(rnd.nextInt(first_list.size())); // 配列の要素からランダムに取ってくる
				if (!(j_left.substring(0, 1).equals(j_down.substring(1, 2))) 		// 左の字と被ってないか
						&& !(j_up.substring(0, 1).equals(j_down.substring(1, 2)))) {// 上の字と被ってないか
					break;															// 被ってなかったらループを抜ける
				}
			}
			while (true) {
				j_right = first_list.get(rnd.nextInt(first_list.size())); // 配列の要素からランダムに取ってくる
				if (!(j_down.substring(1, 2).equals(j_right.substring(1, 2)))			// 下の字と被ってないか
						&& !(j_left.substring(0, 1).equals(j_right.substring(1, 2)))	// 左の字と被ってないか
						&& !(j_up.substring(0, 1).equals(j_right.substring(1, 2)))) { 	// 上の字と被ってないか
					break;																// 被ってなかったらループを抜ける
				}
			}
			// ラベルに表示する変数に文字を入れる
			left = j_left.substring(0, 1);
			up = j_up.substring(0, 1);
			down = j_down.substring(1, 2);
			right = j_right.substring(1, 2);
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	public void easy() {	//　かんたん
		difficulty = 0;
		ans_cnt = 0;
		correct = 0;
		score = 10000;
		Fiveanswer();
		/*for (String a : five_answers) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		System.out.println("");*/
		questions(five_answers[ans_cnt]); // 問題の再設定
		txt_Label.setText(ans_cnt+1+"/5");
		center_label.setText(center);
		left_label.setText(left);
		up_label.setText(up);
		right_label.setText(right);
		down_label.setText(down);
		setTitle("Textvirsion:easy");
		layout.show(card_panel, "TextPazzle");
	}
	
	public void normal() {	//　ふつう
		difficulty = 1;
		ans_cnt = 0;
		correct = 0;
		score = 10000;
		Fiveanswer();
		/*for (String a : five_answers) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		System.out.println("");*/
		questions(five_answers[ans_cnt]); // 問題の再設定
		txt_Label.setText(ans_cnt+1+"/5");
		center_label.setText(center);
		left_label.setText(left);
		up_label.setText(up);
		right_label.setText(right);
		down_label.setText(down);
		setTitle("Textvirsion:normal");
		layout.show(card_panel, "TextPazzle");
	}
	
	public void hard() {	//　むずかしい
		difficulty = 2;
		ans_cnt = 0;
		correct = 0;
		score = 10000;
		Fiveanswer();
		/*for (String a : five_answers) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		System.out.println("");*/
		questions(five_answers[ans_cnt]); // 問題の再設定
		txt_Label.setText(ans_cnt+1+"/5");
		center_label.setText(center);
		left_label.setText(left);
		up_label.setText(up);
		right_label.setText(right);
		down_label.setText(down);
		setTitle("Textvirsion:hard");
		layout.show(card_panel, "TextPazzle");
	}
	
	public void next_difficulty() {
		/*次の難易度への処理*/
		score = 10000;
		difficulty++;
		if(difficulty == 3) {
			String[] buttons = { "タイトルへ戻る", "メニューへ戻る", };
			int button = JOptionPane.showOptionDialog(null, "あなたは最高難易度をクリアしました", "おめでとうございます！！！", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			
			if(button == 0) {
				ReBGMStop();
				TiBGMStart();
				layout.show(card_panel, "Title");
				
			}else if (button == 1) {
				ReBGMStop();
				MeBGMStart();
				layout.show(card_panel, "Menu");
			}	
		}else if(difficulty == 1){
			ans_cnt = 0;
			correct = 0;
			Fiveanswer();
			/*for (String a : five_answers) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
			System.out.println("");*/
			questions(five_answers[ans_cnt]); // 問題の再設定
			txt_Label.setText(ans_cnt+1+"/5");
			center_label.setText(center);
			left_label.setText(left);
			up_label.setText(up);
			right_label.setText(right);
			down_label.setText(down);
			setTitle("Textvirsion:normal");
			layout.show(card_panel, "TextPazzle");
		}else if(difficulty == 2) {
			ans_cnt = 0;
			correct = 0;
			Fiveanswer();
			/*for (String a : five_answers) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
			System.out.println("");*/
			questions(five_answers[ans_cnt]); // 問題の再設定
			txt_Label.setText(ans_cnt+1+"/5");
			center_label.setText(center);
			left_label.setText(left);
			up_label.setText(up);
			right_label.setText(right);
			down_label.setText(down);
			setTitle("Textvirsion:hard");
			layout.show(card_panel, "TextPazzle");
		}
				
	}
	
	public void answer() {
		/* 解答の判定 */
		String ans = text_field.getText();
		// テキストフィールドを空にする
		text_field.setText("");
		if (ans.equals(center)) {
			ans_cnt++;
			correct++;
			miss = 0;
			
			hide_label.setVisible(false); // これで画像が見えなくなる（答えが見える）
			String[] buttons = { "次の問題へ", "メニューへ戻る", };
			int button = JOptionPane.showOptionDialog(null, "正解です", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if (ans_cnt == 5) {
				String[] resultbuttons = { "結果へ" };
				int resultbutton = JOptionPane.showOptionDialog(null, "全問解き終わりました", "結果画面へ", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, resultbuttons, resultbuttons[0]);
				if(resultbutton == 0 || resultbutton == -1) {
					result_label.setText(ans_cnt + "問中" + correct + "問正解");
					if(difficulty == 0) {
						difficulty_label.setText("難易度：かんたん");
					}else if (difficulty == 1) {
						difficulty_label.setText("難易度：ふつう");
					}else if (difficulty == 2) {
						difficulty_label.setText("難易度：むずかしい");
					}
					QBGMStop();
					ReBGMStart();
					score_label.setText("スコア：" + score);
					layout.show(card_panel, "result");
					setTitle("Result");
					ans_cnt = 0;
				}
			}
			if (button == 0 || button == -1) /* 次の問題ボタン */ {

				/* ここから問題を再描画 */
				txt_Label.setText(ans_cnt+1+"/5");
				questions(five_answers[ans_cnt]); // 問題の再設定
				center_label.setText(center);
				left_label.setText(left);
				up_label.setText(up);
				right_label.setText(right);
				down_label.setText(down);
				
			} else if (button == 1) {
				QBGMStop();
				MeBGMStart();
				layout.show(card_panel, "Menu");
			}

		} else {
			miss++;
			if (miss == 1) {
				score -= 250;
			} else if (miss == 2) {
				score -= 750;
			} else if (miss == 3) {
				score -= 1000;
				hide_label.setVisible(false); // これで画像が見えなくなる（答えが見える）
			}
			String[] buttons = { "解答しなおす", "メニューへ戻る" };
			int button = JOptionPane.showOptionDialog(null, "不正解です ", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE, null, buttons, buttons[0]);

			if (button == 0 || button == -1) {

				if (miss == 3 && ans_cnt == 4) {
					String[] missresultbuttons = { "結果へ" };
					int missresultbutton = JOptionPane.showOptionDialog(null, "3回間違えました", "警告", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, missresultbuttons, missresultbuttons[0]);
					if(missresultbutton == 0 || missresultbutton == -1) {
						ans_cnt++;
							result_label.setText(ans_cnt + "問中" + correct + "問正解");
						if(difficulty == 0) {
							difficulty_label.setText("難易度：かんたん");
						}else if (difficulty == 1) {
							difficulty_label.setText("難易度：ふつう");
						}else if (difficulty == 2) {
							difficulty_label.setText("難易度：むずかしい");
						}
						QBGMStop();
						ReBGMStart();
						score_label.setText("スコア：" + score);
						layout.show(card_panel, "result");
						setTitle("Result");
						ans_cnt = 0;
						miss = 0;
					}
				}
				if (miss == 3 && ans_cnt < 4) {
					String[] missbuttons = { "閉じる" };
					int missbutton = JOptionPane.showOptionDialog(null, "3回間違えました", "警告", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, missbuttons, missbuttons[0]);
					if(missbutton == 0 || missbutton == -1) {
						ans_cnt++;
						miss = 0;
						// System.out.println("miss3回");
						questions(five_answers[ans_cnt]); // 問題の再設定
						center_label.setText(center);
						left_label.setText(left);
						up_label.setText(up);
						right_label.setText(right);
						down_label.setText(down);
						txt_Label.setText(ans_cnt+1+"/5");
					}
				}

			} else if (button == 1) {
				ans_cnt = 0;
				miss = 0;
				QBGMStop();
				MeBGMStart();
				layout.show(card_panel, "Menu");
				setTitle("メニュー");
			}
		}
		hide_label.setVisible(true); // これで画像が見える（答えが見えなくなる）
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
	 * 次回再生時にその残りを再生してしまうため、呼び出しています。*/
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
			hide_label.setVisible(false);
			break;
		// enter
		case KeyEvent.VK_ENTER:
			// easy
			if(easy_button.equals(event)) {
				easy();
			}
			// normal
			if(normal_button.equals(event)) {
				normal();
			}
			// hard
			if(hard_button.equals(event)) {
				hard();
			}
			// 解答ボタン上でエンター
			if(answer_button.equals(event)) {
				answer();
				// タブに設定してイベント発生
				e.setKeyCode(KeyEvent.VK_TAB);
				event.dispatchEvent(e);
			}
			// テキストフィールド上でエンター
			if(text_field.equals(event)) {
				e.setKeyCode(KeyEvent.VK_TAB);
				text_field.dispatchEvent(e);
			}
			// Title画面とResult画面でメニューボタン上でエンター
			if(menu_button.equals(event) || go_button.equals(event)) {
				layout.show(card_panel, "Menu");
			}
			// Result画面の次の難易度ボタン上でエンター
			if(next_difficulty_button.equals(event)) {
				next_difficulty();
			}
			// 終了ボタン上でエンター
			if(exit_button.equals(event) || end_button.equals(event)) {
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
				hide_label.setVisible(true);
				break;
		}
	}

}
