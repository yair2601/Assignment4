import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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


	}

	private  boolean insertStudentDataToDB(int secretaryType) {// insert student information to the data base
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

	private  void workOnTest(Test extractTest, int secretaryType) {//update the DB and do their test process
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
		//this is a synchronized function because we want it to print together
		System.out.println("The student ID is: "+extractTest.getStudentId());
		System.out.println("His final grade is: "+extractTest.getStudentFinalGrade());
		System.out.println();
		
	}

	private void UpdateDB(int secretaryType, Test extractTest) {//update the DB tables
		String id = Integer.toString(extractTest.getStudentId());
		String date = convertDateToString(extractTest);
		if(secretaryType==0) {	
			String insertDetails = "INSERT INTO " + "Fatma_Below70" + "(ID, Date, CorrectAnswers, FinalGrade) VALUES('" + id + "','" + date + "'," + extractTest.getNumberOfCorrectAnswers()+ "," +extractTest.getStudentFinalGrade()+")";	//insert these values to DB
			
			try {
				CourseInformation.Fatma.getSqlVector().elementAt(1).insertIntoTable("Fatma_Below70", insertDetails);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(secretaryType==1) {
			int IsOutstanding= IsOutstanding(extractTest);
			String insertDetails = "INSERT INTO " + "Fatma_Above70" + "(ID, Date, CorrectAnswers, FinalGrade, IsOutstanding) VALUES('" + id + "','" + date + "'," + extractTest.getNumberOfCorrectAnswers()+ "," +extractTest.getStudentFinalGrade()+ ", "+IsOutstanding + ")";//insert these values to DB
			try {
				CourseInformation.Fatma.getSqlVector().elementAt(0).insertIntoTable("Fatma_Above70", insertDetails);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}


	private  String convertDateToString(Test extractTest) {//change the format of the date
		String formatter ="dd/MM/yy";
		DateFormat df = new SimpleDateFormat(formatter);
		Date today = extractTest.getDate();   
		String todayAsString = df.format(today);
		return todayAsString;
	}

	private  int IsOutstanding(Test extractTest) {// check if the student is outstanding
		if(extractTest.getStudentFinalGrade()>95) {
			return 1;
		}
		return 0;
	}

	private  void returnTestToCurrentQueue(Test extractTest) {//return the test to the current queue
		CourseInformation.Fatma.getTestQueues().elementAt(4).insert(extractTest);

	}


	private  void passTestToNextQueue(Test extractTest) {// pass the test to the next queue according to the test process
		CourseInformation.Fatma.getTestQueues().elementAt(5).insert(extractTest);

	}

	private void changeStatus(Test extractTest) {//change the test status according to the test process
		extractTest.setStatus(1);

	}
	private void SecretaryTimeOfWork(int secretaryType) {//calculate the secretary work time
		try {
			Thread.sleep((long) ((3-secretaryType)*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
