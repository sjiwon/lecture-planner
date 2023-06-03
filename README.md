# 4학년 DB 프로그래밍 수강신청 웹 애플리케이션 프로젝트

## 요구사항
- PL/SQL 필수

<br>

## 실행 방식
#### 1. git clone https://github.com/sjiwon/lecture-planner.git
#### 2. 로컬상의 Oracle DB 정보 확인
```asciidoc
username=root
password=1234
-> 이 정보가 아니면 application.yml에서 수정 후 실행
```
#### 3. IntelliJ를 통해서 서버 실행 후 `localhost:8080` 접속
- IntelliJ가 아닌 다른 IDE면 실행이 제한될 수 있다
- 이후 재실행 시 `schema.sql` 맨 위에 있는 4개의 DROP 구문 주석 해제 후 실행

<br>

## 프로시저 시나리오
```asciidoc
SET SERVEROUTPUT ON;

> 1. 본인 학년과 맞지 않는 강의는 신청할 수 없습니다.
CALL InsertEnroll('2653B3ABFEF34CBCB1354E969952CD10', '85328680743D42F0AE1DDF7FF0E83AF9');

> 2. 동일 강의를 중복 신청할 수 없습니다.
CALL InsertEnroll('2653B3ABFEF34CBCB1354E969952CD10', 'AC61E77DE0EF4E4E9B84B9B8E346B1EF');

> 3. 이미 등록한 강의와 시간표가 겹칩니다.
CALL InsertEnroll('2653B3ABFEF34CBCB1354E969952CD10', '1F794237441E4117879F21499F0379D7');

> 4. 해당 강의 수강인원이 초과되었습니다.
CALL InsertEnroll('2653B3ABFEF34CBCB1354E969952CD10', '1880AEE1DC764037AA80E5C64F2285E9');

> 5. 수강신청 성공
CALL InsertEnroll('2653B3ABFEF34CBCB1354E969952CD10', '766CE22B3D8147B39142EB23F8527119');

> 6. 최대학점(21학점)을 초과해서 신청할 수 없습니다.
CALL InsertEnroll('2653B3ABFEF34CBCB1354E969952CD10', '5A6EFEE9C2624B8BA6EC68A184353196');
```
