import java.util.*;

public class ExamsDepartmentWorker implements Runnable {
	private String EDWName;
	private static int numberOfStudents;


	public ExamsDepartmentWorker(String EDWName,CourseInformation Fatma) {//constructor
		this.EDWName=EDWName;
		ExamsDepartmentWorker.numberOfStudents=Fatma.getStudents().size();

	}


	public void run() {//run function - what edw will do
		checkTests();
		genrateAnnuncment();
		CourseInformation.Fatma.getTestQueues().elementAt(5).insert(null);
	}


	private  void checkTests() {//check the day isnt over and do his job

		while(!checkIfAllStudentHandled()) {
			DoEDWWork();
			updateRemainStudent();	
		}

	}


	private void genrateAnnuncment() {
		CourseInformation.Fatma.getTestQueues().elementAt(2).insert(new Test(-1));

	}


	private static synchronized void updateRemainStudent() {
		numberOfStudents--;

	}


	private static synchronized boolean checkIfAllStudentHandled() {		
		if(numberOfStudents>0) {
			return false;
		}
		else
			return true;
	}


	private  void DoEDWWork() {//extract student from the bounded queue and scan it to the information system
		Test extractTest;
		extractTest = CourseInformation.Fatma.getTestQueues().elementAt(5).extract();
		if(extractTest!=null) {
			Test currentTest = extractTest;
			EDWRandomWorkTime();
			currentTest.setStatus(1);
			CourseInformation.Fatma.getInformationSystem().insertTest(currentTest);
			printStatement(currentTest);
		}

	}


	private void printStatement(Test currentTest) {//print to the console the test is scanned
		System.out.println("Exam Scanned for: " +currentTest.getStudentId());
		System.out.println();

	}



	private  void EDWRandomWorkTime() {//Simulate the work time		
		try {
			Thread.sleep((long) (4000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}