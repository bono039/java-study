package exam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
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
     * 방법3) Files.lines 이용
     */
    public static void exam01_3() {
        // 테스트 : Ch14Exam.java 150
        try (BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in)); ) {
            
            // 입력된 값 나누기
            String[] input = bfReader.readLine().split(" +");
            
            Path path = null;
            
            // 확장자 입력 안한 경우 자동으로 '.java'로 되게 
            if (input[0].lastIndexOf(".") == -1) {
                path = Paths.get(FILE_PATH, input[0] + FILE_EXTENSION);
            } else {
                path = Paths.get(FILE_PATH, input[0]);
            }
            
            AtomicInteger lineNum = new AtomicInteger(1);
            
            Files.lines(path).forEach(i -> {
                // 현재값을 가져오고 자동 증가
                // getAndIncrement() : 상수여서 람다식에서 값 증가 가능
                if (lineNum.get() <= Integer.parseInt(input[1])) {
                    System.out.println(lineNum.getAndIncrement() + ":" + i);
                }
            });
            
            // 왜 a의 값이 안 바뀌지...?
//            Files.lines(path).forEach(i -> {
//              int a = 1;
//              System.out.println(a + ":" + i);
//              a++;
//            });
        }
        catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        } 
    }
    
    /**
     * 방법4) BufferedReader 이용
     */
    public static void exam01_4() {
        // 테스트 : Ch14Exam.java 150
        try (BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in)); ) {
            
            // 입력된 값 나누기
            String[] input = bfReader.readLine().split(" +");
            
            File file = new File(FILE_PATH, input[0]);
            
            // 확장자 입력 안한 경우 자동으로 '.java'로 되게 
            if (file.getName().lastIndexOf(".") == -1) {
                file = new File(FILE_PATH, input[0] + FILE_EXTENSION); 
            }

            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            
//            bufferReader.lines().forEach(System.out::println);
            
            // lineNum 증가하지 않음
            /*
            bufferReader.lines().forEach(i -> {
                int lineNum = 1;
                System.out.println(lineNum + ":" + i);
                ++lineNum;
            });
            */
            
            /**
             * AtomicInteger : int형 래퍼 클래스로 멀티스레드 환경에서 동시성을 보장함
             */
            AtomicInteger lineNum = new AtomicInteger(1);

            bufferReader.lines().forEach(i -> {
                // 현재값을 가져오고 자동 증가
                if (lineNum.get() <= Integer.parseInt(input[1])) {
                    System.out.println(lineNum.getAndIncrement() + ":" + i);
                }
            });
            
            bufferReader.close();
        }
        catch (IOException e) {
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
     * @date : 2023.03.15
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
    
    /**
     * <pre>
     * [15-3] 다음은 디렉토리의 요약정보를 보여주는 프로그램이다. 
     * 파일의 개수, 디렉토리의 개수, 파일의 총 크기를 계산하는 countFiles()를 완성하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.15
     */
    static int totalFiles = 0; 
    static int totalDirs = 0; 
    static int totalSize = 0;
    
    public static void exam03() {
        // 경로 테스트 : D:\\project\\workspace\\java-study\\source\\JavaStudy\\src\\exam
        try (BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] arr = bfReader.readLine().split(" +");
            
            if (arr.length != 1) {
                System.out.println("USAGE : java DirectoryInfoTest DIRECTORY");
                System.exit(0);
            }
            
            File dir = new File(arr[0]); 
            
            if (!dir.exists() || !dir.isDirectory()) {
                System.out.println("유효하지 않은 디렉토리입니다."); 
                System.exit(0);
            }
            
            /**
             * countFiles() : 입력받은 디렉토리는 빼고 디렉토리 개수를 구함
             * countFiles2(), countFiles3() : 입력받은 디렉토리도 포함해서 디렉토리 개수를 구함
             */
            countFiles(dir); 
            countFiles2(dir);
            countFiles3(dir);
            
            System.out.println();
            System.out.println("총 " + totalFiles + "개의 파일");
            System.out.println("총 " + totalDirs + "개의 디렉토리");
            System.out.println("크기 " + totalSize + " bytes");
//            System.out.println("크기 " + String.format("%.2f", totalSize / 1024.0) + " KB");
            
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }
    
    public static void countFiles(File dir) {
        /*
        (1) 아래의 로직에 맞게 코드를 작성하시오.
           1. dir의 파일목록(File[])을 얻어온다.
           2. 얻어온 파일목록의 파일 중에서... 디렉토리이면, totalDirs의 값을 증가시키고 countFiles()를 재귀호출한다.
           3. 파일이면, totalFiles를 증가시키고 파일의 크기를 totalSize에 더한다.
        */
        
        File[] fileList = dir.listFiles();
        
        for (File file : fileList) {
            if (file.isDirectory()) {
                totalDirs++;
//                 logger.debug("폴더명 : {}", file.getAbsolutePath());
                
                // 폴더인 경우 자기 자신을 다시 호출해서 파일 개수와 사이즈를 구함
                File temp = new File(file.getAbsolutePath());
                countFiles(temp);
            } else if (file.isFile()) {
                totalFiles++;
                totalSize += file.length();
//                logger.debug("파일명 : {} / 파일사이즈 : {}", file.getName(), file.length());
//                logger.debug("totalSize : {}", totalSize);
            }
        }
    }
    
    /**
     * 방법2) Files.walk() 이용 :모든 파일, 폴더(디렉토리) 탐색
     */
    public static void countFiles2(File dir) {
        int totalFiles = 0;    // 파일 개수 합계
        int totalDirs = 0;     // 경로 개수 합계
        int totalSize = 0;     // 파일 사이즈 합계
        
        Path dirPath = Paths.get(dir.getAbsolutePath());
        
        try {
//            Files.walk(dirPath).filter(file -> file.toFile().isDirectory()).forEach(System.out::println);
            totalDirs = Files.walk(dirPath).filter(file -> file.toFile().isDirectory()).mapToInt(i -> 1).sum() - 1;
            totalFiles = Files.walk(dirPath).filter(file -> file.toFile().isFile()).mapToInt(i -> 1).sum();
            totalSize = Files.walk(dirPath).filter(file -> file.toFile().isFile()).mapToInt(i -> (int) i.toFile().length()).sum();
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("=========countFiles2=========");
        sb.append("\n");
        sb.append("총 " + totalFiles + "개의 파일");
        sb.append("\n");
        sb.append("총 " + totalDirs + "개의 디렉토리");
        sb.append("\n");
        sb.append("크기 " + totalSize + " bytes");
        sb.append("\n");
        
        logger.debug(sb.toString());
    }
    
    /**
     * 방법3) FileUtils 이용 : Apache Commons IO 
     */
    public static void countFiles3(File dir) {
        int totalFiles = 0;    // 파일 개수 합계
        int totalDirs = 0;     // 경로 개수 합계
        int totalSize = 0;     // 파일 사이즈 합계
        
        // 경로에 해당하는 모든 디렉토리, 파일 조회
        List<File> fileList = (List<File>) FileUtils.listFilesAndDirs(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        
        for (File file : fileList) {
            if ( FileUtils.isDirectory(file) ) {
                totalDirs++;
            } else if (file.isFile()) {
                totalFiles++;
                totalSize += file.length();
            }
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("=========countFiles3=========");
        sb.append("\n");
        sb.append("총 " + totalFiles + "개의 파일");
        sb.append("\n");
        sb.append("총 " + (totalDirs - 1) + "개의 디렉토리");
        sb.append("\n");
        sb.append("크기 " + totalSize + " bytes");
        sb.append("\n");
        
        logger.debug(sb.toString());
    }
    
    /**
     * <pre>
     * [15-4] 커맨드라인으로 부터 여러 파일의 이름을 입력받고, 이 파일들을 순서대로 합쳐 서 새로운 파일을 만들어 내는 프로그램(FileMergeTest.java)을 작성하시오.
     * 단, 합칠 파 일의 개수에는 제한을 두지 않는다.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.16
     */
    public static void exam04() {
        // 테스트 : java FileMergeTest result.txt test1.txt test2.txt test3.txt
        
        Path originPath = Paths.get("D:", "project", "workspace", "java-study", "source", "JavaStudy", "src", "exam", "test");

        System.out.println("USAGE: java FileMergeTest MERGE_FILENAME FILENAME1 FILENAME2 ...");
        
        try (BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] arr = bfReader.readLine().split(" +");
            
            if ( "type".equals(arr[0]) ) {
                Path readPath = originPath.resolve(arr[1]);
                // 파일이 있는 경우에만
                if (Files.exists(readPath)) {
                    try (FileChannel channel = FileChannel.open(readPath, StandardOpenOption.READ)) {
                       // allocate() : 버퍼 할당
                       ByteBuffer byteBuffer = ByteBuffer.allocate((int) Files.size(readPath));

                       channel.read(byteBuffer);
                       
                       // 시작 위치 초기화
                       byteBuffer.flip();

                       System.out.println(Charset.defaultCharset().decode(byteBuffer).toString());
                    } catch (IOException e) {
                        logger.error("파일 읽기 에러 : {}", e.getMessage());
                    }
                }
            } else {
                Path mergeFile = originPath.resolve(arr[2]);
                
                try {
                    // 복사할 파일이 없으면 생성
                    if (!Files.exists(mergeFile)) {
                        Files.createFile(mergeFile);
                    }
                    
                    FileOutputStream fileOut = new FileOutputStream(mergeFile.toFile());
                    FileInputStream fileIn = null;
                    
                    for (int i=3; i<arr.length; i++) {
                        fileIn = new FileInputStream(originPath.resolve(arr[i]).toFile());
                        
                        FileChannel channelIn =  fileIn.getChannel();
                        FileChannel channelOut = fileOut.getChannel();
                         
                        long size = channelIn.size();
                        channelIn.transferTo(0, size, channelOut);
                    }
                    
                    fileOut.close();
                    fileIn.close();
                    
                } catch (IOException e) {
                    logger.error("에러 : {}", e.getMessage());
                }
                
                System.out.println("파일 합치기 성공");
            }
        } catch (IOException e) {
            logger.error("에러 : {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        exam04();
    }
}