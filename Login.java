package com.tetris.window;

import java.awt.Color;
import java.net.InetAddress;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import java.sql.*;

import com.tetris.main.TetrisMain;
import com.tetris.network.GameClient;

public class Login extends JPanel implements Runnable, KeyListener, MouseListener, ActionListener {

	private Tetris tetris;
	private GameClient client;

	private ImageIcon background = new ImageIcon(TetrisMain.class.getResource("../images/Background.png"));

	public static final int BLOCK_SIZE = 20;
	public static final int BOARD_X = 120;
	public static final int BOARD_Y = 50;
	private int minX = 1, minY = 0, maxX = 10, maxY = 21, down = 50, up = 0;

	private final int MESSAGE_WIDTH = BLOCK_SIZE * (7 + minX);
	private final int MESSAGE_HEIGHT = BLOCK_SIZE * (6 + minY);
	private final int PANEL_WIDTH = maxX * BLOCK_SIZE + MESSAGE_WIDTH + BOARD_X;
	private final int PANEL_HEIGHT = maxY * BLOCK_SIZE + MESSAGE_HEIGHT + BOARD_Y;

	ImageIcon icon1 = new ImageIcon(TetrisMain.class.getResource("../images/login_btn.png"));
	Image image1 = icon1.getImage();
	Image newimg1 = image1.getScaledInstance(150, 80, java.awt.Image.SCALE_SMOOTH);
	private final ImageIcon login = new ImageIcon(newimg1);

	ImageIcon icon2 = new ImageIcon(TetrisMain.class.getResource("../images/join_btn.png"));
	Image image2 = icon2.getImage();
	Image newimg2 = image2.getScaledInstance(150, 50, java.awt.Image.SCALE_SMOOTH);
	private final ImageIcon join = new ImageIcon(newimg2);

	ImageIcon icon3 = new ImageIcon(TetrisMain.class.getResource("../images/exit_btn.png"));
	Image image3 = icon3.getImage();
	Image newimg3 = image3.getScaledInstance(150, 50, java.awt.Image.SCALE_SMOOTH);
	private final ImageIcon exit = new ImageIcon(newimg3);

	private JTextField id_area = new JTextField(10);
	private JPasswordField pw_area = new JPasswordField(10);
	private JLabel login_label = new JLabel("Login");
	private JLabel id_label = new JLabel("ID");
	private JLabel pw_label = new JLabel("PW");
	private JButton btnLogin = new JButton(login);
	private JButton btnJoin = new JButton(join);
	private JButton btnExit = new JButton(exit);

	private String ip;
	private String id;
	private String pw;

	public Login(Tetris tetris, GameClient client) {

        this.tetris = tetris;
		this.client = client;
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
		this.setFocusable(true);

		login_label.setBounds(PANEL_WIDTH - 300, PANEL_HEIGHT - 500, 200, 50);
		id_label.setBounds(PANEL_WIDTH - 430, PANEL_HEIGHT - 400, 200, 30);
		pw_label.setBounds(PANEL_WIDTH - 430, PANEL_HEIGHT - 350, 200, 30);
		id_area.setBounds(PANEL_WIDTH - 400, PANEL_HEIGHT - 400, 200, 30);
		pw_area.setBounds(PANEL_WIDTH - 400, PANEL_HEIGHT - 350, 200, 30);
		btnLogin.setBounds(PANEL_WIDTH - 200, PANEL_HEIGHT - 400, 150, 80);
		btnLogin.setFocusable(false);
		btnLogin.addActionListener(this);
		btnJoin.setBounds(PANEL_WIDTH - 350, PANEL_HEIGHT - 300, 150, 50);
		btnJoin.setFocusable(false);
		btnJoin.addActionListener(this);
		btnExit.setBounds(PANEL_WIDTH - 200, PANEL_HEIGHT - 300, 150, 50);
		btnExit.setFocusable(false);
		btnExit.addActionListener(this);

		login_label.setForeground(Color.WHITE);
		id_label.setForeground(Color.WHITE);
		pw_label.setForeground(Color.WHITE);
		login_label.setFont(new Font("Serif", Font.BOLD, 40));

		this.add(id_area);
		this.add(pw_area);
		this.add(btnLogin);
		this.add(btnJoin);
		this.add(btnExit);
		this.add(login_label);
		this.add(id_label);
		this.add(pw_label);

	}

	public void paintComponent(Graphics g) {
		Image img = background.getImage();
		Image img2 = img.getScaledInstance(PANEL_WIDTH, PANEL_HEIGHT, Image.SCALE_SMOOTH);
		ImageIcon background2 = new ImageIcon(img2);
		g.drawImage(background2.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}

	public String getIp() {
		return ip;
	}

	public String getId() {
		return id;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnLogin) {
			id = id_area.getText();
			pw = pw_area.getText();
			if (client == null && !id.equals("") && !pw.equals("")) {
				Connection connection = null;
				Statement st = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					connection = DriverManager.getConnection(tetris.info[0],tetris.info[1],tetris.info[2]);

					System.out.println("Connection Success");
					st = connection.createStatement();

					String sql;
					sql = "select PW FROM user_info WHERE ID = ? LIMIT 1;";
					PreparedStatement pstmt = connection.prepareStatement(sql);

					pstmt.setString(1, id);
					ResultSet rs = pstmt.executeQuery();

					String pw_in_db ="";
					while (rs.next()) {
						pw_in_db = rs.getString(1);
					}

					if(pw_in_db.equals(pw)){

						System.out.println("matching success");
						InetAddress local = InetAddress.getLocalHost();
						ip = local.getHostAddress();
						System.out.println(ip);
						sql = "update user_info set IP=? WHERE ID =?;";
						pstmt = connection.prepareStatement(sql);
						pstmt.setString(1, ip);
						pstmt.setString(2, id);
						pstmt.executeUpdate();

						tetris.user_Login();
						tetris.go_menu();
					}
					else{
						System.out.println("fail");
						JOptionPane.showMessageDialog(null, "Check your ID or Password!");
					}

					rs.close();
					st.close();
					connection.close();
				}
				catch(SQLException se1){
					se1.printStackTrace();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}finally {
					try {
						if(connection != null){
							connection.close();
						}
					}catch(Exception ex){}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Check your ID or Password!");
			}
		} else if (e.getSource() == btnJoin) {
			try {
				Desktop.getDesktop().browse(new URI("http://icpc.dongguk.ac.kr/OSSP/main.php"));
			} catch (IOException | URISyntaxException e2) {
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnExit) {

			if (client != null) {
				if (tetris.isNetwork()) {
					client.closeNetwork(tetris.isServer());
				}
			} else {
				System.exit(0);
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {   }

	@Override
	public void mouseEntered(MouseEvent arg0) {   }

	@Override
	public void mouseExited(MouseEvent arg0) {   }

	@Override
	public void mousePressed(MouseEvent arg0) {   }

	@Override
	public void mouseReleased(MouseEvent arg0) {   }

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			id = id_area.getText();
			pw = pw_area.getText();
			if (client == null && !id.equals("") && !pw.equals("")) {
				tetris.go_menu();
			} else {
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {   }

	@Override
	public void keyTyped(KeyEvent arg0) {   }

	@Override
	public void run() {   }
}