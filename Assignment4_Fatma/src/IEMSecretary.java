
public class IEMSecretary implements Runnable {

	private String secretaryName;
	private static boolean flag;
	
	private int secretaryType;//0-student academic status not valid 1-for valid student academic status


	public  IEMSecretary(String secretaryName,int secretaryType) {
		this.secretaryName=secretaryName;
		flag=true;
		//this.flag2=true;
		this.secretaryType=secretaryType;
		Thread t = new Thread(this); 
		t.start();
		
	}

	public void run() {
		while(flag=true) {
			flag = insertStudentDataToDB(this.secretaryType);
			if(flag==false) {
				break;
			}
		}
		System.out.println(" iem dead");

	}

	private static synchronized boolean insertStudentDataToDB(int secretaryType) {
		while(flag==true) {
			Test extractTest=CourseInformation.Fatma.getTestQueues().elementAt(4).extract();
			if(extractTest.getStudentId()!=-1){//we got the fake exam
				if(secretaryType==0) {
					if (extractTest.getStudentFinalGrade()<70) {
						workOnTest(extractTest,secretaryType);
					}
					else {
						returnTestToCurrentQueue(extractTest);
						break;//exit from this function and return the key
						
					}
				}
				if(secretaryType==1) {
					if (extractTest.getStudentFinalGrade()>=70) {
						workOnTest(extractTest,secretaryType);
					}
					else {
						returnTestToCurrentQueue(extractTest);
						break;
					}
				}
			}else {
				changeFlag();
				return flag;
				//flag=false;
				//passTestToNextQueue(extractTest);//pass the fake exam
			}
			//Double randomTime=SecretaryTimeOfWork();
		}//while
		return flag;
		

	}
	private static void changeFlag() {
		IEMSecretary.flag=false;
		
	}

	private static synchronized void workOnTest(Test extractTest, int secretaryType) {
		if(secretaryType==0) {
			UpdateDB(secretaryType);	
		}
		if(secretaryType==1) {
			UpdateDB(secretaryType);
		}
		PrintStudentInformation(extractTest);
		changeStatus(extractTest);
		passTestToNextQueue(extractTest);
		SecretaryTimeOfWork(secretaryType);

	}

	private static synchronized void PrintStudentInformation(Test extractTest) {
		System.out.println("The student ID is: "+extractTest.getStudentId());
		System.out.println("His final grade is: "+extractTest.getStudentFinalGrade());
		
	}

	private static synchronized void UpdateDB(int secretaryType) {
		if(secretaryType==0) {
				
		}
		if(secretaryType==1) {
			
		}
		
	}


	private static synchronized void returnTestToCurrentQueue(Test extractTest) {
		CourseInformation.Fatma.getTestQueues().elementAt(4).insert(extractTest);

	}


	private static synchronized void passTestToNextQueue(Test extractTest) {
		CourseInformation.Fatma.getTestQueues().elementAt(5).insert(extractTest);
		System.out.println("insert to bounded");

	}

	private static synchronized void changeStatus(Test extractTest) {
		extractTest.setStatus(1);

	}
	private static synchronized void SecretaryTimeOfWork(int secretaryType) {		
		try {
			Thread.sleep((long) ((3-secretaryType)*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
