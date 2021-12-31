import java.util.Vector;

public class ExerciseChecker {
	private String exerciseCheckertName;
	private int pricePerSecond;
	private double salary;
	private double [][] workGrades;



	public ExerciseChecker(String exerciseCheckertName,Vector<Student> students) {//constructor
		this.exerciseCheckertName=exerciseCheckertName;
		this.pricePerSecond=1;
		this.workGrades= new double[5][students.size()];
		FillworkGrades(students);
		System.out.println("vv");


	}



	private void FillworkGrades(Vector<Student> students) {
		for(int i=0;i<students.size();i++) {
			//for(int j=0;j<workGrades.length;j++) {
				workGrades[0][i]=(double)students.elementAt(i).getStudentId();
		}
		CopyStudentWorksGrades( students);
	}



	private void CopyStudentWorksGrades(Vector<Student> students) {
		for(int i=0;i<students.size();i++) {
			for(int j=1;j<5;j++) {
				workGrades[i][j]=students.elementAt(i).getWorkGrades()[j-1];
			}
			
		}
		
	}
}
