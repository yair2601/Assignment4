
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
	private Vector<SQL> SqlVector;


	public CourseInformation(double Perror,int NumberOfEDW, String url) throws IOException {//constructor
		CourseInformation.Fatma=this;
		initiateVectors();		
		this.NumberOfEDW=NumberOfEDW;		
		informationSystem= new InformationSystem();
		addToTestQueues();
		createTeachingAssistant();
		getStudentFromFile(url);
		createProctors();
		createExerciseChecker();
		createLecturer();	
		createSecretaries();
		createEdW();
		buildSQLDB();
		StartTest(CourseInformation.Fatma.students);
		waitForEndOfTheTest();	
	}

	private void buildSQLDB() {//build the Sql tables
		SQL sql1 = new SQL();
		SQL sql2 = new SQL();
		this.SqlVector.add(sql1);
		this.SqlVector.add(sql2);

		String createFirstTable = "CREATE TABLE " + "Fatma_Above70" +"(ID varchar(9), Date Datetime, CorrectAnswers int, FinalGrade float, IsOutstanding bit)";
		sql1.createTables("Fatma_Above70", createFirstTable);
		String createSecondTable = "CREATE TABLE " + "Fatma_Below70" +"(ID varchar(9), Date Datetime, CorrectAnswers int, FinalGrade float)";
		sql1.createTables("Fatma_Below70", createSecondTable);

	}

	private void createExerciseChecker() {//create the exercise checker
		this.exerciseCheckers= new ExerciseChecker("Marmor",students);
		Thread t1 = new Thread(this.exerciseCheckers); 
		this.threadVector.add(t1);
		t1.start();
	}

	private void waitForEndOfTheTest() {//wait for the end of the test process
		for(int i=0;i<this.threadVector.size();i++) {
			try {
				this.threadVector.elementAt(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void initiateVectors() {//initiate all the course information vectors
		this.threadVector=new Vector<Thread>();
		this.testQueues= new Vector <Queue<Test>>();
		this.IEMSecretary= new Vector<IEMSecretary>() ;
		proctors=new Vector<Proctor>();
		this.students = new Vector<Student>();
		this.studentQueue= new Queue<Student>();
		examsDepartmentWorkers=new Vector<ExamsDepartmentWorker>();
		this.teachingAssistants= new Vector<TeachingAssistant>();
		this.SqlVector= new  Vector<SQL>();

	}

	private void createSecretaries() {//create the secretaries
		IEMSecretary Hana= new IEMSecretary("Hana",0);
		IEMSecretary Yona= new IEMSecretary("Yona",1);
		insertToSecretaryVector(Hana,Yona);
		Thread t1 = new Thread(Hana); 
		Thread t2 = new Thread(Yona);
		addToTreadVectorAndRun2(t1,t2);
	}

	private void addToTreadVectorAndRun2(Thread t1, Thread t2) {//add 2 threads to vector and run it
		this.threadVector.add(t1);
		this.threadVector.add(t2);
		t1.start();
		t2.start();

	}

	private void insertToSecretaryVector(IEMSecretary Hana, IEMSecretary Yona) {//insert the two secretary to their vector
		IEMSecretary.add(Hana);
		IEMSecretary.add(Yona);

	}

	private void createLecturer() {//create the lecturer
		this.lecturer = new Lecturer("Roei");
		Thread t1 = new Thread(this.lecturer); 
		this.threadVector.add(t1);
		t1.start();	
	}


	private void createProctors() {// create the proctors
		Proctor Jorjet= new Proctor("Jorjet", 70, this.getStudents().size());
		Proctor Brijet= new Proctor("Brijet", 75, this.getStudents().size());
		Proctor Jaklin= new Proctor("Jaklin", 80, this.getStudents().size());
		insertToProctorVector(Jorjet,Brijet,Jaklin);
		Thread t1 = new Thread(Jorjet); 
		Thread t2 = new Thread(Brijet);
		Thread t3 = new Thread(Jaklin);
		addToTreadVectorAndRun3(t1, t2, t3);


	}
	private void addToTreadVectorAndRun3(Thread t1, Thread t2,Thread t3) {// add three threads to the thread vector and run them
		this.threadVector.add(t1);
		this.threadVector.add(t2);
		this.threadVector.add(t3);
		t1.start();
		t2.start();
		t3.start();

	}

	private void insertToProctorVector(Proctor Jorjet, Proctor Brijet, Proctor Jaklin) {// insert proctors to the proctors vector
		proctors.add(Jorjet);
		proctors.add(Brijet);
		proctors.add(Jaklin);

	}


	private void createTeachingAssistant() {// create the teaching assistant
		TeachingAssistant Lior = new TeachingAssistant("Lior", 3,Perror);
		TeachingAssistant Maya = new TeachingAssistant("Maya", 3,Perror);
		this.teachingAssistants.add(Maya);
		this.teachingAssistants.add(Lior);
		Thread t1 = new Thread(Lior); 		
		Thread t2 = new Thread(Maya); 
		addToTreadVectorAndRun2(t1, t2);


	}

	private void addToTestQueues() {//add the test queues to the queue vector
		this.testQueues.add(new Queue<Test>());//teaching assist 1 line place 0
		this.testQueues.add(new Queue<Test>());//teaching assist 2 line place 1
		this.testQueues.add(new Queue<Test>());//Lecturer line place 2
		this.testQueues.add(new Queue<Test>());//ExerciseChecker line place 3
		this.testQueues.add(new Queue<Test>());//IEMSecretary line place 4
		//this.testQueues.add(new Queue<Test>());
		this.testQueues.add(new BoundedQueue<Test>(10));//EDW line place 5

	}

	private void createEdW() {// create the EDW
		for(int i=0;i<this.NumberOfEDW;i++) {
			ExamsDepartmentWorker shmulik = new ExamsDepartmentWorker("shmulik", this);
			examsDepartmentWorkers.add(shmulik);
			Thread t = new Thread(this.examsDepartmentWorkers.elementAt(0));
			this.threadVector.add(t);
			t.start();

		}
	}

	private void getStudentFromFile(String import_questions) throws IOException {// File reception get the student from the file 
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
				this.students.add(student);


			}
		}

		catch (FileNotFoundException exception)
		{
			System.out.println ("The file " + Configuration + " was not found. please change the url in the gui class(line 26)");
		}
		catch (IOException exception)
		{
			System.out.println (exception);
		}
		finally{
			inFile.close();
		}


	}

	private double convertStrToDouble(String str) {//convert string to double
		try{
			double number = Double.parseDouble(str);
			return number;
		}
		catch (NumberFormatException ex){
			ex.printStackTrace();
		}
		return 0;
	}
	private int convertStrToInt(String str) {//convert string to int

		try{
			int number = Integer.parseInt(str);
			return number;
		}
		catch (NumberFormatException ex){
			ex.printStackTrace();
		}
		return 0;
	}
	private double[] createGradesArray(String[] StudentInformation) {//create array of choices from the choices in the file
		double[] GradesArray = new double[4];
		for(int i=0;i<GradesArray.length;i++ ) {
			GradesArray[i]=convertStrToDouble(StudentInformation[5+i]);
		}
		return GradesArray;
	}

	private  void StartTest(Vector<Student> students) {//students start to resolve the test
		for(int i=0; i<students.size();i++) {

			Thread t = new Thread(students.elementAt(i)); 
			this.threadVector.add(t);
			t.start();   

		}

	}
	public Vector<TeachingAssistant> getTeachingAssistants() {//get the teaching Assistants vector
		return teachingAssistants;
	}

	public Queue<Student> getStudentQueue() {//get the student Queue vector
		return studentQueue;
	}
	public Vector <Student> getStudents() {//get the students vector
		return this.students;
	}
	public Vector<Queue<Test>> getTestQueues() {//get the test Queues vector
		return this.testQueues;
	}
	public Vector<SQL> getSqlVector() {//get the Sql Vector
		return this.SqlVector;
	}

	public InformationSystem getInformationSystem() {//get the information System
		return informationSystem;
	}
	public Queue<Test> getTestBoundedQueue() {//get the test Bounded Queue
		return testBoundedQueue;
	}

	public double getSalaryCost() {//get the Salary Cost
		return SalaryCost;
	}

	public void setSalaryCost(double salaryCost) {// set the total salary cost
		if (salaryCost>0)
			SalaryCost += salaryCost;
	}
	public double calculateAverageBeforeFactor() {// calculate the tests average before the factor
		double totalgrades = 0;
		for(int i =0;i<this.students.size(); i++) {
			totalgrades+=this.students.elementAt(i).getTest().getStudentGradeBeforeFactor();
		}
		this.testAverageBeforeFactor=totalgrades/this.students.size();
		return this.testAverageBeforeFactor;

	}
	public double calculateAverageAfterFactor() {// calculate the tests average after the factor
		double totalgrades = 0;
		for(int i =0;i<this.students.size(); i++) {
			totalgrades+=this.students.elementAt(i).getTest().getStudentGradeAfterFactor();
		}
		this.testAverageAfterFactor=totalgrades/this.students.size();
		return this.testAverageAfterFactor;

	}

	public double getTestAverageBeforeFactor() {//get the tests average before the factor
		return testAverageBeforeFactor;
	}

	public double getTestAverageAfterFactor() {//get the tests average after the factor
		return testAverageAfterFactor;
	}

	public ExerciseChecker getExerciseCheckers() {//get the exercise Checkers
		return exerciseCheckers;
	}

}
