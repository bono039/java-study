package ch07;

/**
 * Interface 학습
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public interface TestInterface {
    static void printStatic() {
        System.out.println("static 메소드");
    }
    
    default void printDefault() {
        System.out.println("기본 메소드");
    }
    
    public abstract void print();
}
