Java의 정석 Chapter 07 요약(14)
---

#### 1. 클래스 간의 관계 설정
 포함관계(멤버변수로 타클래스를 선언하여 사용), 상속관계(extends 이용)  
 ex) 원(circle)은 점(point)이다 - 상속 / 원은 점을 가지고 있다 - 포함
<br>
<br>

#### 2. 단일 상속
 다중 상속 가능(C++), 단일 상속 가능(Java)
<br>
<br>

#### 3. 오버라이딩
 - 메서드명, 매개변수, 반환타입(1.5부터 공변 반환타입 통해 하위 클래스 타입으로는 변경 가능)은 같아야함
 - 접근 제어자는 상위 클래스보다 넓은 범위로 변경 가능
 - 더 많은 예외를 반환할 수 없음(예외의 범위 주의)
 - 인스턴스 메서드 ↔ static 메서드 변경 불가
 - static 메서드는 재정의가 아니라 다중정의만 가능(클래스명으로 구분되어 메모리에 올라감)
 <br>

 #### 4. super
  this 와 마찬가지로 static 메서드 에서는 사용 불가, 인스턴스에서만 가능
<br>
<br>

#### 5. super()
 Object 클래스 외에는 생성자 첫줄에 자동으로 super(); 삽입됨, 명시적으로 호출하여 초기화 필요
<br>
<br>

#### 6. 클래스 패스 지정
 - windows 환경변수 지정 (CLASSPATH)
 - 실행시 임시 지정 (java -cp [루트 디렉토리] [패키지명])
<br>

#### 7. import
 import java.util.* 으로 여러 클래스를 이용할 수 있지만, 하위 패키지의 클래스까지 포함하는 것은 아님  
 import static 하여 특정 클래스의 static 멤버를 클래스명 생략하여 호출 가능
<br>
<br>

#### 8. 제어자
 - 접근 제어자: public, protected, default, private
 - 이외: static, final, abstract, native, transient, synchronized, volatile, strictfp
<br>

#### 9. private 생성자
이슈1) private 생성자, public static getInstance() 활용 사례
 - 해당 클래스는 extends 대상이 될 수 없음(상속불가), final 붙여서 상속불가함을 표시
 - 상수와 static 메소드로 구성된 클래스(ex Math)는 인스턴스 생성할 필요가 없으므로 private 지정
<br>

#### 10. 업캐스팅, 다운캐스팅
이슈2) 하위 클래스 타입의 참조변수로는 상위 클래스 타입의 인스턴스 참조 불가! (형변환 연산자 이용해도 안됨)
```
class Tv{
    boolean power;
    int channel;
}
class CaptionTv extends Tv{
    String text;
}

Tv t1 = new CaptionTv(); //업캐스팅, 하위 클래스의 멤버변수 참조시에는 에러 발생
CaptionTv c = new Tv();  //다운캐스팅, 컴파일에러

CaptionTv c = (CaptionTv)new Tv(); //런타임 에러 (실행시 ClassCastException 발생)
CaptionTv c = (CaptionTv)t1;       //본래 하위 클래스로 생성된 인스턴스만을 다운캐스팅하여 참조 가능
```
<br>

#### 11. instanceof
 참조변수 instanceof 클래스명, 상속 받은 상위 클래스도 true 반환(형변환 가능하다고 볼 수 있음)
<br>
<br>

#### 12. Vector
 콜렉션 프레임워크(ex ArrayList)가 있으므로 Vector 는 사용하지 않도록함
 - 동적으로 크기가 관리되는 Object 객체 배열 (add, remove, get...)
 - 하나의 스레드만 해당 인스턴스에 접근 가능(Synchronize, Thread Safe)
 - 최대 인덱스를 초과할 경우 현재 배열 크기의 100% 증가
<br>

#### 13. interface
  - 다중상속 가능, 추상 클래스와 달리 추상 메서드와 상수만 정의 가능
  - 인터페이스를 구현한(implements) 클래스의 인스턴스를, 인터페이스로 업캐스팅하여 리턴타입으로 활용할 수 있음
  - 1.8부터 default, static 메서드도 추가 가능, default 메서드는 구현부{} 필수, 단 메서드명 중복 가능성이 있음 주의
<br>

#### 14. 익명 클래스
 - 단 한번만 사용 가능, 하나의 객체만을 생성함
 - 생성자 없음
 - 상속+인터페이스 구현 or 인터페이스1+인터페이스2 구현 불가 (상속 또는 인터페이스 1개 구현만 가능)
 - 해당 클래스의 외부 클래스명$숫자.class 파일 생성됨
 ```
 //new [클래스명|인터페이스명]() {}
 Object oj = new Object() { void anonymousMethod(){} };
 ```
 <br>
