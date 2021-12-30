
public class TeachingAssistant implements Runnable{
	private static  boolean [] correctAnswer=randomAnswer();
	private String teachingAssistantName;
	private int PricePerSecond;
	private double salary;
	private double PError;

	public TeachingAssistant(String teachingAssistantName,int PricePerSecond) {
		this.teachingAssistantName=teachingAssistantName;
		this.PricePerSecond=PricePerSecond;
		this.PError=0.5;//need to update later with the Gui
		Thread t = new Thread(this); 
		t.start();  

	}


	private static boolean[] randomAnswer() {//calculate the correct Answer for the test
		boolean [] tempcorrectAnswer=new boolean [20];
		for(int i=0;i<tempcorrectAnswer.length;i++) {
			double randomNumber= Math.random();
			if(randomNumber<=0.5) {
				tempcorrectAnswer[i]=true;
			}
			else
				tempcorrectAnswer[i]=false;
		}

		return tempcorrectAnswer;
	}


	public static boolean [] getCorrectAnswer() {
		return correctAnswer;
	}


	public void run() {
		Queue<Test> BufferAAssistant = CourseInformation.Fatma.getTestQueues().elementAt(0);
		Queue<Test> BufferBAssistant = CourseInformation.Fatma.getTestQueues().elementAt(1);

		if(this.teachingAssistantName.equals("Maya")){
			try {
				Test extractTest=BufferAAssistant.extract();
				checkTest(extractTest);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
		else {//lior
			
		}

	}


	private void checkTest(Test extractTest) {
		double workTime=TeachingAssistantRandomWorkTime();
		updateAssitantSalary(workTime);
		if(extractTest.getStatus()==1){//test not checked yet
			for (int i=0;i<extractTest.getStudentAnswer().length;i++) {
				checkQuestion (i,extractTest);
			}
			updateTestStatus(extractTest);
			moveTestToSecondAssitent(extractTest);
		}

		if(extractTest.getStatus()==2){//test already checked one time
			for (int i=0;i<extractTest.getStudentAnswer().length;i++) {
				checkQuestion (i,extractTest);
			}
			updateTestStatus(extractTest);
			moveTestToLecturer(extractTest);
		}
	}
	private void moveTestToLecturer(Test extractTest) {
		// TODO Auto-generated method stub

	}


	private void moveTestToSecondAssitent(Test extractTest) {
		

	}


	private void checkQuestion(int i, Test extractTest) {
		if(extractTest.getStudentAnswer()[i]==correctAnswer[i]) {
			extractTest.UpdateStudentGradeBeforeFactor(5);
		}
		else {
			double randomNumber=generateRandomNumber();
			if(randomNumber>this.PError&&extractTest.getStatus()==1)
				extractTest.UpdateStudentGradeBeforeFactor(5);
			else if(randomNumber>(this.PError/2)&&extractTest.getStatus()==2){
				extractTest.UpdateStudentGradeBeforeFactor(5);
			}

		}

	}


	private void updateTestStatus(Test extractTest) {
		extractTest.UpdateStatus(1);

	}


	private double generateRandomNumber() {
		double random = Math.random();
		return random;
	}


	private void updateAssitantSalary(double workTime) {
		this.salary+=(workTime*this.PricePerSecond);

	}


	private double TeachingAssistantRandomWorkTime() {		
		double random = ((Math.random() * (2.5 - 1.5)) + 1.5);//generate random number in the range;
		try {
			Thread.sleep((long) (random*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return random;
	}
}
