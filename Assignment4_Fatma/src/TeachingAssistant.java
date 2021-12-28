
public class TeachingAssistant {
	private static  boolean [] correctAnswer=randomAnswer();
	
	
	public TeachingAssistant() {
		
		
		
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
}
