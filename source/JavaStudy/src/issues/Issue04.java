package issues;

import java.io.FileInputStream;
import java.io.IOException;

public class Issue04 {
    public static void main(String[] args) {
        /* 1. 일반적인 try-catch문 에서 다중 예외가 발생했다면 선행 예외는 무시되고 마지막 예외에 대한 내용만 처리되는지 확인 필요
           2. try-with-resources문에서 발생한 예외를 다 catch 하는 것인지 확인 필요 */
        
        // try () 안에서 생성한 객체는, 별도로 close()를 호출하지 않아도 try 블록 종료 후 자동 호출됨
//        try (CloseableResource cr = new CloseableResource()) {
//            cr.exceptionWork(true);
//        } catch(IOException | NullPointerException e) { // or 가 아니라서 둘 다 출력됨
//            e.printStackTrace();
//        } finally {
//            System.out.println("finally 호출");
//            throw new RuntimeException("RuntimeException 발생");
//        }
        
        try {
//            throw new IOException();
//            FileInputStream in = new FileInputStream(new File(""));
            throw new NullPointerException();
            
        } catch(IOException | NullPointerException e) { // or 가 아니라서 둘 다 출력됨
            e.printStackTrace();
        } finally {
            
        }
    }
}
