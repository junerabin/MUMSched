USE `mumsched`;

insert into user value ("huyle1","1234",1);

INSERT INTO `user_role` VALUES (5,'huyle1','ROLE_STUDENT');

insert into entry value (1,"Oct 2016",100,50,10,"2016-01-01","2016-01-01"); 
insert into entry value (2,"Aug 2016",100,50,10,"2016-05-01","2016-05-01");

insert into student values (1,"student","Anh","Nguyen","1993-08-14","amazingcoder@gmail.com");

insert into block value (1,"Jan","2016-01-01","2016-01-28");
insert into block value (2,"Feb","2016-02-01","2016-02-28");
insert into block value (3,"Mar","2016-03-01","2016-03-28");

insert into entry_block values(1, 1), (1, 2), (1, 3);

insert into course value (1,"420","Computer science", "A");
insert into course value (2,"425","Software engineer", "A");
insert into course value (3,"428","Algorithm","A");

insert into faculty values (1,"faculty","Anh","Nguyen","1993-08-14","amazingcoder@gmail.com","A");

insert into section value (1,"CSE",1,1,1,25);
insert into section value (2,"SE",1,2,1,25);
insert into section value (3,"ALG",1,3,1,25);

insert into section value (4,"CSE1",2,1,1,1);
insert into section value (5,"SE1",2,2,1,25);
insert into section value (6,"ALG1",2,3,1,25);

insert into section value (7,"CSE2",3,1,1,25);
insert into section value (8,"SE2",3,2,1,25);
insert into section value (9,"ALG2",3,3,1,25);

insert into schedule(entry_id, status) values (1, "DRAFT");