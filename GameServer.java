package com.tetris.network;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//TODO:--------------------------[ 핸들러 ]--------------------------
class GameHandler extends Thread{
	private static boolean isStartGame;
	private static int maxRank;
	private int rank;
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String ip;
	private String name;
	private int index;
	private int totalAdd=0;
	private int score=0;
	
	private ArrayList<GameHandler> list;
	private ArrayList<Integer> indexList;
	
	public GameHandler(Socket socket,ArrayList<GameHandler> list, int index, ArrayList<Integer> indexList){
		this.index = index;
		this.indexList = indexList;
		this.socket = socket;
		this.list = list;
		try{
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream()); 
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			DataShip data = (DataShip)ois.readObject();
			ip = data.getIp();
			name = data.getName();
			
			data = (DataShip)ois.readObject();
			printSystemOpenMessage();
			printMessage(ip+":"+name+" is Enter.");
			InetAddress local = InetAddress.getLocalHost();
			String my_ip = local.getHostAddress();
			if(!my_ip.equals(ip)) {
				JOptionPane.showMessageDialog(null, "Other Player is Enter");
			}
		}catch(IOException e){ e.printStackTrace();
		}catch(ClassNotFoundException e){ e.printStackTrace();}
		
		
	}//GameHandler


//TODO:--------------------------[ 요청 대기 ]-------------------------
	public void run(){
		DataShip data = null;
		while(true){
			try{
				data = (DataShip)ois.readObject();
			}catch(IOException e){ e.printStackTrace(); break;
			}catch(ClassNotFoundException e){e.printStackTrace();}

			if(data==null)continue;
			
			if(data.getCommand()==DataShip.CLOSE_NETWORK){
				printSystemMessage("<"+index+"P> EXIT");
				printMessage(ip+":"+name+" is Exit.");
				closeNetwork();
				break;
			}else if(data.getCommand()==DataShip.SERVER_EXIT){
				exitServer();
			}else if(data.getCommand()==DataShip.PRINT_SYSTEM_OPEN_MESSAGE){
				printSystemOpenMessage();
			}else if(data.getCommand()==DataShip.PRINT_SYSTEM_ADDMEMBER_MESSAGE){
				printSystemAddMemberMessage();
			}else if(data.getCommand()==DataShip.ADD_BLOCK){
				addBlock(data.getNumOfBlock());
			}else if(data.getCommand()==DataShip.GAME_START){
				gameStart(data.getSpeed());
			}else if(data.getCommand()==DataShip.SET_INDEX){
				setIndex();
			}else if(data.getCommand()==DataShip.GAME_OVER){
				rank = maxRank--;
				gameover(rank);
			}else if(data.getCommand()==DataShip.PRINT_MESSAGE){
				printMessage(data.getMsg());
			}else if(data.getCommand()==DataShip.PRINT_SYSTEM_MESSAGE){
				printSystemMessage(data.getMsg());
			}else if(data.getCommand()==DataShip.ADD_SCORE) {
				
			}
			
		}//while(true)
		
		try {
			list.remove(this);
			ois.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}//run
	
	public void printMessage(String msg) {
		DataShip data = new DataShip(DataShip.PRINT_MESSAGE);
		data.setMsg(name+"("+index+"P)>" + msg);
		broadcast(data);
	}


	//응답하기 : 네트워크종료
	public void closeNetwork() {
		DataShip data = new DataShip(DataShip.CLOSE_NETWORK);
		indexList.add(index);
		
		int tmp;
		if(indexList.size()>1){
			for(int i=0;i<indexList.size()-1;i++){
				if(indexList.get(i) > indexList.get(i+1)){
					tmp = indexList.get(i+1);
					indexList.remove(i+1);
					indexList.add(i,new Integer(tmp));	
				}
			}
		}
		send(data);
	}
	//응답하기 : 서버종료
	public void exitServer(){
		DataShip data = new DataShip(DataShip.SERVER_EXIT);
		broadcast(data);
	}
	//응답하기 : 게임시작
	public void gameStart(int speed){
		isStartGame = true;
		totalAdd = 0;
		score = 0;
		maxRank = list.size();
		DataShip data = new DataShip(DataShip.GAME_START);
		data.setPlay(true);
		data.setSpeed(speed);
		data.setMsg("<Game Start>");
		broadcast(data);
		for(int i=0 ; i<list.size() ;i++){
			GameHandler handler = list.get(i);
			handler.setRank(0);
		}
	}
	public void printSystemOpenMessage(){
		DataShip data = new DataShip(DataShip.PRINT_SYSTEM_MESSAGE);
		StringBuffer sb = new StringBuffer();
		for(int i=0 ;i<list.size();i++){
			sb.append("<"+list.get(i).index+"P> "+list.get(i).ip + ":" + list.get(i).name);
			if(i<list.size()-1)sb.append("\n");
		}
		data.setMsg(sb.toString());
		send(data);
	}
	public void printSystemAddMemberMessage(){
		DataShip data = new DataShip(DataShip.PRINT_SYSTEM_MESSAGE);
		data.setMsg("<"+index+"P> "+ip + ":" + name);
		broadcast(data);
	}
	public void printSystemWinMessage(int index){
		DataShip data = new DataShip(DataShip.PRINT_SYSTEM_MESSAGE);
		data.setMsg(index+"P> WIN");
		broadcast(data);
	}
	public void printSystemMessage(String msg){
		DataShip data = new DataShip(DataShip.PRINT_SYSTEM_MESSAGE);
		data.setMsg(msg);
		broadcast(data);
	}
	//응답하기 : 블럭추가
	public void addBlock(int numOfBlock){
		DataShip data = new DataShip(DataShip.ADD_BLOCK);
		data.setNumOfBlock(numOfBlock);
		data.setMsg(index+"P -> ADD:"+numOfBlock);
		data.setIndex(index);
		totalAdd+=numOfBlock;
		broadcast(data);
	}
	//응답하기 : 점수추가
	public void addScore(int numOfBlock) {
		
	}
	//응답하기 : 인덱스주기
	public void setIndex(){
		DataShip data = new DataShip(DataShip.SET_INDEX);
		data.setIndex(index);
		send(data);
	}
	//응답하기 : 게임오버
	public void gameover(int rank){
		DataShip data = new DataShip(DataShip.GAME_OVER);
		data.setMsg(index+"P -> OVER:"+rank);
		data.setIndex(index);
		data.setPlay(false);
		data.setRank(rank);
		data.setTotalAdd(totalAdd);
		broadcast(data);
		
		if(rank == 2){
			isStartGame = false;
			for(int i=0 ; i<list.size() ;i++){
				GameHandler handler = list.get(i);
				if(handler.getRank() == 0){
					handler.win();
				}		
			}
		}
	}
	public void win(){
		DataShip data = new DataShip(DataShip.GAME_WIN);
		data.setMsg(index+"P -> WIN");
		data.setTotalAdd(totalAdd);
		broadcast(data);
	}
	
	
	
//TODO:--------------------------[ 명령 전송 ]--------------------------[완료]
	//1명
	private void send(DataShip dataShip){
		try{
			oos.writeObject(dataShip);
			oos.flush();
		}catch(IOException e){e.printStackTrace();}
	}
	
	//n명
	private void broadcast(DataShip dataShip){
		for(int i=1 ; i<list.size() ; i++){
			GameHandler handler = list.get(i);
			if(handler!=null){
				try{
					handler.getOOS().writeObject(dataShip);
					handler.getOOS().flush();
				}catch(IOException e){e.printStackTrace();}
			}
		}

	}// broadcast
	
	public ObjectOutputStream getOOS(){return oos;}
	public int getRank() {return rank;}
	public void setRank(int rank){this.rank = rank;}
	public boolean isPlay(){return isStartGame;}
}//GameHandler



//TODO:--------------------------[ 서버 ]--------------------------[완료]
public class GameServer implements Runnable{
	private ServerSocket ss;
	private ArrayList<GameHandler> list = new ArrayList<GameHandler>();
	private ArrayList<Integer> indexList = new ArrayList<Integer>();
	private int index=1;
	
	public GameServer(int port){
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//GameServer()	
	
	public void startServer(){
		System.out.println("서버가 작동하고 있습니다.");
		index=1;
		new Thread(this).start();
	}

	public ServerSocket getSS(){ return this.ss; }

	@Override
	public void run() {
		try{
			while(true){
				synchronized (GameServer.class) {
					
				Socket socket = ss.accept();
				int index;
				if(indexList.size()>0) {
					index = indexList.get(0);
					indexList.remove(0);
				}else index = this.index++;
				GameHandler handler = new GameHandler(socket,list,index,indexList);
				list.add(handler);
				handler.start();

				}
			}//while(true)

		}catch(IOException e){
			e.printStackTrace();
		}
	}
}//GameServer