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
import java.awt.Color;


/**
 * @author 0H02019 �y�c����
 *
 */
public class TextPazzsample extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField textField;
	private JLabel LeftLabel;
	private JLabel UpLabel;
	private JLabel CenterLabel;
	private JLabel DownLabel;
	private JLabel RightLabel;
	private JLabel HideLabel;
	
	int diffculty = 0; //難易度選択0:easy 1:normal 2:hard
	JLabel[] Labels = {CenterLabel, LeftLabel, UpLabel, DownLabel, RightLabel};
	String C,L="左",U="上",D="下",R="右";
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
					TextPazzsample frame = new TextPazzsample();
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
		setTitle("Textvirsion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		URL Txturl = this.getClass().getResource("resources/one.txt");
		// ファイル操作
		try {
			
			File file = new File(Txturl.toURI());
			FileReader filereader = new FileReader(file);
			BufferedReader br = new BufferedReader(filereader);
			String str = br.readLine();
			Random rnd = new Random();
			ArrayList<String> wordlist = new ArrayList<String>();//可変配列
			
			//仮
			C ="中";
			String work1,work2;
			//テキストファイルの中身を全部配列に入れる
			while(str != null) {
				wordlist.add(str);
				str = br.readLine();
			}
			br.close();	// ファイルを閉じる
			ArrayList<String> firstlist = new ArrayList<String>();	//1文字目が解と同じ単語の配列
			ArrayList<String> secondlist = new ArrayList<String>();	//2文字目が解と同じ単語の配列
			for(String word: wordlist) {
				work1 = word.substring(0,1);	// 1文字目を取り出す
				work2 = word.substring(1,2);	// 2文字目を取り出す
				if(C.equals(work1)) {		// 1文字目と解が同じなら
					firstlist.add(word);	// 配列の要素を足す
				}else if(C.equals(work2)) {	// 2文字目と解が同じなら
					secondlist.add(word);	// 配列の要素を足す
				}
			}
			
			// 保存した配列からランダムに文字を取ってくる操作
			Left = secondlist.get(rnd.nextInt(secondlist.size()));	// 配列の要素からランダムに取ってくる
			while(true) {
				Up = secondlist.get(rnd.nextInt(secondlist.size()));// 配列の要素からランダムに取ってくる
				if(Left.equals(Up)) {								// 被ったらやり直し
					continue;
				}
				break;	// 被ってないからループを抜ける
			}
			Down = firstlist.get(rnd.nextInt(firstlist.size()));		// 配列の要素からランダムに取ってくる
			while(true) {
				Right = firstlist.get(rnd.nextInt(firstlist.size()));	// 配列の要素からランダムに取ってくる
				if(Down.equals(Right)) {								// 被ったらやり直し
					continue;
				}
				break;	// 被ってないからループを抜ける
			}
			// ラベルに表示する変数に文字を入れる
			L = Left.substring(0,1);
			U = Up.substring(0,1);
			D = Down.substring(1,2);
			R = Right.substring(1,2);
			
		}catch(IOException | URISyntaxException e) {
			System.out.println(e);
		}
		
			
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
		
		CenterLabel = new JLabel("中");
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
		
		/*黒い画像*/
		HideLabel = new JLabel(Hide);
		HideLabel.setBounds(100, 100, 80, 80);
		contentPane.add(HideLabel);
		HideLabel.setVisible(true); //これで画像が見える（答えが見えなくなる）
		
		JButton btnNewButton = new JButton("\u89E3\u7B54");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 解答の判定 */
				String ans = textField.getText();
				if (ans.equals(C)) {
					HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
					JOptionPane.showMessageDialog(null, "正解です", "判定結果", JOptionPane.OK_OPTION);

				} else {
					HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
					JOptionPane.showMessageDialog(null, "不正解です", "判定結果", JOptionPane.WARNING_MESSAGE);

				}
				HideLabel.setVisible(true); //これで画像が見える（答えが見えなくなる）
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
	}
}
