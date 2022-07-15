# ISAProjekat

Student 1: Mitar Branković	
Student 2: Miloš Marković
Student 3: Mihailo Majstorović


Projekat se trenutno nalazi na develop grani repozitorijuma.
Uputstvo za pokretanje projekta:
1. Instalirati Eclipse IDE za enterprise java developere
2. Verzija jave koja je potrebna je _JavaSE-11_
3. Importovati projekat kao maven u eclipse
4. Potrebno je imati Postegre bazu podataka instaliranu i u njoj napraviti bazu koja se zove "ISA_DATABASE"
5. Omogućiti korisniku postgres pristup bazi putem šifre wasd ili u aplication.properties fajlu promeniti spring.datasource.password u željeni. Trenutna sifra je "_wasd_" dok je adresa na _8080_ a ne na default-noj _5432_.
6. Startovati _BookingApplicationMain.java_ kao java aplikaciju (default-ni port je 8090)
7. Otvoriti pretraživač i ukucati ```http://localhost:8090/```
8. Sifre nisu hash-irane tako da se kredencijali za logovanje svake uloge mogu videti u data-postgres.sql skripti koja se nalazi u resources