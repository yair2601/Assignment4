
import java.io.*;
import java.util.*;

public class CourseInformation  {
	private Vector<Student> students;
	private Vector<TeachingAssistant> teachingAssistants;
	private Vector<Proctor> proctors;
	private Lecturer lecturer;
	private ExerciseChecker exerciseCheckers;
	private Vector<IEMSecretary> IEMSecretary;
	private Vector<ExamsDepartmentWorker> examsDepartmentWorkers;
	private Queue<Student> studentQueue;
	private Queue<Test> testBoundedQueue;
	private Vector <Queue<Test>> testQueues;
	private InformationSystem informationSystem;
	public static CourseInformation Fatma;
	private double Perror;
	private int NumberOfEDW;
	private double SalaryCost;
	private double testAverageBeforeFactor;
	private double testAverageAfterFactor;
	private Vector<Thread> threadVector;


	public CourseInformation(double Perror,int NumberOfEDW) throws IOException {
		CourseInformation.Fatma=this;
		initiateVectors();		
		this.NumberOfEDW=NumberOfEDW;		
		informationSystem= new InformationSystem();
		addToTestQueues();
		createTeachingAssistant();
		getStudentFromFile("C:\\Users\\yair2\\Java\\Student.txt");
		createProctors();
		createLecturerAndExerciseChecker();//need to break;
		createSecretaries();
		createEdW();
		//informationSystem= new InformationSystem();
		StartTest(CourseInformation.Fatma.students);
		waitForEndOfTheTest();
//		Thread t = new Thread(this);
//		t.start();
		
		
		
		
	}

