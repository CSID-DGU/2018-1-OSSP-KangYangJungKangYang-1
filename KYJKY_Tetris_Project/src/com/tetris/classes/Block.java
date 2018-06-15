package com.tetris.classes;

import java.awt.Color;
import java.awt.Graphics;

import com.tetris.window.MultiPlay;

public class Block {
	private int size = MultiPlay.BLOCK_SIZE;
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
	 * �簢���� �׷��ش�.
	 * @param g
	 */
	public void drawColorBlock(Graphics g){
		if(ghost)g.setColor(ghostColor);
		else g.setColor(color);
		g.fillRect((fixGridX+posGridX)*size + MultiPlay.BOARD_X, (fixGridY+posGridY)*size + MultiPlay.BOARD_Y, width, height);
		g.setColor(Color.BLACK); //
		g.drawRect((fixGridX+posGridX)*size + MultiPlay.BOARD_X, (fixGridY+posGridY)*size + MultiPlay.BOARD_Y, width, height);
		if(ghost)g.setColor(ghostColor);
		else g.setColor(color);
		g.setColor(Color.BLACK);
	}
	
	/**
	 * ���� ���� ������ǥ�� �����ش�.
	 * @return ������� X������ǥ
	 */
	public int getX(){return posGridX + fixGridX;}	
	
	
	/**
	 * ���� ���� ������ǥ�� �����ش�.
	 * @return ������� Y������ǥ
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
