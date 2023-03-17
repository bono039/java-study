import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Exam15_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			int lineNum = Integer.parseInt(args[0]);
			String fileName = args[1];
			
		    File file = new File(fileName);
		    
		    if (file.exists()) {
		    	FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr); //보조 스트림
				
			    String lineTxt = "";
			    
			    for (int i = 1; i <= lineNum; i++) {
			    	lineTxt = br.readLine();
			    	if (lineTxt != null) {
			    		System.out.println(lineTxt);
			    	} else {
			    		break;
			    	}
			    }
			    
			    br.close();
		    } else {
		    	System.out.println(fileName + "은/는 디렉토리이거나, 존재하지 않는 파일임1");
		    	String path = System.getProperty("user.dir");
		        System.out.println("현재 작업 경로: " + path);
		    }
		
		} catch (FileNotFoundException e) {
			//System.out.println(fileName + "은/는 디렉토리이거나, 존재하지 않는 파일임2");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

	}//main end

}
