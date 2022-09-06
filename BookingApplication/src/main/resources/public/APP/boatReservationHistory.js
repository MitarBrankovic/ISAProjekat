Vue.component("BoatReservationHistory", {
    data: function() {
        return {
            dateTimeNow: new Date(),
            appointments: "",
            client: "",
        }
    },
    template :`
    <div>
    <div class="container-fluid">
    <h2 style="margin-top: 1%; margin-bottom: 2%; color:#5cb85c">Sve zakazane rezervacije</h2>
    <table class="table">
        <thead>
        <tr>
            <td>Brod</td>
            <td>Adresa</td>
            <td>Datum i vreme početka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Ocena</td>
            <td>Klijent</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="appointment in appointments">
            <td>{{appointment.boat.name}}</td>
            <td>{{appointment.boat.address}} </td>
            <td>{{appointment.appointmentStart.substring(8,10)}}.{{appointment.appointmentStart.substring(5,7)}}.{{appointment.appointmentStart.substring(0,4)}}. {{appointment.appointmentStart.substring(11,13)}}:{{appointment.appointmentStart.substring(14,16)}}</td>
            <td>{{appointment.duration}}</td>
            <td>{{appointment.maxAmountOfPeople}}</td>
            <td>{{appointment.extraNotes}}</td>
            <td>{{appointment.price}} din.</td>
            <td v-if="appointment.rating == 0">-/5</td>
            <td v-else>{{appointment.rating}}/5</td>
            <td>{{appointment.client.name}} {{appointment.client.surname}}</td>
            <td><button type="button" data-bs-toggle="modal" data-bs-target="#newAppointment" v-on:click="fillClientProfile(appointment.client.id)" class="btn btn-success">Profil klijenta</button></td>
            </tr>
        </tbody>
    </table>
    </div>
    
    <div class="modal fade" id="newAppointment" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-l modal-dialog-centered" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLongTitle" style="color:#5cb85c">Profil klijenta - osnovne informacije</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="input-group mb-3">
				  <span class="input-group-text">Ime:</span>
				  <input type="text" class="form-control" disabled v-model="client.name" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				</div>
				<div class="input-group mb-3" style="width:100">
				  <span class="input-group-text">Prezime:</span>
				  <input type="text" class="form-control" disabled v-model="client.surname" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text">E-mail:</span>
				  <input type="text" class="form-control" disabled v-model="client.email" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text">Broj telefona:</span>
				  <input type="text" class="form-control" disabled v-model="client.phoneNumber" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text">Adresa:</span>
				  <input type="text" class="form-control" disabled v-model="client.address" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text">Grad:</span>
				  <input type="text" class="form-control" disabled v-model="client.city" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text">Država:</span>
				  <input type="text" class="form-control" disabled v-model="client.country" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				</div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
            </div>
          </div>
        </div>
      </div>
      
    </div>	  
    	`
    	,
    methods: {
        fillClientProfile(id) {
          axios
            .get("appUser/getUser/" + id)
	        .then(response => (this.client = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
        },
        
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'ship_owner')
            this.$router.push('/')
           console.log(this.dateTimeNow)
         axios
            .get("boatAppointments/getReservationsHistory/" + this.activeUser.id)
	        .then(response => (this.appointments = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
    },

});