	private void waitForEndOfTheTest() {
		for(int i=0;i<this.threadVector.size();i++) {
			try {
				this.threadVector.elementAt(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void initiateVectors() {
		this.threadVector=new Vector<Thread>();
		this.testQueues= new Vector <Queue<Test>>();
		this.IEMSecretary= new Vector<IEMSecretary>() ;
		proctors=new Vector<Proctor>();
		this.students = new Vector<Student>();
		this.studentQueue= new Queue<Student>();
		examsDepartmentWorkers=new Vector<ExamsDepartmentWorker>();
		this.teachingAssistants= new Vector<TeachingAssistant>();
		
	}

	private void createSecretaries() {
		IEMSecretary Hana= new IEMSecretary("Hana",0);
		IEMSecretary Yona= new IEMSecretary("Yona",1);
		insertToSecretaryVector(Hana,Yona);
		Thread t1 = new Thread(Hana); 
		Thread t2 = new Thread(Yona);
		addToTreadVectorAndRun2(t1,t2);
	}

	private void addToTreadVectorAndRun2(Thread t1, Thread t2) {
		this.threadVector.add(t1);
		this.threadVector.add(t2);
		t1.start();
		t2.start();
		
	}

	private void insertToSecretaryVector(IEMSecretary Hana, IEMSecretary Yona) {
		IEMSecretary.add(Hana);
		IEMSecretary.add(Yona);
		
	}

	private void createLecturerAndExerciseChecker() {
		this.lecturer = new Lecturer("Roei");
		this.exerciseCheckers= new ExerciseChecker("Marmor",students);//changed
		Thread t1 = new Thread(this.lecturer); 
		Thread t2 = new Thread(this.exerciseCheckers);
		addToTreadVectorAndRun2(t1, t2);
		
	}

	private void createProctors() {
		Proctor Jorjet= new Proctor("Jorjet", 70, this.getStudents().size());
		Proctor Brijet= new Proctor("Brijet", 75, this.getStudents().size());
		Proctor Jaklin= new Proctor("Jaklin", 80, this.getStudents().size());
		insertToProctorVector(Jorjet,Brijet,Jaklin);
		Thread t1 = new Thread(Jorjet); 
		Thread t2 = new Thread(Brijet);
		Thread t3 = new Thread(Jaklin);
		addToTreadVectorAndRun3(t1, t2, t3);
		
		
	}
	private void addToTreadVectorAndRun3(Thread t1, Thread t2,Thread t3) {
		this.threadVector.add(t1);
		this.threadVector.add(t2);
		this.threadVector.add(t3);
		t1.start();
		t2.start();
		t3.start();
		
	}

	private void insertToProctorVector(Proctor Jorjet, Proctor Brijet, Proctor Jaklin) {
		proctors.add(Jorjet);
		proctors.add(Brijet);
		proctors.add(Jaklin);
		
	}


	private void createTeachingAssistant() {
		TeachingAssistant Lior = new TeachingAssistant("Lior", 3,Perror);
		TeachingAssistant Maya = new TeachingAssistant("Maya", 3,Perror);
		this.teachingAssistants.add(Maya);
		this.teachingAssistants.add(Lior);
		Thread t1 = new Thread(Lior); 		
		Thread t2 = new Thread(Maya); 
		addToTreadVectorAndRun2(t1, t2);
		
		
	}

	private void addToTestQueues() {
		this.testQueues.add(new Queue<Test>());//teaching assist 1 line place 0
		this.testQueues.add(new Queue<Test>());//teaching assist 2 line place 1
		this.testQueues.add(new Queue<Test>());//Lecturer line place 2
		this.testQueues.add(new Queue<Test>());//ExerciseChecker line place 3
		this.testQueues.add(new Queue<Test>());//IEMSecretary line place 4
		//this.testQueues.add(new Queue<Test>());
		this.testQueues.add(new BoundedQueue<Test>(10));//EDW line place 5
		
	}

	private void createEdW() {
		for(int i=0;i<this.NumberOfEDW;i++) {
			ExamsDepartmentWorker shmulik = new ExamsDepartmentWorker("shmulik", this);
			examsDepartmentWorkers.add(shmulik);
			Thread t = new Thread(this.examsDepartmentWorkers.elementAt(0));
			this.threadVector.add(t);
			t.start();
			
		}
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
		CourseInformation fatma = new CourseInformation(0.2, 2);
	//	StartTest(fatma.students);
		System.out.println("dffd");

	}
	private  void StartTest(Vector<Student> students) {
		for(int i=0; i<students.size();i++) {

			Thread t = new Thread(students.elementAt(i)); 
			this.threadVector.add(t);
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
	public Vector <Student> getStudents() {
		return this.students;
	}
	public Vector<Queue<Test>> getTestQueues() {
		return this.testQueues;
	}
	public InformationSystem getInformationSystem() {
		return informationSystem;
	}
	public Queue<Test> getTestBoundedQueue() {
		return testBoundedQueue;
	}

	public double getSalaryCost() {
		return SalaryCost;
	}

	public void setSalaryCost(double salaryCost) {
		if (salaryCost>0)
		SalaryCost += salaryCost;
	}
	public double calculateAverageBeforeFactor() {
		double totalgrades = 0;
	for(int i =0;i<this.students.size(); i++) {
		totalgrades+=this.students.elementAt(i).getTest().getStudentGradeBeforeFactor();
	}
	this.testAverageBeforeFactor=totalgrades/this.students.size();
	return this.testAverageBeforeFactor;
	
	}
	public double calculateAverageAfterFactor() {
		double totalgrades = 0;
	for(int i =0;i<this.students.size(); i++) {
		totalgrades+=this.students.elementAt(i).getTest().getStudentGradeAfterFactor();
	}
	this.testAverageAfterFactor=totalgrades/this.students.size();
	return this.testAverageAfterFactor;
	
	}

	public double getTestAverageBeforeFactor() {
		return testAverageBeforeFactor;
	}

	public double getTestAverageAfterFactor() {
		return testAverageAfterFactor;
	}

	public ExerciseChecker getExerciseCheckers() {
		return exerciseCheckers;
	}

//	public void run() {
//		waitForEndOfTheTest();
//		System.out.println("ended");
//		
//	}
}
