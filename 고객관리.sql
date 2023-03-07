--고객관리
create table customer (
id        number,   /*식별자PK*/
name      varchar2(50) not null, /*고객명*/
gender    varchar2(3) default '남' not null, /*성별:남/여*/
email     varchar2(50), /*이메일*/
phone     varchar2(13),  /*전화번호*/
constraint customer_id_pk primary key(id)
);

-- 고객관리 테이블의 PK(id 컬럼)에 지정한 시퀀스
create sequence seq_customer 
start with 1 increment by 1 nocache;

-- 고객관리 테이블의 PK인 id컬럼에 시퀀스 자동적용할 트리거
create or replace trigger trg_customer
    before insert on customer
    for each row
begin
    select seq_customer.nextval into :new.id from dual;
end;
/


-- 데이터삽입해보기
insert into customer ( name, email, phone, gender)
values ( '심청', 'sim@naver.com', '062-9854-2123', '여');
insert into customer ( name, email, phone)
values ( '박문수', 'park@naver.com', '010-5412-3654');
insert into customer ( name, email, phone)
values ( '나그네', 'test@naver.com', '02-8456-2122');

commit;

--고객정보 확인
select * from customer;

/*
DML : insert/update/delete + commit/rollback
DDL : auto commit : create, alter, rename 
DCL : auto commit : grant, revoke


*/









