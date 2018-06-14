package com.tetris.window;

import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tetris.network.GameClient;
import com.tetris.network.GameServer;

public class Tetris extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private GameServer server;
    private GameClient client;
    public Login login = new Login(this, client);
    private Menu menu = new Menu(this, client);
    private MultiPlay multi = new MultiPlay(this, client);
    private SinglePlay single = new SinglePlay(this, client);
    private JMenuItem itemServerStart = new JMenuItem("Connect with Server");
    private JMenuItem itemClientStart = new JMenuItem("Access as a Client");
    private JMenuItem itemGameManual = new JMenuItem("Game Manual");
    private JMenuItem itemAboutGame = new JMenuItem("About Game");

    private boolean isNetwork;
    private boolean isServer;

    private final JFrame frame;
    private final JPanel panel;
    private final JLabel text;

    String[] info; // DB information

    public Tetris() {
        JMenuBar mnBar = new JMenuBar();
        //JMenu mnGame = new JMenu("Connection");
        JMenu mnAbout = new JMenu("About");

        frame = new JFrame();
        panel = new JPanel(new FlowLayout());
        text = new JLabel();

        panel.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4)); //

        //frame.setLocationRelativeTo(board); //
        //mnGame.add(itemServerStart);
        //mnGame.add(itemClientStart);
        //mnBar.add(mnGame);

        mnAbout.add(itemGameManual);
        mnAbout.add(itemAboutGame);
        mnBar.add(mnAbout);

        this.setJMenuBar(mnBar);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().add(login);


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

                Connection connection = null;
                Statement st = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(info[0],info[1],info[2]);

                    System.out.println("BAConnection Success");
                    st = connection.createStatement();

                    String sql;

                    //출력 : 현재 방을 개설한 사람이 없습니다.

                    //방을 열은 사람이 없는 것
                    //자신의 openroom을 1로 변경하고 대기
                    sql = "update user_info set open_room = 0 WHERE ID = ?;";


                    System.out.println("check other_ip_1");
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, login.getId());
                    pst.executeUpdate();

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

                if (client != null) {
                    if (isNetwork) {
                        client.closeNetwork(isServer);
                    }
                } else {
                    System.exit(0);
                }
            }
        });

        String line = "";
        this.info = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\url.txt"));
            while((line = reader.readLine())!=null) {
                this.info = line.split(",");
            }
            reader.close();
        }catch (Exception fe){
            fe.printStackTrace();
        }

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
                        multi.setClient(client);
                        multi.getBtnStart().setEnabled(true);
                        multi.startNetworking(ip, port, nickName);
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
                    multi.setClient(client);
                    multi.startNetworking(ip, port, nickName);
                    isNetwork = true;
                }
            }
        } else if (e.getSource() == itemGameManual) {
            frame.setTitle("Tetris Game Manual");

            String contentText =
                    "<html><body><p>"
                            + "Left : ←<br>Right : →<br>Down : ↓<br>Rotate : ↑<br>Quick Down : Space<br>Hold : Shift"
                            + "</p></body></html>";

            text.setText(contentText);
            panel.add(text);
            frame.add(panel);

            frame.setSize(300, 200);
            frame.setVisible(true);
        } else if (e.getSource() == itemAboutGame) {
            frame.setTitle("About Tetris Game");

            String contentText = "<html><body><p>" + "Modified By<br>"
                    + "Dongguk Univ Computer Engineering..<br>"
                    + "[OSSP] Kang Yang Jung Kang Yang"
                    + "</p></body></html>";

            text.setText(contentText);
            panel.add(text);
            frame.add(panel);

            frame.setSize(300, 150);
            frame.setVisible(true);
        }
    }

    public void closeNetwork() {
        isNetwork = false;
        client = null;
        itemServerStart.setEnabled(true);
        itemClientStart.setEnabled(true);
        multi.setPlay(false);
        multi.setClient(null);
    }

    public void user_Login() {
        String ip = null;
        int port = 0;
        String id = login.getId();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            port = 9500;
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }

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
                client = new GameClient(this, ip, port, id);
                if (client.start()) {
                    multi.setClient(client);
                    multi.getBtnStart().setEnabled(false);
                    multi.startNetworking(ip, port, id);
                    isNetwork = true;
                    isServer = true;
                }
            }
        }
    }

    public void go_menu() {
        this.getContentPane().remove(login);
        this.getContentPane().add(menu);
        this.revalidate();
        this.repaint();
    }

    public void multi_to_menu(){

        if (client != null) {
            if (isNetwork) {
                client.closeNetwork(isServer);
            }
        }

        this.getContentPane().remove(multi);
        this.getContentPane().add(menu);
        this.revalidate();
        this.repaint();

    }


    public void go_multi() {


        this.getContentPane().remove(menu);
        this.getContentPane().add(multi);
        this.revalidate();
        this.repaint();

        {
            Connection connection = null;
            Statement st = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(info[0],info[1],info[2]);

                System.out.println("Connection Success");
                st = connection.createStatement();

                String sql1;
                String sql2;

                sql1 = "select IP FROM user_info WHERE open_room = 1 LIMIT 1;";
                PreparedStatement pstmt = connection.prepareStatement(sql1);
                ResultSet rs = pstmt.executeQuery();

                String Other_IP ="";

                while (rs.next()) {
                    Other_IP = rs.getString(1);
                }

                System.out.println(Other_IP);

                if(Other_IP == "") {
                    JOptionPane.showMessageDialog(null, "현재 방을 개설한 사람이 없습니다.");

                    sql2 = "update user_info set open_room = 1 where ID = ?;";
                    PreparedStatement pst = connection.prepareStatement(sql2);
                    pst.setString(1, login.getId());
                    pst.executeUpdate();

                    user_Login();
                    multi.getBtnStart().setEnabled(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "상대방이 검색 되었습니다.");

                    client = new GameClient(this, Other_IP , 9500, "OTHER");

                    Thread.sleep((100));

                    if (client.start()) {
                        multi.setClient(client);
                        multi.startNetworking(login.getIp(), 9500, login.getId());
                        isNetwork = true;
                    }
                    else {
                        System.out.println("Client Error");
                    }

                    sql2 = "update user_info set open_room = 2 where ID = ?;";
                    PreparedStatement pst = connection.prepareStatement(sql2);
                    pst.setString(1, login.getId());
                    pst.executeUpdate();

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
        }
    }

    public void go_single() {
        this.getContentPane().remove(menu);
        this.getContentPane().add(single);
        this.revalidate();
        this.repaint();
    }

    public JMenuItem getItemServerStart() {
        return itemServerStart;
    }

    public JMenuItem getItemClientStart() {
        return itemClientStart;
    }

    public MultiPlay getBoard() {
        return multi;
    }

    public void gameStart(int speed) {
        multi.gameStart(speed);
    }

    public boolean isNetwork() {
        return isNetwork;
    }

    public void setNetwork(boolean isNetwork) {
        this.isNetwork = isNetwork;
    }

    public void printSystemMessage(String msg) {
        multi.printSystemMessage(msg);
    }

    public void printMessage(String msg) {
        multi.printMessage(msg);
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean isServer) {
        this.isServer = isServer;
    }

    public void changeSpeed(Integer speed) {
        multi.changeSpeed(speed);
    }
}