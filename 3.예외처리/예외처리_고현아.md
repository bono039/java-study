Java의 정석 Chapter 08 요약(06)
---

#### 1. 클래스 계층구조
이슈1) checked 예외가 컴파일 단계에서 정말 잡히는지 확인 필요  
 Object - Throwable - Exception, Error  
 Error: OutOfMemoryError, StackOverFlowError...  
 Exception: IOException, ClassNotFoundException,  
           RuntimeException(ArithmeticException, ClassCaseException,   
           NullPointException, IndexOutOfBoundsException...)  
 - 예외 클래스 중 RuntimeException 클래스 외에는 하위 클래스를 갖지 않음
 - RuntimeException 이하 클래스들은 컴파일러가 예외처리 여부를 확인하지 않음(unchecked예외)
 - 위를 제외한 예외 클래스들은 throw new IOException(); 등 코딩할 경우에  
   컴파일 단계에서 처리여부 확인됨(checked예외)
<br>
<br>

#### 2. 멀티 catch 블록
 - 연결할 예외 클래스 갯수에 제한이 없음
 - 상속 관계의 예외 클래스를 연결할 경우 컴파일 에러 발생
```
try {
} catch (IOException | ArithmeticException exc) {
    exc.printStackTrace();
    System.out.println(exc.getMessage());
}
```
<br>

#### 3. 선언시 예외 표기
 - 선언부에 throws 이용하여 발생 가능성 있는 예외를 알림 및 예외처리를 강제할 수 있음  
   (해당 메소드를 호출한 쪽에 예외를 전달하여 처리할 수 있도록함)
 - 통상 RuntimeException 클래스들은 던지지 않음
<br>
<br>

#### 4. finally 블록
 - 선행되는 try, catch 블록에서 return 하더라도 finally 실행 후 메소드 종료됨  
  따라서 최종적으로는 finally 에서 지정한 값이 return 됨 (return or throw)
 <br>
 <br>

#### 5. try-with-resources
이슈2) 예외 동시 발생시 마지막 예외만 처리되는지 확인 필요  
- try () 안에서 생성한 객체는, 별도로 close()를 호출하지 않아도 try 블록 종료 후 자동 호출됨
- 2개 이상 생성할 경우 ; 으로 문장을 구분함
- close()를 자동호출할 수 있는 클래스는 AutoCloseable 인터페이스를 구현한 것임
- 예외가 try{}, close() 모두에서 발생한 경우 나중에 실행된 close()의 예외가 억제된 예외로 다뤄짐
  (Throwable 클래스의 addSuppressed(), getSuppressed() 에 의함)
- 일반적인 try-catch 에서 두 예외가 발생했다면 선행 예외는 무시되고 마지막 예외에 대한 내용만 처리됨  
  (ex try, finally 블록에서 발생한 경우)
```
try (CloseableResource cr = new CloseableResource()) {
  cr.exceptionWork(true);
} catch(WorkException | CloseException e) { // or 가 아니라서 둘 다 출력됨
  e.printStackTrace();
}

class CloseableResource implements AutoCloseable {
  public void exceptionWork(boolean exception) throws WorkException {
    System.out.println("exceptionWork 호출");
    if (exception) throw new WorkException("WorkException 발생");
  }
  public void close() throws CloseException {
    System.out.println("CloseException 호출");
    throw new CloseException("CloseException 발생");
  }
}

class WorkException extends Exception {
  WorkException(String msg) {super(msg);}
}
class CloseException extends Exception {
  CloseException(String msg) {super(msg);}
}
```
<br>

#### 6. 연결된 예외(chained exception)
 - A 로 인해 B 예외 발생시 A 가 '원인 예외(cause exception)'
 - initCause() 이용하여 원인이 되는 예외 지정 가능
 - getCause() 원인 예외 반환 메소드
 - RuntimeException(Throwable cause) 생성자를 이용하여 원인 예외 지정 가능  
   위와 같이 생성하여 throw 하면 checked 예외들도 예외처리 없이 넘길 수 있음
<br>
