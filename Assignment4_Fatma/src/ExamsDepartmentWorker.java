import java.util.*;

public class ExamsDepartmentWorker implements Runnable {
	private String EDWName;
	private static int numberOfStudents;
	

	public ExamsDepartmentWorker(String EDWName,CourseInformation Fatma) {
		this.EDWName=EDWName;
		ExamsDepartmentWorker.numberOfStudents=Fatma.getStudents().size();
		Thread t = new Thread(this); 
		t.start();   

	}


	public void run() {
		while(!checkIfAllStudentHandled()) {
			DoEDWWork();
			updateRemainStudent();	
		}
		genrateAnnuncment();
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


	private synchronized static void DoEDWWork() {
		Test extractTest;
			try {
				System.out.println("sup");
				extractTest = CourseInformation.Fatma.getTestQueues().elementAt(5).extract();
				System.out.println("im in exam");
				Test currentTest = extractTest;
				EDWRandomWorkTime();
				currentTest.setStatus(1);
				CourseInformation.Fatma.getInformationSystem().insertTest(currentTest);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}





	private static void addToTeachingAssitantsQueue(Test currentTest) {
		Queue<Test> teachingAssit1Queue = CourseInformation.Fatma.getTestQueues().elementAt(0);
		Queue<Test> teachingAssit2Queue = CourseInformation.Fatma.getTestQueues().elementAt(1);
		if(teachingAssit1Queue.getBuffer().size()>teachingAssit2Queue.getBuffer().size()) {
			teachingAssit2Queue.insert(currentTest);
			System.out.println("im in 1 -"+currentTest.getStudentId());
		}else {
			teachingAssit1Queue.insert(currentTest);
			System.out.println("im in 2 -"+currentTest.getStudentId());
		}

	}


	private static void EDWRandomWorkTime() {				
		try {
			Thread.sleep((long) (4000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}