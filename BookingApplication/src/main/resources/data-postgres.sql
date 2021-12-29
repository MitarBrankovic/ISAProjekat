
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
insert into fishing_instructor (id, text, biography, available_from, available_until) values ( 5, 'neki tekst', 'biografija', '2021-12-12 12:00:00', '2021-12-27 12:00:00');
insert into fishing_instructor (id, text, biography, available_from, available_until) values ( 6, 'neki tekst', 'biografija coveka','2021-12-12 12:00:00', '2021-12-27 12:00:00');
insert into admin (id, text) values ( 7, 'neki tekst');


insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list ,cottage_owner_id, rating) values ( nextval('cottage_seq_gen'), 'Zuta vikendica', 'Popovica', 'Wow kako je dobro', 3, 6, 'Nema pravila','10 dinala', 3, 2);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id, rating) values ( nextval('cottage_seq_gen'), 'Crvena vikendica', 'Ledinci', 'Wow wow wow', 4, 4, 'Nema pravila','Parking 100 din', 3, 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id, rating) values ( nextval('cottage_seq_gen'), 'Zelena vikendica', 'Ledinci', 'Wow wow wow', 2, 12, 'Nema pravila','Wifi dan - 100 din', 3, 3.2);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id, rating) values ( nextval('cottage_seq_gen'), 'Plava vikendica', 'Ledinci', 'Opis', 6, 4, 'Nema pravila','Dorucak - 30 din', 3, 4.4);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, cottage_owner_id, rating) values ( nextval('cottage_seq_gen'), 'Bela vikendica', 'Ledinci', 'Najs', 1, 3, 'Nema pravila','Rucak - 70 din', 3, 4.1);

insert into request_delete_acc (id, app_user_id, is_finished, text) values ( nextval('request_seq_gen'), 2, false, 'Tekst zahteva');

insert into complaint (id, text, entity_id, owner, client_id) values ( nextval('complaint_seq_gen'), 'Tekst zalbe', 1, 4, 1);

insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id, rating ) values ( nextval('boat_seq_gen'), 'Titanik', 'Krstarica',120.0, '123DSAWE32', 75, 30.0, 'Gps', 'Bore Tirica 89', 'NAjjaci brod',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4, 3);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id, rating ) values ( nextval('boat_seq_gen'), 'Brod 2', 'Camac',50.0, '213dasdda', 10, 50.0, 'Radio', 'Bore Tirica 90', 'Camac za bleju',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4, 4);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id, rating ) values ( nextval('boat_seq_gen'), 'Plavi brod', 'Jahta',150.0, '9832SDS', 90, 40.0, 'Fishfinder', 'Bore Tirica 91', 'Jahta za bogatu bracu',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4, 2.7);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id, rating ) values ( nextval('boat_seq_gen'), 'Brodic', 'Gliser',70.0, '23121SDC', 30, 60.0, 'Sonar', 'Bore Tirica 92', 'NAjjaci brod',50, 'Da se gliserise', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4, 4.6);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, cancellation_terms, ship_owner_id, rating ) values ( nextval('boat_seq_gen'), 'AC4 brod', 'Gusarski brod',200.0, '2132dSD', 0, 30.0, '', 'Karibi 3', 'NAjjaci brod',50, 'Iz igrice Edward kapetan broda', 'Stap za pecanje', 'votkica - 200 din', 'free_cancellation',4, 4.5);

insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, cottage_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2022-12-20 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 5000, 1, 1);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, cottage_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2022-12-25 12:00:00', 2, 5, 'quick', 'Nema dodatnih informacija', 2500, 2, 1);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, cottage_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2022-12-26 12:00:00', 3, 5, 'quick', 'Nema dodatnih informacija', 2500, 2, null);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, cottage_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2021-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 2500, 1, 2);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, cottage_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2019-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 3200, 1, 1);

insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, boat_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2022-12-20 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 3000, 5, 1);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, boat_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2022-12-28 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 2500, 5, 1);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, boat_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2022-12-28 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 2500, 5, null);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, boat_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2021-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 2500, 1, 1);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, boat_id, client_id) values ( nextval('cottage_appointment_seq_gen'), '2019-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 3200, 1, 1);

insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_per_hour, rating, cancelling_precentage, fishing_instructor_id) values ( nextval('fishing_adventure_seq_gen'), 'Pecanje - camac Romeo', 'Palicka 43', 'Subotica', 'Ne propustite divan dan na ovom skromnom camcu', 'Nesto', 5, 'Nema pravila', 'Stap za pecanje, udica, mamci', 500, 3, 0, 5);
insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_per_hour, rating, cancelling_precentage, fishing_instructor_id) values ( nextval('fishing_adventure_seq_gen'), 'Pecanje - camac Gandalf', 'Strand 2', 'Novi Sad', 'Ne propustite divan dan na ovom carobnom camcu', 'Nesto', 7, 'You shall not pass', 'Stap za pecanje, udica, mamci, pice', 600, 4, 0, 5);
insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_per_hour, rating, cancelling_precentage, fishing_instructor_id) values ( nextval('fishing_adventure_seq_gen'), 'Pecanje - camac Julija', 'Kneza Lazara 22', 'Beograd', 'Ne propustite divan dan na ovom divnom camcu', 'Nesto', 5, 'Nema pravila', 'Stap za pecanje, udica, mamci', 700, 5, 10, 6);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2021-12-20 12:00:00', 'Palicka, 43', 'Subotica', 4, 8, 'regular', true, 0, 'Nema dodatnih informacija', 2500, 1, 1);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2022-12-25 12:00:00', 'Palicka, 43', 'Subotica', 4, 7, 'quick', true, 0, 'Nema dodatnih informacija', 2500, 1, 1);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2021-12-22 12:00:00', 'Palicka, 43', 'Subotica', 4, 6, 'quick', true, 0, 'Nema dodatnih informacija', 2500, 1, null);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2021-12-23 12:00:00', 'Palicka, 43', 'Subotica', 4, 5, 'regular', true, 0, 'Nema dodatnih informacija', 2500, 1, 2);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2019-12-23 12:00:00', 'Palicka, 43', 'Subotica', 4, 5, 'regular', true, 0, 'Nema dodatnih informacija', 3200, 2, 1);

insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 400, 'Ručak', 5);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 200, 'Dodatni mamci', 5);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 1000, 'Profesionalni štap', 5);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 500, 'Ručak', 6);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 250, 'Dodatni mamci', 6);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 1400, 'Profesionalni štap', 6);


insert into subscribe_cottage (id, cottage_id, client_id) values (nextval('subs_cott_seq_gen'), 1, 1);
insert into subscribe_boat (id, boat_id, client_id) values (nextval('subs_boat_seq_gen'), 1, 1);
insert into subscribe_adventure (id, fishing_adventure_id, client_id) values (nextval('subs_adv_seq_gen'), 1, 1);

