import java.util.Vector;

public class InformationSystem {
	private Vector<Test> Tests; 

	public InformationSystem () {
		Tests= new Vector<Test>();
	}
	public synchronized void insertTest(Test test) {
		this.Tests.add(test);
		this.notifyAll();
	}
	public synchronized void FindMyExam(Student student) {
		while(checkIfExamExist(student)==false) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private synchronized boolean checkIfExamExist(Student student) {
		for(int i=0;i<this.Tests.size();i++) {
			if(student.getTest().getStudentId()==Tests.elementAt(i).getStudentId()) {
				return true;
			}
		}

		return false;
	}



}
