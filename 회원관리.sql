--id 컬럼에 PK 지정
alter table member 
add constraint member_id_pk primary key(id);

-- pk, name 컬럼에 not null 지정
alter table member
modify( pw not null, name not null );

-- salt 컬럼 추가
alter table member add ( salt varchar2(300) );
-- pw 데이터타입 사이즈 변경
alter table member modify( pw varchar2(300) );

-- email 컬럼 추가
alter table member add ( email varchar2(50) );

update member set email = 'ojink2@naver.com';
commit;

-- 성별(gender), 소셜구분(social:N/K), admin(관리자여부:Y/N) 컬럼 추가
alter table member add
(
gender varchar2(3) default '남' not null,
social varchar2(1),
admin varchar2(1) default 'N' not null
);

alter table member add
(
post varchar2(5),
birth date
);
alter table member modify(id varchar2(100));


--소셜로그인시 비번정보를 받아올수 없으므로 null을 허용해야 함
alter table member modify ( pw null );

update member set profile=null
where profile like 'My%';
commit;

select id, name, profile from member ;


