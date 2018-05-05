package com.tetris.shape;

import java.awt.Color;

import com.tetris.classes.TetrisBlock;

public class Line extends TetrisBlock {

	public Line(int x, int y) {
		super(x, y, new Color(92,209,229),new Color(255,255,255));
		type = TYPE_LINE;
	}

	@Override
	/**
	 * ROTATION_0 :
	 * 		1 0 2 3
	 * 
	 * ROTATION_90 : 
	 * 		  1
	 *        0
	 *        2
	 *        3
	 *        
	 * ROTATION_180 :
	 * 	  3 2 0 1
	 * 		
	 * ROTATION_270 :
	 * 		  3
	 *        2
	 *        0
	 *        1
	 *        
	 */
	public void rotation(int rotation_index) {
		this.rotation_index = rotation_index;
		switch(rotation_index){
		case ROTATION_0 : 
			colBlock[1].setFixGridXY(-1,0);
			colBlock[0].setFixGridXY(0,0);
			colBlock[2].setFixGridXY(1,0);
			colBlock[3].setFixGridXY(2,0);
			break;
		case ROTATION_90 : 
			colBlock[1].setFixGridXY(0,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[2].setFixGridXY(0,1);
			colBlock[3].setFixGridXY(0,2);
			break;
		case ROTATION_180 : 
			colBlock[3].setFixGridXY(-2,0);
			colBlock[2].setFixGridXY(-1,0);
			colBlock[0].setFixGridXY(0,0);
			colBlock[1].setFixGridXY(1,0);
			break;
		case ROTATION_270 : 
			colBlock[3].setFixGridXY(0,-2);
			colBlock[2].setFixGridXY(0,-1);
			colBlock[0].setFixGridXY(0,0);
			colBlock[1].setFixGridXY(0,1);
			break;
		}//switch
	}//rotation

}
