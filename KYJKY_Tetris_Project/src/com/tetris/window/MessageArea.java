package com.tetris.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class MessageArea extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private MultiPlay board;
	private SinglePlay single;
	private JTextArea area = new JTextArea();
	private JTextField txtField = new JTextField();
	private String[] swear_word = { "사랑합니다" };

	public MessageArea(MultiPlay board, int x, int y, int width, int height) {
		this.board = board;
		this.setLayout(new BorderLayout(2, 2));
		this.setBounds(x, y, width, height);
		area.setEditable(false);
		area.setLineWrap(true);

		JScrollPane scroll = new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		txtField.addActionListener(this);

		this.add("Center", scroll);
		this.add("South", txtField);
	}

	public MessageArea(SinglePlay board, int x, int y, int width, int height) {
		this.single = board;
		this.setLayout(new BorderLayout(2, 2));
		this.setBounds(x, y, width, height);
		area.setEditable(false);
		area.setLineWrap(true);

		JScrollPane scroll = new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		txtField.addActionListener(this);

		this.add("Center", scroll);
		this.add("South", txtField);
	}

	public void printMessage(String msg) {
		for (int i = 0; i < this.swear_word.length; i++) {
			if (msg.contains(this.swear_word[i])) { // Filtering
				String str = "";
				int l = this.swear_word[i].length();
				for (int j = 0; j < l; j++)
					str += "*";
				msg = msg.replace(this.swear_word[i], str);
			}
		}
		area.append(msg + "\n");
		area.setCaretPosition(area.getText().length());
	}

	public void clearMessage() {
		area.setText("");
	}

	@Override
	public void requestFocus() {
		txtField.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (board != null) {
			if (board.isPlay())
				board.requestFocus();
			if (board.getClient() != null)
				board.getClient().printMessage(txtField.getText().trim());
			txtField.setText("");
		}
	}
}
