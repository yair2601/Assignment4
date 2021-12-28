import java.util.Vector;

public class Student implements Runnable{
	private int studentId;
	private String studentName;
	private int studentClass;
	private double studentLevel;
	private int studentPace;
	private Test test;
	
	public Student(int studentId,String studentName,int studentClass, double studentLevel, int studentPace ) {
		this.studentClass = studentClass;
		this.studentId=studentId;
		this.studentLevel=studentLevel;
		this.studentPace=studentPace;
		this.studentName=studentName;
	}

	@Override
	public void run() {
		 this.test = new Test(studentId);
		test.setStudentId(this.studentId);
		test.setDate();
		SolveTest();
	   
	}

	private void SolveTest() {//solve the test
		for(int i=0;i<test.getStudentAnswer().length;i++) {
			answerQuestion(i);
		}
			
		
	    try {
	    		
				Thread.sleep(this.studentPace);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}

	private void answerQuestion(int i) {//generate answer for the test
		double randomNumber= Math.random();
		if(randomNumber<=this.studentLevel) {
			test.getStudentAnswer()[i]=correctAnswer[i];//need to put here the correct answer
		}
	}
}
