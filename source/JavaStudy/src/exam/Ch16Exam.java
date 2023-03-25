package exam;

import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 챕터16 문제
 *  
 * @author : pej 
 * @date : 2023.03.21
 */
public class Ch16Exam {
    @SuppressWarnings("unused")
    private static Logger logger = LogManager.getLogger(Ch16Exam.class);
    
    /**
     * <pre>
     * ip주소가 192.168.10.100이고 서브넷 마스크(subnet mask)가 255.255.255.0일때, 
     * 네트워크 주소와 호스트 주소를 계산하여 화면에 출력하는 프로그램을 작성하시오.
     * 단, 비트연산자를 사용해서 계산해야 한다.
     * 
     * [실행결과]
     * 네트워크 주소:192.168.10.0.
     * 호스트 주소:0.0.0.100.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.23
     */
    public static void exam01() {
        byte[] ipArr = {                     // IP 주소 
              (byte) 192
            , (byte) 168
            , 10
            , (byte) 100 
        };
        
        byte[] subnetMaskArr = {             // 서브넷 마스크 
              (byte) 255
            , (byte) 255
            , (byte) 255
            , 0 
        };
        
        /**
         * IPv4기준
         * IP주소 : A.B.C.D
         * 네트워크 주소 : A.B.C
         * 호스트 주소 : D
         */
        byte[] networkAddr = new byte[ipArr.length];    // 네트워크 주소
        byte[] hostAddr = new byte[ipArr.length];       // 호스트 주소
        
        StringBuffer sb = new StringBuffer();
        
        // 네트워크 주소 구하기
        for (int i = 0; i < ipArr.length; i++) {
            // 비트연산자로 계산
            networkAddr[i] = (byte) (ipArr[i] & subnetMaskArr[i]);
            
            sb.append( (networkAddr[i] < 0) ? networkAddr[i] + 256 : networkAddr[i]) ;
            sb.append(".");
        }
        
        System.out.println("네트워크 주소 : " + sb.toString());
        
        StringBuffer sb2 = new StringBuffer();
        
        // 호스트 주소 구하기
        for (int i = 0; i < ipArr.length; i++) {
            // 비트연산자로 계산
            // ~ : NOT, 반대로 바꿈
            hostAddr[i] = (byte) (ipArr[i] & ~subnetMaskArr[i]);

            sb2.append( (hostAddr[i] < 0) ? hostAddr[i] + 256 : hostAddr[i]) ;
            sb2.append(".");
        }
        
        System.out.println("호스트 주소 : " + sb2.toString());
    }
    
    /**
     * <pre>
     * [16-3] TextField에 URL을 입력하고 Enter키를 누르면 해당 페이지의 소스를 보여주는 'Source Viwer'프로그램이다. 
     * 예제15-4를 참고해서 (1) 에 알맞은 코드를 넣어 완성하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.21
     */
    @SuppressWarnings("serial")
    public static class SourceViewer extends Frame { 
        TextField tf = new TextField();
        TextArea ta = new TextArea();
        
        SourceViewer(String title) { 
            super(title);

            add(tf, "North");
            add(ta, "Center");

            tf.addActionListener(new ActionListener() { 
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
            String charset = "";
            
            ta.setText(""); 
            
            try {
                if (!address.startsWith("http://")) {
                    address = "http://"+address;
                }
                
                url = new URL(address);
                
                // 웹에 설정된 인코딩에 따라서 글자가 깨질수도 있음
                if (address.indexOf("javachobo") != -1) {
                    charset = "ks_c_5601-1987";
                } else {
                    charset = "UTF-8";
                }
                
                input = new BufferedReader(new InputStreamReader(url.openStream(), charset));

                StringBuffer sb = new StringBuffer();
                
                 /*
                 (1) 알맞은 코드를 넣어 완성하시오.
                 */
                // input.lines().forEach(System.out::println);
                input.lines().forEach(i -> {
                    sb.append(i);
                    sb.append("\n");
                });
                
                ta.setText(sb.toString());
                
                input.close();
             } catch(Exception e) { 
                 ta.setText("유효하지 않은 URL입니다.");
             }
         } // displaySource()
    }
    
    public static void exam03() {
        /**
         * 테스트
         * http://www.javachobo.com/
         * http://www.irudacom.co.kr/
         */
        new SourceViewer("Source Viewer");
    }
    
    /**
     * <pre>
     * [16-4] 다음의 코드는 통신을 하는 예제 16-6, 16-7을 결합하여 GU채팅프로그램을 작성한 것이다.
     * (1) ~ (4)에 알맞은 코드를 넣어 프로그램을 완성하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.21
     */
    public static void exam04() {
        
    }

    public static void main(String[] args) {
        exam03();
    }
}
