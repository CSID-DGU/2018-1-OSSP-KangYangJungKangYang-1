package com.tetris.network;

import java.io.*;

public class DataShip implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final int CLOSE_NETWORK = 0;
	public static final int EXIT = 1;
	public static final int SERVER_EXIT = 2;
	public static final int PRINT_SYSTEM_OPEN_MESSAGE = 3;
	public static final int PRINT_SYSTEM_ADDMEMBER_MESSAGE = 4;
	public static final int GAME_START = 5;
	public static final int GAME_OVER = 6;
	public static final int ADD_BLOCK = 7;
	public static final int SET_INDEX = 8;
	public static final int PRINT_MESSAGE = 9;
	public static final int PRINT_SYSTEM_MESSAGE=10;
	public static final int GAME_WIN=11;
	public static final int ADD_SCORE = 12;
	
	
	private int cmd = -1;
	private String name;
	private String ip;
	private String msg;
	private int numOfBlock;
	private int index;
	private int rank;
	private boolean isPlay;
	private int totalAdd;
	private int speed;
	private int score;
	
	

	public DataShip(){}
	public DataShip(int cmd){this.cmd = cmd;}

	
	public void setCommand(int type){this.cmd = type;};
	public int getCommand(){return cmd;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getIp() {return ip;}
	public void setIp(String ip) {this.ip = ip;}
	public String getMsg() {return msg;}
	public void setMsg(String msg) {this.msg = msg;}
	public int getNumOfBlock() {return numOfBlock;}
	public void setNumOfBlock(int numOfBlock) {this.numOfBlock = numOfBlock;}
	public int getIndex() {return index;}
	public void setIndex(int index) {this.index = index;}
	public int getRank() {return rank;}
	public void setRank(int rank) {this.rank = rank;}
	public boolean isPlay() {return isPlay;}
	public void setPlay(boolean isPlay) {this.isPlay = isPlay;}
	public int getTotalAdd() {return totalAdd;}
	public void setTotalAdd(int totalAdd) {this.totalAdd = totalAdd;}
	public int getSpeed() {return speed;}
	public void setSpeed(int speed) {this.speed = speed;}
	public int getScore() {return score;}
	public void setScore(int score) {this.score = score;}
}
