import java.util.Vector;

public class InformationSystem {
	private Vector<Test> Tests; 

	public InformationSystem () {//constructor
		Tests= new Vector<Test>();
	}
	public synchronized void insertTest(Test test) {//insert into the vector and wake all the student
		this.Tests.add(test);
		this.notifyAll();
	}
	public synchronized void FindMyExam(Student student) {//search the exam of givien student in the vector
		while(checkIfExamExist(student)==false) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private synchronized boolean checkIfExamExist(Student student) {//find the exam in the vector
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<this.Tests.size();i++) {
			if(student.getTest().getStudentId()==Tests.elementAt(i).getStudentId()) {
				return true;
			}
		}

		return false;
	}



}
