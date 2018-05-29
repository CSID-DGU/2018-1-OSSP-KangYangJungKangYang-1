package com.tetris.classes;

import java.awt.Color;
import java.awt.Graphics;

import com.tetris.window.TetrisBoard;

public class Block {
	private int size = TetrisBoard.BLOCK_SIZE;
	private int width = size, height = size;
	private int gap = 3;
	private int fixGridX, fixGridY;
	private int posGridX, posGridY;
	private Color color;
	private Color ghostColor;	
	private boolean ghost;
	
	
	/**
	 * 
	 * @param fixGridX :
	 * @param fixGridY :
	 * @param color :
	 */
	public Block(int fixGridX, int fixGridY, Color color, Color ghostColor) {
		this.fixGridX = fixGridX;
		this.fixGridY = fixGridY;
		this.color=color;
		this.ghostColor = ghostColor;
	}
	

	/**
	 * ?????? ??????.
	 * @param g
	 */
	public void drawColorBlock(Graphics g){
		if(ghost)g.setColor(ghostColor);
		else g.setColor(color);
		g.fillRect((fixGridX+posGridX)*size + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size + TetrisBoard.BOARD_Y, width, height);
		g.setColor(Color.BLACK); //
		g.drawRect((fixGridX+posGridX)*size + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size + TetrisBoard.BOARD_Y, width, height);
		// ???? \
		//g.drawLine((fixGridX+posGridX)*size + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size + TetrisBoard.BOARD_Y, (fixGridX+posGridX)*size+width + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size+height + TetrisBoard.BOARD_Y);
		// ???? /
		//g.drawLine((fixGridX+posGridX)*size + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size+height + TetrisBoard.BOARD_Y, (fixGridX+posGridX)*size+width + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size + TetrisBoard.BOARD_Y);
		if(ghost)g.setColor(ghostColor);
		else g.setColor(color);
		//g.fillRect((fixGridX+posGridX)*size+gap + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size+gap + TetrisBoard.BOARD_Y, width-gap*2, height-gap*2);
		g.setColor(Color.BLACK);
		//g.drawRect((fixGridX+posGridX)*size+gap + TetrisBoard.BOARD_X, (fixGridY+posGridY)*size+gap + TetrisBoard.BOARD_Y, width-gap*2, height-gap*2);
	}
	
	/**
	 * ???? ???? ????????? ???????.
	 * @return ??????? X???????
	 */
	public int getX(){return posGridX + fixGridX;}	
	
	
	/**
	 * ???? ???? ????????? ???????.
	 * @return ??????? Y???????
	 */
	public int getY(){return posGridY + fixGridY;}

	
	/**
	 * Getter Setter
	 */
	public int getPosGridX(){return this.posGridX;}
	public int getPosGridY(){return this.posGridY;}
	public void setPosGridX(int posGridX) {this.posGridX = posGridX;}
	public void setPosGridY(int posGridY) {this.posGridY = posGridY;}
	public void setPosGridXY(int posGridX, int posGridY){this.posGridX = posGridX;this.posGridY = posGridY;}
	public void setFixGridX(int fixGridX) {this.fixGridX = fixGridX;}
	public void setFixGridY(int fixGridY) {this.fixGridY = fixGridY;}
	public void setFixGridXY(int fixGridX, int fixGridY){this.fixGridX = fixGridX;this.fixGridY = fixGridY;}
	public void setGhostView(boolean b){this.ghost = b;}
}
