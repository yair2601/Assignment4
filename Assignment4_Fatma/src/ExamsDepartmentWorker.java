import java.util.*;

public class ExamsDepartmentWorker implements Runnable {
	private String EDWName;
	private static int numberOfStudents;
	

	public ExamsDepartmentWorker(String EDWName,CourseInformation Fatma) {
		this.EDWName=EDWName;
		ExamsDepartmentWorker.numberOfStudents=Fatma.getStudents().size();
 

	}


	public void run() {
		checkTests();
		genrateAnnuncment();
		System.out.println("im deadzzzzz"+ EDWName);
	}


	private static synchronized void checkTests() {
		
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
		System.out.println("EDW numberof students: "+numberOfStudents);

	}


	private static synchronized boolean checkIfAllStudentHandled() {		
		if(numberOfStudents>0) {
			return false;
		}
		else
			return true;
	}


	private synchronized static void DoEDWWork() {
		Test extractTest;
			extractTest = CourseInformation.Fatma.getTestQueues().elementAt(5).extract();
			Test currentTest = extractTest;
			EDWRandomWorkTime();
			System.out.println("EDW status");
			currentTest.setStatus(1);
			CourseInformation.Fatma.getInformationSystem().insertTest(currentTest);
			printStatement(currentTest);
		}





	private static void printStatement(Test currentTest) {
		System.out.println("Exam Scanned for" +currentTest.getStudentId());
		
	}


//	private static void addToTeachingAssitantsQueue(Test currentTest) {
//		Queue<Test> teachingAssit1Queue = CourseInformation.Fatma.getTestQueues().elementAt(0);
//		Queue<Test> teachingAssit2Queue = CourseInformation.Fatma.getTestQueues().elementAt(1);
//		if(teachingAssit1Queue.getBuffer().size()>teachingAssit2Queue.getBuffer().size()) {
//			teachingAssit2Queue.insert(currentTest);
//			System.out.println("im in 1 -"+currentTest.getStudentId());
//		}else {
//			teachingAssit1Queue.insert(currentTest);
//			System.out.println("im in 2 -"+currentTest.getStudentId());
////		}
//
//	}


	private synchronized static void EDWRandomWorkTime() {				
		try {
			Thread.sleep((long) (4000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}