package ch07;

/**
 * 하위 클래스
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class Me extends Parent {
    public Me() {
        // super() 를 선언하지 않았음
        System.out.println("Me 생성자입니다.");
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Me me = new Me();
    }
}
