import java.time.LocalDate;
import java.util.*;

public class Test {
	private int studentId;
	private LocalDate date ;
	private  boolean [] studentAnswer;
	private double studentGradeBeforeFactor;
	private double studentGradeAfterFactor;
	private double studentFinalGrade;
	private int Status;
	
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

}
