package com.tetris.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.tetris.network.GameClient;

import javax.swing.JLabel;

public class Login extends JPanel implements Runnable, KeyListener, MouseListener, ActionListener{
	private Tetris tetris;
	private GameClient client;
	
	
	
	public static final int BLOCK_SIZE = 20;
	public static final int BOARD_X = 120;
	public static final int BOARD_Y = 50;
	private int minX=1, minY=0, maxX=10, maxY=21, down=50, up=0;
	
	private final int MESSAGE_X = 2;
	private final int MESSAGE_WIDTH = BLOCK_SIZE * (7 + minX);
	private final int MESSAGE_HEIGHT = BLOCK_SIZE * (6 + minY);
	private final int PANEL_WIDTH = maxX*BLOCK_SIZE + MESSAGE_WIDTH + BOARD_X;
	private final int PANEL_HEIGHT = maxY*BLOCK_SIZE + MESSAGE_HEIGHT + BOARD_Y;
	
	private JTextField id_area = new JTextField(10);
	private JTextField pw_area = new JTextField(10);
	private JLabel id_label = new JLabel("ID");
	private JLabel pw_label = new JLabel("PW");
	private JButton btnLogin = new JButton("LOGIN");
	private JButton btnJoin = new JButton("JOIN");
	private JButton btnExit = new JButton("EXIT");
	
	private String id;
	private String pw;
	
	public Login(Tetris tetris, GameClient client) {
		this.tetris = tetris;
		this.client = client;
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));//기본크기
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
		this.setFocusable(true);
		
		id_label.setBounds(PANEL_WIDTH - 430, PANEL_HEIGHT - 400, 200, 30);
		pw_label.setBounds(PANEL_WIDTH - 430, PANEL_HEIGHT - 350, 200, 30);
		id_area.setBounds(PANEL_WIDTH - 400, PANEL_HEIGHT - 400, 200, 30);
		pw_area.setBounds(PANEL_WIDTH - 400, PANEL_HEIGHT - 350, 200, 30);
		btnLogin.setBounds(PANEL_WIDTH - 200, PANEL_HEIGHT - 400, 150, 80);
		btnLogin.setFocusable(false);
		//btnLogin.setEnabled(false);
		btnLogin.addActionListener(this);
		btnJoin.setBounds(PANEL_WIDTH - 350, PANEL_HEIGHT - 300, 150, 50);
		btnJoin.setFocusable(false);	
		btnJoin.addActionListener(this);
		btnExit.setBounds(PANEL_WIDTH - 200, PANEL_HEIGHT - 300, 150, 50);
		btnExit.setFocusable(false);	
		btnExit.addActionListener(this);
		
		this.add(id_area);
		this.add(pw_area);
		this.add(btnLogin);	
		this.add(btnJoin);
		this.add(btnExit);
		this.add(id_label);
		this.add(pw_label);
		
		//if(id_area.getText() != "" && pw_area.getText() != "")
		//	btnLogin.setEnabled(true);
	}

	
	
	
	



	public String getId() {
		return id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnLogin){
			id = id_area.getText();
			pw = pw_area.getText();
			if(client==null && !id.equals("") && !pw.equals("")){
				tetris.user_Login();
			}else{
				
			}
		}else if(e.getSource() == btnExit){
			
			if(client!=null ){
				if(tetris.isNetwork()){
					client.closeNetwork(tetris.isServer());
				}
			}else{
				System.exit(0);
			}
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
