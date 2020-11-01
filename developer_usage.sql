drop table userreg; 
create table userreg(
	name varchar(40),
	pass varchar(40),
    primary key (name)
);
create table userreg(
	name varchar(40),
	pass varchar(40),
    primary key (name)
);
drop table testrec;
create table testrec(
	sid int auto_increment primary key ,
    sname varchar(20),
    smarks int(3),
    tname varchar(20),
    foreign key (tname) references userreg(name)
);
insert into testrec(sname,smarks,tname) values('someone',50,'nitish');
insert into testrec(sname,smarks,tname) values('someone1',50,'nitish1');
select * from testrec;
update testrec set sname='xd',smarks=30 where sid=11;
delete from testrec where sid=8;
select * from userreg;
insert into userreg values('nitish','hello');
insert into userreg values('nitish1','hello');
update userreg set createdby="Admin" where name="nitish1";
update userreg set createdby="Admin" where name="nitish";
select * from userreg where name='nitish' and pass='hello';
create database User;
use User;  
alter table userreg add createdby varchar(40);
delete from userreg where createdby is null;
ALTER TABLE userreg modify createdby varchar(40) NOT NULL;