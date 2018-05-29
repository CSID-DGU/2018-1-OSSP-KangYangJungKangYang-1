package com.tetris.shape;

import java.awt.Color;

import com.tetris.classes.TetrisBlock;

public class Nemo extends TetrisBlock {

	public Nemo(int x, int y) {
		super(x, y, new Color(153,138,0), new Color(255,255,255));
		type = TYPE_NEMO;
	}

	@Override
	/**
	 * ROTATION_0 :
	 * 		 2 3
	 * 		 0 1
	 * 
	 * ROTATION_90 : 
	 * 		 2 3
	 * 		 0 1
	 * 
	 * ROTATION_180 :
	 * 		 2 3
	 * 		 0 1
	 * 
	 * ROTATION_270 :
	 * 		 2 3
	 * 		 0 1
	 * 
	 */
	public void rotation(int rotation_index) {
		this.rotation_index = rotation_index;
		switch(rotation_index){
		case ROTATION_0 : 
			colBlock[2].setFixGridXY(0,-1);
			colBlock[3].setFixGridXY(1,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[1].setFixGridXY(1,0);
			break;
		case ROTATION_90 : 
			colBlock[2].setFixGridXY(0,-1);
			colBlock[3].setFixGridXY(1,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[1].setFixGridXY(1,0);
			break;
		case ROTATION_180 : 
			colBlock[2].setFixGridXY(0,-1);
			colBlock[3].setFixGridXY(1,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[1].setFixGridXY(1,0);
			break;
		case ROTATION_270 : 
			colBlock[2].setFixGridXY(0,-1);
			colBlock[3].setFixGridXY(1,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[1].setFixGridXY(1,0);
			break;
		}//switch
	}//rotation
}
