package javapro;

public class DiceGame {
	
	public static void main(String[] args) {
		int Winner= 0; //A�� �̱�� 1 B�� �̱�� 2
		
		while(true){
			int a = (int)(Math.random()*6+1); // 1~6
			int b = (int)(Math.random()*6+1); // 1~6
	
			if(a>b) {
				System.out.println("A: "+a+" B: "+b);
				System.out.println("A�� �¸��߽��ϴ�! ");
				Winner=1;
				break;
			}
			else if(a<b) {
				System.out.println("A: "+a+" B: "+b);
				System.out.println("B�� �¸��߽��ϴ�! ");
				Winner=2;
				break;
			}
			else if(a==b) {
				System.out.println("A: "+a+" B: "+b);
				System.out.println("�����ϴ�. ");
			}

		}
	}

}
