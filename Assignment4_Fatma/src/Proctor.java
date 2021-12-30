import java.util.Vector;

public class Proctor implements Runnable {
	private String proctorName;
	private int proctorAge;
	private static int numberOfStudents;
	//private CourseInformation Fatma;

	public Proctor(String proctorName,int proctorAge,CourseInformation Fatma) {
		this.proctorAge=proctorAge;
		this.proctorName=proctorName;
		Proctor.numberOfStudents=Fatma.getStudents().size();
		Thread t = new Thread(this); 
		t.start();   

	}


	public void run() {
		while(!checkIfAllStudentHandled()) {
			System.out.println("n= "+numberOfStudents);

			DoProctorWork();


			updateRemainStudent();

		}

		//this.run();//after finish with one student return to the next run
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


	private synchronized void DoProctorWork() {
		Student extractStudent;

		try {
			extractStudent = CourseInformation.Fatma.getStudentQueue().extract();
			Test currentTest = extractStudent.getTest();
			ProctorRandomWorkTime();
			currentTest.setStatus(1);
			currentTest.setClassNumber(extractStudent.getStudentClass());
			addToTeachingAssitantsQueue(currentTest);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}


	private void addToTeachingAssitantsQueue(Test currentTest) {
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


	private void ProctorRandomWorkTime() {		
		double random = ((Math.random() * (3 - 1)) + 1);//generate random number in the range;
		try {
			Thread.sleep((long) (random*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
