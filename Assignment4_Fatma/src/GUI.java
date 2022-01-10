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
	public static String url ="C:\\Users\\yair2\\Java\\Student3.txt";
	private static JLabel lblNewLabel_2 = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {//main
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

	public GUI() {//GUI constructor
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
		ButtonStart.addActionListener(new ActionListener() {//what will happened if the start button will pressed
			public void actionPerformed(ActionEvent e) {
				//in this code block we create swingworker to deal with the label change during the program
				//the gui thered is ETD wich mean it can do thing if it busy the worker is a soultion for the problem
				SwingWorker<Void, String> worker= new SwingWorker <Void, String>(){

					@Override
					protected Void doInBackground() throws Exception {
						lblNewLabel_3.setText("total cost: ");
						lblNewLabel_2.setText("Test started please wait...");
						try {
							new CourseInformation((Double)spinner.getValue(),Integer.parseInt((String) comboBox.getSelectedItem()),url);
						} catch (NumberFormatException e) {
							
							e.printStackTrace();
						}
						lblNewLabel_2.setText("");
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
