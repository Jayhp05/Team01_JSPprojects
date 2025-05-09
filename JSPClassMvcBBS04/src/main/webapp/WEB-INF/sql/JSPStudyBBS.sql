/* 기존의 jspbbs 테이블을 삭제하고 추천, 땡큐를 저장할 컬럼을 추가하여 다시 생성한다.
 **/
 -- 게시글 번호, 제목, 글쓴이, 내용, 날짜, 조회수, 비밀번호, 파일정보, 추천, 땡큐
-- no, title, writer, content, reg_date, read_count, pass, file1, recommend, thank
DROP TABLE jspbbs;
CREATE TABLE jspbbs(
  no NUMBER PRIMARY KEY,
  title VARCHAR2(50 CHAR) NOT NULL,
  writer VARCHAR2(20 CHAR) NOT NULL,
  content VARCHAR2(1000 CHAR) NOT NULL,
  reg_date TIMESTAMP NOT NULL,
  read_count NUMBER(5) NOT NULL,
  pass VARCHAR2(20 CHAR) NOT NULL,
  file1 VARCHAR2(100 CHAR),
  recommend NUMBER DEFAULT 0,
  thank NUMBER DEFAULT 0
);

/* jspbbs에서 사용할 시퀀스
 * 기존의 시퀀스를 삭제하고 다시 생성한다.
 **/
DROP SEQUENCE jspbbs_seq;
CREATE SEQUENCE jspbbs_seq;


-- 페이징 처리와 댓글 처리를 위해서 아래 SQL 쿼리를 COMMIT까지 10번 실행 한다. 
-- 주의 : 199번과 200번 게시글을 댓글(reply) 테이블에서 참조하고 있음
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'공지 사항 입니다.','관리자','안녕하세요' || CHR(13) || CHR(10) || '이번에 어쩌구 저쩌구 해서리...' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '\r\n\r\n이렇게 운영계획과 약관을 바꾸게 되었습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '회원님들의 양해를 구하며 사이트 이용해 참고 하시기 바랍니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10),'2023-04-27 01:44:58',15,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'공지 사항 잘 읽었습니다.','회원1','아~ 관리자님~' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '공지사항 잘 읽었습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그런데 궁금한 것이 있어 게시글 남깁니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '이렇게 저렇게 해서리... 궁금합니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '답변 부탁 드립니다.','2023-04-27 05:43:38',53,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'관심을 가져 주셔서...','관리자','안녕하세요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '관리자 입니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '이번에 변경된 회원 약관에 대해 관심을 가져 주셔서 감사합니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '사이트를 운영하다 보니.. ' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '어쩔수 없이 어쩌구 저쩌구 해서 약관이 변경되었습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 좋은 하루 되시길 바랍니다.','2023-04-27 05:44:32',35,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'저두 궁금했었는데','회원2','안녕하세요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '저두 궁금했었는데...' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '님께서 질문을 해 주셔서 고맙습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '즐공하시고 행복한 하루 되세요..^_^','2023-04-27 05:44:32',103,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'당연히 회원이면 관심을 가져야죠^_^','회원1','안녕하세요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '당연히 회원이니까 약관 변경에 관심을 가져야죠' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '아무튼 오늘도 좋은 하루 보내세요','2023-04-27 05:44:32',42,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'많은 회원님들께서 관심을 가져주셔서 감사합니다.','관리자','안녕하세요' || CHR(13) || CHR(10) || '관리자 입니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '많은 회원분들께서 이번 약관 변경에 관심을 가져 주셔서 정말 고맙습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '앞으로 더욱 발전하는 사이트가 되겠습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 언제나 행복하세요.^_^','2023-04-27 16:30:41',36,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'사이트 발전에 관심이 많은 사람입니다.','회원3','안녕하삼~ 관리자님~' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '사이트 발전에 관심이 많은 사람으로서 지금 보다 나은 사이트를 위해' || CHR(13) || CHR(10) || '운영계획과 약관을 변경하는 것은 잘 된 일이라 생각합니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 수고 많이 하삼~','2023-04-27 05:03:52',82,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'정말 정말 감사합니다.','관리자','안녕하세요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '관리자 입니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '회원님의 많은 관심에 몸둘바를 모르겠습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '언제나 해복하고 건강하시기 바라겠습니다.','2023-04-27 05:44:32',132,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'네 잘 알겠습니다.','회원4','안녕하세요...' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '역쉬~ 관리자님은 부지런 하십니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '하하하~ 사이트가 팍팍 발전해 나가는 것이 보이는 것 같습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '관리자님의 끊임없는 노력에 박수를 보냅니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 수고하세요','2023-04-27 03:37:44',97,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'내용을 줄 바꿈하지 않아서...','관리자','안녕하세요..' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '님께서 작성한 글이 줄 바꿈되지 않아서..' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '테이블이 늘어나내요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || 'ㅋㅋㅋ','2023-04-27 05:04:23',64,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'저두요~','회원5','저두 그게 궁금했는데...' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || 'ㅋㅋㅋ 님께서 대신 해주시네요...^_^','2023-04-27 05:44:32',15,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'당연하죠~','회원6','안녕하세요 관리자님~' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '회원이니까 관심을 갖는건 당연하죠..' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '아무튼 수고가 많으시네요..','2023-04-27 05:44:32',9,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'별 말씀을 다하시네요','회원1','안녕하세요 관리자님~' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '회원이면 당연히... 관심을 가져야져..' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '헤헤헤' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '수고하세요','2023-04-27 05:44:32',79,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'회원이면 당연한 것을...','회원3','ㅋㅋㅋ' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '님들도 다 같은 생각을 가지고 계시군요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '뭐 같은 회원이니...' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '아무튼 모두들 행복하삼~','2023-04-27 05:44:32',38,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'그러게요','회원3','아~ ' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '줄 바꿈 하지 않아도 자동으로 될 줄 알았죠..^_^' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '관리자님이 프로그램 잘 해주셔서... 줄 바꿈 해주삼~' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 이만','2023-04-27 04:59:15',46,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'감사합니다.','관리자','안녕하세요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '답변글 감사합니다.\r\n\r\n\r\n그럼','2023-04-27 03:40:43',16,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'아 줄 바꿈 문제 해결 했습니다.','관리자','안녕하세요 관리자 입니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '먼저 엔터키를 치지않고 글 을 입력하면 줄 바꿈 되지 않는 문제가 발생했는데.. ' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '어제 해결 했습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 모두들 즐공 하삼~' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '','2023-04-27 04:58:45',35,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'저두 궁금했는데','회원7','안녕하세요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '저두 궁금한 점이 해결 되었습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '감사합니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 모두 수고하삼~','2023-04-27 05:44:32',81,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'궁금한게 해결 되었네요','회원8','안녕하세요' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '님 덕분에 궁금한점이 해결 되었습니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '감사합니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '그럼 이만...^_^','2023-04-27 05:50:21',77,'1234',null);
INSERT INTO jspbbs (no,title,writer,content,reg_date,read_count,pass,file1) VALUES (jspbbs_seq.NEXTVAL,'감사합니다.','회원1','제 덕분에 궁금한게 해결 되었다니...' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '다행입니다.' || CHR(13) || CHR(10) || CHR(13) || CHR(10) || '즐공하삼~','2023-04-27 05:44:32',162,'1234',null);
COMMIT;
SELECT * FROM jspbbs order by no desc;


