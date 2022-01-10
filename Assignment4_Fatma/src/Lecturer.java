import java.util.Vector;

public class Lecturer implements Runnable {
	private String lecturerName;
	private Vector<Integer> excellentStudent;
	private boolean flag;

	public Lecturer(String lecturerName) {//constructor
		this.excellentStudent=new Vector<Integer>();
		this.lecturerName= lecturerName;
		this.flag=true;		
	}


	public void run() {//run function - what the lecturer will do
		checkTest();
		PrintResults();
	}

	private void PrintResults() {//at the end of the program publish the resualt
		System.out.println("Test is over! Grades are published, and here are the results:");
		System.out.println("The number of examinees is: "+ CourseInformation.Fatma.getStudents().size());
		System.out.println("The average grade before factor is: "+ CourseInformation.Fatma.calculateAverageBeforeFactor());
		System.out.println("The average grade after factor is: "+ CourseInformation.Fatma.calculateAverageAfterFactor());
		PrintExcelentIDs();
		System.out.println("The total salary is: "+calculateTotalSalary());
		
	}


	private double calculateTotalSalary() {//calculate the salary 
		double totalSalary=0;
		for (int i=0;i<CourseInformation.Fatma.getTeachingAssistants().size();i++) {//calculate teaching assistant salary
			totalSalary+=CourseInformation.Fatma.getTeachingAssistants().elementAt(i).getSalary();
		}
		totalSalary+=CourseInformation.Fatma.getExerciseCheckers().getSalary(); //add exercise checker salary
		return totalSalary;
	}


	private void PrintExcelentIDs() {//print the excellent student id
		System.out.println("The Excelent students are: ");
		for(int i=0;i<this.excellentStudent.size();i++) {
			System.out.println(this.excellentStudent.elementAt(i));
		}
	}


	private void checkTest() {//check the student exam 
		while(flag==true) {
			LecturerTimeOfWork();
			Test extractTest=CourseInformation.Fatma.getTestQueues().elementAt(2).extract();
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

	private void LecturerTimeOfWork() {//simulate the lecturer work time
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void announceEndOfTest(Test extractTest) {//pass fictive exam to announce the test is over
		flag=false;
		passAnnnouceToTeachingAssitance(extractTest);
		passTestToNextQueue(extractTest);		
	}

	private void passAnnnouceToTeachingAssitance(Test extractTest) {//tell the teching assitance they can finish the work
		CourseInformation.Fatma.getTestQueues().elementAt(0).insert(extractTest);
		CourseInformation.Fatma.getTestQueues().elementAt(1).insert(extractTest);
		

	}

	private void getExecllentStudent(Test extractTest) {//check if given student is excellent
		if(extractTest.getStudentGradeAfterFactor()>95) {
			this.excellentStudent.add( extractTest.getStudentId());
		}

	}

	private void passTestToNextQueue(Test extractTest) {//pass test to the ecxesise checker
		CourseInformation.Fatma.getTestQueues().elementAt(3).insert(extractTest);

	}

	private void changeStatus(Test extractTest) {//add one to the test status
		extractTest.setStatus(1);
	}

	private void giveFactor(Test extractTest) {//factor logic if needed
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