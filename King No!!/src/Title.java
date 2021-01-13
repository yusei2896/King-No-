

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Title extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton GoButton;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Title frame = new Title();
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
	public Title() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GoButton = new JButton("click to start");
		GoButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 24));
		GoButton.setBounds(371, 368, 207, 57);
		GoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(GoButton);
		
		JLabel TitleLabel = new JLabel("虫食い!!漢字クロス");
		TitleLabel.setBounds(267, 87, 437, 94);
		TitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 42));
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(TitleLabel);
	}
}
