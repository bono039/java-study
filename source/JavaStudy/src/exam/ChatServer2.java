package exam;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer2 extends Frame {
    private static final long serialVersionUID = 1L;

    String nickname = "";

    DataOutputStream out; 
    DataInputStream in;

    Panel p = new Panel(); 
    TextArea ta = new TextArea();
    TextField tf = new TextField();
    
    ChatServer2(String nickname) { 
        super("Chatting"); 
        this.nickname = nickname;

        p.setLayout(new BorderLayout()); 
        p.add(tf, "Center");

        add(ta, "Center");
        add(p, "South");

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
                System.exit(0);
            }
        });
        
        EventHandler handler = new EventHandler(); 
        ta.addFocusListener(handler); 
        tf.addFocusListener(handler); 
        tf.addActionListener(handler);

        ta.setEditable(false); 
        setBounds(200, 200, 300, 200);
        setVisible(true); 
        tf.requestFocus();
        
        
    }
    
    void startServer() {
        ServerSocket serverSocket = null; 
        Socket socket = null;
        
        String msg = "";
         
        try {
            /*
            (1) 아래의 로직에 맞게 코드를 작성하시오.
                1. 서버소켓을 생성하여 7777번 포트와 결합시킨다.
                2. ta에 "서버가 준비되었습니다."라고 보여준다.
                3. 상대방의 연결을 기다린다.
                4. ta에 "상대방과 연결되었습니다."라고 보여준다.
                   ta.append("\r\n" +"상대방과 연결되었습니다.");
                5. 연결된 상대방 소켓의 입력스트림과 출력스트립을 얻어온다.
                6. 반복문을 이용해서 입력스트림이 null이 아닌 동안 입력스트림으로부터 데이터를 읽어서 변수 msg에 저장한다.
            */
            
            // 1. 서버소켓을 생성하여 7777번 포트와 결합시킨다.
            // IP 미입력 시 "localhost"가 기본값.
//            serverSocket = new ServerSocket(7777);
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("127.0.0.1", 80));
            
            // 2. ta에 "서버가 준비되었습니다."라고 보여준다.
            ta.setText("서버가 준비되었습니다.");
            
            // 3. 상대방의 연결을 기다린다.
            socket = serverSocket.accept();
            
            // 4. ta에 "상대방과 연결되었습니다."라고 보여준다.
            ta.append("\r\n" +"상대방과 연결되었습니다.");
            
            // 5. 연결된 상대방 소켓의 입력스트림과 출력스트립을 얻어온다.
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
            // 6. 반복문을 이용해서 입력스트림이 null이 아닌 동안 입력스트림으로부터 데이터를 읽어서 변수 msg에 저장한다.
            while (in != null) {
                msg = in.readUTF();
                ta.append("\r\n" + msg);
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("USAGE : java ChatServer2 NICKNAME"); 
            System.exit(0);
        }
        
        ChatServer2 chatWin = new ChatServer2(args[0]); 
        chatWin.startServer();
    }
    
    class EventHandler extends FocusAdapter implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent ae) {
            String msg = tf.getText();
    
            if ("".equals(msg)) return;
            /*
            (2) 알맞은 코드를 넣어 완성하시오.
            */
            try {
                if (out != null) {
                    out.writeUTF(nickname +">"+ msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
           
            ta.append("\r\n" + nickname +">"+ msg);
            tf.setText("");
        }
        
        @Override
        public void focusGained(FocusEvent e) { 
            tf.requestFocus();
        }
    } // class EventHandler
}
