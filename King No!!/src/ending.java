


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ending extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel nanidopanel;
	private JButton EasyButton;
	private JButton NormalButton;
	private JButton HardButton;
	private JPanel EndPanel;
	private JButton EndButton;
	private JPanel panel;
	private JLabel DispLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ending frame = new ending();
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
	public ending() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(20, 20));
		setContentPane(contentPane);
		
		nanidopanel = new JPanel();
		FlowLayout fl_nanidopanel = (FlowLayout) nanidopanel.getLayout();
		fl_nanidopanel.setVgap(70);
		fl_nanidopanel.setHgap(30);
		contentPane.add(nanidopanel, BorderLayout.CENTER);
		
		EasyButton = new JButton("\u304B\u3093\u305F\u3093");
		EasyButton.setBackground(Color.WHITE);
		EasyButton.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 18));
		nanidopanel.add(EasyButton);
		
		NormalButton = new JButton("\u3075\u3064\u3046");
		NormalButton.setBackground(Color.WHITE);
		NormalButton.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 18));
		nanidopanel.add(NormalButton);
		
		HardButton = new JButton("\u3080\u305A\u304B\u3057\u3044");
		HardButton.setBackground(Color.WHITE);
		HardButton.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 18));
		HardButton.setHorizontalAlignment(SwingConstants.RIGHT);
		nanidopanel.add(HardButton);
		
		EndPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) EndPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(EndPanel, BorderLayout.SOUTH);
		
		EndButton = new JButton("\u7D42\u4E86");
		EndButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		EndButton.setBackground(Color.BLACK);
		EndButton.setForeground(Color.WHITE);
		EndButton.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 14));
		EndButton.setHorizontalAlignment(SwingConstants.LEFT);
		EndPanel.add(EndButton);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		DispLabel = new JLabel("");
		panel.add(DispLabel);
	}

}
