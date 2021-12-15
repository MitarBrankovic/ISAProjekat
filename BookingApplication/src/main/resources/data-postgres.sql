
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


insert into cottage (id, name, address, description, rooms_num, beds_num, rules, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Zuta vikendica', 'Popovica', 'Wow kako je dobro', 3, 6, 'Nema pravila', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Crvena vikendica', 'Ledinci', 'Wow wow wow', 4, 4, 'Nema pravila', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Zelena vikendica', 'Ledinci', 'Wow wow wow', 2, 12, 'Nema pravila', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Plava vikendica', 'Ledinci', 'Opis', 6, 4, 'Nema pravila', 3);
insert into cottage (id, name, address, description, rooms_num, beds_num, rules, cottage_owner_id) values ( nextval('cottage_seq_gen'), 'Bela vikendica', 'Ledinci', 'Najs', 1, 3, 'Nema pravila', 3);

insert into request_delete_acc (id, app_user_id, is_finished, text) values ( nextval('request_seq_gen'), 2, false, 'Tekst zahteva');

insert into complaint (id, text, app_user_id, cottage_id) values ( nextval('complaint_seq_gen'), 'Tekst zalbe', 2, 1);

insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_and_info, cancelling_precentage, fishing_instructor_id) values ( nextval('fishing_adventure_seq_gen'), 'Pecanje - camac Romeo', 'Palicka 43', 'Subotica', 'Ne propustite divan dan na ovom skromnom camcu', 'Nesto', 5, 'Nema pravila', 'Stap za pecanje, udica, mamci', 'Cena: 3000, 6 sati', 0, 5);
insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_and_info, cancelling_precentage, fishing_instructor_id) values ( nextval('fishing_adventure_seq_gen'), 'Pecanje - camac Gandalf', 'Strand 2', 'Novi Sad', 'Ne propustite divan dan na ovom carobnom camcu', 'Nesto', 7, 'You shall not pass', 'Stap za pecanje, udica, mamci, pice', 'Cena: 4000, 4 sata', 0, 5);
insert into fishing_adventure (id, name, address, city, description, photo, max_amount_of_people, behaviour_rules, equipment, price_and_info, cancelling_precentage, fishing_instructor_id) values ( nextval('fishing_adventure_seq_gen'), 'Pecanje - camac Romeo', 'Kneza Lazara 22', 'Beograd', 'Ne propustite divan dan na ovom divnom camcu', 'Nesto', 5, 'Nema pravila', 'Stap za pecanje, udica, mamci', 'Cena: 2500, 5 sati', 10, 6);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type,  extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2021-12-20 12:00:00', 'Palicka, 43', 'Subotica', 4, 8, 'regular', 'Nema dodatnih informacija', 2500, 1, 1);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type,  extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2021-12-21 12:00:00', 'Palicka, 43', 'Subotica', 4, 7, 'quick', 'Nema dodatnih informacija', 2500, 1, 1);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type,  extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2021-12-22 12:00:00', 'Palicka, 43', 'Subotica', 4, 6, 'quick', 'Nema dodatnih informacija', 2500, 1, 2);
insert into fishing_appointment (id, appointment_start, address, city, duration, max_amount_of_people, appointment_type,  extra_notes, price, fishing_adventure_id, client_id) values ( nextval('fishing_appointment_seq_gen'), '2021-12-23 12:00:00', 'Palicka, 43', 'Subotica', 4, 5, 'regular', 'Nema dodatnih informacija', 2500, 1, 2);
