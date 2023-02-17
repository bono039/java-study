package exam;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 챕터13 문제
 *  
 * @author : pej 
 * @date : 2023.02.04
 */
public class Ch13Exam {
    @SuppressWarnings("unused")
    private static Logger logger = LogManager.getLogger(Ch13Exam.class);
    
    
    /**
     * <pre>
     * 다음의 코드는 Thread클래스를 상속받아서 쓰레드를 구현한 것이다.이 코드를 Runnable인터페이스를 구현하도록 변경하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.02.04 
     */
    public void exam01() {
//        class Thread1 extends Thread {
//            @Override
//            public void run() {
//                for(int i=0; i < 300; i++) { 
//                    System.out.print('-');
//                }
//            }
//        }
        
        class Thread1 implements Runnable {
            @Override
            public void run() {
                for(int i=0; i < 300; i++) { 
                    System.out.print('-');
                }
            }
        }
    }
    
    
    /**
     * <pre>
     * 다음 코드의 실행결과로 옳은 것은?
     * 정답 : a. 숫자가 순차적으로 출력된다.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.02.04 
     */
    public static void exam02() {
        /**
         * Q. 어떻게 순차적으로 되는건지?
         * A. 스레드 실행시킨게 아니라 단순히 run() 메소드만 호출한거여서 순차적으로 출력됨
         *    start() 메소드만 호출한다면 섞여서 출력됨
         */
        class Thread2 extends Thread { 
            public void run() {
                for (int i=0; i < 10; i++) {
                    System.out.print(i);
                }
            }
        }
        
        Thread2 t1 = new Thread2(); 
        t1.run();

        for (int i=0; i < 10; i++) {
            System.out.print(i);
        }
    }

    /**
     * <pre>
     * 다음의 코드를 실행한 결과를 예측하고, 직접 실행한 결과와 비교하라. 
     * 만일 예측한 결과와 실행한 결과의 차이가 있다면 그 이유를 설명하라.
     * 
     * => 바로 에러가 날 줄 알았지만 스레드가 멈출 것이다.
     * 정답 : 스레드 끝까지 돌았음
     * </pre>
     *
     * @author : pej 
     * @date : 2023.02.04
     */
    public static void exam05() throws Exception{
        class Thread3 extends Thread { 
            public void run() {
                for (int i=0; i < 10; i++) { 
                    System.out.println(i);

                    try {
                        Thread.sleep(1000);
                    } catch(Exception e) {

                    }
                }
            } // run()
        }
        
        Thread3 th1 = new Thread3(); 
        th1.start();

        try {
            Thread.sleep(5*1000);
        } catch(Exception e) {

        }

        throw new Exception("꽝~!!!");
    }
    
    /**
     * <pre>
     * 다음의 코드를 실행한 결과를 예측하고, 직접 실행한 결과와 비교하라. 
     * 만일 예측한 결과와 실행한 결과의 차이가 있다면 그 이유를 설명하라.
     * 
     * => 데몬스레드여서 메인에서 에러나면서 다같이 종료될 것 같다.
     * 정답 : main 스레드에서 에러가 나면서 데몬 스레드도 종료되었다.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.02.18
     */
    public static void exam06() throws Exception{
        class Thread4 extends Thread { 
            public void run() {
                for (int i=0; i < 10; i++) { 
                    System.out.println(i);

                    try {
                        Thread.sleep(1000);
                    } catch(Exception e) {

                    }
                }
            } // run()
        }
        
        Thread4 th1 = new Thread4();
        th1.setDaemon(true);
        th1.start();

        try {
            th1.sleep(5*1000);
        } catch(Exception e) {

        }

        throw new Exception("꽝~!!!");
    }
    
    static boolean exam07_stopped = false;
    
    /**
     * <pre>
     * 다음의 코드는 쓰레드 th1을 생성해서 실행시킨 다음 6초 후에 정지시키는 코드이다.
     * 그러나 실제로 실행시켜보면 쓰레드를 정지시킨 다음에도 몇 초가 지난 후에서야 멈춘다.
     * 그 이유를, 설명하고 쓰레드를 정지시키면 지체없이 바로 정지되도록 코드를 개선하시오.
     * 
     * => stop() 추가
     * 정답 : 
     * </pre>
     *
     * @author : pej 
     * @date : 2023.02.18
     */
    public static void exam07() {
        class Thread5 extends Thread {
            @Override
            public void run() {
                // stopped false의 값이 동안 반복한다.
                for (int i=0; !exam07_stopped; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(3*1000);
                        stop();
                    } catch(Exception e) {
//                        interrupt();
                    }
                }
            } // run()
        }
        
        Thread5 th1 = new Thread5();
        th1.start();
        
        try {
            Thread.sleep(6*1000);
        } catch(Exception e) {
            
        }
        
        exam07_stopped = true; // 쓰레드를 정지시킨다.
        /**
         * Q. 답안 풀이대로 하면 더 느리게 끝남. 이유가 뭔지?
         */
//        th1.interrupt(); // 답안 풀이

        System.out.println("stopped");
    }
    
    /**
     * <pre>
     * 다음은 사용자의 입력을 출력하고 종료하는 프로그램을 작성한 것으로, 10초 동안 입력이 없으면 자동종료되어야 한다.
     * 그러나 실행결과를 보면, 사용자의 입력이 10초 안에 이루어졌음에도 불구하고 프로그램이 즉시 종료되지 않는다. 
     * 사용자로부터 입력받는 즉시 프로그램이 종료되도록 수정하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.02.18
     */
    public static void exam09() throws Exception{
        class Exercise13_9_1 extends Thread { 
            public void run() {
                int i = 10;
                
                while (i!=0 && !isInterrupted()) {
                    System.out.println(i--);

                    try {
                        Thread.sleep(1000); // 1초 지연
                    } catch (InterruptedException e) {
                        /**
                         * sleep() 에서 InterruptedException 발생하면서 interrupted 상태를 false로 변경함
                         * 그래서 다시 바꿔주는 interrupt() 호출
                         */
                        interrupt();
                    }
                }

                System.out.println("카운트가 종료되었습니다.");
            }
        }
        
        Exercise13_9_1 th1 = new Exercise13_9_1(); 
        th1.start();
        
        String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
        System.out.println("입력하신 값은 " + input + "입니다."); 
        
        th1.interrupt(); // 쓰레드에게 작업을 멈추라고 요청한다.
    }
    
    public static void main(String[] args) throws Exception {
        exam07();
    }
}
