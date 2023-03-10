package ch15;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
    
    private static final String FILE_PATH = "D:\\project\\workspace\\java-study\\source\\JavaStudy\\src\\ch15\\";
    
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
        String fileName = "test2.txt";
        
        File file = new File(FILE_PATH, fileName);
        
        try (
             FileOutputStream fileOut = new FileOutputStream(file);
             FileInputStream fileIn = new FileInputStream(file);
            ) {
            
            // 파일 생성
            StringBuffer sb = new StringBuffer();
            sb.append("123456789");
//            String str = "이것은 한글 테스트";
            fileOut.write(sb.toString().getBytes());

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
        String fileName = "test3.txt";
        
        File file = new File(FILE_PATH, fileName);
        
        try (FileInputStream fileIn = new FileInputStream(file)) {
            BufferedInputStream bufferIn = new BufferedInputStream(fileIn, 1024);
            
            byte[] byteBuff = new byte[32];
            
            bufferIn.read(byteBuff, 0, byteBuff.length);
            
            logger.debug("파일 내용 출력 : {}", new String(byteBuff)); 
            
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void test4() {
        /**
         * BufferedInputStream/BufferedOutputStream :
         *      바이트배열를 이용해서 한번에 여러 바이트를 출력
         */
        String fileName = "test4.txt";
        
        File file = new File(FILE_PATH, fileName);
        
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            // 버퍼 크기를 5로 지정함
            BufferedOutputStream bufferOut = new BufferedOutputStream(fileOut, 5);
            
            for (int i='1'; i<='9'; i++) {
                bufferOut.write(i);
            }
            
            // 파일 내용 다 출력됨
            bufferOut.close();
            // 파일 내용이 다 출력되지 못하고 끝나버림 
            // fileOut.close(); 
            
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void test5() {
        /**
         *  DataInputStream/DataOutputStream :
         *      기본형 타입으로 데이터를 읽고 쓸 수 있음
         *      출력한 값을 이진 데이터(binary data)로 저장되어서 문서 편집기로 볼 수 없음
         */
        String fileName = "test5.txt";
        
        File file = new File(FILE_PATH, fileName);
        
        try (FileOutputStream fileOut = new FileOutputStream(file);
            DataOutputStream dataOut = new DataOutputStream(fileOut);
            FileInputStream fileIn = new FileInputStream(file);
            DataInputStream dataIn = new DataInputStream(fileIn)) {
            
            for (int i=1; i<=9; i++) {
                dataOut.writeInt(i);
            }
            
            while (true) {
                // readInt() : 데이터가 없는 경우 EOFException을 발생시킴
                logger.debug("파일 내용 출력 : {}", dataIn.readInt());
            }
            
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
        System.out.println();
    }
    
    public static void test6() {
        File file = new File(FILE_PATH, "error.txt");
        
        try (FileOutputStream fileOut = new FileOutputStream(file);
             PrintStream print = new PrintStream(fileOut)) {
            
            // 출력 대상을 파일로 변경
            System.setErr(print);
            
            System.out.println("System.out");
            System.err.println("System.err");
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void test7() {
        // 폴더가 다 생성되지 않았다면 파일 생성시 오류 발생함
        String filePath = "D:\\project\\workspace\\java-study\\source\\JavaStudy\\src\\ch15\\test\\";
        String fileName = "test7.txt";
        
        File file = new File(filePath, fileName);

        // 방법1) File 이용
        try {
            file.mkdirs();
            file.createNewFile();
            
            logger.debug("파일 생성여부 : {}", file.exists());
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
        
        // 방법2) FileOutputStream 이용
        try ( FileOutputStream fileOut = new FileOutputStream(file)) {
            logger.debug("파일 생성여부 : {}", file.exists());
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        test6();
    }
}
