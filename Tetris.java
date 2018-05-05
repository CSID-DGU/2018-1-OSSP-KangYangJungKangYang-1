package com.tetris.window;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.tetris.network.GameClient;
import com.tetris.network.GameServer;

public class Tetris extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private GameServer server;
	private GameClient client;
	private TetrisBoard board = new TetrisBoard(this, client);
	private JMenuItem itemServerStart = new JMenuItem("Connect with Server");
	private JMenuItem itemClientStart = new JMenuItem("Access as a Client");
	private JMenuItem itemGameManual = new JMenuItem("Game Manual");
	private JMenuItem itemAboutGame = new JMenuItem("About Game");

	private boolean isNetwork;
	private boolean isServer;

	private final JFrame frame;
	private final JPanel panel;
	private final JLabel text;

	public Tetris() {
		JMenuBar mnBar = new JMenuBar();
		JMenu mnGame = new JMenu("Connection");
		JMenu mnAbout = new JMenu("About");

		frame = new JFrame();
		panel = new JPanel(new FlowLayout());
		text = new JLabel();

		panel.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4)); // 화면의 공백 상/좌/하/우
		//frame.setLocationRelativeTo(board); // 화면이 나타날 위치 설정

		mnGame.add(itemServerStart);
		mnGame.add(itemClientStart);
		mnBar.add(mnGame);

		mnAbout.add(itemGameManual);
		mnAbout.add(itemAboutGame);
		mnBar.add(mnAbout);

		this.setJMenuBar(mnBar);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().add(board);

		this.setResizable(false);
		this.pack();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((size.width - this.getWidth()) / 2, (size.height - this.getHeight()) / 2);
		frame.setLocation((size.width - this.getWidth()) / 2, (size.height - this.getHeight()));
		this.setVisible(true);

		itemServerStart.addActionListener(this);
		itemClientStart.addActionListener(this);
		itemGameManual.addActionListener(this);
		itemAboutGame.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (client != null) {
					if (isNetwork) {
						client.closeNetwork(isServer);
					}
				} else {
					System.exit(0);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String ip = null;
		int port = 0;
		String nickName = null;
		if (e.getSource() == itemServerStart) {

			String sp = JOptionPane.showInputDialog("Enter Port Number", "9500");
			if (sp != null && !sp.equals(""))
				port = Integer.parseInt(sp);
			nickName = JOptionPane.showInputDialog("Enter Your ID", "User1");

			if (port != 0) {
				if (server == null)
					server = new GameServer(port);
				server.startServer();
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				if (ip != null) {
					client = new GameClient(this, ip, port, nickName);
					if (client.start()) {
						itemServerStart.setEnabled(false);
						itemClientStart.setEnabled(false);
						board.setClient(client);
						board.getBtnStart().setEnabled(true);
						board.startNetworking(ip, port, nickName);
						isNetwork = true;
						isServer = true;
					}
				}
			}
		} else if (e.getSource() == itemClientStart) {
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}

			ip = JOptionPane.showInputDialog("Enter Your IP", ip);
			String sp = JOptionPane.showInputDialog("Enter Port Number", "9500");
			if (sp != null && !sp.equals(""))
				port = Integer.parseInt(sp);
			nickName = JOptionPane.showInputDialog("Enter Your ID", "User2");

			if (ip != null) {
				client = new GameClient(this, ip, port, nickName);
				if (client.start()) {
					itemServerStart.setEnabled(false);
					itemClientStart.setEnabled(false);
					board.setClient(client);
					board.startNetworking(ip, port, nickName);
					isNetwork = true;
				}
			}
		} else if (e.getSource() == itemGameManual) {
			frame.setTitle("Tetris Game Manual"); // 실행되는 화면의 제목

			String contentText = // 화면에 출력되는 내용
					"<html><body><p>"
							+ "Left : ←<br>Right : →<br>Down : ↓<br>Rotate : ↑<br>Quick Down : Space<br>Hold : Shift"
							+ "</p></body></html>";

			text.setText(contentText); // 출력될 내용을 label 위에 저장
			panel.add(text); // label을 panel 위에 올림
			frame.add(panel); // panel을 frame 위에 올림

			frame.setSize(300, 200); // 화면의 크기
			frame.setVisible(true); // about me와 software를 눌렀을 때 화면에 보이도록 설정
		} else if (e.getSource() == itemAboutGame) {
			frame.setTitle("About Tetris Game");

			String contentText = "<html><body><p>" + "Modified By<br>"
					+ "Dongguk Univ Computer Engineering..<br>"
					+ "[OSSP] Kang Yang Jung Kang Yang"
					+ "</p></body></html>";

			text.setText(contentText); // 출력될 내용을 label 위에 저장
			panel.add(text); // label을 panel 위에 올림
			frame.add(panel); // panel을 frame 위에 올림

			frame.setSize(300, 150); // 화면의 크기
			frame.setVisible(true); // about me와 software를 눌렀을 때 화면에 보이도록 설정
		}
	}

	public void closeNetwork() {
		isNetwork = false;
		client = null;
		itemServerStart.setEnabled(true);
		itemClientStart.setEnabled(true);
		board.setPlay(false);
		board.setClient(null);
	}

	public JMenuItem getItemServerStart() {
		return itemServerStart;
	}

	public JMenuItem getItemClientStart() {
		return itemClientStart;
	}

	public TetrisBoard getBoard() {
		return board;
	}

	public void gameStart(int speed) {
		board.gameStart(speed);
	}

	public boolean isNetwork() {
		return isNetwork;
	}

	public void setNetwork(boolean isNetwork) {
		this.isNetwork = isNetwork;
	}

	public void printSystemMessage(String msg) {
		board.printSystemMessage(msg);
	}

	public void printMessage(String msg) {
		board.printMessage(msg);
	}

	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}

	public void changeSpeed(Integer speed) {
		board.changeSpeed(speed);
	}
}
