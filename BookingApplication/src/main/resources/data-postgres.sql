insert into proba(id, name) values ( nextval('my_user_seq'), 'Mirko');

insert into app_user (id, name, surname, email, password, address, city, country, role, first_login) values ( nextval('my_user_seq'), 'Mirko', 'Mrkva', 'mrki@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', 0, true);