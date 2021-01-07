import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Result extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton BacktoMenuButton;
	private JButton ExitButton;
	private JButton NextdifficultyButton;
	private JLabel ResultLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Result frame = new Result();
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
	public Result() {
		setTitle("結果画面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 854, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		BacktoMenuButton = new JButton("メニューに戻る");
		panel.add(BacktoMenuButton);
		
		NextdifficultyButton = new JButton("次の難易度へ");
		panel.add(NextdifficultyButton);
		
		ExitButton = new JButton("終了");
		panel.add(ExitButton);
		
		ResultLabel = new JLabel("〇問中〇問正解");
		ResultLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 40));
		ResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(ResultLabel, BorderLayout.CENTER);
	}

}
