package com.tetris.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tetris.main.TetrisMain;
import com.tetris.network.GameClient;

public class Menu extends JPanel implements Runnable, KeyListener, MouseListener, ActionListener{
	private Tetris tetris;
	private GameClient client;
	
	private ImageIcon background = new ImageIcon(TetrisMain.class.getResource("../images/Background.png"));
	
	public static final int BLOCK_SIZE = 20;
	public static final int BOARD_X = 120;
	public static final int BOARD_Y = 50;
	private int minX=1, minY=0, maxX=10, maxY=21, down=50, up=0;
	
	private final int MESSAGE_WIDTH = BLOCK_SIZE * (7 + minX);
	private final int MESSAGE_HEIGHT = BLOCK_SIZE * (6 + minY);
	private final int PANEL_WIDTH = maxX*BLOCK_SIZE + MESSAGE_WIDTH + BOARD_X;
	private final int PANEL_HEIGHT = maxY*BLOCK_SIZE + MESSAGE_HEIGHT + BOARD_Y;

	ImageIcon icon1 = new ImageIcon(TetrisMain.class.getResource("../images/multi_btn.png"));
	Image image1 = icon1.getImage();
	Image newimg1 = image1.getScaledInstance(150, 60, java.awt.Image.SCALE_SMOOTH);
	private final ImageIcon multi = new ImageIcon(newimg1);

	ImageIcon icon2 = new ImageIcon(TetrisMain.class.getResource("../images/single_btn.png"));
	Image image2 = icon2.getImage();
	Image newimg2 = image2.getScaledInstance(150, 60, java.awt.Image.SCALE_SMOOTH);
	private final ImageIcon single = new ImageIcon(newimg2);

	ImageIcon icon3 = new ImageIcon(TetrisMain.class.getResource("../images/rank_btn.png"));
	Image image3 = icon3.getImage();
	Image newimg3 = image3.getScaledInstance(150, 60, java.awt.Image.SCALE_SMOOTH);
	private final ImageIcon rank = new ImageIcon(newimg3);

	ImageIcon icon4 = new ImageIcon(TetrisMain.class.getResource("../images/exit_btn.png"));
	Image image4 = icon4.getImage();
	Image newimg4 = image4.getScaledInstance(150, 60, java.awt.Image.SCALE_SMOOTH);
	private final ImageIcon exit = new ImageIcon(newimg4);

	private JLabel menu_label = new JLabel("Game Menu");
	private JButton btnMulti = new JButton(multi);
	private JButton btnSingle = new JButton(single);
	private JButton btnRank = new JButton(rank);
	private JButton btnExit = new JButton(exit);
	
	public Menu(Tetris tetris, GameClient client) {
		this.tetris = tetris;
		this.client = client;
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
		this.setFocusable(true);
		
		menu_label.setBounds(PANEL_WIDTH - 310, PANEL_HEIGHT - 530, 200, 50);
		btnMulti.setBounds(PANEL_WIDTH - 300, PANEL_HEIGHT - 450, 150, 60);
		btnMulti.setFocusable(false);
		btnMulti.addActionListener(this);
		btnSingle.setBounds(PANEL_WIDTH - 300, PANEL_HEIGHT - 350, 150, 60);
		btnSingle.setFocusable(false);	
		btnSingle.addActionListener(this);
		btnRank.setBounds(PANEL_WIDTH - 300, PANEL_HEIGHT - 250, 150, 60);
		btnRank.setFocusable(false);
		btnRank.addActionListener(this);
		btnExit.setBounds(PANEL_WIDTH - 300, PANEL_HEIGHT - 150, 150, 60);
		btnExit.setFocusable(false);
		btnExit.addActionListener(this);
		
		menu_label.setForeground(Color.WHITE);
		menu_label.setFont(new Font("Serif", Font.BOLD, 30));
		
		this.add(menu_label);
		this.add(btnMulti);	
		this.add(btnSingle);
		this.add(btnRank);
		this.add(btnExit);
	}
	
	
	public void paintComponent(Graphics g) {
		Image img = background.getImage();
		Image img2 = img.getScaledInstance(PANEL_WIDTH, PANEL_HEIGHT, Image.SCALE_SMOOTH);
		ImageIcon background2 = new ImageIcon(img2);
        g.drawImage(background2.getImage(), 0, 0, null);
        setOpaque(false);
        super.paintComponent(g);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnMulti){
			tetris.go_multi();
		}else if(e.getSource() == btnSingle){
			tetris.go_single();	
		}else if(e.getSource() == btnRank){
			try {
				Desktop.getDesktop().browse(new URI("http://icpc.dongguk.ac.kr/OSSP/main.php"));
			} catch (IOException | URISyntaxException e2) {
				e2.printStackTrace();
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
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void run() {}
}
