---
created: 2023-03-07
title: 입출력_박은주
author: pej
category: study
tag: study
aliases: []
---

#### 책 목차
- 자바에서의 입출력
- 바이트기반 스트림
- 바이트기반의 보조스트림
- 문자기반 스트림
- 문자기반의 보조스트림
- 표준입출력과 File
- 직렬화

#### 학습할 것
- 바이트기반 스트림과 보조스트림
- 문자기반 스트림과 보조스트림
- 직렬화

#### 학습 내용
1. 자바의 입출력
	+ 스트림(Stream) 기반의 I/O
		+ 스트림(Stream)[^1] : **데이터의 연결 통로**
		+ **단방향통신만 가능, 따라서 입력 스트림, 출력 스트림 2개가 필요함**
		+ 큐(Queue)처럼 먼저 보낸 데이터를 먼저 받게되는 FIFO 구조
		+ java.io 패키지에서 사용함
	+ 버퍼(Buffer) 기반의 I/O
		+ 버퍼(Buffer) : **데이터를 일시적으로 저장하기 위한 메모리**
		+ 실제 데이터를 주고받는 스트림이 아니어서 **직접 입출력 할 수 없음**
		+ 스트림의 기능을 향상시키거나 다른 기능을 추가 할 수 있음
		+ 기반 스트림을 먼저 생성한 다음에 이를 이용해서 보조 스트림을 생성해야 하며 단순히 기반 스트림의 메소드를 그대로 호출함
		+ 기반스트림의 close(), flush()를 호출 할 필요없이 보조 스트림에 있는걸로 호출하기만 하면 됨
		+ FilterInputStream, FilterOutputStream은 모든 보조 스트림의 조상 
	+ 채널(Channel) 기반의 I/O
		+ **양방향 입출력이 가능**
		+ 항상 **버퍼를 이용**해서 입출력을 함
		+ java.nio 패키지에서 사용함

> java.nio
> + JDK 1.4부터 추가된 패키지로, New Input Output이라는 의미
> + JDK 1.7부터 NIO2 API가 추가되었음

2. 바이트 기반 스트림과 보조 스트림
	+ [InputStream](https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html), [OutputStream](https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html)
		+ **모든 바이트기반의 스트림의 조상이며 추상 클래스**
		+ java.io 패키지에 있음
		+ 주요 메서드
			+ read() : 데이터(바이트) 읽기
			+ write() : 데이터(바이트) 쓰기 
			+ close() : 스트림 닫기
				+ JVM에서 자동적으로 닫아 주기는 하지만 모든 작업을 마친 후에 반드시close() 호출하는 것이 좋으며 메모리를 사용하는 스트림과 표준 입출력 스트림은 닫아 주지 않아도 됨
	+ 특징
		+ 1Byte로 처리함
		+ byte 배열을 이용
	
	![바이트기반정리](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbF0tA2%2FbtqX1oIEkAO%2FyTdgOxzIyFmYkaBWiSUWoK%2Fimg.png)

3. 문자 기반 스트림과 보조 스트림
	+ [Reader](https://docs.oracle.com/javase/7/docs/api/java/io/Reader.html), [Writer](https://docs.oracle.com/javase/7/docs/api/java/io/Writer.html)
		+ **모든 문자 스트림의 조상이며 추상 클래스**
		+ java.io 패키지에 있음
		+ 주요 메서드는 InputStream, OutputStream 클래스와 동일함
	+ 특징
		+ **2Byte로 처리를 하며 유니코드간의 변환을 자동적으로 처리해 줌**
		+ char 배열을 이용
		+ 바이트기반의 스트림으로 2Byte인 데이터를 처리하는데 어려움이 있어서 이를 보완하기 위해서 제공되었으며 문자 데이터를 이용할 때 사용

	![문자기반스트림정리](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FnFpQp%2FbtqXZJftANn%2FYP2X08Fj8BMqzZaw4Hg6ik%2Fimg.png)
4. 표준 입출력
	+ **콘솔창을 통한 데이터 입출력**을 의미함
	+ 자바 어플리케이션 실행과 동시에 자동적으로 생성됨
	+ 종류
		+ `System.out`, `System.in`, `System.err`
	+ System 클래스
		+ final 클래스로 상속 및 인스턴스를 만들 수 없음
		+ 소스상에서는 `BufferedInputStream`, `BufferedOutputStream`을 이용하고 있음
	+ 표준 입출력의 대상변경
		+ setIn() : 표준 입력 스트림을 지정한 대상으로 재할당함
		+ setOut() : 표준 출력 스트림을 지정한 대상으로 재할당함
		+ setErr() : 표준 오류 출력 스트림을 지정한 대상으로 재할당함
	```
	File file = new File(FILE_PATH, "error.txt");

	try (FileOutputStream fileOut = new FileOutputStream(file);
	     PrintStream print = new PrintStream(fileOut)) {
	    
	    // 출력 대상을 파일로 변경
	    System.setErr(print);
	    
	    System.out.println("System.out");
	    System.err.println("System.err");
	} catch (IOException e) {
	    logger.error("에러 : {}", e.getMessage());
	}
	[실행결과]
	System.out
	```
4. 직렬화(Serialization), 역직렬화(Deserialization)
	+ 직렬화 
		+ 객체에 저장된 데이터를 스트림에서 입출력하기 위해서 **연속적인 데이터로 변환하는 것**
		+ JVM 메모리에 있는 객체 데이터를 바이트 형태로 변환하는 것
	+ 역직렬화
		+ 스트림에서 데이터를 읽어서 객체를 만드는 것
		+ 바이트 형태의 데이터를 객체로 변환하여 JVM 메모리에 넣는 것
	+ ObjectInputStream, ObjectOutputStream
		+ 보조 스트림으로 이것만 가지고 직렬화/역직렬화 할 수는 없으며 각각 InputStream과 OutputStream을 상속받음
		+ ObjectInputStream : 역직렬화시 사용
		+ ObjectOutputStream : 직렬화시 사용

#### 출처(참고문헌)
- Java의 정석
- https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html
- https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html
- https://docs.oracle.com/javase/7/docs/api/java/io/Reader.html
- https://docs.oracle.com/javase/7/docs/api/java/io/Writer.html
- https://techblog.woowahan.com/2550/
- https://codingnotes.tistory.com/216
- https://dev-coco.tistory.com/42
- https://brunch.co.kr/@myner/47

#### 각주
[^1]: 챕터 14 람다에서 나오는 스트림과는 다른 의미




