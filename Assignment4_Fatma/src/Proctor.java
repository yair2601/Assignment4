import java.util.Vector;

public class Proctor implements Runnable {
	private String proctorName;
	private int proctorAge;
	private static int numberOfStudents;

	public Proctor(String proctorName,int proctorAge,int numberOfStudents) {//constructor
		this.proctorAge=proctorAge;
		this.proctorName=proctorName;
		Proctor.numberOfStudents=numberOfStudents;
	}


	public void run() {//run method what will proctor do
		takeTestFromStudents();
		CourseInformation.Fatma.getStudentQueue().insert(null);
	}


	private  void takeTestFromStudents() {//if there is more student that didnt finish and pass handel them 
		while(!checkIfAllStudentHandled()) {
			DoProctorWork();
			updateRemainStudent();	
		}

	}


	private static synchronized void updateRemainStudent() {//update the left student counter
		numberOfStudents--;

	}


	private static synchronized boolean checkIfAllStudentHandled() {//check the number of left student	
		if(numberOfStudents>0) {
			return false;
		}
		else
			return true;
	}


	private void DoProctorWork() {//do the work
		Student extractStudent;
		extractStudent = CourseInformation.Fatma.getStudentQueue().extract();
		if(extractStudent!=null) {
			Test currentTest = extractStudent.getTest();
			ProctorRandomWorkTime();
			currentTest.setStatus(1);
			currentTest.setClassNumber(extractStudent.getStudentClass());
			addToTeachingAssitantsQueue(currentTest);
		}

	}

	private  void addToTeachingAssitantsQueue(Test currentTest) {//pass the test for check
		Queue<Test> teachingAssit1Queue = CourseInformation.Fatma.getTestQueues().elementAt(0);
		Queue<Test> teachingAssit2Queue = CourseInformation.Fatma.getTestQueues().elementAt(1);
		if(teachingAssit1Queue.getBuffer().size()>teachingAssit2Queue.getBuffer().size()) {
			teachingAssit2Queue.insert(currentTest);
		}else {
			teachingAssit1Queue.insert(currentTest);
		}

	}


	private  void ProctorRandomWorkTime() {//simulate the work time		
		double random = ((Math.random() * (3 - 1)) + 1);//generate random number in the range;
		try {
			Thread.sleep((long) (random*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}