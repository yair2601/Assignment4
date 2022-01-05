import java.util.Vector;

public class Proctor implements Runnable {
	private String proctorName;
	private int proctorAge;
	private static int numberOfStudents;

	public Proctor(String proctorName,int proctorAge,int numberOfStudents) {
		this.proctorAge=proctorAge;
		this.proctorName=proctorName;
		Proctor.numberOfStudents=numberOfStudents;


	}


	public void run() {
		takeTestFromStudents();
		System.out.println("proctor dead");
	}


	private static synchronized void takeTestFromStudents() {
		while(!checkIfAllStudentHandled()) {
			DoProctorWork();
			updateRemainStudent();	
		}
		
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


	private synchronized static void DoProctorWork() {
		Student extractStudent;
			extractStudent = CourseInformation.Fatma.getStudentQueue().extract();
			Test currentTest = extractStudent.getTest();
			ProctorRandomWorkTime();
			System.out.println("proctor status");
			currentTest.setStatus(1);
			currentTest.setClassNumber(extractStudent.getStudentClass());
			addToTeachingAssitantsQueue(currentTest);
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


	private static void ProctorRandomWorkTime() {		
		double random = ((Math.random() * (3 - 1)) + 1);//generate random number in the range;
		try {
			Thread.sleep((long) (random*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}