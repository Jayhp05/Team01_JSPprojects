/* 먼저 HR 스키마에 member 테이블 생성한다.
 * 아래의 회원 데이터를 member 테이블에 추가한다.
 **/
DROP TABLE member;
CREATE TABLE member(
	id VARCHAR2(20) PRIMARY KEY,
	name VARCHAR2(5 CHAR) NOT NULL,
	pass VARCHAR2(15) NOT NULL,
	email VARCHAR2(30) NOT NULL,
	mobile VARCHAR2(13) NOT NULL,
	zipcode VARCHAR2(5) NOT NULL,
	address1 VARCHAR2(60 CHAR) NOT NULL,
	address2 VARCHAR2(40 CHAR) NOT NULL,
	phone VARCHAR2(13),
	email_get VARCHAR2(1),
	reg_date TIMESTAMP NOT NULL
);

INSERT INTO member VALUES('midas', '홍길동', '1234', 
	'midastop@naver.com', '010-1234-5678', '14409', 
	'경기 부천시 오정구 수주로 18 (고강동, 동문미도아파트)', '미도아파트 1동 513호', 
	'032-1234-5678', '1', '2023-06-06 12:10:30');
INSERT INTO member VALUES('admin', '이순신', '1234', 
	'midastop1@naver.com', '010-4321-8765', '08292', 
	'서울 구로구 구로중앙로34길 33-4(구로동, 영림빌딩)', '경영기술개발원 교육센터 1층 교무실', 
	'02-5678-4325', '0', '2023-05-11 11:20:50');
INSERT INTO member VALUES('servlet', '강감찬', '1234', 
	'midas@daum.net', '010-5687-5678', '06043', 
	'서울 강남구 강남대로146길 28 (논현동, 논현아파트)', '논현신동아파밀리에아파트 103동 302호', 
	'02-5326-5678', '1', '2023-06-05 12:10:30');

commit;
SELECT * FROM member;

show parameters nls_date_format;
alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS';


