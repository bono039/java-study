package ch09;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 얕은복사, 깊은복사 테스트
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestClone {
    private static Logger logger = LogManager.getLogger(TestClone.class);
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        TestVO obj1 = new TestVO();
        obj1.setCpuNm("i7 12세대");
        obj1.setRamSize(32);
        
        ArrayList<TestVO> orgList = new ArrayList<>();
        orgList.add(obj1);
        
        /**
         * 얕은  복사(Shallow Copy) 
         *  - 원본과 같은 주소값을 참조하기 때문에 원본의 값이 바뀌면 같이 변경됨
         */
        ArrayList<TestVO> shallowCopyList = (ArrayList<TestVO>) orgList.clone();
        
        /**
         * 깊은  복사(Deep Copy) 
         *  - 원본과 복사본이 다른 주소값을 참조하기때문에 원본의 값이 변경되어도 복사본의 값은 변경되지 않음
         */
        ArrayList<TestVO> deepCopyList = new ArrayList<>();
        for (TestVO item : orgList) {
            TestVO tmp = new TestVO();
            tmp.setCpuNm(item.getCpuNm());
            tmp.setRamSize(item.getRamSize());
            tmp.setSsdSize(item.getSsdSize());
            
            deepCopyList.add(tmp);
//             deepCopyList.add(item); // 얕은 복사
        }
        
        // 값 변경
        obj1.setSsdSize(128);
        
        logger.debug("#### orgList ####");
        for (TestVO item : orgList) {
            logger.debug(item.toString());
            
        }
        logger.debug("#### shallowCopyList ####");
        for (TestVO item : shallowCopyList) {
            logger.debug(item.toString());
        }
        logger.debug("#### deepCopyList ####");
        for (TestVO item : deepCopyList) {
            logger.debug(item.toString());
        }
        
    }
}
