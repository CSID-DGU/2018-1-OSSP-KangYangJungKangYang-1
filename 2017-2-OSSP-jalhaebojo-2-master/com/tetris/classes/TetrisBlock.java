package com.tetris.classes;

import java.awt.Color;
import java.awt.Graphics;

//��Ʈ���� ��
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
	public static final int ROTATION_0 = 0;			//���� �����   0�� ȸ��
	public static final int ROTATION_90 = 1;		//���� �����  90�� ȸ��
	public static final int ROTATION_180 = 2;		//���� ����� 180�� ȸ��
	public static final int ROTATION_270 = 3;		//���� ����� 270�� ȸ��
	
	/* Rotation Type */
	public static final int ROTATION_LEFT = 1;		//�ð����ȸ��
	public static final int ROTATION_RIGHT = -1;	//�ݽð����ȸ��
	
	/* �׿� �ʵ� */
	protected int type;								//�����;
	protected Block[] colBlock= new Block[4];		//����� ��Ÿ���� 4����
	protected int rotation_index;					//��ȸ�� ���
	protected int posX,posY;						//����� ��ǥ
	protected Color color;							//��ϻ���
	
	
	
	
	public TetrisBlock(int x, int y, Color color, Color ghostColor) {
		this.color = color;
		for(int i=0 ; i<colBlock.length ; i++){
			colBlock[i] = new Block(0,0,color,ghostColor);
		}
		this.rotation(ROTATION_0); //�⺻ ȸ����� : 0��
		this.setPosX(x);
		this.setPosY(y);
	}
	
	
	/**
	 * ��Ʈ���� ������� ȸ���Ѵ�. 
	 * @param rotation_index : ȸ�����
	 * ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270
	 */
	public abstract void rotation(int rotation_index);
	
	
	/**
	 * ��Ʈ���� ������� �������� �̵���Ų��.
	 * @param addX : �̵���
	 * 0�̻��� ���� �־�� �Ѵ�.
	 */
	public void moveLeft(int addX) {this.setPosX(this.getPosX()-addX);}
	
	
	/**
	 * ��Ʈ���� ������� ���������� �̵���Ų��.
	 * @param addX : �̵���
	 * 0�̻��� ���� �־�� �Ѵ�.
	 */
	public void moveRight(int addX) {this.setPosX(this.getPosX()+addX);}
	
	
	/**
	 * ��Ʈ���� ������� �Ʒ��� �̵���Ų��.
	 * @param addY : �̵���
	 * 0�̻��� ���� �־�� �Ѵ�.
	 */
	public void moveDown(int addY) {this.setPosY(this.getPosY()+addY);}
	
	
	/**
	 * ��Ʈ���� ���� Graphics�� �̿��Ͽ� �׸���.
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
