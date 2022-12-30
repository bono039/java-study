package issues;
public class CloseableResource implements AutoCloseable {
    public void exceptionWork(boolean exception) throws WorkException {
        System.out.println("exceptionWork 호출");
        if (exception) {
            throw new WorkException("WorkException 발생");
        }
    }
    
     @Override
     public void close() throws CloseException {
         System.out.println("CloseException 호출");
//         throw new CloseException("CloseException 발생");
     }
}
