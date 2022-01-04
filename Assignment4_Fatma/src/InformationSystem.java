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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<this.Tests.size();i++) {
			if(student.getTest().getStudentId()==Tests.elementAt(i).getStudentId()) {
				System.out.println("this id my exam"+student.getStudentId());
				return true;
			}
		}

		return false;
	}



}
