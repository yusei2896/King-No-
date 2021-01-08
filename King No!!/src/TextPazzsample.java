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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

/**
 * @author King no !!
 *
 */
public class TextPazzsample extends JFrame {
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
	int anscnt=0;
	int correct = 0;
	int miss = 0;
	String C, L = "左", U = "上", D = "下", R = "右";
	String Left, Up, Down, Right;

	URL HideimageURL = this.getClass().getResource("resources/84089164_480x480.png");
	private ImageIcon Hide = new ImageIcon(HideimageURL); // 問題を隠している画像
	private JPanel ButtonPanel;
	private JButton MenuButton;
	private JButton NextdifficultyButton;
	private JButton ExitButton;
	private JLabel lblNewLabel_1;
	
	

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
		//for(String a:fiveans) {System.out.print(a);} // スラッシュを消すとコンソールに解を表示
		Questions(fiveans[anscnt]);
		// タイトルの後ろに難易度を表示
		if (diffculty == 0) {
			setTitle("Textvirsion:easy");
		} else if (diffculty == 1) {
			setTitle("Textvirsion:normal");
		} else if (diffculty == 2) {
			setTitle("Textvirsion:hard");
		}
		
		/*問題カード*/
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
		
		JButton btnNewButton = new JButton("\u89E3\u7B54");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					if(anscnt == 5) {
						anscnt = 0;
						lblNewLabel_1.setText("5問中"+correct+"問正解");
						layout.show(cardPanel, "result");
						setTitle("Result");
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
					if(miss == 3) {
						HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
					}
					String[] buttons = { "閉じる", "メニューへ戻る" };
					int button = JOptionPane.showOptionDialog(null, "不正解です ", "判定結果", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, buttons, buttons[0]);
					if (button == 0) {
						if(miss == 3 && anscnt == 4) {	
							anscnt = 0;
							miss = 0;
							lblNewLabel_1.setText("5問中"+correct+"問正解");
							layout.show(cardPanel, "result");
							setTitle("Result");
						}
						if(miss == 3 &&  anscnt < 4) {
							anscnt++;
							miss = 0;
							//System.out.println("miss3回");
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
		});
		btnNewButton.setBounds(317, 202, 89, 21);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u30D2\u30F3\u30C81");
		btnNewButton_1.setBounds(317, 20, 89, 21);
		contentPane.add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(317, 173, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_2 = new JButton("\u30D2\u30F3\u30C82");
		btnNewButton_2.setBounds(317, 51, 89, 21);
		contentPane.add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("\u30D2\u30F3\u30C8\u306E\u5185\u5BB9");
		lblNewLabel.setBounds(317, 82, 89, 70);
		contentPane.add(lblNewLabel);
		
		/*結果カード*/
		card1 = new JPanel();
		
	    card1.setLayout(new BorderLayout(0, 0));
	    
	    ButtonPanel = new JPanel();
	    card1.add(ButtonPanel, BorderLayout.SOUTH);
	    
	    MenuButton = new JButton("メニューに戻る");
	    ButtonPanel.add(MenuButton);
	    
	    NextdifficultyButton = new JButton("次の難易度へ");
	    ButtonPanel.add(NextdifficultyButton);
	    
	    ExitButton = new JButton("終了");
	    ExitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		System.exit(0);
	    	}
	    });
	    ButtonPanel.add(ExitButton);
	    
	    lblNewLabel_1 = new JLabel("5問中〇問正解");
	    lblNewLabel_1.setFont(new Font("MS UI Gothic", Font.BOLD, 20));
	    lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	    card1.add(lblNewLabel_1, BorderLayout.CENTER);
	    
	    
	    /*親（cardPanel自身）の定義*/
	    cardPanel = new JPanel();
	    layout = new CardLayout();
	    cardPanel.setLayout(layout);

	    cardPanel.add(contentPane, "TextPazzle");
	    cardPanel.add(card1, "result");
	    
	    getContentPane().add(cardPanel, BorderLayout.CENTER); //最初に表示させるカードの指定（今回なら問題カード）

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
				for(int j = 0; j < i; j++) {
					if (!(fiveans[j].equals(answork))) { // 被ってなかったらループを抜ける
					}else {
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
		//System.out.print(anscnt+1+"問目");
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
			while(true) {
				Down = firstlist.get(rnd.nextInt(firstlist.size())); // 配列の要素からランダムに取ってくる
				if(!(Left.substring(0, 1).equals(Down.substring(1, 2))) && !(Up.substring(0, 1).equals(Down.substring(1, 2)))) {
					break;
				}
			}
			while (true) {
				Right = firstlist.get(rnd.nextInt(firstlist.size())); // 配列の要素からランダムに取ってくる
				if (!(Down.substring(1, 2).equals(Right.substring(1, 2))) && !(Left.substring(0, 1).equals(Right.substring(1, 2)))&& !(Up.substring(0, 1).equals(Right.substring(1, 2)))) { // 被ってなかったらループを抜ける
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

}
