package ch15;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * I/O 학습
 *  
 * @author : pej 
 * @date : 2023.03.08
 */
public class TestIO {
    private static Logger logger = LogManager.getLogger(TestIO.class);
    
    public static void test1() {
        byte[] inArr = { 1, 2, 3, 4, 5 };
        byte[] outArr = null;
        
        /**
         * ByteArrayInputStream
         * ByteArrayOutputStream : 
         *  바이트배열은 사용하는 자원이 메모리뿐, 가비지컬렉터에 의해 자동으로 자원이 반환됨
         */
        ByteArrayInputStream in = new ByteArrayInputStream(inArr);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        int data = 0;
        
        /**
         * 방법1) 1Byte씩 읽기
         */
        // 데이터 읽기
        // read() : 0 ~ 255범위의 int로 반환, 읽을 데이터가 없는 경우 -1를 반환함
        while ( (data = in.read()) != -1) {
            // 데이터 출력
            out.write(data);
            logger.debug("data : {}", data);
        }
        
        outArr = out.toByteArray();
        
        logger.debug("방법1) 출력 : {}", Arrays.toString(outArr));
        
        /**
         * 방법2) 배열의 크기만큼 한꺼번에 읽기
         */
        byte[] outArr2 = null;
        byte[] temp = new byte[inArr.length];
        
        ByteArrayInputStream in2 = new ByteArrayInputStream(inArr);
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        
        // 데이터 읽기
        // read(byte b[], int off, int len) : 주어진 배열 b에 내용중에 off번째부터 len개 만큼 읽어서 입력
        in2.read(temp, 0,  temp.length);
        // 데이터 출력
        // write(byte b[], int off, int len) : 주어진 배열 b에 내용중에 off번째부터 len개 만큼 읽어서 출력
        out2.write(temp, 0, temp.length);
        
        outArr2 = out2.toByteArray();
        
        logger.debug("방법2) 출력 : {}", Arrays.toString(outArr2));
    }
    
    public static void test2() {
        /**
         * FileInputStream
         * FileOutputStream :
         *  파일 입출력 하기 위한 스트림
         */
        File file = new File("D:\\project\\workspace\\java-study\\source\\JavaStudy\\src\\ch15\\file.txt");
        
        try (
             FileOutputStream fileOut = new FileOutputStream(file);
             FileInputStream fileIn = new FileInputStream(file);
            ) {
            
            // 파일 생성
            String str = "이것은 한글 테스트";
            fileOut.write(str.getBytes());

            // 파일 읽기
            /**
             * 방법1) 한글이 깨져서 안됨
             */
            /*
            int data = 0;
            while ( (data = fileIn.read()) != -1 ) {
                logger.debug("파일 내용 출력 : {}", (char) data);
            }
            */
            
            /**
             * 방법2)
             */
            byte[] temp = new byte[fileIn.available()];
            fileIn.read(temp, 0, temp.length);
            
            logger.debug("파일 내용 출력 : {}", new String(temp));
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void test3() {
        /**
         * 바이트 기반의 보조스트림
         *  BufferedInputStream/BufferedOutputStream, 
         *  DataInputStream/DataOutputStream 등
         */
        File file = new File("D:\\project\\workspace\\java-study\\source\\JavaStudy\\src\\ch15\\file.txt");
        
        try (FileInputStream fileIn = new FileInputStream(file)) {
            BufferedInputStream bufferIn = new BufferedInputStream(fileIn, 1024);
            
            byte[] byteBuff = new byte[32];
            
            bufferIn.read(byteBuff, 0, byteBuff.length);
            
            logger.debug("파일 내용 출력 : {}", new String(byteBuff)); 
            
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        test3();
    }
}