/* 먼저 HR 스키마에 reply 테이블과 reply 테이블에서 사용할 SEQUENCE를 생성한다.
 * 아래의 댓글 데이터를 reply 테이블에 추가한다.
 **/
DROP TABLE reply;
CREATE TABLE reply(
	no NUMBER(7) PRIMARY KEY,
	bbs_no NUMBER(7) NOT NULL,
	reply_content VARCHAR2(500 CHAR),
	reply_writer VARCHAR2(20 CHAR) NOT NULL,
	reg_date TIMESTAMP NOT NULL,	
	CONSTRAINT reply_fk FOREIGN KEY(bbs_no) REFERENCES jspbbs(no)
);

-- 댓글 테이블의 시퀀스
DROP SEQUENCE reply_seq;
CREATE SEQUENCE reply_seq
      MAXVALUE 9999999
      INCREMENT BY 1      
      NOCACHE
      NOORDER
      NOCYCLE;


-- 댓글 데이터 추가하기 - 1번만 실행 한다. --
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '항상 감사합니다.' || CHR(13) || CHR(10) || ' 땡큐!~', 'midas', '2023-05-08 13:44:32');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '저두 감사드립니다.', 'servlet', '2023-05-09 05:24:57');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '저두요~ ^_^', 'midas', '2023-05-09 09:19:23');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '나두 관심이 많은뎅~', 'servlet', '2023-05-09 11:54:45');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '헤헷~ 감사 합니다.', 'admin', '2023-05-09 12:16:51');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '아~ 다들 이 사이트가 잘되길 바라는 군요', 'midas', '2023-05-09 13:34:11');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '관리자님께서 워낙 신경을 쓰시니...' || CHR(13) || CHR(10) || '잘될 겁니다. ', 'servlet', '2023-05-09 14:03:37');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '저두 사이트 발전에 관심이 많습니다.' || CHR(13) || CHR(10) || '감사합니다.' , 'midas', '2023-05-09 14:39:29');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '와~ 대단하네요 우리 사이트~' || CHR(13) || CHR(10) || 'ㅋㅋㅋ~', 'servlet', '2023-05-09 14:41:18');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '우리 관리자님이 워낙 열심이시라~' || CHR(13) || CHR(10) || '그리고 회원님들도 열성으로 사이트에 충성 접속하시니...'|| CHR(13) || CHR(10) ||' 않될 수가 있나요...^_^', 'admin', '2023-05-09 14:52:48');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '네 맞아요~', 'admin', '2023-05-10 15:42:12');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '그렇죠 그렇고 말구요', 'servlet', '2023-05-11 15:44:57');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '항상 감사합니다.', 'midas', '2023-05-15 16:19:23');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '땡큐!~', 'servlet', '2023-05-16 17:31:45');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '저두요~ 땡큐!~', 'servlet', '2023-05-19 18:16:51');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '모두들 열성이네요~' || CHR(13) || CHR(10) || '헤헤~' || CHR(13) || CHR(10) || '땡큐!~', 'admin', '2023-05-20 10:34:11');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '땡큐~ 여기 붙어라~ ^_^', 'midas', '2023-05-20 11:33:27');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 199, '땡큐~ 붙었어요~', 'admin', '2023-05-20 13:19:59');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '당연 회원이면 관심 가져야죠~', 'servlet', '2023-06-10 13:41:17');
INSERT INTO reply(no, bbs_no, reply_content, reply_writer, reg_date) VALUES(reply_seq.NEXTVAL, 200, '동감 합니다.' || CHR(13) || CHR(10) ||' 땡큐!~', 'midas', '2023-06-12 14:52:48');

commit;
SELECT * FROM reply;

select * from reply order by no desc;

update reply set reply_content;


