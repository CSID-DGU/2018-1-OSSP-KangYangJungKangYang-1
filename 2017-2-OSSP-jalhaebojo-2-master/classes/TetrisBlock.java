package com.tetris.classes;

import java.awt.Color;
import java.awt.Graphics;

//테트리스 블럭
public abstract class TetrisBlock {
	/* TetrisBlock Type*/
	public static final int TYPE_CENTERUP = 0 ;
	public static final int TYPE_LEFTTWOUP = 1 ;
	public static final int TYPE_LEFTUP = 2 ;
	public static final int TYPE_LINE = 3 ;
	public static final int TYPE_NEMO = 4 ;
	public static final int TYPE_RIGHTTWOUP = 5 ;
	public static final int TYPE_RIGHTUP = 6 ;
	
	/* Rotation Index */
	public static final int ROTATION_0 = 0;			//원래 모양의   0도 회전
	public static final int ROTATION_90 = 1;		//원래 모양의  90도 회전
	public static final int ROTATION_180 = 2;		//원래 모양의 180도 회전
	public static final int ROTATION_270 = 3;		//원래 모양의 270도 회전
	
	/* Rotation Type */
	public static final int ROTATION_LEFT = 1;		//시계방향회전
	public static final int ROTATION_RIGHT = -1;	//반시계방향회전
	
	/* 그외 필드 */
	protected int type;								//블럭모양;
	protected Block[] colBlock= new Block[4];		//모양을 나타내는 4개블럭
	protected int rotation_index;					//블럭회전 모양
	protected int posX,posY;						//모양의 좌표
	protected Color color;							//블록색상
	
	
	
	
	public TetrisBlock(int x, int y, Color color, Color ghostColor) {
		this.color = color;
		for(int i=0 ; i<colBlock.length ; i++){
			colBlock[i] = new Block(0,0,color,ghostColor);
		}
		this.rotation(ROTATION_0); //기본 회전모양 : 0도
		this.setPosX(x);
		this.setPosY(y);
	}
	
	
	/**
	 * 테트리스 블럭모양을 회전한다. 
	 * @param rotation_index : 회전모양
	 * ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270
	 */
	public abstract void rotation(int rotation_index);
	
	
	/**
	 * 테트리스 블럭모양을 왼쪽으로 이동시킨다.
	 * @param addX : 이동양
	 * 0이상의 값을 넣어야 한다.
	 */
	public void moveLeft(int addX) {this.setPosX(this.getPosX()-addX);}
	
	
	/**
	 * 테트리스 블럭모양을 오른쪽으로 이동시킨다.
	 * @param addX : 이동양
	 * 0이상의 값을 넣어야 한다.
	 */
	public void moveRight(int addX) {this.setPosX(this.getPosX()+addX);}
	
	
	/**
	 * 테트리스 블럭모양을 아래로 이동시킨다.
	 * @param addY : 이동양
	 * 0이상의 값을 넣어야 한다.
	 */
	public void moveDown(int addY) {this.setPosY(this.getPosY()+addY);}
	
	
	/**
	 * 테트리스 블럭을 Graphics를 이용하여 그린다.
	 * @param g
	 */
	public void drawBlock(Graphics g){
		for(Block col : colBlock){
			if(col!=null)col.drawColorBlock(g);
		}
	}
	
	

	/* Getter */
	public Block[] getBlock() {return colBlock;}
	public Block getBlock(int index) {return colBlock[index];}
	public int getPosX() {return posX;}
	public int getPosY() {return posY;}
	public int getRotationIndex() {return rotation_index;}
	public int getType() {return type;}
	
	
	/* Setter */
	public void setType(int type) {this.type = type;}
	public void setBlock(Block[] blocks) {this.colBlock = blocks;}
	public void setBlock(int index, Block block) {this.colBlock[index] = block;}
	public void setPosX(int x) {
		this.posX = x;
		for(int i=0; i<colBlock.length ;i++){
			if(colBlock[i]!=null)colBlock[i].setPosGridX(x);
		}
	}
	public void setPosY(int y) {
		this.posY = y;
		for(int i=0; i<colBlock.length ;i++){
			if(colBlock[i]!=null)colBlock[i].setPosGridY(y);
		}
	}
	public void setGhostView(boolean b){
		for(int i=0; i<colBlock.length ;i++){
			if(colBlock[i]!=null)colBlock[i].setGhostView(b);
		}
	}


}
