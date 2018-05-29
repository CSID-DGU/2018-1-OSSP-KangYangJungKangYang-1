package com.tetris.window;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class SystemMessageArea extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private static JTextArea area = new JTextArea();
	public SystemMessageArea(int x, int y, int width, int height) {
		super(area);
		this.setBounds(x, y, width, height);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		area.setEditable(false);
		area.setLineWrap(true);
	}
	
	
	public void printMessage(String msg){
		if(msg!=null && !msg.equals("")){
			area.append(msg+"\n");
			area.setCaretPosition(area.getText().length());
		}
	}
	public void clearMessage(){
		area.setText("");
	}
}
