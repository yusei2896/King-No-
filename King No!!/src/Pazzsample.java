import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

/**
 * 
 */

public class Pazzsample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;



	URL HideimageURL = this.getClass().getResource("resources/84089164_480x480.png");
	private ImageIcon Hide = new ImageIcon(HideimageURL); // 問題を隠している画像
	URL QimageURL = this.getClass().getResource("resources/Q.png");
	private ImageIcon Img = new ImageIcon(QimageURL); // 問題の画像
	private JTextField textField;
	private JLabel HideLabel;
	private JButton Hint1Button;
	private JButton Hint2Button;
	private JLabel DispLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pazzsample frame = new Pazzsample();
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
	public Pazzsample() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/* 黒い画像 */
		HideLabel = new JLabel(Hide);
		HideLabel.setBounds(115, 98, 70, 60);
		contentPane.add(HideLabel);
		HideLabel.setVisible(true); // これで画像が見える（答えが見えなくなる）
		JLabel QLabel = new JLabel(Img);
		QLabel.setBounds(10, 10, 281, 248);
		contentPane.add(QLabel);

		JButton AnsButton = new JButton("\u89E3\u7B54");
		AnsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 解答の判定 */
				String ans = textField.getText();
				if (ans.equals("土")) {
					HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
					JOptionPane.showMessageDialog(null, "正解です", "判定結果", JOptionPane.OK_OPTION);

				} else {
					HideLabel.setVisible(false); // これで画像が見えなくなる（答えが見える）
					JOptionPane.showMessageDialog(null, "不正解です", "判定結果", JOptionPane.WARNING_MESSAGE);

				}
				HideLabel.setVisible(true); // これで画像が見える（答えが見えなくなる）
				JLabel Qlabel = new JLabel(Img);
				QLabel.setBounds(10, 10, 281, 248);
				contentPane.add(QLabel);
			}
		});
		AnsButton.setBounds(317, 202, 89, 21);
		contentPane.add(AnsButton);

		Hint1Button = new JButton("\u30D2\u30F3\u30C81");
		Hint1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DispLabel.setText("身近なもの");
			}
		});
		Hint1Button.setBounds(317, 20, 89, 21);
		contentPane.add(Hint1Button);

		textField = new JTextField();
		textField.setBounds(317, 173, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		Hint2Button = new JButton("\u30D2\u30F3\u30C82");
		Hint2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DispLabel.setText("茶色");
			}
		});
		Hint2Button.setBounds(317, 51, 89, 21);
		contentPane.add(Hint2Button);

		DispLabel = new JLabel("\u30D2\u30F3\u30C8\u306E\u5185\u5BB9");
		DispLabel.setBounds(317, 82, 89, 70);
		contentPane.add(DispLabel);
	}
}
