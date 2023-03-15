package exam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 챕터15 문제
 *  
 * @author : pej 
 * @date : 2023.03.10
 */
public class Ch15Exam {
    private static Logger logger = LogManager.getLogger(Ch15Exam.class);
    
    private static final String FILE_PATH = "D:\\project\\workspace\\java-study\\source\\JavaStudy\\src\\exam\\";
    private static final String FILE_EXTENSION = ".java";
    
    /**
     * <pre>
     * [15-1] 커맨드라인으로 부터 파일명과 숫자를 입력받아서, 입력받은 파일의 내용의 처음부터 입력받은 숫자만큼의 라인을 출력하는 프로그램(FileHead.java)을 작성하라.
     * [Hint] BufferedReader의 readLIne()을 사용하라.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.10
     */
    public static void exam01() {
        /**
         * 방법1) 한글자씩 읽기 => 너무 비효율적임
         */
        /*
        int inputData = 0;
        try {
            while ( (inputData = System.in.read()) != -1) {
                System.out.println("입력값 : " + (char) inputData);
            }
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
        */
        /**
         * 방법2) 라인씩 읽기
         */
        try (BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in))) {
            // 입력된 값 나누기
            String[] input = bfReader.readLine().split(" +");
            // Stream.of(input).forEach(System.out::println);
            
            
            File file = new File(FILE_PATH, input[0]);
            
            // 확장자 입력 안한 경우 자동으로 '.java'로 되게 
            if (file.getName().lastIndexOf(".") == -1) {
                file = new File(FILE_PATH, input[0] + FILE_EXTENSION); 
            }
            // 파일 존재여부 체크
            if ( !file.exists() ) {
                System.out.println(input[0] +"은/는 디렉토리이거나, 존재하지 않는 파일입니다.");
                return;
            }
            
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            StringBuffer sb = new StringBuffer();
            
            for (int i=0; i<Integer.parseInt(input[1]); i++) {
                /**
                 * Q.입력받은 라인수보다 소스코드 더 적은경우에는 입력받은 라인수 상관없이 전체 소스만 출력되게 
                 */
//                System.out.println(br.readLine());
                sb.append(i+1);
                sb.append(":");
                sb.append(br.readLine());
                sb.append("\n");
            }
            
            System.out.println(sb.toString());
            
            br.close();
        } catch (NumberFormatException | IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    /**
     * <pre>
     * [15-2] 지정된 이진파일의 내용을  실행결과와 같이 16진수로 보여주는 프로그램 (HexaViewer.java)을 작성하라.
     * [Hint] PrintStream과 printf()를 사용하라.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.10
     */
    public static void exam02() {
        File file = new File(FILE_PATH, "exam02.dat");
        
        // byte 배열은 이진법
        byte[] byteArr = { 
            (byte) 202, (byte) 254, (byte) 186, (byte)190, 0, 0, 0, 49, 0, 68, 10, 0, 12, 30, 9,
            0, 31, 0, 32, 8, 0, 33, 10, 0, 8, 0, 34, 10, 0, 31, 0
        };

        try (FileOutputStream fileOut = new FileOutputStream(file);
             FileInputStream fileIn = new FileInputStream(file);
             // 이렇게 하면 sysout으로 출력할 필요 없이 자동으로 콘솔창에 나옴
             PrintStream printOut = new PrintStream(System.out); ) {
            
            // 파일에 내용 추가
            fileOut.write(byteArr);
            
            // 파일 읽기
            int data = 0;
            while ( (data = fileIn.read()) != -1 ) {
                /**
                 * %02x : 소문자로 나옴
                 * %02X : 대문자로 나옴
                 */
                printOut.printf("%02X ", data);
            }
            
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void test() {
//        byte byteArray[] = {(byte)00, (byte)10, (byte)20, (byte)30, (byte)40};
        byte[] byteArray = { (byte) 202, (byte) 254, (byte) 186, (byte)190, 0, 0, 0, 49, 0 };
            String hexString = 
                javax.xml.bind.DatatypeConverter
                .printHexBinary(byteArray);
            System.out.println("Byte Array: "); 
            System.out.println(Arrays.toString(byteArray));
            System.out.println("Hex String Conversion: "
                               + hexString);
    }
    
    static int totalFiles = 0; 
    static int totalDirs = 0; 
    static int totalSize = 0;
    
    /**
     * <pre>
     * [15-3] 다음은 디렉토리의 요약정보를 보여주는 프로그램이다. 파일의 개수, 디렉토리의 개수, 파일의 총 크기를 계산하는 countFiles()를 완성하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.10
     */
    public static void exam03(String[] arr) {
        if (arr.length != 1) {
            System.out.println("USAGE : java DirectoryInfoTest DIRECTORY"); 
            System.exit(0);
        }
        
        File dir = new File(arr[0]); 
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("유효하지 않은 디렉토리입니다."); 
            System.exit(0);
        }
        countFiles(dir); 
        System.out.println();
        System.out.println("총 " + totalFiles + "개의 파일");
        System.out.println("총 " + totalDirs + "개의 디렉토리"); System.out.println("크기 " + totalSize + " bytes");
    }
    
    public static void countFiles(File dir) {
        /*
        (1) 아래의 로직에 맞게 코드를 작성하시오.
         1. dir의 파일목록(File[])을 얻어온다.
         2. 얻어온 파일목록의 파일 중에서... 디렉토리이면, totalDirs의 값을 증가시키고 countFiles()를 재귀호출한다.
         3. 파일이면, totalFiles를 증가시키고 파일의 크기를 totalSize에 더한다.
        */
    }

    public static void main(String[] args) {
        exam02();
    }
}
