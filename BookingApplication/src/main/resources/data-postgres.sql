
INSERT INTO ROLES (id, name) VALUES (1, 'USER');
INSERT INTO ROLES (id, name) VALUES (2, 'ADMIN');
INSERT INTO ROLES (id, name) VALUES (3, 'BOATOWNER');
INSERT INTO ROLES (id, name) VALUES (4, 'COTTAGEOWNER');
INSERT INTO ROLES (id, name) VALUES (5, 'CLIENT');
INSERT INTO ROLES (id, name) VALUES (6, 'FISHINGINSTRUCTOR');

insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, ROLES_id, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Mirko', 'Mrkva', 'mrki@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'client', true, 5, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, ROLES_id, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Dare', 'Dare', 'dare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'client', true, 5, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, ROLES_id, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Kale', 'Kale', 'kale@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'cottage_owner', true, 4, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, ROLES_id, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Rale', 'Rale', 'rale@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'ship_owner', true, 3, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, ROLES_id, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Zare', 'Zare', 'zare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'fishing_instructor', true, 6, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, ROLES_id, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Mare', 'Mare', 'mare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'fishing_instructor', true, 6, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, ROLES_id, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Admin', 'Admin', 'admin@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '1212312', 'admin', true, 2, 0, 'regular');

/*insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Mirko', 'Mrkva', 'mrki@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'client', true, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Dare', 'Dare', 'dare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'client', true, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Kale', 'Kale', 'kale@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'cottage_owner', true, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Rale', 'Rale', 'rale@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'ship_owner', true, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Zare', 'Zare', 'zare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'fishing_instructor', true, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Mare', 'Mare', 'mare@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '12312312', 'fishing_instructor', true, 0, 'regular');
insert into app_user (id, name, surname, email, password, address, city, country, phone_number, role, verified, loyalty_points, loyalty_status) values ( nextval('my_user_seq'), 'Admin', 'Admin', 'admin@gmail.com', '111','Palih studenata 2', 'Novi Sad', 'Serbia', '1212312', 'admin', true, 0, 'regular');
*/

insert into client (id, text, penalties) values ( 1, 'neki tekst', 0);
insert into client (id, text, penalties) values ( 2, 'neki tekst', 0);
insert into cottage_owner (id, cottage_text) values ( 3, 'neki tekst');
insert into ship_owner (id, text) values ( 4, 'neki tekst');
insert into fishing_instructor (id, text, biography, available_from, available_until) values ( 5, 'neki tekst', 'biografija', '2021-12-12 08:00:00', '2022-04-27 20:00:00');
insert into fishing_instructor (id, text, biography, available_from, available_until) values ( 6, 'neki tekst', 'biografija coveka','2021-12-12 08:00:00', '2022-04-27 20:00:00');
insert into admin (id, text, admin_type) values ( 7, 'neki tekst', 'main');


insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, price_per_hour, cottage_owner_id, rating, max_amount_of_people) values ( nextval('cottage_seq_gen'), 'Zuta vikendica', 'Popovica', 'Wow kako je dobro', 3, 6, 'Nema pravila','10 dinala', 500, 3, 2, 6);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, price_per_hour, cottage_owner_id, rating, max_amount_of_people) values ( nextval('cottage_seq_gen'), 'Crvena vikendica', 'Ledinci', 'Wow wow wow', 4, 4, 'Nema pravila','Parking 100 din', 450, 3, 3, 11);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, price_per_hour, cottage_owner_id, rating, max_amount_of_people) values ( nextval('cottage_seq_gen'), 'Zelena vikendica', 'Ledinci', 'Wow wow wow', 2, 12, 'Nema pravila','Wifi dan - 100 din', 600, 3, 3.2, 12);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, price_per_hour, cottage_owner_id, rating, max_amount_of_people) values ( nextval('cottage_seq_gen'), 'Plava vikendica', 'Ledinci', 'Opis', 6, 4, 'Nema pravila','Dorucak - 30 din', 400, 3, 4.4, 5);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, price_list, price_per_hour, cottage_owner_id, rating, max_amount_of_people) values ( nextval('cottage_seq_gen'), 'Bela vikendica', 'Ledinci', 'Najs', 1, 3, 'Nema pravila','Rucak - 70 din', 300, 3, 4.1, 8);

insert into request_delete_acc (id, app_user_id, is_finished, text) values ( nextval('request_seq_gen'), 2, false, 'Tekst zahteva');

insert into complaint (id, text, entity_id, owner, client_id) values ( nextval('complaint_seq_gen'), 'Tekst zalbe', 1, 4, 1);

insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, price_per_hour, cancellation_terms, ship_owner_id, rating, max_amount_of_people ) values ( nextval('boat_seq_gen'), 'Titanik', 'Krstarica',120.0, '123DSAWE32', 75, 30.0, 'Gps', 'Bore Tirica 89', 'NAjjaci brod',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 300, 'free_cancellation',4, 3, 11);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, price_per_hour, cancellation_terms, ship_owner_id, rating, max_amount_of_people ) values ( nextval('boat_seq_gen'), 'Brod 2', 'Camac',50.0, '213dasdda', 10, 50.0, 'Radio', 'Bore Tirica 90', 'Camac za bleju',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 410, 'free_cancellation',4, 4, 5);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, price_per_hour, cancellation_terms, ship_owner_id, rating, max_amount_of_people ) values ( nextval('boat_seq_gen'), 'Plavi brod', 'Jahta',150.0, '9832SDS', 90, 40.0, 'Fishfinder', 'Bore Tirica 91', 'Jahta za bogatu bracu',50, 'Ne skaci sa broda', 'Stap za pecanje', 'votkica - 200 din', 330, 'free_cancellation',4, 2.7, 8);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, price_per_hour, cancellation_terms, ship_owner_id, rating, max_amount_of_people ) values ( nextval('boat_seq_gen'), 'Brodic', 'Gliser',70.0, '23121SDC', 30, 60.0, 'Sonar', 'Bore Tirica 92', 'NAjjaci brod',50, 'Da se gliserise', 'Stap za pecanje', 'votkica - 200 din', 300, 'free_cancellation',4, 4.6, 9);
insert into boat (id, name, boat_type, length, engine_number, engine_power,max_speed, navigation_equipment, address, description, capacity, rules, fishing_equipment, price_list, price_per_hour, cancellation_terms, ship_owner_id, rating, max_amount_of_people ) values ( nextval('boat_seq_gen'), 'AC4 brod', 'Gusarski brod',200.0, '2132dSD', 0, 30.0, '', 'Karibi 3', 'NAjjaci brod',50, 'Iz igrice Edward kapetan broda', 'Stap za pecanje', 'votkica - 200 din', 220, 'free_cancellation',4, 4.5, 12);

insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, cottage_id, client_id, version) values ( nextval('cottage_appointment_seq_gen'), '2022-01-10 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 5000, 1000, 4000, 1, 1, 0);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, cottage_id, client_id, version) values ( nextval('cottage_appointment_seq_gen'), '2022-12-25 12:00:00', 2, 5, 'quick', 'Nema dodatnih informacija', 2500, 500, 2000, 2, 1, 0);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, cottage_id, client_id, version) values ( nextval('cottage_appointment_seq_gen'), '2022-12-26 12:00:00', 3, 5, 'quick', 'Nema dodatnih informacija', 2500, 500, 2000, 2, null, 0);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, cottage_id, client_id, version) values ( nextval('cottage_appointment_seq_gen'), '2021-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 2500, 500, 2000, 1, 2, 0);
insert into cottage_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, cottage_id, client_id, version) values ( nextval('cottage_appointment_seq_gen'), '2019-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 3200, 640, 2560, 1, 1, 0);

insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, boat_id, client_id, version) values ( nextval('boat_appointment_seq_gen'), '2022-12-20 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 3000, 600, 2400, 5, 1, 0);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, boat_id, client_id, version) values ( nextval('boat_appointment_seq_gen'), '2022-12-28 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 2500, 500, 2000, 5, 1, 0);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, boat_id, client_id, version) values ( nextval('boat_appointment_seq_gen'), '2022-12-28 12:00:00', 4, 5, 'quick', 'Nema dodatnih informacija', 2500, 500, 2000, 5, null, 0);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, boat_id, client_id, version) values ( nextval('boat_appointment_seq_gen'), '2021-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 2500, 500, 2000, 1, 1, 0);
insert into boat_appointment (id, appointment_start, duration, max_amount_of_people, appointment_type,  extra_notes, price, owner_profit, system_profit, boat_id, client_id, version) values ( nextval('boat_appointment_seq_gen'), '2019-12-28 12:00:00', 4, 5, 'regular', 'Nema dodatnih informacija', 3200, 640, 2560, 1, 1, 0);

insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_per_hour, rating, cancelling_precentage, fishing_instructor_id) values ( nextval('fishingadventure_seq_gen'), 'Pecanje - camac Romeo', 'Palicka 43', 'Subotica', 'Ne propustite divan dan na ovom skromnom camcu', 'Kul', 5, 'Nema pravila', 'Stap za pecanje, udica, mamci', 500, 3, 0, 5);
insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_per_hour, rating, cancelling_precentage, fishing_instructor_id) values ( nextval('fishingadventure_seq_gen'), 'Pecanje - camac Gandalf', 'Strand 2', 'Novi Sad', 'Ne propustite divan dan na ovom carobnom camcu', 'Kul', 7, 'You shall not pass', 'Stap za pecanje, udica, mamci, pice', 600, 4, 0, 5);
insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_per_hour, rating, cancelling_precentage, fishing_instructor_id) values ( nextval('fishingadventure_seq_gen'), 'Pecanje - camac Julija', 'Kneza Lazara 22', 'Beograd', 'Ne propustite divan dan na ovom divnom camcu', 'Kul', 5, 'Nema pravila', 'Stap za pecanje, udica, mamci', 700, 5, 10, 6);

insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, instructor_profit, system_profit, fishing_adventure_id, client_id, version) values ( nextval('fishing_appointment_seq_gen'), '2022-01-04 12:00:00', 'Palicka, 43', 'Subotica', 4, 8, 'regular', true, 0, 'Nema dodatnih informacija', 2500, 500, 2000, 1, 1, 0);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, instructor_profit, system_profit, fishing_adventure_id, client_id, version) values ( nextval('fishing_appointment_seq_gen'), '2022-12-25 12:00:00', 'Palicka, 43', 'Subotica', 4, 7, 'quick', true, 0, 'Nema dodatnih informacija', 2500, 500, 2000, 1, 1, 0);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, instructor_profit, system_profit, fishing_adventure_id, client_id, version) values ( nextval('fishing_appointment_seq_gen'), '2021-12-22 12:00:00', 'Palicka, 43', 'Subotica', 4, 6, 'quick', true, 0, 'Nema dodatnih informacija', 2500, 500, 2000, 1, null, 0);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, instructor_profit, system_profit, fishing_adventure_id, client_id, version) values ( nextval('fishing_appointment_seq_gen'), '2021-12-23 12:00:00', 'Palicka, 43', 'Subotica', 4, 5, 'regular', true, 0, 'Nema dodatnih informacija', 2500, 500, 2000, 1, 2, 0);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, instructor_profit, system_profit, fishing_adventure_id, client_id, version) values ( nextval('fishing_appointment_seq_gen'), '2019-12-23 12:00:00', 'Palicka, 43', 'Subotica', 4, 5, 'regular', true, 0, 'Nema dodatnih informacija', 3200, 640, 2560, 2, 1, 0);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type, available, rating, extra_notes, price, instructor_profit, system_profit, fishing_adventure_id, client_id, version) values ( nextval('fishing_appointment_seq_gen'), '2022-01-09 12:00:00', 'Palicka, 43', 'Subotica', 96, 7, 'quick', true, 0, 'Nema dodatnih informacija', 2500, 500, 2000, 1, 1, 0);


insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 400, 'Ručak', 5);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 200, 'Dodatni mamci', 5);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 1000, 'Profesionalni štap', 5);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 500, 'Ručak', 6);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 250, 'Dodatni mamci', 6);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 1400, 'Profesionalni štap', 6);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 650, 'Rucak', 3);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 1200, 'Stoni tenis', 3);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 550, 'Rucak', 4);
insert into pricelist_item (id, price, description, app_user_id) values ( nextval('pricelist_seq_gen'), 1500, 'Oprema za pecanje', 4);


insert into subscribe_cottage (id, cottage_id, client_id) values (nextval('subs_cott_seq_gen'), 1, 1);
insert into subscribe_boat (id, boat_id, client_id) values (nextval('subs_boat_seq_gen'), 1, 1);
insert into subscribe_adventure (id, fishing_adventure_id, client_id) values (nextval('subs_adv_seq_gen'), 1, 1);

insert into boat_appointment_report (id, comment, is_approved, rating_option, appointment_id, client_id, owner_id) values (1, 'tekst', true, 'penalty', 2, 1, 4);
insert into fishing_appointment_report (id, comment, is_approved, rating_option, appointment_id, client_id, owner_id) values (1, 'tekst', true, 'penalty', 4, 1, 5);
/*tinsert into fishing_appointment_report (id, comment, is_approved, rating_option, appointment_id, client_id, owner_id) values (2, 'tekst', true, 'penalty', 4, 1, 5);*/
insert into loyalty_program (id, bronze_points, silver_points, gold_points, bronze_client, silver_client, gold_client, bronze_precentage, silver_precentage, gold_precentage, bronze_discount, silver_discount, gold_discount) values ( nextval('loyalty_seq_gen'), 2000, 4000, 6000, 400, 300, 200, 84, 88, 92, 5, 10, 15);


