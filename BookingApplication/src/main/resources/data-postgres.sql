
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

insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id ) values ( nextval('boat_seq_gen'), 'Titanik', 'Krstarica',120.0, '123DSAWE32', 75, 30.0, 'Gps', 'Bore Tirica 89', 'NAjjaci brod',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id ) values ( nextval('boat_seq_gen'), 'Brod 2', 'Camac',50.0, '213dasdda', 10, 50.0, 'Radio', 'Bore Tirica 90', 'Camac za bleju',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id ) values ( nextval('boat_seq_gen'), 'Plavi brod', 'Jahta',150.0, '9832SDS', 90, 40.0, 'Fishfinder', 'Bore Tirica 91', 'Jahta za bogatu bracu',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id ) values ( nextval('boat_seq_gen'), 'Brodic', 'Gliser',70.0, '23121SDC', 30, 60.0, 'Sonar', 'Bore Tirica 92', 'NAjjaci brod',50, 'Da se gliserise', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id ) values ( nextval('boat_seq_gen'), 'AC4 brod', 'Gusarski brod',200.0, '2132dSD', 0, 30.0, '', 'Karibi 3', 'NAjjaci brod',50, 'Iz igrice Edward kapetan broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4);






