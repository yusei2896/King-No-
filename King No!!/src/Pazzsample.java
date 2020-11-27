import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author 0H02019 •y“c—Á‰î
 *
 */
public class Pazzsample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private ImageIcon Img=new ImageIcon("C:\\KIC\\2020\\java\\“y.png");
	private JTextField textField;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel QLabel = new JLabel(Img);
		QLabel.setBounds(10, 10, 281, 248);
		contentPane.add(QLabel);
		
		JButton btnNewButton = new JButton("\u89E3\u7B54");
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
