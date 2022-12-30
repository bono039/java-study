package ch08;

/**
 * 테스트 사용자 정의 에러
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class MyException extends Exception {
    
    private static final long serialVersionUID = 1L;

    MyException(String msg) {
        super(msg);
    }
}
