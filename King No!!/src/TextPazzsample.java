import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;

/**
 * @author King no !!
 *
 */
public class TextPazzsample extends JFrame implements KeyListener {
	JPanel cardPanel;
	CardLayout layout;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel card1;
	private JTextField textField;
	private JLabel LeftLabel;
	private JLabel UpLabel;
	private JLabel CenterLabel;
	private JLabel DownLabel;
	private JLabel RightLabel;
	private JLabel HideLabel;

	// 解のテキストURL
	URL easyansurl = this.getClass().getResource("resources/easy.txt");
	URL normalansurl = this.getClass().getResource("resources/normal.txt");
	URL hardansurl = this.getClass().getResource("resources/hard.txt");
	URL[] ansurllist = { easyansurl, normalansurl, hardansurl };
	// 熟語のテキストURL
	URL easyTxturl = this.getClass().getResource("resources/J-easy.txt");
	URL normalTxturl = this.getClass().getResource("resources/J-normal.txt");
	URL hardTxturl = this.getClass().getResource("resources/J-hard.txt");
	URL[] difflist = { easyTxturl, normalTxturl, hardTxturl };

	int diffculty = 0; // 難易度選択0:easy 1:normal 2:hard
	JLabel[] Labels = { CenterLabel, LeftLabel, UpLabel, DownLabel, RightLabel };
	String[] fiveans = new String[5]; // 5つの解を入れる配列

	int anscnt = 0;
	int correct = 0;
	int miss = 0;

	String C, L = "左", U = "上", D = "下", R = "右";
	String Left, Up, Down, Right;

