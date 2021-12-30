import java.time.LocalDate;
import java.util.*;

public class Test {
	private int studentId;
	private LocalDate date ;
	private  boolean [] studentAnswer;
	private double studentGradeBeforeFactor;
	private double studentGradeAfterFactor;
	private double studentFinalGrade;
	private int Status;//0-default 1-Signature by proctor 2-check once 3-check Twice 4-confirmed+factor 5- after works 6-in DB 7- read by student
	private int classNumber;
	
	public Test(int studentId) {
		this.setStudentId(studentId);
		this.studentAnswer=new boolean [20];

	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate() {
		this.date = LocalDate.now();
	}

	public boolean[] getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(boolean[] studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int amount) {
		if (amount==1)
			this.Status++;
		System.out.println(this.Status);
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		if(classNumber>0) {
			this.classNumber = classNumber;
		}
				
	}

}
