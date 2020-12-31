import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main extends JFrame {
    Socket s = null;
    Server server = new Server();
    final JTextArea messageText = null;

    public Main() {


        setTitle("服务端");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        final JTextArea messageText = new JTextArea(10, 20);
        final JTextArea sendText = new JTextArea(1, 15);
        messageText.setLineWrap(true);
        sendText.setLineWrap(true);
        panel.add(messageText);
        panel.add(sendText);

        JButton BtnSend = new JButton("发送");
        BtnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("提交: " + sendText.getText());
                messageText.append("服务端 对 客户端 说：" + sendText.getText());
                messageText.append("\r\n");
                new Thread(() -> server.sendMessage(sendText.getText())).start();
            }
        });
        panel.add(BtnSend);
        setContentPane(panel);
        setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        Socket s = new ServerSocket(1237).accept();
                        System.out.println("user  " + s.getInetAddress().getHostName() + ":successfully connecting");
                        server.setSocket(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    String a = server.receiveMessage();
                                    if (a != null) {
                                        messageText.append("客户端 对 服务端 说：" + a);
                                        messageText.append("\r\n");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        }).start();


    }

    public static void main(String[] args) {
        new Main();
    }

}

class Server {
    private Socket socket = null;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Server() {

    }

    public void close() {
        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(message);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage() {
        try {
            BufferedReader b = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            return b.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}