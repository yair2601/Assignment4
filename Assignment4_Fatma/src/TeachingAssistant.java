
public class TeachingAssistant implements Runnable{
	private static  boolean [] correctAnswer=randomAnswer();
	private String teachingAssistantName;
	private int pricePerSecond;
	private double salary;
	private double PError;
	private boolean flag;//change while we are getting a fake exam

	public TeachingAssistant(String teachingAssistantName,int PricePerSecond,double Perror) {//constructor
		this.teachingAssistantName=teachingAssistantName;
		this.pricePerSecond=PricePerSecond;
		this.PError=Perror;
		this.flag=true;
 

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


	public static boolean [] getCorrectAnswer() {//get the correct test answers
		return correctAnswer;
	}


	public void run() {// thread run
		Queue<Test> BufferAAssistant = CourseInformation.Fatma.getTestQueues().elementAt(0);
		Queue<Test> BufferBAssistant = CourseInformation.Fatma.getTestQueues().elementAt(1);

		if(this.teachingAssistantName.equals("Maya")){
			while(flag==true) {
				Test extractTest=BufferAAssistant.extract();
				System.out.println("im in assitstant 1");
				if(extractTest.getStudentId()!=-1)//we got the fake exam
					checkTest(extractTest,0,BufferAAssistant,BufferBAssistant);//indicator 0 for maya
				else 
					flag=false;

			}//while

		}
		else {//lior
			while(flag==true) {
				Test extractTest=BufferBAssistant.extract();
				System.out.println("im in assitstant 2");
				if(extractTest.getStudentId()!=-1)//we got the fake exam
					checkTest(extractTest,1,BufferAAssistant,BufferBAssistant);//indicator 1 for Lior
				else 
					flag=false;
			}
		}
		updateTotalSalary();
		System.out.println("teachingAssist dead");
	}

	private void updateTotalSalary() {//update the total salary in the course information
		CourseInformation.Fatma.setSalaryCost(salary);
		
	}
	private void checkTest(Test extractTest, int indicator, Queue<Test> bufferAAssistant, Queue<Test> bufferBAssistant) {// check the test
		double workTime=TeachingAssistantRandomWorkTime();
		updateAssitantSalary(workTime);
		if(extractTest.getStatus()==1){//test not checked yet
//			for (int i=0;i<extractTest.getStudentAnswer().length;i++) {
//				//checkQuestion (i,extractTest); need to check if needed i think the grade is just for the second check
//			}
			updateTestStatus(extractTest);
			moveTestToSecondAssitent(extractTest,indicator,bufferAAssistant,bufferBAssistant);
		}

		else if(extractTest.getStatus()==2){//test already checked one time
			for (int i=0;i<extractTest.getStudentAnswer().length;i++) {
				checkQuestion (i,extractTest);
			}
			updateTestStatus(extractTest);
			moveTestToLecturer(extractTest);
		}
	}
	private void moveTestToLecturer(Test extractTest) {//put this exam in the lecturer queue
		Queue<Test> BufferToLecturer = CourseInformation.Fatma.getTestQueues().elementAt(2);
		BufferToLecturer.insert(extractTest);

	}


	private void moveTestToSecondAssitent(Test extractTest, int indicator, Queue<Test> bufferAAssistant, Queue<Test> bufferBAssistant) {//put this exam in the second assistant queue
		if(indicator==0) {
			bufferBAssistant.insert(extractTest);
		}
		if(indicator==1) {
			bufferAAssistant.insert(extractTest);
		}
	}


	private void checkQuestion(int i, Test extractTest) {//check if the question is correct and give a score on the question
		if(extractTest.getStudentAnswer()[i]==correctAnswer[i]) {
			extractTest.UpdateStudentGradeBeforeFactor(5);
			extractTest.setCorrectAnswers(1);
		}
		else {
			double randomNumber=generateRandomNumber();
			if(randomNumber<this.PError&&extractTest.getStatus()==1)
				extractTest.UpdateStudentGradeBeforeFactor(5);
			else if(randomNumber<(this.PError/2)&&extractTest.getStatus()==2){
				extractTest.UpdateStudentGradeBeforeFactor(5);
				extractTest.setCorrectAnswers(1);
			}

		}

	}


	private void updateTestStatus(Test extractTest) {//update the test status according to his location in the process
		System.out.println("teaching assis status");
		extractTest.setStatus(1);

	}


	private double generateRandomNumber() {//generate a random number
		double random = Math.random();
		return random;
	}


	private void updateAssitantSalary(double workTime) {// update the assistant salary according to her work time 
		this.salary+=(workTime*this.pricePerSecond);

	}
	public double getSalary() {// get the salary
		return this.salary;
	}


	private double TeachingAssistantRandomWorkTime() {//calculate teaching assistant work time
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
