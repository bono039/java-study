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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;

public class Exam16_server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// server side
		
		//exam16_1();
		//exam16_3();
		
		//이하 16_4 실행문
		if(args.length != 1) {
			System.out.println("USAGE : java ChatServer NICKNAME");
			System.exit(0);
		}
		ChatServer chatWin = new ChatServer(args[0]);
		chatWin.startServer();
	}
	
	static void exam16_1() {
		//IP주소와 서브넷 마스크를 이용하여(비트연산)
		//네트워크 주소와 호스트 주소를 구하여 출력하는 프로그램 작성
		int[] ipArr = {192, 168, 10, 100};
		int[] subNetArr = {255, 255, 255, 0};
		
		int[] NetArr = new int[4];
		int[] HostArr = new int[4];
		
		//네트워크 주소 계산
		for(int i = 0; i < ipArr.length; i++) {
			int tmp = (byte)ipArr[i] & (byte)subNetArr[i];
			
			if (tmp < 0) {tmp += 256;}
			
			NetArr[i] = tmp;
		}
		
		//호스트 주소 계산
		for(int i = 0; i < ipArr.length; i++) {
			int tmp = (byte)ipArr[i] & ~(byte)subNetArr[i];
			
			if (tmp < 0) {tmp += 256;}
			
			HostArr[i] = tmp;
		}
		
		//결과 출력
		System.out.println("네트워크 주소: " + Arrays.toString(NetArr));
		System.out.println("호스트 주소: " + Arrays.toString(HostArr));
	}//exam1 end
	
	static void exam16_3() {
		//URL을 입력한 뒤 enter 키를 누르면 해당 페이지의 소스를 보여주는 프로그램
		SourceViewer mainWin = new SourceViewer("Source Viewer");
	}//exam3 end
	
}


class SourceViewer extends Frame {
	TextField tf = new TextField();
	TextArea ta = new TextArea();
	
	SourceViewer(String title) {
		super(title);
		
		add(tf, "North");
		add(ta, "Center");
		
		tf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				displaySource();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		setBounds(500, 200, 500, 300);
		setVisible(true);
	}
	
	void displaySource() {
		URL url = null;
		BufferedReader input = null;
		String address = tf.getText().trim();
		String line = "";
		
		ta.setText("");
		try {
			if(!address.startsWith("https://")) address = "https://"+address;
			/* (1) 알맞은 코드를 넣어 완성하시오. */
			url = new URL(address);
			
			input = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			//openStream 은 URLConnection 의 openConnection() 과 getInputStream() 한 것과 같다
			
			while((line = input.readLine()) != null) {
				ta.append(line + "\n");
			}
			
			input.close();
			
		} catch(Exception e) {
			ta.setText("유효하지 않은 URL입니다.");
		}
	} // displaySource()
	
}//SourceViewer end


class ChatServer extends Frame { 
	String nickname = "";

	DataOutputStream out; 
	DataInputStream in;
	
	Panel p = new Panel(); 
	TextArea ta = new TextArea();
	TextField tf = new TextField();
	
	ChatServer(String nickname) { 
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
	}//ChatServer end
	
	void startServer() {
		ServerSocket serverSocket = null; 
		Socket socket = null;
		
		try {
		/* (1) 아래의 로직에 맞게 코드를 작성하시오.
		1.	서비소켓을 생성하여 7777번 포트와 결합시킨다.
		2.	ta에 "서버가 준비되었습니다."라고 보여준다.
		3.	상대방의 연결을 기다린다.
		4.	ta에 "상대방과 연결되었습니다."라고 보여준다.
		    ta.append("\r\n" +"상대방과 연결되었습니다.");
		5.	연결된 상대방 소켓의 입력스트림과 출력스트립을 얻어온다.
		6.	반복문을 이용해서 입력스트림이 null이 아닌 동안 입력스트림으로부터 데이터를 읽어서 변수 msg에 저장한다. */
			
			serverSocket = new ServerSocket(7777);
			ta.append("===서버가 준비되었습니다===");
			
			socket = serverSocket.accept();
			
			if (socket.isConnected()) ta.append("\r\n" +"상대방과 연결되었습니다.");
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			while(in != null) {
				String msg = in.readUTF();
				ta.append("\r\n" + msg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class EventHandler extends FocusAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String msg = tf.getText();
			if("".equals(msg)) return;
			/* (2) 알맞은 코드를 넣어 완성하시오. */
			if (out != null) {
				try {
					out.writeUTF(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ta.append("\r\n" + nickname +">"+ msg);
			tf.setText("");
		}
		public void focusGained(FocusEvent e) {
			tf.requestFocus();
		}
	} // EventHandler end
	
} // ChatServer end
