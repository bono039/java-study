import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public class Exam15_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String fileName = args[0];
		    File file = new File(fileName);
		    
		    if (file.exists()) {
		    	FileInputStream fi = new FileInputStream(file);
		    	PrintWriter fw = new PrintWriter(System.out);
				
			    int data = 0;
			    int index = 0;
			    
			    while ((data = fi.read()) != -1) {
			    	fw.printf("%02X ", data);
			    	index++; //출력 횟수 count
			    	if (index%16 == 0) {
			    		fw.println();
			    	}
			    }
			    
			    fw.close();
			    fi.close();
		    } else {
		    	System.out.println(fileName + "은/는 디렉토리이거나, 존재하지 않는 파일임1");
		    	String path = System.getProperty("user.dir");
		        System.out.println("현재 작업 경로: " + path);
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

	}

}
