import java.util.*;

public class ExerciseChecker implements Runnable {
	private String exerciseCheckertName;
	private int pricePerSecond;
	private double salary;
	private long [][] workGrades;
	boolean flag;


	public ExerciseChecker(String exerciseCheckertName,Vector<Student> students) {//constructor
		this.exerciseCheckertName=exerciseCheckertName;
		this.pricePerSecond=1;
		this.flag=true;
		this.workGrades= new long[students.size()][6];
		FillworkGrades(students);
		Thread t = new Thread(this); 
		t.start();  
		System.out.println("vv");


	}

	private void calculateAssignmentsGrade(long[][] workGrades, Test extractTest) {
		for(int i=0;i<workGrades.length;i++) {
			if(workGrades[i][0]==extractTest.getStudentId()) {
				workGrades[i][5]=(long) (workGrades[i][1]*0.02+workGrades[i][2]*0.04+workGrades[i][3]*0.06+workGrades[i][4]*0.08);
				break;
			}
		}
	}

	private void FillworkGrades(Vector<Student> students) {
		for(int i=0;i<students.size();i++) {
			workGrades[i][0]=(students.elementAt(i).getStudentId());
		}
		CopyStudentWorksGrades( students);

	}

	private void CopyStudentWorksGrades(Vector<Student> students) {
		for(int i=0;i<students.size();i++) {
			for(int j=1;j<5;j++) {
				workGrades[i][j]=(long) students.elementAt(i).getWorkGrades()[j-1];
			}

		}

	}

	public void run() {
		calculateFinalGrade();
		updateTotalSalary();
		System.out.println("Echecker dead");
	}



	private void updateTotalSalary() {
		CourseInformation.Fatma.setSalaryCost(salary);
		
	}

	private void calculateFinalGrade() {
		while(flag==true) {
			Double randomTime=ExerciseCheckerTimeOfWork();
			updateExerciseCheckerSalary(randomTime);
			Test extractTest=CourseInformation.Fatma.getTestQueues().elementAt(3).extract();
			System.out.println("im in excerisze");
			if(extractTest.getStudentId()!=-1){//we got the fake exam
				calculateGradeAfterFactor(extractTest);
				changeStatus(extractTest);
				passTestToNextQueue(extractTest);
			}else {
				flag=false;
				passTestToNextQueue(extractTest);//pass the fake exam
			}
		}//while

	}

	private void passTestToNextQueue(Test extractTest) {
		CourseInformation.Fatma.getTestQueues().elementAt(4).insert(extractTest);

	}

	public double getSalary() {
		return this.salary;
	}

	private void calculateGradeAfterFactor(Test extractTest) {
		double finalGrade;
		int studentLocation=findStudentLocation(extractTest.getStudentId());
		calculateAssignmentsGrade(workGrades,extractTest);
		finalGrade=(extractTest.getStudentGradeAfterFactor()*0.8+workGrades[studentLocation][5]);
		extractTest.setStudentFinalGrade(finalGrade);
	}



	private int findStudentLocation(int studentId) {
		for(int i=0;i<this.workGrades.length;i++) {
			if(workGrades[i][0]==studentId)
				return i;
		}
		return 0;//need to decide what to do if the student id doesnot exist

	}



	private void updateExerciseCheckerSalary(Double randomTime) {
		this.salary+=randomTime*this.pricePerSecond;

	}

	private void changeStatus(Test extractTest) {
		System.out.println("Excersize status");
		extractTest.setStatus(1);

	}

	private double ExerciseCheckerTimeOfWork() {		
		double random = ((Math.random() * (3 - 2)) + 2);//generate random number in the range;
		try {
			Thread.sleep((long) (random*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return random;
	}

}
