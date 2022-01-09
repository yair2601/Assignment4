import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private static JLabel lblNewLabel_3 = new JLabel("total cost: ");
	public static GUI frame;
	private static JComboBox comboBox = new JComboBox();
	private static JSpinner spinner = new JSpinner();
	public static String url ="C:\\Users\\yair\\Java\\Student.txt";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI.frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		});

	}
	
	

	/**
	 * Create the frame.
	 * @return 
	 */
	public JLabel getlblNewLabel_3() {
		return this.lblNewLabel_3;
	}
	
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
		
		
		spinner.setModel(new SpinnerNumberModel(0.2, 0.1, 0.9, 0.01));
		spinner.setBounds(203, 57, 74, 20);
		contentPane.add(spinner);
		


		
		JLabel lblNewLabel_1 = new JLabel("Number of EDW");
		lblNewLabel_1.setBounds(52, 109, 103, 27);
		contentPane.add(lblNewLabel_1);
		
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox.setSelectedIndex(1);
		comboBox.setBounds(203, 112, 74, 21);
		contentPane.add(comboBox);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(52, 160, 225, 11);
//		progressBar.setMinimum(0);
//		progressBar.setMaximum(CourseInformation.Fatma.getStudents().size());
		
		contentPane.add(progressBar);
		
		JLabel lblNewLabel_2 = new JLabel("test in progress please wait");
		lblNewLabel_2.setBounds(52, 146, 139, 13);
		contentPane.add(lblNewLabel_2);
		
		
		lblNewLabel_3.setBounds(52, 203, 180, 13);
		contentPane.add(lblNewLabel_3);
		
		
		
		JButton ButtonExist = new JButton("Exit");
		ButtonExist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		ButtonExist.setBounds(203, 242, 85, 21);
		contentPane.add(ButtonExist);
		
		JButton ButtonStart = new JButton("Start");
		ButtonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, String> worker= new SwingWorker <Void, String>(){

					@Override
					protected Void doInBackground() throws Exception {
						lblNewLabel_3.setText("total cost: ");
						try {
							new CourseInformation((Double)spinner.getValue(),Integer.parseInt((String) comboBox.getSelectedItem()),url);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lblNewLabel_3.setText("total cost: "+ CourseInformation.Fatma.getSalaryCost());
						return null;
					}
					
				};
				worker.execute();
			}
		});
		ButtonStart.setBounds(52, 243, 85, 20);
		contentPane.add(ButtonStart);
		

		
	}
	
}
