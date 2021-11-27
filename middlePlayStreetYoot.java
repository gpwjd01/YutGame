package yutgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayStreetYoot implements ActionListener{
	private Player []players; //player 객체 배열
	private YootPan yootPan; // 윷놀이 판
	private int pieceNum; // 말의 개수
	private int playerNum = 2; // 상수 2로 고정되어있지만 편의를 위해 변수로 지정함. player 명 수
	private int firstTurn = 0;//처음에는 선공권으로 활용되고, 나중에는 순서를 가르키는 변수
	private int gameWinner = -1; // 총 게임 승자 ID 
	private int gameResult = 0; // 총 게임 결과
	private Player nowPlayer; // 현재 진행하고 있는 플레이어를 담는 객체
	private int situation = 1; // 윷 던지기, 새로운 말 판에 놓기, 기존의 말 옮기기와 같은 세가지 상황을 결정하는 변수
	
	/**/
	
	PlayStreetYoot(int mal)
	{
		players = new Player[2];
		for(int i=0;i<2;i++)
		{
			players[i] = new Player(i,mal);
		}
		pieceNum = mal;
		yootPan = new YootPan(this);//윷판을 생성함.
		
		for(int i=0; i<playerNum; i++) {
			yootPan.setplayerInfo(i, players[i].playerPiece());//플레이어의 정보를 설정해줌.
			//** yootPan에서 미니 게임 결과와 크루 선정의 정보를 이 메소드를 통해 저장해줘야 될 듯함.
		}
	}
	
	public int getPlayerNum() {
		return playerNum; 
	}
	
	int checkEnd()
	{
		for(int i=0;i<players.length;i++)
		{
			if(players[i].getArrivePiece() == pieceNum)
			{
				players = null;
				yootPan.finishMessage(i); //** 마지막 게임 승자 결과 출력하는 메소드 구현해야 함.
				return i;// 승리 플레이어의 넘버
			}
		}
		return -1;
	}
	
	int checkCatch(int index)//지금 플레이어 인덱스값
	{
		Player catcher = players[index];//catcher는 지금의 플레이어
		int catchX,catchY;
		for(int n =0; n < catcher.getPieces().size() ; n++)//현재 플레이어의 모든 말
		{ // 말의 개수 2로 고정이면 size() -> 2로 가능
			catchX = catcher.getPieces().get(n).getX();
			catchY = catcher.getPieces().get(n).getY();//현재 플레이어의 n번째 말 위치
			for(int i = 0 ; i < playerNum ; i++)
			{
				if(i!=index)
				{
					if(players[i].checkCatch(catchX,catchY)==1)//i번째 Player의 말들과 비교해서 같으면 없앰
					{
						yootPan.message("P"+index+"가 P"+i+"의 말을 잡았다"); // **말을 잡았을 때 GUI에서 출력, 이는 선택하여 안 해도 되긴 함.
						return 1;//한칸에 서로 다른 플레이어의 말이 겹쳐있지 않으므로 그냥 만나자 마자 종료
					}
				}
			}
		}
		return 0;
	}
	

	void throwYut()
	{
		if(situation == 1) {
			gameResult = 0;
			nowPlayer = players[firstTurn];
			if(nowPlayer.getPieces().size() == 0 && nowPlayer.getRestPiece()>0) {//판위에 말이 없고 대기중인 말이 있다면 0,0에 새로 만들고
				boardMessage("판 위에 올라가 있는 말 없음");
			}
			else
			{
				boardMessage("");
			}
			yootPan.changePlayer(firstTurn);
			gameResult=Yoot.throwing();//던지기 버튼 클릭
			yootPan.printResult(gameResult);//던진 결과 화면에 출력
			phaze1changeBtncolor();//UI 버튼 색깔 변경
			
			for(int i=0; i<playerNum; i++) {
				yootPan.setplayerInfo(i, players[i].playerPiece());
			}

			situation = 3;
		}
	}
	
	void putPiece()
	{
		if(situation==2)
		{
			//말이 있으면 Player에서 알아서 찾고 도개걸 결과로 이동함
			if(nowPlayer.createPiece()==1) //대기중인 말의 수가 있다면 새로 생성 가능
			{
				nowPlayer.move(0, 0, gameResult); //여기서 알아서 업어가는지 판단해줌
				
				for(int i=0; i<playerNum; i++) {
					yootPan.setplayerInfo(i, players[i].playerPiece());
				}
				
				yootPan.printPiece(firstTurn,0,gameResult,nowPlayer.getPieceCarryNum(0,gameResult));//플레이어, 이동 이후 좌표
				phaze2changeBtncolor();//UI 버튼 색깔 변경
				if(checkCatch(firstTurn)==1 || gameResult == 4 || gameResult ==5)//다시 윷 던지기 조건
				{
					situation=1;
					throwYut();
				}
				else
				{
					nextTurn();//지금 플레이어 턴 종료
				}
			}
			else
			{
				boardMessage("더 이상 말을 생성 할 수 없습니다.");
				situation=3;//말 생성 한도를 넘어가면 phaze3Pieceact가 작동 할 수 있도록 한다.
			}
		}
	}
	
	void pieceMove(int posx, int posy)
	{
		int index;
		int x,y,point;
		
		if(situation==3)
		{// if(지금 플레이어가 판 위에 올려 놓은 말의 갯수가 0 && 지금 플레이어의 남은 말 0 이상(남은 말=전체 말 - 골인한 말))
			if(nowPlayer.getPieces().size()==0 && nowPlayer.getRestPiece()>0)//판위에 말이 없고 대기중인 말이 있다면 0,0에 새로 만들고
			{
				situation=2;
				putPiece();
			}
			else
			{
				index = nowPlayer.checkExist(posx, posy);//해당 버튼에 말이 있는지 확인 있으면 말 배열에 인덱스 반환
				if(index!=-1)
				{
					//말이 있으면 Player에서 알아서 찾고 도개걸 결과로 이동함
					yootPan.printPiece(4, posx, posy, 0);//가기전에 흰색으로 원상 복구 후 이동
					if(nowPlayer.move(posx, posy, gameResult)==1) //여기서 알아서 업어가는지 판단해줌
					{//들어가거나 겹쳐졌을때 화면에 표시를 안한다 이것때문에 자꾸 오류가 난다.
						boardMessage("P "+firstTurn+" 말 하나가 업혔습니다");
						if(posx == 0 && posy == 5) {
							posx = 1;
							posy = 0;
						}
						else if(posx == 0 && posy==10){
							posx=2;
							posy=0;
						}
						posy=posy+gameResult;
						if(gameResult > 0) {
							if(posx == 1 && posy==3)
							{
								posx=2;
								posy=3;
							}
							else if(posx==1 && posy>5){
								posx=0;
								posy+=9;
							}
							else if(posx==2 && posy>5) {
								posx=0;
								posy+=14;
							}
						}else {
							if(posx==1 && posy<1) {
								posx=0;
								posy=5+posy;
							}else if(posx==1 && posy==3) {
								posx=2;
								posy=3;
							}else if(posx==2 && posy<1) {
								posx=0;
								posy=10+posy;
							}else if(posx==0 && posy<1) {
								posy=20+posy;
							}
						}
						index = nowPlayer.checkExist(posx, posy);
						//이 지점에서 생기는 버그: 말 A가 이동해 말 B 위에 업혔다. 그럼 말 B point += 말 A point 하고 말 A 객체 삭제
						//말 A가 삭제되었으니 piece(==말)의 Arraylist의 index에 말이 없어서 범위 익셉션 뜸 그래서 index를 갱신해 줘야함
						x = nowPlayer.getPieces().get(index).getX();
						y = nowPlayer.getPieces().get(index).getY();
						point = nowPlayer.getPieces().get(index).getPoint();
						yootPan.printPiece(firstTurn,x,y,point);
					}
					else if(nowPlayer.checkPieceIn() ==1)
					{
						boardMessage("P "+firstTurn+" 말 하나가 골인했습니다");
					}
					else
					{
						x = nowPlayer.getPieces().get(index).getX();
						y = nowPlayer.getPieces().get(index).getY();
						point = nowPlayer.getPieces().get(index).getPoint();
						yootPan.printPiece(firstTurn,x,y,point);//플레이어, 이동 이후 좌표

					}

					for(int i=0; i<playerNum; i++) {
						yootPan.setplayerInfo(i, players[i].playerPiece());
					}
					
					phaze2changeBtncolor();//UI 버튼 색깔 변경
					if(nowPlayer.getRestPiece()<=0 && nowPlayer.getPieces().size()<=0)//대기중인 말과 판위에 말이 없으면
					{
						situation=-1;//경기 종료
						System.out.println("경기 종료");
						nextTurn();//해당 플레이어 턴 종료
					}
					else//게임이 끝나지 않았다면 (이렇게 해놔야 자바 익셉션 안뜸)
					{
						if(checkCatch(firstTurn)==1 || gameResult == 4 || gameResult ==5)//다시 윷 던지기 조건
						{
							situation=1;
							throwYut();
						}
						else
						{
							nextTurn();//해당 플레이어 턴 종료
						}
					}
				}
				else
				{
					boardMessage("엉뚱한 버튼 클릭함"+posx +" , "+posy);
				}
			}
		}
		else
		{
			//아직 윷을 던지지도 않았는데 판 클릭하면 아무 동작 안함
		}
	}

	void nextTurn()
	{
		gameWinner = checkEnd();
		if(gameWinner != -1)
		{
			situation=5;//아무 동작 안함
			System.out.println(gameWinner+ " 번째 플레이어가 승리하였습니다.");
		}
		else 
		{
			firstTurn++;
			firstTurn = firstTurn % 2;
			/*
			firstTurn++;
			if(firstTurn >= playerNum)
			{
				firstTurn =0;
			}
			*/
			boardMessage("P "+firstTurn+" 차례");
			boardRefreashFrame();//다음 플레이어로 넘어가니까 Piece 그림 바꿔줌
			initBtncolor();
			situation = 1;
		}
	}
	
	
	
	//GUI 쪽 코드 이는 엮을 때 변경 필요함.
	//******************************************************************
	
	void boardMessage(String s) {
		yootPan.message(s);
	}
	
	void boardRefreashFrame() {
		yootPan.refreashFrame();
	}
	
	void phaze1changeBtncolor() {
		yootPan.buttonColor("throwBtnOFF");
		if(nowPlayer.getRestPiece()>0)//대기중인 말이 있다면 
		{
			yootPan.buttonColor("newPieceBtnON");//새로운 말 버튼 활성화
		}
		if(nowPlayer.getPieces().size()>0)//판에 말이 있다면 
		{
			yootPan.buttonColor("clickBoardON");//판 클릭 버튼 활성화
		}
	}
	
	void phaze2changeBtncolor() {
		if(nowPlayer.getPieces().size()>0)
		{
			yootPan.buttonColor("clickBoardON");
		}
		else
		{
			yootPan.buttonColor("clickBoardOFF");
		}
		if(nowPlayer.getRestPiece()>0)
		{
			yootPan.buttonColor("newPieceBtnON");
		}
		else
		{
			yootPan.buttonColor("newPieceBtnOFF");
		}
	}
	
	void initBtncolor() {
		yootPan.buttonColor("throwBtnON");
		yootPan.buttonColor("newPieceBtnOFF");
		yootPan.buttonColor("clickBoardOFF");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==yootPan.throwButton && situation == 1)
		{
			throwYut();
		}
		if(e.getSource()==yootPan.newPiece && situation == 3)
		{
			situation=2;
			putPiece();
		}
		if(situation==3) {
			for(int i=1;i<21;i++) {
				if(e.getSource()==yootPan.panButton[0][i])
				{
					pieceMove(0,i);
				}
			}
			for(int p=1;p<6;p++) {
				if(e.getSource()==yootPan.panButton[1][p])
				{
					pieceMove(1,p);
				}	
			}
			for(int q=1;q<6;q++) {
				if(e.getSource()==yootPan.panButton[2][q])
				{
					pieceMove(2,q);
				}
			}
		}
		for(int r=0;r<6;r++)
		{
			if(e.getSource()==yootPan.testButton[r] && situation == 1)
			{
				throwYut();
				r--;
				if(r==0)
				{
					gameResult = 5;
				}
				else
				{
					gameResult = r;
				}
				yootPan.printResult(gameResult);//던진 결과 화면에 출력
			}
		}
	}
}
