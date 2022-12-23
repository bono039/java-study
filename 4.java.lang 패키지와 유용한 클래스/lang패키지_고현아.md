Java의 정석 Chapter 09 요약(21)
---

#### 1. Object 클래스
 - protected Object clone() :  
    객체 자신의 복사본 반환.  
    값만 복사하므로 복제된 인스턴스는 같은 주소를 가짐.  
    배열 포함하여 이용시 오버라이딩 하여 새 배열 생성 + 값 복사 해야함.  
    Cloneable 인터페이스를 구현한 클래스만 해당 메소드 이용 가능(복제 허용).  
    배열은 System.arraycopy() 와 같은 결과를 얻음.  
    CloneNotSupporteException 예외처리 필수.
 - protected void finalize() : 객체 소멸시 자동 호출(가비지 콜렉터에 의해서)
 - equals() : **1)두 객체의 주소값 비교
 - hashCode() : 
    입력 값이 저장된 위치를 알려주는 해시코드 반환. 64bit JVM 에서는 8byte 주소값을  
    4byte 해시코드로 만들기 때문에 해시코드 중복 가능성 있음.
 - getClass() :  
    Class 객체는 클래스 당 1개만 존재함  
    클래스 파일이 ClassLoader에 의해 메모리에 올라갈 때 생성됨.  
    ClassLoader는 메모리에서 해당 클래스 객체를 찾고, 없으면 클래스 패스 통해 클래스 파일을 찾아 객체 생성
```
//'Class' 객체 가져오기
new Object().getClass()  //생성된 객체의 메소드 이용
Object.class  //클래스 리터럴(*.class 표기)을 이용한 객체 호출
Class.forName("Object")  //클래스명을 이용

//객체 생성 방법
new Object()  //new 연산자 이용
Object.class.newInstance()  //'Class' 객체를 이용, InstantiationException 예외처리 필수
```
<br>

#### 2. identityHashCode()
 입력 받은 객체의 주소값으로 해시코드를 생성, 모든 객체에 대해 항상 다른 해시코드 값을 반환함.  
 (System.identityHashCode(Object o) 호출 결과는 실행 할 때 마다 달라질 수 있음 주의)
<br>
<br>

#### 3. 얕은 복사와 깊은 복사
 얕은 복사(shallow copy) : 원본과 복제본이 같은 객체를 공유하여 서로 영향을 받음.  
 깊은 복사(deep copy): 원본과 복제본이 서로 다른 객체를 가리킴
<br>
<br>

#### 4. String
 - 생성된 String 인스턴스의 문자열은 변경 불가하므로 결합, 추출 등 연산 마다 새 인스턴스 생성됨.  
   (수정이 잦은 경우 StringBuffer, StringBuilder 이용해야)
 - 문자열 리터럴("") 지정시 해당 클래스가 메모리에 로드될 때 객체 생성됨.   
   new String() 은 연산자에 의해 메모리에 할당되므로 같은 문자여도 새로운 인스턴스가 생성됨.
 - 보통 String s = ""; char c = ' '; 빈 문자열, 공백 문자로 초기화 함.
<br>

#### 5. join()
 - 지정 구분자를 넣어 문자열 결합 가능 (new StringJoiner(",", "[", "]"))
 - 해당 클래스는 1.8부터 추가 되었음
<br>

#### 6. 보충문자(supplementary characters)
 - 유니코드가 기존 16bit 에서 20bit으로 확장되었는데 1.5부터 적용됨
 - 새로 추가된 문자들을 보충문자라고 하며 매개변수 int ch 이면 지원하는 것임
<br>

#### 7. 문자 인코딩 변환
 - 자바는 기본적으로 UTF-16 이용하지만, 문자열 리터럴의 문자들은 OS 인코딩을 따름
 - 한글 윈도우즈는 CP949(MS949) 이용함
 ```
 //사용 가능 문자 인코딩 목록
 System.out.println(java.nio.charset.Carset.availableCharsets();)

 //문자열 인코딩 변환
 new String("원하는 문자열".getBytes("UTF-8", "UTF-8")); //생성자에서 byte 배열을 받으면 바꿔줌
 ```
<br>

#### 8. substring()
 - substring(int start, int end) 에서 start부터(포함) end 이전(미포함) 문자열만 추출됨.
 - 메소드 실행시 end 값이 length 넘거나 start 보다 작으면 checkBoundBeginEnd() 의해 예외 발생함
<br>

#### 9. StringBuffer
 - 생성자에서 기본 16자(byte) 크기로 생성. 
 - 생성시 지정 문자열이 있는 경우 문자열 크기 + 16 으로 생성됨.
 - equals() 오버라이딩 않는 클래스라 주소 비교가 되어 같은 문자열도 false, 비교하려면 string 으로!
 - 멀티 스레드에 안전하도록 동기화함
