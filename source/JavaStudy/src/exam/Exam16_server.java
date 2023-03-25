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
		
//		exam16_1();
		exam16_3();
		
		//���� 16_4 ���๮
//		if(args.length != 1) {
//			System.out.println("USAGE : java ChatServer NICKNAME");
//			System.exit(0);
//		}
//		ChatServer chatWin = new ChatServer(args[0]);
//		chatWin.startServer();
	}
	
	static void exam16_1() {
		//IP�ּҿ� ����� ����ũ�� �̿��Ͽ�(��Ʈ����)
		//��Ʈ��ũ �ּҿ� ȣ��Ʈ �ּҸ� ���Ͽ� ����ϴ� ���α׷� �ۼ�
		int[] ipArr = {192, 168, 10, 100};
		int[] subNetArr = {255, 255, 255, 0};
		
		int[] NetArr = new int[4];
		int[] HostArr = new int[4];
		
		//��Ʈ��ũ �ּ� ���
		for(int i = 0; i < ipArr.length; i++) {
			int tmp = (byte)ipArr[i] & (byte)subNetArr[i];
			
			if (tmp < 0) {tmp += 256;}
			
			NetArr[i] = tmp;
		}
		
		//ȣ��Ʈ �ּ� ���
		for(int i = 0; i < ipArr.length; i++) {
			int tmp = (byte)ipArr[i] & ~(byte)subNetArr[i];
			
			if (tmp < 0) {tmp += 256;}
			
			HostArr[i] = tmp;
		}
		
		//��� ���
		System.out.println("��Ʈ��ũ �ּ�: " + Arrays.toString(NetArr));
		System.out.println("ȣ��Ʈ �ּ�: " + Arrays.toString(HostArr));
	}//exam1 end
	
	static void exam16_3() {
		//URL�� �Է��� �� enter Ű�� ������ �ش� �������� �ҽ��� �����ִ� ���α׷�
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
			/* (1) �˸��� �ڵ带 �־� �ϼ��Ͻÿ�. */
			url = new URL(address);
			
			input = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			//openStream �� URLConnection �� openConnection() �� getInputStream() �� �Ͱ� ����
			
			while((line = input.readLine()) != null) {
				ta.append(line + "\n");
			}
			
			input.close();
			
		} catch(Exception e) {
			ta.setText("��ȿ���� ���� URL�Դϴ�.");
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
		/* (1) �Ʒ��� ������ �°� �ڵ带 �ۼ��Ͻÿ�.
		1.	��������� �����Ͽ� 7777�� ��Ʈ�� ���ս�Ų��.
		2.	ta�� "������ �غ�Ǿ����ϴ�."��� �����ش�.
		3.	������ ������ ��ٸ���.
		4.	ta�� "����� ����Ǿ����ϴ�."��� �����ش�.
		    ta.append("\r\n" +"����� ����Ǿ����ϴ�.");
		5.	����� ���� ������ �Է½�Ʈ���� ��½�Ʈ���� ���´�.
		6.	�ݺ����� �̿��ؼ� �Է½�Ʈ���� null�� �ƴ� ���� �Է½�Ʈ�����κ��� �����͸� �о ���� msg�� �����Ѵ�. */
			
			serverSocket = new ServerSocket(7777);
			ta.append("===������ �غ�Ǿ����ϴ�===");
			
			socket = serverSocket.accept();
			
			if (socket.isConnected()) ta.append("\r\n" +"����� ����Ǿ����ϴ�.");
			
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
			/* (2) �˸��� �ڵ带 �־� �ϼ��Ͻÿ�. */
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
