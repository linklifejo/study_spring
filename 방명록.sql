--방명록관리
create table board(
id          number constraint board_id_pk primary key,
title       varchar2(300) not null,
content     varchar2(4000) not null,
writer      varchar2(50) not null 
            constraint board_writer_fk references member(id) 
                 on delete cascade,
writedate   date default sysdate,
readcnt     number default 0
);

create sequence seq_board start with 1 increment by 1 nocache;

create or replace trigger trg_board
    before insert on board
    for each row
begin
    select seq_board.nextval into :new.id from dual;
end;
/

--방명록 첨부파일 관리
create table board_file(
id            number constraint board_file_id_pk primary key,
filename      varchar2(300) not null,
filepath      varchar2(300) not null,
board_id      number constraint board_file_fk 
                            references board(id) on delete cascade
);

create sequence seq_board_file start with 1 increment by 1 nocache;
create or replace trigger trg_board_file
    before insert on board_file
    for each row
begin
    select seq_board_file.nextval into :new.id from dual;
end;
/

select * from
(select b.*, name, row_number() over(order by b.id) no  
from board b inner join member m on b.writer = m.id ) b
where no between -9 and 1
order by no desc;

select * from board;
select * from board_file;



