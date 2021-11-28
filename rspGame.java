//윷레디에 포함-시연
package javapro;

public class rspGame {

	public static void main(String[] args) {
		int Winner= 0; //A가 이기면 1 B가 이기면 2
		while(true){ //무한 루프
			int a = (int)(Math.random()*3+1); //1~3 사이의 난수 발생
			int b = (int)(Math.random()*3+1); //1~3 사이의 난수 발생
			
			if(b==1){ //컴퓨터 가위
				if(a==1){ //사용자 가위
					System.out.println("A: 가위   B: 가위");
					System.out.println("비겼습니다.");
				}
				if(a==2){ //사용자 바위
					System.out.println("A: 바위   B: 가위");
					System.out.println("A가 승리했습니다! ");
					Winner=1;
					break;
				}
				if(a==3){ //사용자 보
					System.out.println("A: 보  B: 가위");
					System.out.println("B가 승리했습니다! ");
					Winner=2;
					break;
				}
			}
			else if (b==2){ //컴퓨터 바위
				if(a==1){ //사용자 가위
					System.out.println("A: 가위  B: 바위");
					System.out.println("B가 승리했습니다! ");
					Winner=2;
					break;
				}
				if(a==2){ //사용자 바위
					System.out.println("A: 바위  B: 바위");
					System.out.println("비겼습니다.");
				}
				if(a==3){ //사용자 보
					System.out.println("A: 보  B: 바위");
					System.out.println("A가 승리했습니다! ");
					Winner=1;
					break;
				}
			}
			else if (b==3){ //컴퓨터 보
				if(a==1){ //사용자 가위
					System.out.println("A: 가위  B: 보");
					System.out.println("A가 승리했습니다! ");
					Winner=1;
					break;
				}
				if(a==2){  //사용자 바위
					System.out.println("A 바위  B: 보");
					System.out.println("B가 승리했습니다! ");
					Winner=2;
					break;
				}
				if(a==3){  //사용자 보
					System.out.println("A: 보  B: 보");
					System.out.println("비겼습니다.");
				}
			}
		}

	}

}
