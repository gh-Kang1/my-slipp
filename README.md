# 자바 웹 어플리케이션 과정 youtube javajigi
1.  연습1 - 동영상을 보면서 전체 과정 성공에 집중
2. 연습2 - 동영상을 보며 각 과정의 중요 부분 메모
  1.
3. 연습3 - 동영상을 보지 않고 메모를 참고해 전체 과정을 진행
4. 연습4 - 메모를 참고하며 추가 학습하고 싶은 내용 한두가지씩 추가 학습후 정리
* 이후 자신이 만족할때까지 연습4 반복
* 최종 메모를 보지 않고 전체를 성공
* 이후 시간 단축 연습
* 이 같은 연습을 익숙해지는 시점까지 매일 일정시간 (매일 1시간 ..) 투자하여 연습 한번에 많이 하기보다 한 반복주기로 자주하는것이 좋다.

---
* 라이브러리 추가시 maven 기준 dependeny 추가하여 jar 파일로 불러오기
  * bootStrap , jquery
  * dependency를 추가하여 라이브러리에 압축 해제된 jar 파일에 경로에 있는 js 라이브러리를 불러와 html에 사용 *jar 경로*
  ~~~
   <link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
  ~~~
* mustache 특정 스프링 부트에서 한글 인코딩 깨지는 이슈
  * propertis
  ~~~
  server.servlet.encoding.force-response=true
  ~~~

* 배포
  * ./mvnw clean package *컴파일된 파일 clasees파일 제거 후 컴파일*
  * clean -> 컴파일된 target에 clasees파일 제거
  * package 컴파일 실행
* 서버에 자바 파일 띄우기
  * java -jar my-slip-0.0.1-SNAPSHOT.jar

* 배포되어있는 프로세스 죽이기
  * ps -ef | grep java -> 서버에 올라가져있는 프로세스중 java 라는 아이디가 있는 프로세스 찾기
  * kill -9 pid -> 찾은 프로세스에 프로세스 아이디를 지정하여 프로세스 끄기

* url이동으로 정적 자료에 위치가 달라질경우 include하고 있는 파일이 상대경로라면 위치가 맞지않아서 파일 못불러올수 있으니 절대경로로 설정하자

* 맥에 host 등록하여 ip로 직접 접속하지 않고 hostName으로 접근하는 방법
* sudo vi /etc/hosts
  * ip주소 [hostName]
  * hostName이 ip주소에 역활을 하여 접속할수있다.
  * host 변경시 도메인 접근도 아이피가 아닌 설정 hostName:port로 접근 가능하다.

* mustache include기능이 jar 파일로 배포했을때 열리지 않는 문제
  * /mvnw spring-boot:run & 메이븐에서 스프링부트를 바로 컴파일에서 여는 문제로 해결

* vi 단축키 참고 블로그: https://blog.outsider.ne.kr/540
* 연습 vimGolf.com 터미널에 설치하여 터미널 내에서 연습 가능

* 로그인
* mustache를 통한 세션 적용 session attribute의 내용을 model에 추가함 
  * spring.mustache.servlet.expose-session-attributes=true
  * spring.mustache.servlet.expose-request-attributes=true <- request attribute에 값을 model에 추가함

* 객체에 데이터에 대한 로직은 객체에게 물어봐야함
  * 객체에 데이터를 꺼내서 확인하는것은 좋지 않음

* 리소스파일 jar 배포시 문제점으로 인한 war파일로 배포하는법
  * packaing war -> 외장 톰캣 dependency 추가 후 외장 톰캣 초기화를 위한 SpringBootServletInitializer 상속받은 클래스 생성
    * return builder.sources(MySlipApplication.class); 서버 시작할때 초기화를 담당하는 클래스명을 지정
~~~
<packaging>war</packaging>
...
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
~~~
