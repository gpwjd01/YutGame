package javapro;

public class DiceGame {
	
	public static void main(String[] args) {
		int Winner= 0; //A가 이기면 1 B가 이기면 2
		
		while(true){
			int a = (int)(Math.random()*6+1); // 1~6
			int b = (int)(Math.random()*6+1); // 1~6
	
			if(a>b) {
				System.out.println("A: "+a+" B: "+b);
				System.out.println("A가 승리했습니다! ");
				Winner=1;
				break;
			}
			else if(a<b) {
				System.out.println("A: "+a+" B: "+b);
				System.out.println("B가 승리했습니다! ");
				Winner=2;
				break;
			}
			else if(a==b) {
				System.out.println("A: "+a+" B: "+b);
				System.out.println("비겼습니다. ");
			}

		}
	}

}
