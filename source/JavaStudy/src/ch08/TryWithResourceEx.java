package ch08;

public class TryWithResourceEx {
    public static void main(String args[]) {

//        try (CloseableResource cr = new CloseableResource()) {
//            cr.exceptionWork(false); // 예외가 발생하지 않는다.
//        } catch(WorkException e) {
//            e.printStackTrace();
//        } catch(CloseException e) {
//            e.printStackTrace();
//        }
//        System.out.println();
//    
//        try (CloseableResource cr = new CloseableResource()) {
//            cr.exceptionWork(true); // 예외가 발생한다.
//        } catch(WorkException e) {
//            e.printStackTrace();
//        } catch(CloseException e) {
//            e.printStackTrace();
//        }
//        System.out.println();
        
        /**
         * P438 일반 try-catch문일때 WorkException은 무시되고, CloseException만 나온다고 하는데 
         * 실행결과 WorkException만 나옴
         */
//        CloseableResource cr = null;
//        
//        try {
//            cr = new CloseableResource();
//            cr.exceptionWork(true); // 예외가 발생한다.
//            cr.close();
//        } catch (CloseException e) {
//            System.out.println("catch - CloseException");
//            e.printStackTrace();
//        } catch(WorkException e) {
//            System.out.println("catch - WorkException");
//            e.printStackTrace();
//        } finally {
//            System.out.println("finally");
//            try {
//                if (cr != null) {
//                  cr.close();
//                }
//            } catch (CloseException e) {
//               e.printStackTrace();
//            }
//        }
        
        
        // 외부 try에서 잡으면 마지막 CloseException만 나옴... 왜지???
        CloseableResource cr = new CloseableResource();
        try {
            try {
                cr.exceptionWork(true);
            } finally {
                try {
                    cr.close();
                } finally {

                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } // main의 끝
}

class CloseableResource implements AutoCloseable {
    public void exceptionWork(boolean exception) throws WorkException {
        System.out.println("exceptionWork("+exception+")가 호출됨");

        if(exception)
            throw new WorkException("WorkException발생!!!");
    }

    public void close() throws CloseException {
        System.out.println("close()가 호출됨");
        throw new CloseException("CloseException발생!!!");
    }
}

class WorkException extends Exception {
    WorkException(String msg) { super(msg); }
}

class CloseException extends Exception {
    CloseException(String msg) { super(msg); }
}
