package javapro;

public class rspGame {

	public static void main(String[] args) {
		int Winner= 0; //A�� �̱�� 1 B�� �̱�� 2
		while(true){ //���� ����
			int a = (int)(Math.random()*3+1); //1~3 ������ ���� �߻�
			int b = (int)(Math.random()*3+1); //1~3 ������ ���� �߻�
			
			if(b==1){ //��ǻ�� ����
				if(a==1){ //����� ����
					System.out.println("A: ����   B: ����");
					System.out.println("�����ϴ�.");
				}
				if(a==2){ //����� ����
					System.out.println("A: ����   B: ����");
					System.out.println("A�� �¸��߽��ϴ�! ");
					Winner=1;
					break;
				}
				if(a==3){ //����� ��
					System.out.println("A: ��  B: ����");
					System.out.println("B�� �¸��߽��ϴ�! ");
					Winner=2;
					break;
				}
			}
			else if (b==2){ //��ǻ�� ����
				if(a==1){ //����� ����
					System.out.println("A: ����  B: ����");
					System.out.println("B�� �¸��߽��ϴ�! ");
					Winner=2;
					break;
				}
				if(a==2){ //����� ����
					System.out.println("A: ����  B: ����");
					System.out.println("�����ϴ�.");
				}
				if(a==3){ //����� ��
					System.out.println("A: ��  B: ����");
					System.out.println("A�� �¸��߽��ϴ�! ");
					Winner=1;
					break;
				}
			}
			else if (b==3){ //��ǻ�� ��
				if(a==1){ //����� ����
					System.out.println("A: ����  B: ��");
					System.out.println("A�� �¸��߽��ϴ�! ");
					Winner=1;
					break;
				}
				if(a==2){  //����� ����
					System.out.println("A ����  B: ��");
					System.out.println("B�� �¸��߽��ϴ�! ");
					Winner=2;
					break;
				}
				if(a==3){  //����� ��
					System.out.println("A: ��  B: ��");
					System.out.println("�����ϴ�.");
				}
			}
		}

	}

}