<br>

#### 10. StringBuilder
 싱글 스레드인 경우 StringBuffer 보다 성능이 좋을 수 있음. 동기화만 않고 기능은 같음.
<br>
<br>

#### 11. 예외 발생 메서드(*Exact())
 - 1.8부터 추가됨. 증감, 사칙, 부호 전환 연산시 오버플로우가 발생하면 ArithmeticException 발생시킴
 - 부호 변환의 경우 ~a+1 인데, ~a 한 시점에서 최대값일 수 있음
<br>

#### 12. Wrapper 클래스
 - 기본형(primitive type)의 값을 객체 형태로 작업해야할 때 이용
 - 오토박싱(기본형에서 Wrapper타입으로) ex valueOf()  
   언박싱(Wrapper타입에서 기본형으로) ex intValue()  
   박싱되므로 형변환(int)(Integer) 생략 가능!
 - Wrapper 클래스 객체에 비교 연산자(==)를 이용할 수는 없음(equals(), compareTo()이용해야)
 - 각 MAX_VALUE, MIN_VALUE, BYTES 등 상수 있음
<br>

#### 13. Number 클래스
 Byte, Short, Integer, Long, Float, Double, BigInteger, BigDecimal ...  
 (BigInteger, BigDecimal 는 Wrapper 아님)
<br>
<br>

#### 14. 문자열을 숫자로 변환
 - 문자열에서 기본형으로: Byte.parseByte()
 - 문자열에서 객체로(Wrapper): Byte.valueOf()
 - valueOf() 으로 통일 가능하지만 성능은 낮음
<br>

#### 15. java.util.Objects 클래스 (Object 보조용)
 - import static java.util.Objects.*; 
   클래스명(Objects) 생략하여 호출 가능, 해당 메소드는 다중정의만 가능!
 - isNull() : null 이면 true
 - nonNull() : null 아니면 true
 - requireNonNull() : null 이면 NullPointerException 예외 발생시킴
 - deepEquals() : 객체를 재귀적으로 비교. ex 2차원 문자열 배열 등
 - equals() : **2) return (a == b) || (a != null && a.equals(b));  
                   'a와 b가 모두 null인 경우에는 참을 반환한다는 점 빼고는 특별한 것이 없다'
 ```
 import java.util.*;

 Object o1 = null;
 Object o2 = null;
 System.out.print(Objects.equals(o1, o2)); //실행결과 true
 ```
<br>

#### 16. 대소문자 구분 않는 문자열 비교
 compare("aAa", "Aaa", String.CASE_INSENSITIVE_ORDER)
<br>
<br>

#### 17. java.util.Random
 - Math.random()은 내부적으로 Random 클래스를 이용함
 - 같은 종자값을 갖는 Random 인스턴스는 시스템이나 실행시간 등에 관계 없이 
   항상 같은 값을 같은 순서로 반환함
<br>

#### 18. 정규식(Regular Expression)
 ```
 import java.util.rejex.*; //Pattern(정규식 정의시), Matche(정규식과 데이터 비교시) 
 
 Pattern p = Pattern.compile("c[a-z]*");
 Matcher m = p.matcher(dataArr[i]);
 if(m.matches()){//부합하는 경우의 실행}

 m.find() //대상 문자 중 일치하는 부분을 찾으면 true
 m.appendReplacement(sb, "대체 문자열") //대상 문자열의 맨 처음부터 대체된 문자열까지 sb에 더함
 m.appendTail(sb) //마지막으로 치환된 문자 그 이후의 문자열을 sb에 더함 (find()와 관계 없음)
 ```
<br>

#### 19. 문자열 구분 방법
 - Scanner.useDelimiter(), String.split(), StringTokenizer ...
 - split() 은 "" 빈문자열을 인식하고 배열에 담아 반환, StringTokenizer 는 빈문자열 다루지 않음.  
   빈문자열이 필요 없다면 StringTokenizer 가 성능 좋음.
<br>

#### 20. 한글 숫자를 아라비아 숫자로 변환 예제(9-41)
https://replit.com/@rank846/study002#Main.java
```
 tmpResult += num; 
 result += (tmpResult!=0 ? tmpResult : 1) * UNIT_NUM[UNIT.indexOf(token)];
 tmpResult = 0; // 어떻게 30이 남음?? result 에 넣은거 아님???
```

#### 21. BigInteger, BigDecimal 클래스
 - 정수형으로는 한계가 있으므로 문자열, n진수, Long타입 숫자 등으로 생성
 - 위와 같은 이유로 문자열이나 byte배열로 변환할 수 있음
 - 기본형으로 변환하는 메소드 있음
 - BigDecimal 의 경우 double 타입으로 생성, 지수 형태로 출력시 오차 발생하거나 상이한 결과 나올 가능성 있음
<br>
<br>
