import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Test {
	private int studentId;
	private Date date ;
	private  boolean [] studentAnswer;
	private double studentGradeBeforeFactor;
	private double studentGradeAfterFactor;
	private double studentFinalGrade;
	private int Status;//0-default 1-Signature by proctor 2-check once 3-check Twice 4-confirmed+factor 5- after works 6-in DB 7-scanned 8 read by student
	private int classNumber;
	private int CorrectAnswers;
	
	public Test(int studentId) {//constructor
		this.setStudentId(studentId);
		this.studentAnswer=new boolean [20];

	}

	public int getStudentId() {//getter for student id
		return studentId;
	}

	public void setStudentId(int studentId) {//setter for student id
		this.studentId = studentId;
	}

	public Date getDate() {//getter for date
		return date;
	}

	public void setDate() {//setter for date - current date
		this.date =  Calendar.getInstance().getTime();
	}

	public boolean[] getStudentAnswer() {//getter for student answers
		return studentAnswer;
	}

	public void setStudentAnswer(boolean[] studentAnswer) {//setter for stuent anser
		this.studentAnswer = studentAnswer;
	}

	public int getStatus() {//getter for status
		return Status;
	}

	public void setStatus(int amount) {//setter for status get only 1 as valid
		if (amount==1)
			this.Status++;
	}

	public int getClassNumber() {//getter for class number
		return classNumber;
	}

	public void setClassNumber(int classNumber) {//setter for class number valid if possitive
		if(classNumber>0) {
			this.classNumber = classNumber;
		}
				
	}

	public double getStudentGradeBeforeFactor() {////getter for student grade before factor
		return studentGradeBeforeFactor;
	}

	public void UpdateStudentGradeBeforeFactor(double points) {//setter for student grade before factor
		this.studentGradeBeforeFactor += points;
	}


	public double getStudentGradeAfterFactor() {//getter for grade after factor
		return studentGradeAfterFactor;
	}

	public void setStudentGradeAfterFactor(double studentGradeAfterFactor) {//setter for student grade after factor
		if(studentGradeAfterFactor>=this.studentGradeBeforeFactor) {
			this.studentGradeAfterFactor = studentGradeAfterFactor;
		}
		if(this.studentGradeAfterFactor>100) {
			this.studentGradeAfterFactor=100;
		}
		
	}

	public double getStudentFinalGrade() {//getter for final grade
		return studentFinalGrade;
	}

	public void setStudentFinalGrade(double studentFinalGrade) {//setter for student final  grade 
		this.studentFinalGrade = studentFinalGrade;
	}

	public int getNumberOfCorrectAnswers() {//getter for number of correct answers
		return this.CorrectAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {//setter for correct answer
		CorrectAnswers += correctAnswers;
	}

}
