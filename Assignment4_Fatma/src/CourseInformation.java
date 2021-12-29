
import java.io.*;
import java.util.*;

public class CourseInformation {
	private Vector<Student> students;
	private Vector<TeachingAssistant> teachingAssistants;
	private Vector<Proctor> proctors;
	private Lecturer lecturer;
	private Vector<ExerciseChecker> exerciseCheckers;
	private Vector<IEMSecretary> IEMSecretary;
	private Vector<ExamsDepartmentWorker> examsDepartmentWorker;
	//private Vector<Queue<?>> queus;
	private Queue<Student> studentQueue;

	public CourseInformation() throws IOException {
		TeachingAssistant Lior = new TeachingAssistant();
		TeachingAssistant Maya = new TeachingAssistant();
		//		this.queus = new Vector<Queue<?>>();
		this.students = new Vector<Student>();
		this.studentQueue= new Queue<Student>();
		getStudentFromFile("C:\\Users\\yair2\\Java\\Student.txt");
	}
	private void getStudentFromFile(String import_questions) throws IOException {
		String Configuration = import_questions;
		BufferedReader inFile=null;
		try
		{
			FileReader fr = new FileReader (Configuration);
			inFile = new BufferedReader (fr);
			String str;
			while ((str = inFile.readLine()) != null){
				String[] StudentInformation= new String[9];
				StudentInformation = str.split("\t");
				if(StudentInformation[0].equals("id")) {
					continue;
				}
				double[] workGrades = createGradesArray(StudentInformation);
				Student student = new Student(convertStrToInt(StudentInformation[0]), StudentInformation[1], convertStrToInt(StudentInformation[2]), convertStrToDouble(StudentInformation[4]), convertStrToDouble(StudentInformation[3]), workGrades, this);
				//					
				this.students.add(student);
				//				}else {
				//					EnglishQuestion question = new EnglishQuestion(questionPart[1],Integer.valueOf(questionPart[2]), questionPart[3].charAt(0),cohiceArray,questionPart[9]);
				//					this.questions.add(question);
				//				}

			}
		}

		catch (FileNotFoundException exception)
		{
			System.out.println ("The file " + Configuration + " was not found.");
		}
		catch (IOException exception)
		{
			System.out.println (exception);
		}
		finally{
			inFile.close();
		}


	}

	private double convertStrToDouble(String str) {
		try{
			double number = Double.parseDouble(str);
			return number;
		}
		catch (NumberFormatException ex){
			ex.printStackTrace();
		}
		return 0;
	}
	private int convertStrToInt(String str) {

		try{
			int number = Integer.parseInt(str);
			return number;
		}
		catch (NumberFormatException ex){
			ex.printStackTrace();
		}
		return 0;
	}
	private double[] createGradesArray(String[] StudentInformation) {//create array of choises from the choises in the file
		double[] GradesArray = new double[4];
		for(int i=0;i<GradesArray.length;i++ ) {
			GradesArray[i]=convertStrToDouble(StudentInformation[5+i]);
		}
		return GradesArray;
	}

	public static void main(String[] args) throws IOException{
		CourseInformation fatma = new CourseInformation();
		StartTest(fatma.students);
		System.out.println("dffd");

	}
	private static void StartTest(Vector<Student> students) {
		for(int i=0; i<students.size();i++) {

			Thread t = new Thread(students.elementAt(i)); 
			t.start();   
		}

	}
	public Vector<TeachingAssistant> getTeachingAssistants() {
		return teachingAssistants;
	}
	//	public Vector<Queue<?>> getQueus() {
	//		return this.queus;
	//	}
	public Queue<Student> getStudentQueue() {
		return studentQueue;
	}
}
