import java.util.Vector;

public class Lecturer implements Runnable {
	private String lecturerName;
	private Vector<Integer> excellentStudent;
	private boolean flag;

	public Lecturer(String lecturerName) {
		this.excellentStudent=new Vector<Integer>();
		this.lecturerName= lecturerName;
		this.flag=true;
		
	}


	public void run() {
		checkTest();
		PrintResults();
		System.out.println("lecturer dead");
		

	}

	private void PrintResults() {
		System.out.println("Test is over! Grades are published, and here are the results:");
		System.out.println("The number of examinees is: "+ CourseInformation.Fatma.getStudents().size());
		System.out.println("The average grade before factor is: "+ CourseInformation.Fatma.calculateAverageBeforeFactor());
		System.out.println("The average grade after factor is: "+ CourseInformation.Fatma.calculateAverageAfterFactor());
		PrintExcelentIDs();
		System.out.println("The total salary is: "+calculateTotalSalary());
		
	}


	private double calculateTotalSalary() {
		int totalSalary=0;
		for (int i=0;i<CourseInformation.Fatma.getTeachingAssistants().size();i++) {//calculate teaching assistant salary
			totalSalary+=CourseInformation.Fatma.getTeachingAssistants().elementAt(i).getSalary();
		}
		totalSalary+=CourseInformation.Fatma.getExerciseCheckers().getSalary(); //add exercise checker salary
		return totalSalary;
	}


	private void PrintExcelentIDs() {
		System.out.println("The Excelent sdutents are: ");
		for(int i=0;i<this.excellentStudent.size();i++) {
			System.out.println(this.excellentStudent.elementAt(i));
		}
	}


	private void checkTest() {
		while(flag==true) {
			LecturerTimeOfWork();
			Test extractTest=CourseInformation.Fatma.getTestQueues().elementAt(2).extract();
			System.out.println("im in lecturer");
			if(extractTest.getStudentId()!=-1){//we got the fake exam
				giveFactor(extractTest);
				getExecllentStudent(extractTest);
				changeStatus(extractTest);
				passTestToNextQueue(extractTest);
			}

			else {
				announceEndOfTest(extractTest);					
			}


		}//while

	}

	private void LecturerTimeOfWork() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void announceEndOfTest(Test extractTest) {
		System.out.println("end of exam");
		flag=false;
		passAnnnouceToTeachingAssitance(extractTest);
		passTestToNextQueue(extractTest);		
	}

	private void passAnnnouceToTeachingAssitance(Test extractTest) {
		CourseInformation.Fatma.getTestQueues().elementAt(0).insert(extractTest);
		CourseInformation.Fatma.getTestQueues().elementAt(1).insert(extractTest);

	}

	private void getExecllentStudent(Test extractTest) {
		if(extractTest.getStudentGradeAfterFactor()>=95) {
			this.excellentStudent.add( extractTest.getStudentId());
		}

	}

	private void passTestToNextQueue(Test extractTest) {
		CourseInformation.Fatma.getTestQueues().elementAt(3).insert(extractTest);

	}

	private void changeStatus(Test extractTest) {
		System.out.println("lecturer status");
		extractTest.setStatus(1);

	}

	private void giveFactor(Test extractTest) {
		double currentGrade= extractTest.getStudentGradeBeforeFactor();
		if(currentGrade>=50&&currentGrade<=55) {
			extractTest.setStudentGradeAfterFactor(56);
		}
		else if(currentGrade>56) {
			extractTest.setStudentGradeAfterFactor(currentGrade+5);
		}
		else {
			extractTest.setStudentGradeAfterFactor(extractTest.getStudentGradeBeforeFactor());
		}

	}
}