	URL HideimageURL = this.getClass().getResource("resources/84089164_480x480.png");
	private ImageIcon Hide = new ImageIcon(HideimageURL); // 問題を隠している画像
	private JPanel ButtonPanel;
	public JButton MenuButton;
	public JButton NextdifficultyButton;
	public JButton ExitButton;
	private JLabel ResultLabel;
	private JLabel ScoreLabel;
	public JButton answerButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextPazzsample frame = new TextPazzsample();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(640, 360, 480, 360);
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
	public TextPazzsample() {
		anscnt = 0;
		correct = 0;
		miss = 0;
		Fiveanswer();

		for (String a : fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		System.out.println("");

		Questions(fiveans[anscnt]);
		// タイトルの後ろに難易度を表示
		if (diffculty == 0) {
			setTitle("Textvirsion:easy");
		} else if (diffculty == 1) {
			setTitle("Textvirsion:normal");
		} else if (diffculty == 2) {
			setTitle("Textvirsion:hard");
		}

		/* 問題カード */
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		LeftLabel = new JLabel(L);
		LeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LeftLabel.setFont(new Font("MS 明朝", Font.PLAIN, 50));
		LeftLabel.setBounds(10, 100, 80, 80);
		contentPane.add(LeftLabel);

		UpLabel = new JLabel(U);
		UpLabel.setFont(new Font("MS 明朝", Font.PLAIN, 50));
		UpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UpLabel.setBounds(100, 10, 80, 80);
		contentPane.add(UpLabel);

		CenterLabel = new JLabel(C);
		CenterLabel.setForeground(Color.BLACK);
		CenterLabel.setFont(new Font("MS 明朝", Font.PLAIN, 50));
		CenterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CenterLabel.setBounds(100, 100, 80, 80);
		contentPane.add(CenterLabel);

		DownLabel = new JLabel(D);
		DownLabel.setFont(new Font("MS 明朝", Font.PLAIN, 50));
		DownLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DownLabel.setBounds(100, 190, 80, 80);
		contentPane.add(DownLabel);

		RightLabel = new JLabel(R);
		RightLabel.setFont(new Font("MS 明朝", Font.PLAIN, 50));
		RightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RightLabel.setBounds(190, 100, 80, 80);
		contentPane.add(RightLabel);

		/* 黒い画像 */
		HideLabel = new JLabel(Hide);
		HideLabel.setBounds(100, 100, 80, 80);
		contentPane.add(HideLabel);
		HideLabel.setVisible(true); // これで画像が見える（答えが見えなくなる）

		answerButton = new JButton("\u89E3\u7B54");
		answerButton.addKeyListener(this);
		answerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer();
			}
		});
		answerButton.setBounds(317, 202, 89, 21);
		contentPane.add(answerButton);

		textField = new JTextField();
		textField.addKeyListener(this);
		textField.setBounds(317, 173, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel txtLabel = new JLabel("");
		txtLabel.setBounds(317, 82, 89, 70);
		contentPane.add(txtLabel);

		/* 結果カード */
		card1 = new JPanel();
		card1.setLayout(null);

		ButtonPanel = new JPanel();
		ButtonPanel.setBounds(0, 232, 436, 31);
		card1.add(ButtonPanel);

		MenuButton = new JButton("メニューに戻る");
		MenuButton.addKeyListener(this);
		ButtonPanel.add(MenuButton);

		NextdifficultyButton = new JButton("次の難易度へ");
		NextdifficultyButton.addKeyListener(this);
		ButtonPanel.add(NextdifficultyButton);

		ExitButton = new JButton("終了");
		ExitButton.addKeyListener(this);
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		ButtonPanel.add(ExitButton);
		ResultLabel = new JLabel("5問中〇問正解");
		ResultLabel.setBounds(0, 0, 440, 90);
		ResultLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		ResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card1.add(ResultLabel);
		
		ScoreLabel = new JLabel("スコア：");
		ScoreLabel.setBounds(0, 100, 430, 133);
		ScoreLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 30));
		ScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card1.add(ScoreLabel);

		/* 親（cardPanel自身）の定義 */
		cardPanel = new JPanel();
		layout = new CardLayout();
		cardPanel.setLayout(layout);

		cardPanel.add(contentPane, "TextPazzle");
		cardPanel.add(card1, "result");
		
		getContentPane().add(cardPanel, BorderLayout.CENTER); // 最初に表示させるカードの指定（今回なら問題カード）

	}

	public void Fiveanswer() {
		try {
			Random rnd = new Random();
			String answork;
			File ansfile = new File(ansurllist[diffculty].toURI()); // 出題用解答文字ファイル
			FileReader ansfilereader = new FileReader(ansfile);
			BufferedReader ansbr = new BufferedReader(ansfilereader);
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
		} catch (IOException | URISyntaxException e) {
			System.out.println(e);
		}
	}

	public void Questions(String answer) {
		C = answer;
		// System.out.print(anscnt+1+"問目");
		// ファイル操作
		try {
			File jfile = new File(difflist[diffculty].toURI()); // 出題用熟語ファイル
			FileReader jfilereader = new FileReader(jfile);
			BufferedReader jbr = new BufferedReader(jfilereader);
			Random rnd = new Random();
			ArrayList<String> wordlist = new ArrayList<String>();// 可変配列
			String work1, work2;

			// テキストファイルの中身を全部配列に入れる
			String str = jbr.readLine();
			while (str != null) {
				wordlist.add(str);
				str = jbr.readLine();
			}
			jbr.close(); // ファイルを閉じる

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

		} catch (IOException | URISyntaxException e) {
			System.out.println(e);
		}
	}
	
	public void answer() {
		/* 解答の判定 */
		String ans = textField.getText();
		if (ans.equals(C)) {
			anscnt++;
			correct++;
			miss = 0;
			HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
			String[] buttons = { "閉じる", "次の問題へ", "メニューへ戻る", };
			int button = JOptionPane.showOptionDialog(null, "正解です", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if (anscnt == 5) {
				ResultLabel.setText(anscnt + "問中" + correct + "問正解");
				layout.show(cardPanel, "result");
				setTitle("Result");
				anscnt = 0;
			}
			if (button == 0) {

			} else if (button == 1) /* 次の問題ボタン */ {

				/* ここから問題を再描画 */
				Questions(fiveans[anscnt]); // 問題の再設定
				CenterLabel.setText(C);
				LeftLabel.setText(L);
				UpLabel.setText(U);
				RightLabel.setText(R);
				DownLabel.setText(D);
			} else if (button == 2) {
				System.exit(0);
			}

		} else {
			miss++;
			if (miss == 3) {
				HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
			}
			String[] buttons = { "閉じる", "メニューへ戻る" };
			int button = JOptionPane.showOptionDialog(null, "不正解です ", "判定結果", JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE, null, buttons, buttons[0]);

			if (button == 0) {

				if (miss == 3 && anscnt == 4) {
					anscnt++;
					ResultLabel.setText(anscnt + "問中" + correct + "問正解");
					layout.show(cardPanel, "result");
					setTitle("Result");
					anscnt = 0;
					miss = 0;
				}
				if (miss == 3 && anscnt < 4) {
					anscnt++;
					miss = 0;
					// System.out.println("miss3回");
					Questions(fiveans[anscnt]); // 問題の再設定
					CenterLabel.setText(C);
					LeftLabel.setText(L);
					UpLabel.setText(U);
					RightLabel.setText(R);
					DownLabel.setText(D);
				}

			} else if (button == 1) {
				System.exit(0);
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
		// enter
		case KeyEvent.VK_ENTER:
			// 解答ボタン上でエンター
			if(answerButton.equals(event)) {
				answer();
			}
			// テキストフィールド上でエンター
			if(textField.equals(event)) {
				e.setKeyCode(KeyEvent.VK_TAB);
				textField.dispatchEvent(e);
			}
			// 終了ボタン上でエンター
			if(ExitButton.equals(event)) {
				System.exit(0);
			}	
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {	
	}
	@Override
	public void keyReleased(KeyEvent e) {		
	}

}
