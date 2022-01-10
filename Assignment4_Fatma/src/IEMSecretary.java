import java.sql.SQLException;

public class IEMSecretary implements Runnable {

	private String secretaryName;
	private static boolean flag;
	private int secretaryType;//0-student academic status not valid 1-for valid student academic status


	public  IEMSecretary(String secretaryName,int secretaryType) {//constructor
		this.secretaryName=secretaryName;
		flag=true;
		this.secretaryType=secretaryType;
		
		
	}

	public void run() {//thread run
		while(flag=true) {
			flag = insertStudentDataToDB(this.secretaryType);
			if(flag==false) {
				break;
			}
		}
		System.out.println(" iem dead");

	}

	private static synchronized boolean insertStudentDataToDB(int secretaryType) {// insert student information to the data base
		while(flag==true) {
			Test extractTest=CourseInformation.Fatma.getTestQueues().elementAt(4).extract();
			if(extractTest.getStudentId()!=-1){//we got the fake exam
				if(secretaryType==0) {
					if (extractTest.getStudentFinalGrade()<70) {
						workOnTest(extractTest,secretaryType);
					}
					else {
						returnTestToCurrentQueue(extractTest);
						break;//exit from this function and return the key
					}
				}
				if(secretaryType==1) {
					if (extractTest.getStudentFinalGrade()>=70) {
						workOnTest(extractTest,secretaryType);
					}
					else {
						returnTestToCurrentQueue(extractTest);
						break;
					}
				}
			}else {
				changeFlag();
				return flag;
			}
		}//while
		return flag;
		

	}
	private static void changeFlag() {//change flag to false
		IEMSecretary.flag=false;
		
	}

	private static synchronized void workOnTest(Test extractTest, int secretaryType) {//update the DB and do their test process
		if(secretaryType==0) {
			UpdateDB(secretaryType,extractTest);	
		}
		if(secretaryType==1) {
			UpdateDB(secretaryType,extractTest);
		}
		PrintStudentInformation(extractTest);
		changeStatus(extractTest);
		passTestToNextQueue(extractTest);
		SecretaryTimeOfWork(secretaryType);

	}

	private static synchronized void PrintStudentInformation(Test extractTest) {// print the student ID+final grade
		System.out.println("The student ID is: "+extractTest.getStudentId());
		System.out.println("His final grade is: "+extractTest.getStudentFinalGrade());
		
	}

	private static synchronized void UpdateDB(int secretaryType, Test extractTest) {//update the DB tables
		String id = Integer.toString(extractTest.getStudentId());
		if(secretaryType==0) {
			
			String insertDetails = "INSERT INTO " + "Fatma_Below70" + "(ID, Date, CorrectAnswers, FinalGrade) VALUES('" + id + "','" + extractTest.getDate() + "'," + extractTest.getNumberOfCorrectAnswers()+ "," +extractTest.getStudentFinalGrade()+")";	//insert these values to DB
			
			try {
				CourseInformation.Fatma.getSqlVector().elementAt(1).insertIntoTable("Fatma_Below70", insertDetails);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(secretaryType==1) {
			int IsOutstanding= IsOutstanding(extractTest);
			String insertDetails = "INSERT INTO " + "Fatma_Above70" + "(ID, Date, CorrectAnswers, FinalGrade, IsOutstanding) VALUES('" + id + "','" + extractTest.getDate() + "'," + extractTest.getNumberOfCorrectAnswers()+ "," +extractTest.getStudentFinalGrade()+ ", "+IsOutstanding + ")";//insert these values to DB
			try {
				CourseInformation.Fatma.getSqlVector().elementAt(0).insertIntoTable("Fatma_Above70", insertDetails);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}


	private static int IsOutstanding(Test extractTest) {// check if the student is outstanding
		if(extractTest.getStudentFinalGrade()>95) {
			return 1;
		}
		return 0;
	}

	private static synchronized void returnTestToCurrentQueue(Test extractTest) {//return the test to the current queue
		CourseInformation.Fatma.getTestQueues().elementAt(4).insert(extractTest);

	}


	private static synchronized void passTestToNextQueue(Test extractTest) {// pass the test to the next queue according to the test process
		CourseInformation.Fatma.getTestQueues().elementAt(5).insert(extractTest);
		System.out.println("insert to bounded");

	}

	private static synchronized void changeStatus(Test extractTest) {//change the test status according to the test process
		System.out.println("IEM status");
		extractTest.setStatus(1);

	}
	private static synchronized void SecretaryTimeOfWork(int secretaryType) {//calculate the secretary work time
		try {
			Thread.sleep((long) ((3-secretaryType)*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
