import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;

public class GUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("choose P error");
		lblNewLabel.setBounds(52, 53, 103, 27);
		contentPane.add(lblNewLabel);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0.2, 0.1, 0.9, 0.01));
		spinner.setBounds(203, 57, 74, 20);
		contentPane.add(spinner);
		
		JButton ButtonStart = new JButton("Start");
		ButtonStart.setBounds(52, 242, 85, 21);
		contentPane.add(ButtonStart);
		
		JLabel lblNewLabel_1 = new JLabel("Number of EDW");
		lblNewLabel_1.setBounds(52, 109, 103, 27);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox.setSelectedIndex(1);
		comboBox.setBounds(203, 112, 74, 21);
		contentPane.add(comboBox);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(52, 160, 225, 11);
		contentPane.add(progressBar);
		
		JLabel lblNewLabel_2 = new JLabel("test in progress please wait");
		lblNewLabel_2.setBounds(52, 146, 139, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("total cost: ");
		lblNewLabel_3.setBounds(52, 203, 180, 13);
		contentPane.add(lblNewLabel_3);
		
		JButton ButtonExist = new JButton("Exit");
		ButtonExist.setBounds(203, 242, 85, 21);
		contentPane.add(ButtonExist);
	}
}
