Vue.component("FishingInstructorsReports", {
    data: function() {
        return {
            appointments: "",
            client: "",
            report: {type: "", comment: "", appointmentId: "", ownerId: "", clientId: ""},
        }
    },
    template :`
    <div>
    <div class="container-fluid">
    <h2 style="margin-top: 1%; margin-bottom: 2%; color:#5cb85c">Završene rezervacije</h2>
    <table class="table">
        <thead>
        <tr>
            <td>Avantura</td>
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
            <td>{{appointment.fishingAdventure.name}}</td>
            <td>{{appointment.fishingAdventure.address}}, {{appointment.fishingAdventure.city}} </td>
            <td>{{appointment.appointmentStart.substring(8,10)}}.{{appointment.appointmentStart.substring(5,7)}}.{{appointment.appointmentStart.substring(0,4)}}. {{appointment.appointmentStart.substring(11,13)}}:{{appointment.appointmentStart.substring(14,16)}}</td>
            <td>{{appointment.duration}} h</td>
            <td>{{appointment.maxAmountOfPeople}}</td>
            <td>{{appointment.extraNotes}}</td>
            <td>{{appointment.price}} din.</td>
            <td v-if="appointment.rating == 0">-/5</td>
            <td v-else>{{appointment.rating}}/5</td>
            <td>{{appointment.client.name}} {{appointment.client.surname}}</td>
            <td><button type="button" data-bs-toggle="modal" data-bs-target="#newAppointment" v-on:click="rememberAppointment(appointment)" class="btn btn-success">Ostavi izveštaj</button></td>
            </tr>
        </tbody>
    </table>
    </div>
    
    <div class="modal fade" id="newAppointment" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" style="color:#5cb85c" id="exampleModalLabel">Izveštaj o klijentu</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
	        </button>
	      </div>
	      <div class="modal-body">
	        <span class="input-group-text" style="margin-top: 2%;" id="basic-addon1">Tip izveštaja:</span>
            <select class="form-select" style="margin-bottom: 3%" v-model="report.type" aria-label="Example select with button addon">
                  <option value="positive">Pozitivna kritika</option>
                  <option value="penalty">Negativna kritika - zahtev za penal</option>
                  <option value="didnt_show">Klijent se nije pojavio</option>
            </select>
	        <span class="input-group-text" style="margin-top: 1%;">Komentar:</span>
            <textarea class="form-control" v-model="report.comment" rows="8" placeholder="Unesite komentar o klijentu..."></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-success" data-bs-dismiss="modal" v-on:click="sendReport()">Pošalji izveštaj</button>
	        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Otkaži</button>
	      </div>
	    </div>
	  </div>
	</div>
      
    </div>	  
    	`
    	,
    methods: {
        sendReport() {
        if (this.validateReport()) {
       	  console.log(this.report)
          axios
          .post("reports/sendFishingReport/",  this.report)
          .then(response => {
          	  this.appointments = response.data
	          Swal.fire({ icon: 'success', title: 'Izveštaj uspešno poslat !', showConfirmButton: false, timer: 2000 })
	          this.report.type = "";
	          this.report.comment = "";
          })
        }
        },
        
        refreshAppointments() {
        	axios
            .get("fishingAppointments/getReservationsForReports/" + this.activeUser.id)
	        .then(response => (this.appointments = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
	        	
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
        },
        
        rememberAppointment(app) {
          this.report.appointmentId = app.id;
          this.report.clientId = app.client.id;
          this.report.ownerId = app.fishingAdventure.fishingInstructor.id;
        },
        
        validateReport() {
        	if (this.report.type == "" || this.report.comment == "") {
        		Swal.fire({icon: 'error', title: 'Morate popuniti sva potrebna polja !'})
        		return false;
        	}
        	return true;
        },
        
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'fishing_instructor')
            this.$router.push('/')
         axios
            .get("fishingAppointments/getReservationsForReports/" + this.activeUser.id)
	        .then(response => (this.appointments = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
    },

});