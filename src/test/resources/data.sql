insert into person( `id` ,`name`,`age`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) VALUES (1,'A',5,'A',1111,1,1);
insert into person( `id` ,`name`,`age`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) VALUES (2,'B',4,'B',1112,5,5);
insert into person( `id` ,`name`,`age`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) VALUES (3,'C',3,'O',1113,3,3);
insert into person( `id` ,`name`,`age`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) VALUES (4,'D',2,'AB',1114,4,4);
insert into person( `id` ,`name`,`age`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) VALUES (5,'E',1,'O',1115,5,5);

insert into block(`id`,`name`) values (1, 'A');
insert into block(`id`,`name`) values (2, 'C');

update person set block_id = 1 where id = 1;
update person set block_id = 2 where id = 3;