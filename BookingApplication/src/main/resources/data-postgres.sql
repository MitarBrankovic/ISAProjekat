
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified) values ( nextval('my_user_seq'), 'Mirko', 'Mrkva', 'mrki@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'client', true);
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified) values ( nextval('my_user_seq'), 'Dare', 'Dare', 'dare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'client', true);
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified) values ( nextval('my_user_seq'), 'Kale', 'Kale', 'kale@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'cottage_owner', true);
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified) values ( nextval('my_user_seq'), 'Rale', 'Rale', 'rale@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'ship_owner', true);
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified) values ( nextval('my_user_seq'), 'Zare', 'Zare', 'zare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'fishing_instructor', true);
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified) values ( nextval('my_user_seq'), 'Mare', 'Mare', 'mare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'fishing_instructor', true);
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified) values ( nextval('my_user_seq'), 'Admin', 'Admin', 'admin@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '1212312', 'admin', true);


insert into client (id, text) values ( 1, 'neki tekst');
insert into client (id, text) values ( 2, 'neki tekst');
insert into cottage_owner (id, cottage_text) values ( 3, 'neki tekst');
insert into ship_owner (id, text) values ( 4, 'neki tekst');
insert into fishing_instructor (id, text) values ( 5, 'neki tekst');
insert into fishing_instructor (id, text) values ( 6, 'neki tekst');
insert into admin (id, text) values ( 7, 'neki tekst');


insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list ,cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Zuta vikendica', 'Popovica', 'Wow kako je dobro', 3, 6, 'Nema pravila','10 dinala', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Crvena vikendica', 'Ledinci', 'Wow wow wow', 4, 4, 'Nema pravila','Parking 100 din', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Zelena vikendica', 'Ledinci', 'Wow wow wow', 2, 12, 'Nema pravila','Wifi dan - 100 din', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Plava vikendica', 'Ledinci', 'Opis', 6, 4, 'Nema pravila','Dorucak - 30 din', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Bela vikendica', 'Ledinci', 'Najs', 1, 3, 'Nema pravila','Rucak - 70 din', 3);


insert into request_delete_acc (id, app_user_id, is_finished, text) values ( nextval('request_seq_gen'), 2, false, 'Tekst zahteva');

insert into complaint (id, text, app_user_id, cottage_id) values ( nextval('complaint_seq_gen'), 'Tekst zalbe', 2, 1);