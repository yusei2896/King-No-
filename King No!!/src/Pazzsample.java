import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */

/**
 * @author 0H02019 �y�c����
 *
 */
public class Pazzsample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private ImageIcon Img = new ImageIcon("C:\\KIC\\2020\\java\\�y.png"); // ���̉摜
	private ImageIcon Hide = new ImageIcon("C:\\KIC\\2020\\java\\84089164_480x480.png"); // �����B���Ă�摜
	private JTextField textField;
	private JLabel HideLabel;

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

		/* �����摜 */
		HideLabel = new JLabel(Hide);
		HideLabel.setBounds(115, 98, 70, 60);
		contentPane.add(HideLabel);
		HideLabel.setVisible(true); // ����ŉ摜��������i�����������Ȃ��Ȃ�j
		JLabel QLabel = new JLabel(Img);
		QLabel.setBounds(10, 10, 281, 248);
		contentPane.add(QLabel);

		JButton btnNewButton = new JButton("\u89E3\u7B54");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* �𓚂̔��� */
				String ans = textField.getText();
				if (ans.equals("�y")) {
					HideLabel.setVisible(false); // ����ŉ摜�������Ȃ��Ȃ�i������������j
					JOptionPane.showMessageDialog(null, "�����ł�", "���茋��", JOptionPane.OK_OPTION);

				} else {
					HideLabel.setVisible(false); // ����ŉ摜�������Ȃ��Ȃ�i������������j
					JOptionPane.showMessageDialog(null, "�s�����ł�", "���茋��", JOptionPane.WARNING_MESSAGE);

				}
				HideLabel.setVisible(true); // ����ŉ摜��������i�����������Ȃ��Ȃ�j
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
