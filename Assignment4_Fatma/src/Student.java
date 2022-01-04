import java.util.Vector;

public class Student implements Runnable{
	private int studentId;
	private String studentName;
	private int studentClass;
	private double studentLevel;
	private double studentPace;
	private Test test;
	private CourseInformation course;
	private double[] WorkGrades;
	
	public Student(int studentId,String studentName,int studentClass, double studentLevel, double studentPace, double[] grades, CourseInformation course ) {
		this.studentClass = studentClass;
		this.studentId=studentId;
		this.studentLevel=studentLevel;
		this.studentPace=studentPace;
		this.studentName=studentName;
		this.course=course;
		this.WorkGrades=grades;
	}

	public void run() {
		this.test = new Test(studentId);
		test.setStudentId(this.studentId);
		test.setDate();
		SolveTest();
		course.getStudentQueue().insert(this);
		System.out.println("im in"+this.studentName);
		CourseInformation.Fatma.getInformationSystem().FindMyExam(this); // until someone scan the exam
		this.test.setStatus(1);
		System.out.println("im out"+this.studentId);
	}

	private void SolveTest() {//solve the test
		for(int i=0;i<test.getStudentAnswer().length;i++) {
			answerQuestion(i);
			 try {
		    		
					Thread.sleep((long) (this.studentPace*1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	private void answerQuestion(int i) {//generate answer for the test
		double randomNumber= Math.random();
		boolean correctAnswer =TeachingAssistant.getCorrectAnswer()[i];
		if(randomNumber<=this.studentLevel) {//the probability to answer correct
			test.getStudentAnswer()[i]=correctAnswer;
		}
		else {//Incorrect answer
			if(correctAnswer==true) {
				test.getStudentAnswer()[i]=false;
			}
			else {
				test.getStudentAnswer()[i]=true;
			}
		}
	}
	public Test getTest() {
		return this.test;
	}

	public int getStudentClass() {
		return studentClass;
	}
	public int getStudentId() {
		return studentId;
	}

	public double[] getWorkGrades() {
		return WorkGrades;
	}
}
