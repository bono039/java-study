---
created: 2023-03-19
title: 네트워킹_박은주
author: pej
category: study
tag: study
aliases: []
---

#### 책 목차
- 네트워킹(Networking)
- 소켓 프로그래밍

#### 학습할 것
- InetAddress, URLConnection
- TCP, UDP

#### 학습 내용
1. 네트워킹(Networking)
	+ [InetAddress](https://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html)
		+ IP주소를 다루기 위한 클래스
		```
		InetAddress ip = null;
		
		try {
		    ip = InetAddress.getByName("www.naver.com");
		    // IP주소를 바이트배열로 반환
		    logger.debug("getAddress : {}", ip.getAddress()); 
		    // IP주소를 반환
		    logger.debug("getHostAddress : {}", ip.getHostAddress()); 
		    // FQDN(Fully Qualified Domain Name)을 반환
		    logger.debug("getCanonicalHostName : {}", ip.getCanonicalHostName()); 
		} catch (UnknownHostException e) {
		    logger.error("에러 : {}", e.getMessage());
		}
		
		InetAddress[] ipArr = null;
		
		try {
		    // 호스트명으로 모든  IP를 찾을 수 있음
		    ipArr = InetAddress.getAllByName("www.naver.com");
		    
		    for (InetAddress item : ipArr) {
		        logger.debug(item); 
		    }
		    
		} catch (UnknownHostException e) {
		    logger.error("에러 : {}", e.getMessage());
		}
		[실행결과]
		getAddress : [-33, -126, -61, -56]
		getHostAddress : 223.130.195.200
		getCanonicalHostName : 223.130.195.200
		www.naver.com/223.130.195.200
		www.naver.com/223.130.200.107
		```
	+ URL 
		+ URL을 다루기 위한 클래스
	+ [URLConnection](https://docs.oracle.com/javase/8/docs/api/java/net/URLConnection.html)
		+ URL간의 통신연결을 나타내는 최상위 추상 클래스
		```
		String addr = "https://www.naver.com";

		URL url = null;
		
		BufferedReader bf = null;
		   
		try {
		    url = new URL(addr);
		    
		    bf = new BufferedReader(new InputStreamReader(url.openStream()));
		
		    // 한 줄씩 데이터 읽어서 출력
		    bf.lines().forEach(System.out::println);
		    
		    bf.close();
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
		```

> FQDN vs PQDN
> + FQDN(Fully Qualified Domain Name)
> 	+ 전체 주소 도메인 네임, 절대 주소 도메인 네임
> 	+ **호스트 이름과 도메인 이름을 포함한 전체 도메인을 의미함**
> 	+ 예) `www.naver.com`
> + PQDN(Partially Qualified Domain Name)
> 	+ 상대 도메인 네임
> 	+ **호스트 이름만 의미함**
> 	+ 예) `naver.com`

> URI vs URL
> + URI(Uniform Resource Identifier)
> 	+ 통합 자원 식별자를 의미하면 **웹에서 자원을 식벽하는 고유한 문자열 시퀀스**
> + URL(Uniform Resource Location)
> 	+ 인터넷상에 존재하는 자원에 접근 할 수 있는 주소, 흔히 웹 주소라고 부름
> 	+ URI의 하위
> 	+ `프로토콜://호스트명::포트번호/경로명/파일명?query` 이런 형태로 이루어져 있음

2. 소켓 프로그래밍
	+ 소켓(Socket)
		+ 통신의 양 끝단을 의미
		+ **양방향 통신 가능**
	+ TCP 소켓
		+ clinet와 server간의 **1:1 통신**(예: 전화)
		+ 데이터의 전송순서가 보장되며 수신여부를 확인함
		+ `Socket`
			+ **프로세스간의 통신(입출력)을 담당**, InputStream과 OutputStream을 가지고 있음
		+ `ServerSocket`
			+ **외부의 연결요청을 기다리는 것을 담당**, 연결이 되면 `Socket`을 생성하여 프로세스간의 통신이 이루어지도록 함
	+ UDP 소켓
		+ 1:1, 1:N, N:N 통신(예: 소포)
		+ 데이터의 전송순서를 보장하지 않고 수신여부를 확인하지 않음
		+ `DatagramPacket`
			+ **데이터간의 통신을 담당**
		+ `DatagramSocket`
			+ **데이터 전송을 담당**
		+ `MulticastSocket`
			+ **다중 프로세스간의 통신을 담당**
			
> HTTP 통신 vs 소켓 통신
> + HTTP 통신
> 	+ client 요청이 있을때만 server가 응답하는 **단방향 통신**
> 	+ **응답을 받고나면 연결이 종료됨**
> 	+ 예) 포털 사이트 검색
> + 소켓 통신
> 	+ client와 server가 실시간으로 데이터를 주고 받는 **양방향 통신**
> 	+ 지정된 포트로 **서로 연결**되어 있음
> 	+ 예) 실시간 스트리밍

#### 출처(참고문헌)
- Java의 정석
- https://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html
- https://docs.oracle.com/javase/8/docs/api/java/net/URLConnection.html
- https://www.charlezz.com/?p=44767
- https://nearhome.tistory.com/104
- https://www.sslcert.co.kr/guides/kb/51
- https://woodforest.tistory.com/800

#### 각주