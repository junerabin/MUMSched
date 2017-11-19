USE `mumsched`;

INSERT INTO `user` VALUES ('admin','123',1),('faculty','123',1),('student','123',1),
						  ('salek','123',1),('snolle','123',1),('lu','123',1),('cor','123',1),
						  ('nai','123',1),('joh','123',1),('jam','123',1),('tom','123',1);

INSERT INTO `user_role` VALUES (1,'admin','ROLE_ADMIN'),(2,'faculty','ROLE_FACULTY'),(3,'student','ROLE_STUDENT'),
								(4,'salek','ROLE_FACULTY'),(5,'snolle','ROLE_FACULTY'),(6,'lu','ROLE_FACULTY'),
								(7,'cor','ROLE_FACULTY'),(8,'nai','ROLE_FACULTY'),(9,'joh','ROLE_FACULTY'),
								(10,'jam','ROLE_FACULTY'),(11,'tom','ROLE_FACULTY');
								
insert into entry values(1, "Aug 2016", 100, 50, 10, "2016-08-15", "2017-02-15"),
						(2, "Oct 2016", 100, 50, 10, "2016-10-15", "2017-04-15");
						
insert into student values (1,"student","Anh","Nguyen","1993-08-14","amazingcoder@gmail.com");

-- block of Aug entry
insert into block values(1, "Aug", "2016-08-15", "2016-09-14"),
						(2, "Sep", "2016-09-15", "2016-10-14"),
						(3, "Oct", "2016-10-15", "2017-11-14"),
						(4, "Nov", "2016-11-15", "2017-12-14"),
						(5, "Dec", "2016-12-15", "2017-01-14"),
						(6, "Jan", "2017-01-15", "2017-02-14");
						
-- block of Oct entry
insert into block values(7, "Feb", "2017-02-15", "2017-03-14"),
						(8, "Mar", "2017-03-15", "2017-04-14");
						
insert into entry_block values(1, 1), (1, 2),(1, 3),(1, 4),(1, 5),(1, 6),
							  (2, 3), (2, 4),(2, 5),(2, 6),(2, 7),(2, 8);
							  
insert into course values(1, "420", "MPP", null),
						 (2, "425", "Software engineer", "A"),
						 (3, "428", "Algorithm", "A, B, C"),
						 (4, "525", "Big Data", "A, C"),
						 (5, "515", "WAA", "C"),
						 (6, "437", "Web Programming", "A, B"),
						 (7, "505", "Enterprise Architect", "E, D"),
						 (8, "448", "FPP", null),
						 (9, "500", "ASD", "A, C"),
						 (10, "510", "Modern Web Programming", null),
						 (11, "520", "Computer Graphic", null);
						 
insert into course_prerequisite values(5, 6), (7, 1), (9, 2);

insert into faculty values(1, "salek", "Payman", "Salek", "1976-10-10", "psalek@mum.edu", "A, B, E"),
						  (2, "snolle", "Steve", "Nolle", "1970-10-10", "snolle@mum.edu", "C, D"),
						  (3, "lu", "Yang", "Lu", "1973-10-10", "ylu@mum.edu", "A, C"),
						  (4, "cor", "Paul", "Corraza", "1971-10-10", "pcor@mum.edu", "A, D"),
						  (5, "nai", "Robert", "Nair", "1968-10-10", "snai@mum.edu", "C, D"),
						  (6, "joh", "Michael", "John", "1972-10-10", "mjoh@mum.edu", "D, E"),
						  (7, "jam", "James", "Thomas", "1965-10-10", "jthom@mum.edu", "C, E"),
						  (8, "tom", "Michael", "Tommy", "1977-10-10", "mtom@mum.edu", "D, A");
						  
insert into faculty_course values(1, 1), (1, 7), (2, 2), (3, 9), (3, 4), (4, 3), (4, 1), (5, 4), (6, 8), (7, 6), (7, 9), (8, 6), (7, 10);