package com.tetris.shape;

import java.awt.Color;

import com.tetris.classes.TetrisBlock;

public class CenterUp extends TetrisBlock {

	public CenterUp(int x, int y) {
		super(x, y, new Color(153,0,133), new Color(255,255,255));
		type = TYPE_CENTERUP;
	}

	@Override
	/**
	 * ROTATION_0 : 
	 * 		2 0 3
	 * 		  1
	 * ROTATION_90 : 
	 * 		  2
	 *      1 0
	 *        3
	 * ROTATION_180 : 
	 * 		  1
	 *      3 0 2
	 * ROTATION_270 : 
	 * 		  3
	 *        0 1
	 *        2
	 * 
	 */
	public void rotation(int rotation_index) {
		this.rotation_index = rotation_index;
		switch(rotation_index){
		case ROTATION_0 : 
			colBlock[2].setFixGridXY(-1,0);
			colBlock[0].setFixGridXY(0,0);
			colBlock[3].setFixGridXY(1,0);
			colBlock[1].setFixGridXY(0,1);
			break;
		case ROTATION_90 : 
			colBlock[2].setFixGridXY(0,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[3].setFixGridXY(0,1);
			colBlock[1].setFixGridXY(-1,0);
			break;
		case ROTATION_180 : 
			colBlock[3].setFixGridXY(-1,0);
			colBlock[0].setFixGridXY(0,0);
			colBlock[2].setFixGridXY(1,0);
			colBlock[1].setFixGridXY(0,-1);
			break;
		case ROTATION_270 : 
			colBlock[3].setFixGridXY(0,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[2].setFixGridXY(0,1);
			colBlock[1].setFixGridXY(1,0);
			break;
		}//switch
	}//rotation

}
