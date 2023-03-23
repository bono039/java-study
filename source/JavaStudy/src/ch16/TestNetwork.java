package ch16;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 네트워크 학습
 *  
 * @author : pej 
 * @date : 2023.03.19
 */
public class TestNetwork {
    private static Logger logger = LogManager.getLogger(TestNetwork.class);
    
    /**
     * InetAddress : IP주소를 다루기 위한 클래스
     */
    public static void test() {
        InetAddress ip = null;
        
        try {
            ip = InetAddress.getByName("www.naver.com");
            // IP주소를 바이트배열로 반환
            logger.debug("getAddress : {}", ip.getAddress()); 
            // IP주소를 반환
            logger.debug("getHostAddress : {}", ip.getHostAddress()); 
            // FQDN(Fully Qualified Domain Name)을 반환
            logger.debug("getCanonicalHostName : {}", ip.getCanonicalHostName()); 
        } catch (UnknownHostException e) {
            logger.error("에러 : {}", e.getMessage());
        }
        
        InetAddress[] ipArr = null;
        
        try {
            // 호스트명으로 모든  IP를 찾을 수 있음
            ipArr = InetAddress.getAllByName("www.naver.com");
            
            for (InetAddress item : ipArr) {
                logger.debug(item); 
            }
            
        } catch (UnknownHostException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    /**
     * URLConnection : URL간의 통신 연결을 나타내는 클래스 
     */
    public static void test2() {
        String addr = "https://www.naver.com";
        
        URL url = null;
        
        BufferedReader bf = null;
           
        try {
            url = new URL(addr);
            
            bf = new BufferedReader(new InputStreamReader(url.openStream()));

            // 한 줄씩 데이터 읽어서 출력
            bf.lines().forEach(System.out::println);
            
            bf.close();
            
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void test3() {
        String ip = "223.130.200.107";
        logger.debug("서버ip : {}", ip);
        
        try {
            // 서벗 소켓 생성
            Socket socket = new Socket(ip, 80);
            // 입력 스트림 생성
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            // 소켓으로부터 받은 데이터를 출력
            System.out.println(dataIn.readUTF());
            
            dataIn.close();
            socket.close();
            
            logger.debug("연결 종료");
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void test4() {
        // 키보드 입력 받기 위한 변수
        Scanner scanner = new Scanner(System.in);
        System.out.println("보낼 메세지 입력 :");
        String msg = scanner.next();
         
        try{
            // 전송할 수 있는 UDP 소켓 생성
            DatagramSocket dsoc = new DatagramSocket();
             
            // 받을 곳의 주소 생성
            InetAddress ia = InetAddress.getByName("127.0.0.1");
             
            // 전소할 데이터 생성
            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length,ia, 7779);
             
            dsoc.send(dp);
            dsoc.close();
            
            System.out.println("전송 성공");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public static void test5() {
        try {
            // 상대방이 연결할수 있도록 UDP 소켓 생성
            DatagramSocket dsoc = new DatagramSocket(7779);
            // 전송받은 데이터를 지정할 바이트 배열선언
            byte [] date = new byte[66536];
             
            // UDP 통신으로 전송을 받을 packet 객체생성
            DatagramPacket dp = new DatagramPacket(date, date.length);
             
            System.out.println("데이터 수신 준비 완료....");
            while (true) {
                // 데이터 전송 받기
                dsoc.receive(dp);
                // 데이터 보낸곳 확인
                System.out.println(" 송신 IP : " + dp.getAddress());
                // 보낸 데이터를 Utf-8에 문자열로 벼환
                String toMsg = new String(dp.getData(),"UTF-8");
                System.out.println("보내 온 내용  : " + toMsg);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        test();
    }
}
