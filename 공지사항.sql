--공지글관리
create table notice (
id         number  /* PK */,
title      varchar2(300) not null /* 제목 */,
content    varchar2(4000) not null /* 내용 */,
writer     varchar2(100) /* 작성자: 회원의id */,
writedate  date default sysdate /* 작성일자 */ , 
readcnt    number default 0 /*조회수*/,
filename   varchar2(300) /* 첨부파일명 */,
filepath   varchar2(300) /* 저장된첨부파일 */,
root       number,  
step       number default 0,
indent     number default 0,
constraint notice_id_pk primary key(id),
constraint notice_writer_fk foreign key(writer) 
                references member(id) on delete cascade
);

--답글쓰기 처리가 있는 경우
alter table notice add (
root       number,  
step       number default 0,
indent     number default 0
);

alter table notice
add (
filename   varchar2(300) ,
filepath   varchar2(300) 
);


--공지글PK 인 id 컬럼에 적용할 시퀀스
create sequence seq_notice start with 1 increment by 1 nocache;

-- 공지글 테이블의 pk인 id에 시퀀스 자동적용
create or replace trigger trg_notice 
    before insert on notice
    for each row
begin
    select seq_notice.nextval into :new.id from dual;
    if( :new.root is null ) then
        select seq_notice.currval into :new.root from dual;
    end if;
end;
/

select id, name, admin, salt, social from member;
insert into member(id, pw, name, admin)
values( 'admin1', 'manager', '관리자',  'Y');
insert into member(id, pw, name, admin)
values( 'admin2', 'manager', '운영자',  'Y');
commit;

-- 공지글을 임의로 저장해둔다
insert into notice( title, content, writer)
values ('테스트 글', '테스트 글 내용입니다', 'admin1');
insert into notice( title, content, writer)
values ('임의저장 글', '임의로 저장해둔 글 내용입니다', 'admin2');

select n.*, name 
from notice n inner join member m on n.writer = m.id  
order by n.id desc;


insert into notice(title, content, writer)
select title, content, writer from notice;
commit;

select rownum no, n.* from
(select n.*, name 
from notice n inner join member m on n.writer = m.id  
order by n.id) n 
order by no desc
;

select row_number() over(order by n.id) no, n.*, name 
from notice n inner join member m on n.writer = m.id  
order by no desc
;

--순위 rank() over(순위기준) : 1,2,2,2,5,6...
--    dense_rank() over(순위기준) : 1,2,2,2,3,4...
--순서 row_numer() over(순서기준)


select count(*) from notice;

select * from
(select row_number() over(order by n.id) no, n.*, name 
from notice n inner join member m on n.writer = m.id) n
where no between 1015 and 1024
order by no desc
;

select id, writer from notice
;

select count(*) from notice
where writer like '%관리%'
;

select title, id, root, step, indent, writedate
from notice order by id desc;

update notice set root= id;
commit;










