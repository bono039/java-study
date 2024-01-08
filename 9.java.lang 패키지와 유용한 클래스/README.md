| created    | title                  | author         | category |
|------------|------------------------|-----------------|----------|
| 2024-01-05 | java.lang 패키지와 유용한 클래스 | 한의정  | JAVA     |


<br/>

## java.lang 패키지

----------------------------------------------------------

### Object 클래스

- 모든 클래스의 최고 조상이므로 모든 클래스에서 바로 사용 가능
![img.png](img.png)

#### **equals()**
  - 두 객체의 같고 다름을 참조값으로 비교
```
p1.equals(p2) → id == ((Person)obj).id → true 
```
<br/>

#### **clone()**
	- 자신을 복제해 새 인스턴스 생성
    - 인스턴스 변수의 값만을 복사. 참조 타입(배열, 인스턴스)은 제대로 복제 X
    - 사용 방법
    1. 복제할 클래스가 Cloneable 인터페이스 구현해야 함
    2. clone() 오버라이딩하면서 접근 제어자를 public으로 변경
    3. 조상 클래스의 clone()을 호출하는 코드가 포함된 try-catch문 작성 
```
class Point implements Cloneable {		// 1) Cloneable 인터페이스 구현
	...
	public Object clone() {			// 2) 접근 제어자를 protected에서 public으로 변경
		Object obj = null;
		
		try {
			obj = super.clone();	// 3) try-catch 내에서 조상 클래스의 clone() 호출
		} catch(CloneNotSupportedException e) {}
		return obj;
	}
}
```

<br/>

#### 공변 반환타입 (covariant return type)
> 오버라이딩 시 조상 메소드의 반환타입을 자손 클래스의 타입으로 변경을 허용하는 것


  :  배열 뿐만 아니라 java.util 패키지의 Vector, ArrayList, LinkedList, HashSet 등도 복제 가능
```
ArrayList list = new ArrayList<>();
ArrayList list2 = (ArrayList)list.clone();
```

<details>
<summary>clone() 예제</summary>

	int[] arr = {1, 2, 3, 4, 5};
	int[] arrClone = arr.clone();
<br/>

    int[] a rr = {1,2,3,4,5};
	int[] arrClone = new int[arr.length];		// 배열 생성하고
	System.arraycopy(arr, 0, arrClone, arr.length);	// 내용 복사

</details>

<br/>

#### 얕은 복사와 깊은 복사
  
      얕은 복사 : 객체에 저장된 값 그대로 복제할 뿐, 참조 객체까지는 복제 X
      깊은 복사 : 원본이 참조하고 있는 객체까지 복사


[<img width="400" height="200" src="https://velog.velcdn.com/images%2Fksung1889%2Fpost%2F4b1040a4-e7ff-4348-8411-cf7dc8616558%2Fimage.png">](https://velog.io/@ksung1889/%EC%96%95%EC%9D%80%EB%B3%B5%EC%82%AC-%EA%B9%8A%EC%9D%80-%EB%B3%B5%EC%82%AC)


```
// 얕은 복사
public Circle shallowCopy() {
  Obj obj = null;
  
  try {
    obj = super.clone();
  } catch(CloneNotSupportedException e) {}
  
  return (Circle)obj;
}

// 깊은 복사 = 얕은 복사 + 원본이 참조하고 있는 객체까지 복사
Circle c = (Circle)obj;
c.p = new Point(this.p.x, this.p.y);
```

<br/>

#### getClass()

<br/>

### String 클래스
- 변경 불가
- '+' 연산자 사용해 문자열 결합하는 것은 새 String 인스턴스 생성하는 것 (but 비추. StringBuffer 클래스 추천)
- equals() vs == : 두 문자열 내용 비교 / String 인스턴스의 주소 비교




| 용어         | 설명                      |
|------------|-------------------------|
| **컴파일 에러** | 컴파일 시 발생하는 에러           |
| **런타임 에러** | 실행 시 발생하는 에러            |
| **논리적 에러** | 실행은 되지만, 의도와 다르게 동작하는 것 |

- **에러** : 프로그램 코드에 의해 수습할 수 없는 심각한 오류 (메모리 부족, 스택오버플로우)
- **예외** : 프로그램 코드에 의해 수습할 수 있는 다소 미약한 오류

<br/>

#### ◾ 예외 클래스의 계층구조
<img src="img.png" width="450" height="450">

- **Exception 클래스들**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : 외적 요인에 의해 발생하는 예외 (사용자 실수 - 클래스명 잘못 적음, 데이터 형식 잘못 됨)
- **RuntimeException 클래스들** : 프로그래머의 실수로 발생하는 예외 (null, 형변환, 0으로 나누기 등)

<br/>

#### ◾ 예외 처리하기 - try-catch문
- 정의 : 프로그램 실행 시 발생 가능한 예외 발생에 대비한 코드 작성하는 것
- 목적 : 프로그램의 비정상 종료 막고, 정상적 실행상태 유지

```
try {
    // 예외 발생할 가능성이 있는 문장들 넣음
} catch(Exception e1) {
    // Exception1이 발생했을 경우, 이를 처리하기 위한 문장 적음
} catch(Exception e2) {
    // Exception2이 발생했을 경우, 이를 처리하기 위한 문장 적음
} catch(Exception eN) {
    // ExceptionN이 발생했을 경우, 이를 처리하기 위한 문장 적음
}
```

<br/>



---
### 🔗 출처 및 참고 자료
- [Java의 정석 3판](https://www.yes24.com/Product/Goods/24259565)
- [예외 클래스 이미지 출처](https://tcpschool.com/java/java_exception_class)
- [TCP SCHOOL](https://www.tcpschool.com/java/java_exception_throw)